package org.walkingarchive.app;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.api.WalkingArchiveApi;
import org.walkingarchive.app.ui.TradeCard;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Displays and updates a new or existing Trade
 */
public class TradeActivity extends Activity {
    
    public static final int FOR_GIVING_LIST = 1;
    public static final int FOR_RECEIVING_LIST = 2;
    
    private int tradeId = -1;
    
    ArrayList<TradeCard> crListItems=new ArrayList<TradeCard>();
    ArrayList<TradeCard> cgListItems=new ArrayList<TradeCard>();
    TradeCardAdapter crListAdapter;
    TradeCardAdapter cgListAdapter;
    
    /**
     * Renders the initial Trade. A new Trade will be created, unless an existing trade is passed in a string Extra named tradeJson.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade);
        
        if(getIntent().hasExtra("tradeJson"))
            loadTrade();
        else
            createTrade();
        
        ListView crList = (ListView) findViewById(R.id.cardsReceivingList);
        ListView cgList = (ListView) findViewById(R.id.cardsGivingList);

        TextView cgValue = (TextView) findViewById(R.id.cardsGivingValue);
        TextView crValue = (TextView) findViewById(R.id.cardsReceivingValue);
        
        cgValue.setText(getTotal(cgListItems));
        crValue.setText(getTotal(crListItems));

        crListAdapter = new TradeCardAdapter(this, crListItems);
        crList.setAdapter(crListAdapter);
        
        cgListAdapter = new TradeCardAdapter(this, cgListItems);
        cgList.setAdapter(cgListAdapter);
        
        DataSetObserver tradeUpdater = new DataSetObserver()
        {
            public void onChanged()
            {
                onCardListUpdate();
            }
        };
        
        crListAdapter.registerDataSetObserver(tradeUpdater);
        cgListAdapter.registerDataSetObserver(tradeUpdater);
        
        cgList.setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                        TradeCard result = (TradeCard) adapter.getItemAtPosition(position);
                        Intent cardViewerIntent = new Intent(TradeActivity.this, CardViewerActivity.class);
                        cardViewerIntent.putExtra("cardJson", result.toJson());
                        TradeActivity.this.startActivity(cardViewerIntent);
                    }
                });
        
        crList.setOnItemClickListener(
                new OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                        TradeCard result = (TradeCard) adapter.getItemAtPosition(position);
                        Intent cardViewerIntent = new Intent(TradeActivity.this, CardViewerActivity.class);
                        cardViewerIntent.putExtra("cardJson", result.toJson());
                        TradeActivity.this.startActivity(cardViewerIntent);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trade, menu);
        return true;
    }
    
    /**
     * Starts the Search flow with the intent of adding the result to the receiving list.
     */
    public void addToReceivingList (View v)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forwardResult", true);
        this.startActivityForResult(intent, FOR_RECEIVING_LIST);
    }
    
    /**
     * Starts the Search flow with the intent of adding the result to the giving list.
     */
    public void addToGivingList (View v)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forwardResult", true);
        this.startActivityForResult(intent, FOR_GIVING_LIST);
    }

    /**
     * Callback executed when a SearchResult to be added is chosen.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        TextView cgValue = (TextView) findViewById(R.id.cardsGivingValue);
        TextView crValue = (TextView) findViewById(R.id.cardsReceivingValue);
        JSONObject cardJson;
        try
        {
            cardJson = new JSONObject(data.getStringExtra("card"));
        
            switch(requestCode)
            {
                case FOR_GIVING_LIST:
                    cgListItems.add(new TradeCard(cardJson));
                    cgListAdapter.notifyDataSetChanged();
                    
                    cgValue.setText(getTotal(cgListItems));
                    crValue.setText(getTotal(crListItems));
                    break;
                case FOR_RECEIVING_LIST:
                    crListItems.add(new TradeCard(cardJson));
                    crListAdapter.notifyDataSetChanged();
                    
                    cgValue.setText(getTotal(cgListItems));
                    crValue.setText(getTotal(crListItems));
                    break;
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
    
    /**
     * Starts the async call to create a new Trade, unless one is already pending.
     */
    private void createTrade()
    {
        tradeId = 0;
        WalkingArchiveApi api = new WalkingArchiveApi();
        AsyncTaskCallback callback = new AsyncTaskCallback()
        {
            public void run(Object o)
            {
                String response = (String) o;
                try
                {
                    JSONObject json = new JSONObject(response);
                    tradeId = json.getInt("id");
                    onCardListUpdate();
                }
                catch (JSONException e) { }
            }
        };
        
        api.createTradeAsync(3, callback);
    }
    
    /**
     * Loads an existing Trade from a JSON-formatted string Extra named "tradeJson"
     */
    private void loadTrade()
    {
        try
        {
            JSONObject json = new JSONObject(getIntent().getStringExtra("tradeJson"));
            tradeId = json.getInt("id");
            JSONArray givingCards = json.getJSONArray("givingCards");
            for(int i = 0; i < givingCards.length(); i++)
            {
                TradeCard card = new TradeCard(givingCards.getJSONObject(i));
                cgListItems.add(card);
            }
            
            JSONArray receivingCards = json.getJSONArray("receivingCards");
            for(int i = 0; i < receivingCards.length(); i++)
            {
                TradeCard card = new TradeCard(receivingCards.getJSONObject(i));
                crListItems.add(card);
            }
        }
        catch (JSONException e) { }
    }
    
    /**
     * Updates the trade in the background, creating a new one if needed.
     */
    private void onCardListUpdate()
    {
        // New trade + update already posted
        if(tradeId == 0) return;
        
        WalkingArchiveApi api = new WalkingArchiveApi();
        
        // We need to make a new trade before updating it
        if(tradeId == -1)
        {
            createTrade();
            return;
        }
        
        AsyncTaskCallback callback = new AsyncTaskCallback()
        {
            public void run(Object o)
            {
            }
        };
        
        try
        {
            ArrayList<Integer> giving = new ArrayList<Integer>();
            for(TradeCard card : cgListItems)
            {
                giving.add(new JSONObject(card.toJson()).getInt("id"));
            }
            
            ArrayList<Integer> receiving = new ArrayList<Integer>();
            for(TradeCard card : crListItems)
            {
                receiving.add(new JSONObject(card.toJson()).getInt("id"));
            }
            
            api.updateTradeAsync(tradeId, giving, receiving, callback);
        }
        catch (JSONException e) { }
    }
    
    /**
     * Calculates the total worth of a List<TradeCard>
     * @param cards  The List of TradeCard objects to calculate the total value of.
     * @return  A String containing the pretty-formatted dollar amount.
     */
    private String getTotal(ArrayList<TradeCard> cards) {
        Double result = 0.0;
        for (TradeCard c : cards) {
            try {
                JSONArray prices = new JSONObject(c.toJson()).getJSONArray("prices");
                if(prices.length() > 0) {
                    Double min = Double.MAX_VALUE;
                    Double max = Double.MIN_VALUE;
                    
                    for(int i = 0; i < prices.length(); i++) {
                        JSONObject price = prices.getJSONObject(i);
                        Double value = (Double) price.get("price");
                        if(value < min) min = value;
                        if(value > max) max = value;
                    }
                    
                    Double average = (min + max)/2;
                    average = Math.round(average * 100) / 100d;
                    
                    result += average;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return NumberFormat.getCurrencyInstance().format(result);
    }
}
