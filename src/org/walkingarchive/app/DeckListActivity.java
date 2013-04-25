package org.walkingarchive.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.api.WalkingArchiveApi;
import org.walkingarchive.app.ui.DeckListResult;
import org.walkingarchive.app.ui.TradeHistoryResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeckListActivity extends Activity {

    Handler marshaller = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck_list);

        ListView deckListView = (ListView) findViewById(R.id.deckListView);
        deckListView.setOnItemClickListener(
                new OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
                    {
                        DeckListActivity.this.onItemClick(adapter, view, position, id);
                    }
                });
        
        AsyncTaskCallback callback = new AsyncTaskCallback()
        {    
            public void run(Object o)
            {
                onDecksReturned((String) o);
            }
        };
        
        WalkingArchiveApi api = new WalkingArchiveApi();
        // TODO: Make this not ghetto
        api.getDecksAsync(3, callback);
        
        // TODO: Show a busy screen here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deck_list, menu);
        return true;
    }
    
    public void goToNewDeck (View v) {
        Intent intent = new Intent(this, DeckActivity.class);
        this.startActivity(intent);
    }
    
    public void onDecksReturned(final String json) {
        final DeckListActivity thisRef = this;
        
        // This marshals the UI update code back to the UI thread,
        // because you're not allowed to update the UI from any other thread
        marshaller.post(new Runnable() {
            public void run() {
                ArrayList<DeckListResult> results = new ArrayList<DeckListResult>();
                try {
                    JSONArray array = new JSONArray(json);
                    for(int i = 0; i < array.length(); i++) {
                        JSONObject deck = array.getJSONObject(i);
                        DeckListResult deckResult = new DeckListResult(deck);
                        results.add(deckResult);
                    }
                }
                catch (JSONException e) {
                    // hmm.
                }
                
                ListView resultsListView = (ListView) findViewById(R.id.deckListView);
                resultsListView.setAdapter(new ArrayAdapter<DeckListResult>(thisRef, android.R.layout.simple_list_item_1, results));
            }
        });
    }

    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            DeckListResult result = (DeckListResult) adapter.getItemAtPosition(position);
            Intent deckIntent = new Intent(this, DeckActivity.class);
            deckIntent.putExtra("deckJson", result.toJson());
            startActivity(deckIntent);
    }
}
