
package org.walkingarchive.app;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.ui.SearchResult;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class SearchResultsActivity extends ListActivity {	
	
	final Context context = this;
	private ListView lv;
	private ArrayAdapter<SearchResult> listAdapter ; 
	private ArrayList<SearchResult> cardList = new ArrayList<SearchResult>();
	private ArrayList<SearchResult> showcardList = new ArrayList<SearchResult>();
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
                
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
        	cardList.add(new SearchResult(cardJson));
        	showcardList.add(new SearchResult(cardJson));
        }
        
        
        lv = getListView(); 
        
        
           
        listAdapter = new ArrayAdapter<SearchResult>(this, R.layout.list_text, R.id.listText, cardList);  
          
        
        // Set the ArrayAdapter as the ListView's adapter.  
       lv.setAdapter( listAdapter );  
        
        
        
        
       final EditText cardName = (EditText) findViewById(R.id.cardName);
       // cardName.setText("");
        
        cardName.addTextChangedListener(new TextWatcher() {
        	 
        	   public void afterTextChanged(Editable s) {
        	   }
        	 
        	   public void beforeTextChanged(CharSequence s, int start, 
        	     int count, int after) {
        	   }
        	 
        	   public void onTextChanged(CharSequence s, int start, 
        	     int before, int count) {
        		   listAdapter.clear();
        		   
        		   
        		   if(!cardName.getText().toString().equals("")){
        			   String name = cardName.getText().toString();
       	        	
       	        	
       	        	for(int i=0; i < showcardList.size(); i++){
       	        		
       	        		SearchResult sr = showcardList.get(i);
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
        	    	 
        	    	 for(int i =0; i< showcardList.size(); i++){
        	    		 
        	    		 listAdapter.add(showcardList.get(i));
        	    		 
        	    	 }
        	    	 
        	    	 
        	   }
        	   }
        	  });       
        
     
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				
				SearchResult result = (SearchResult) adapter.getItemAtPosition(position);
				Intent cardViewerIntent = new Intent( SearchResultsActivity.this, CardViewerActivity.class);
				cardViewerIntent.putExtra("cardJson", result.toJson());
				SearchResultsActivity.this.startActivity(cardViewerIntent);
			}
		});
        
        
        lv.setTextFilterEnabled(true);
  
        
	// make listview scroll	
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
	Intent intent = new Intent(context, SearchActivity.class);
						startActivity(intent);
  }
}
