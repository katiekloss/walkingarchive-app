package org.walkingarchive.app;

import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TradeActivity extends Activity {
    
    public static final int FOR_GIVING_LIST = 1;
    public static final int FOR_RECEIVING_LIST = 2;
    
    private int tradeId = -1;
    
    ArrayList<TradeCard> crListItems=new ArrayList<TradeCard>();
    ArrayList<TradeCard> cgListItems=new ArrayList<TradeCard>();
    ArrayAdapter<TradeCard> crListAdapter;
    ArrayAdapter<TradeCard> cgListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade);
        
        createTrade();
        
        ListView crList = (ListView) findViewById(R.id.cardsReceivingList);
        ListView cgList = (ListView) findViewById(R.id.cardsGivingList);

        crListAdapter = new ArrayAdapter<TradeCard>(this,
                android.R.layout.simple_list_item_1,
                crListItems);
        crList.setAdapter(crListAdapter);
        
        cgListAdapter = new ArrayAdapter<TradeCard>(this,
                android.R.layout.simple_list_item_1,
                cgListItems);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trade, menu);
        return true;
    }
    
    public void addToReceivingList (View v)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forwardResult", true);
        this.startActivityForResult(intent, FOR_RECEIVING_LIST);
    }
    
    public void addToGivingList (View v)
    {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forwardResult", true);
        this.startActivityForResult(intent, FOR_GIVING_LIST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        JSONObject cardJson;
        try
        {
            cardJson = new JSONObject(data.getStringExtra("card"));
        
            switch(requestCode)
            {
                case FOR_GIVING_LIST:
                    cgListItems.add(new TradeCard(cardJson));
                    cgListAdapter.notifyDataSetChanged();
                    break;
                case FOR_RECEIVING_LIST:
                    crListItems.add(new TradeCard(cardJson));
                    crListAdapter.notifyDataSetChanged();
                    break;
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
    
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
                catch (JSONException e) { } // like i give a f**k.
            }
        };
        
        // TODO: AAAAAAAAAAAAAAAAAAAAAAAAAAAAA
        api.createTradeAsync(3, callback);
    }
    
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
                // I guess we should do some shit here.
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
}
