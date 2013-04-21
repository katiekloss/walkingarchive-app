package org.walkingarchive.app;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.api.WalkingArchiveApi;
import org.walkingarchive.app.ui.TradeHistoryResult;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TradeHistoryActivity extends Activity {

    Handler marshaller = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade_history);
        
        ListView historyListView = (ListView) findViewById(R.id.tradeResultsListView);
        historyListView.setOnItemClickListener(
                new OnItemClickListener()
                {
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
                    {
                        TradeHistoryActivity.this.onItemClick(adapter, view, position, id);
                    }
                });
        
        AsyncTaskCallback callback = new AsyncTaskCallback()
        {    
            public void run(Object o)
            {
                onTradeHistoryReturned((String) o);    
            }
        };
        
        WalkingArchiveApi api = new WalkingArchiveApi();
        // TODO: Make this not ghetto
        api.getTradeHistoryAsync(3, callback);
        
        // TODO: Show a busy screen here
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trade_history, menu);
        return true;
    }

    public void goToNewTrade (View v) {
        Intent intent = new Intent(this, TradeActivity.class);
        this.startActivity(intent);
    }

    public void onTradeHistoryReturned(final String json)
    {
        final TradeHistoryActivity thisRef = this;
        
        // This marshals the UI update code back to the UI thread,
        // because you're not allowed to update the UI from any other thread
        marshaller.post(new Runnable()
        {
            public void run()
            {
                ArrayList<TradeHistoryResult> results = new ArrayList<TradeHistoryResult>();
                try
                {
                    JSONArray array = new JSONArray(json);
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject trade = array.getJSONObject(i);
                        TradeHistoryResult historyResult = new TradeHistoryResult(trade);
                        results.add(historyResult);
                    }
                }
                catch (JSONException e)
                {
                    // hmm.
                }
                
                ListView resultsListView = (ListView) findViewById(R.id.tradeResultsListView);
                resultsListView.setAdapter(new ArrayAdapter<TradeHistoryResult>(thisRef, android.R.layout.simple_list_item_1, results));
            }
        });
    }
    
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
    {
            TradeHistoryResult result = (TradeHistoryResult) adapter.getItemAtPosition(position);
            Intent tradeIntent = new Intent(this, TradeActivity.class);
            tradeIntent.putExtra("tradeJson", result.toJson());
            startActivity(tradeIntent);
    }
}