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

/**
 * Renders a list of cards in a given Deck and allows the user to add new ones
 */
public class DeckActivity extends Activity {
    private int deckId = -1;
    private final String TAG = "org.walkingarchive.app.DeckActivity";
    private List<DeckCard> cardListItems = new ArrayList<DeckCard>();
    private ArrayAdapter<DeckCard> cardListAdapter;
    public static final int FOR_CARD_LIST = 3;

    /**
     * Render the interface.
     * <p>
     * The deck's JSON must be passed as a string Extra named "deckJson"
     * </p>
     */
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

    /**
     * Starts an asynchronous call to create a new Deck on the server
     */
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
                catch (JSONException e) { }
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

    /**
     * Starts an asynchronous API call to add a Card to this Deck
     * <p>
     * This will create the deck if needed, or wait for an existing deck-creation call to return.
     * </p>
     */
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

    /**
     * Create the initial list of Cards from a given chunk of JSON for the Deck.
     */
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
    
    /**
     * This launches the Card Search interface to add a new Card to the deck
     * @param v
     */
    public void addCard (View v) {
        //go to search page
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forwardResult", true);
        this.startActivityForResult(intent, FOR_CARD_LIST);
    }
    
    /**
     * Callback for when a child Activity finishes.
     * <p>
     * This is guaranteed to be the Search activity returning a card to add to the Deck
     * </p>
     */
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
