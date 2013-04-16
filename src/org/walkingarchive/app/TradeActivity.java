package org.walkingarchive.app;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TradeActivity extends Activity {
	
	public static final int FOR_GIVING_LIST = 1;
	public static final int FOR_RECEIVING_LIST = 2;
	
    ArrayList<String> crListItems=new ArrayList<String>();
    ArrayList<String> cgListItems=new ArrayList<String>();
    ArrayAdapter<String> crListAdapter;
    ArrayAdapter<String> cgListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade);
        ListView crList = (ListView) findViewById(R.id.cardsReceivingList);
        ListView cgList = (ListView) findViewById(R.id.cardsGivingList);

        crListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                crListItems);
        crList.setAdapter(crListAdapter);
        
        cgListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                cgListItems);
        cgList.setAdapter(cgListAdapter);
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
        intent.putExtra("forOtherActivity", true);
        this.startActivityForResult(intent, FOR_RECEIVING_LIST);
    }
    
    public void addToGivingList (View v)
    {
    	Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("forOtherActivity", true);
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
		    		cgListItems.add(cardJson.getString("name"));
		    		cgListAdapter.notifyDataSetChanged();
		    		break;
		    	case FOR_RECEIVING_LIST:
		    		crListItems.add(cardJson.getString("name"));
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
}
