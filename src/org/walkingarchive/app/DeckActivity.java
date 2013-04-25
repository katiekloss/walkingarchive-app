package org.walkingarchive.app;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.api.WalkingArchiveApi;
import org.walkingarchive.app.ui.DeckCard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class DeckActivity extends Activity {
    private int deckId = -1;
    private final String TAG = "org.walkingarchive.app.DeckActivity";
    private List<DeckCard> cardListItems = new ArrayList<DeckCard>();
    private ArrayAdapter<DeckCard> cardListAdapter;
    public static final int FOR_CARD_LIST = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck);
        
        if(getIntent().hasExtra("deckJson")) {
            loadDeck();

            JSONObject json;
            try {
                json = new JSONObject(getIntent().getStringExtra("deckJson"));
                EditText titleText = (EditText) findViewById(R.id.deckNameTitle);
                titleText.setText(json.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else {
            createDeck();
        }
        
        ListView cardList = (ListView) findViewById(R.id.deckView);
        
        cardListAdapter = new ArrayAdapter<DeckCard>(DeckActivity.this, 
                android.R.layout.simple_list_item_1, 
                cardListItems);
        cardList.setAdapter(cardListAdapter);
        
        DataSetObserver deckUpdater = new DataSetObserver() {
            public void onChanged() {
                onCardListUpdate();
            }
        };
        
        cardListAdapter.registerDataSetObserver(deckUpdater);
        
        cardList.setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                        DeckCard result = (DeckCard) adapter.getItemAtPosition(position);
                        Intent cardViewerIntent = new Intent(DeckActivity.this, CardViewerActivity.class);
                        cardViewerIntent.putExtra("cardJson", result.toJson());
                        DeckActivity.this.startActivity(cardViewerIntent);
                    }
                });
    }

    private void createDeck() {
        deckId = 0;
        final WalkingArchiveApi api = new WalkingArchiveApi();
        final AsyncTaskCallback callback = new AsyncTaskCallback()
        {
            public void run(Object o)
            {
                String response = (String) o;
                try
                {
                    JSONObject json = new JSONObject(response);
                    deckId = json.getInt("id");
                    onCardListUpdate();
                }
                catch (JSONException e) { } // like i give a f**k.
            }
        };
        
        // TODO: replace user id
        
        AlertDialog alert = new AlertDialog.Builder(DeckActivity.this).create();
        
        alert.setTitle("Deck Name");
        alert.setMessage("Name your deck.");
        final EditText input = new EditText(DeckActivity.this);
        input.setText("New Deck");
        alert.setView(input);
        
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Editable inputText = input.getText();
                EditText titleText = (EditText) findViewById(R.id.deckNameTitle);
                titleText.setText(inputText.toString());
                api.createDeckAsync(3, inputText.toString(), callback);
             }
        });
        
        alert.show();
        
    }

    protected void onCardListUpdate() {
        // New deck and update already posted
        if (deckId == 0) return;
        
        WalkingArchiveApi api = new WalkingArchiveApi();
        
        //no trade yet, make one
        if (deckId == -1) {
            createDeck();
            return;
        }
        
        AsyncTaskCallback callback = new AsyncTaskCallback() {
            public void run(Object o) {
                //do callback here
            }
        };
        
        try {
            EditText nameText = (EditText) findViewById(R.id.deckNameTitle);
            List<Integer> cards = new ArrayList<Integer>();
            for (DeckCard card : cardListItems) {
                if (card != null) {
                    cards.add(new JSONObject(card.toJson()).getInt("id"));
                }
            }
            
            api.updateDeckAsync(deckId, nameText.getText().toString(), cards, callback);
        }
        catch (JSONException e) {
            //swallow
        }
    }

    private void loadDeck() {
        try {
            JSONObject json = new JSONObject(getIntent().getStringExtra("deckJson"));
            deckId = json.getInt("id");
            JSONArray cards = json.getJSONArray("collection");
            for (int i = 0; i < cards.length(); i++) {
                if (cards.get(i) != null && cards.get(i) != "null") {
                    DeckCard card = new DeckCard(cards.getJSONObject(i));
                    cardListItems.add(card);
                }
            }

        }
        catch (JSONException e) {
            //TODO - handle exception
            Log.e(TAG, "Error parsing json in loading deck");
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deck, menu);
        return true;
    }
    
    public void addCard (View v) {
        //go to search page
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forwardResult", true);
        this.startActivityForResult(intent, FOR_CARD_LIST);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        JSONObject cardJson;
        try {
            cardJson = new JSONObject(data.getStringExtra("card"));

            if (cardJson != null) {
                cardListItems.add(new DeckCard(cardJson));
                cardListAdapter.notifyDataSetChanged();
            }
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
