package org.walkingarchive.app;

import java.util.ArrayList;
import java.util.Arrays;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.ui.SearchResult;


import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;

public class TradeHistoryActivity extends ListActivity {
	final Context context = this;
//	final String[] list = new String[] {
	//	"May 15, 2012",
      //  "Apr. 6, 2012",
     //   "Oct. 10, 2012",
     //   "Sep. 10, 2012",
     //   "Mar. 10, 2012",
     //   "Feb. 25, 2002",
     //   "May 15, 20011",
      //  "Sep. 6, 2011",
      //  "Oct. 10, 2011",
     //   "Sep. 10, 2011",
     //   "Mar. 10, 2010",
     //   "Feb. 25, 2010",
     //   "May 15, 2009",
     //   "Sep. 6, 2009",

   // };
	
	private ListView lv;
	private ArrayAdapter<SearchResult> listAdapter ; 
	private ArrayList<SearchResult>  historyList = new ArrayList<SearchResult>();
	private ArrayList<SearchResult> showhistoryList = new ArrayList<SearchResult>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade_history);
		
		String resultString = getIntent().getExtras().getString("resultString");
		JSONArray json;
        try
		{
			json = new JSONArray(resultString);
		}
		catch(JSONException e)
		{
			// Hmm...
			return;
		}
		
        for(int i = 0; i < json.length(); i++)
        {
        	JSONObject cardJson;
        	try
        	{
				cardJson = json.getJSONObject(i);
			}
        	catch (JSONException e)
        	{
        		// TODO: Log/whatever this
        		continue;
			}
        	historyList.add(new SearchResult(cardJson));
        	showhistoryList.add(new SearchResult(cardJson));
        }
        
        lv = getListView(); 
		
		listAdapter = new ArrayAdapter<SearchResult>(this, R.layout.list_text, R.id.listText, historyList);
		
		 lv.setAdapter( listAdapter ); 
		 
		 final EditText tradeDate = (EditText) findViewById(R.id.tradeDate);
	        
		 tradeDate.addTextChangedListener(new TextWatcher() {
	        	 
	        	   public void afterTextChanged(Editable s) {
	        	   }
	        	 
	        	   public void beforeTextChanged(CharSequence s, int start, 
	        	     int count, int after) {
	        	   }
	        	 
	        	   public void onTextChanged(CharSequence s, int start, 
	        	     int before, int count) {
	        		   listAdapter.clear();
	        		  
	        		   
	        		   if(!tradeDate.getText().toString().equals("")){
	        	        	String name = tradeDate.getText().toString();
	        	        	
	        	        	for(int i=0; i < showhistoryList.size(); i++){
	        	        		
	        	        		SearchResult sr = showhistoryList.get(i);
	        	        		String cardJson = sr.toJson();
	        	        		
	        	        		String cardNames;
	        	        		JSONObject json;
	        	        		
	        	        		try{
	        	                	json = new JSONObject(cardJson);
	        	                	cardNames = json.getString("name");
	        	                	
	        	        	    }
	        	        		catch(JSONException e){
	        	        	        	throw new RuntimeException(e);
	        	        	    }
	        	        	   
	        	        	   if(cardNames.contains(name)){
	        	        		   listAdapter.add(sr);
	        	        	   }    	
	        	        	}  	
	        	     }else{
	        	    	 
	        	    	 for(int i =0; i< showhistoryList.size(); i++){
	        	    		 
	        	    		 listAdapter.add(showhistoryList.get(i));
	        	    		 
	        	    	 }
	        	    	 
	        	    	 
	        	    	 
	        	    	 
	        	     }
	        		   
	        		   
	        		   
	        	   }
	        	  });        
	        
	    lv.setTextFilterEnabled(true);

		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				
				SearchResult result = (SearchResult) adapter.getItemAtPosition(position);
				Intent cardViewerIntent = new Intent( TradeHistoryActivity.this, CardViewerActivity.class);
				cardViewerIntent.putExtra("cardJson", result.toJson());
				TradeHistoryActivity.this.startActivity(cardViewerIntent);
			}
		});
		
		lv.setTextFilterEnabled(true);

		
		lv.setOnTouchListener(new ListView.OnTouchListener() {
	        @Override
	        public boolean onTouch(View v, MotionEvent event) {
	            int action = event.getAction();
	            switch (action) {
	            case MotionEvent.ACTION_DOWN:
	                // Disallow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(true);
	                break;

	            case MotionEvent.ACTION_UP:
	                // Allow ScrollView to intercept touch events.
	                v.getParent().requestDisallowInterceptTouchEvent(false);
	                break;
	            }

	            // Handle ListView touch events.
	            v.onTouchEvent(event);
	            return true;
	        }
	    });
		
	}

	
	public void onGoBackButtonDown(View v)
    {
    	
    	
    	Intent searchIntent = new Intent(this, MainActivity.class);
    	this.startActivity(searchIntent);
    }
}
