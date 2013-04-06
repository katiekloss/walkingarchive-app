package org.walkingarchive.app;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.ui.SearchResult;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MyCollectionActivity extends ListActivity {
	
	final Context context = this;
	private ListView lv;
	private ArrayAdapter<SearchResult> listAdapter ; 
	private ArrayList<SearchResult> cardList = new ArrayList<SearchResult>();
	private ArrayList<SearchResult> showcardList = new ArrayList<SearchResult>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);		
		
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
		
		 
		 lv.setAdapter( listAdapter ); 
		 
		 final EditText cardName = (EditText) findViewById(R.id.cardName);
		 getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		 
		 cardName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		      public void onFocusChange(View v, boolean hasFocus) {
		          if (hasFocus) {
		              getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		           
		          } else if (!hasFocus) {
		           getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		          }
		      }
		  });
		 
	        
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
					Intent cardViewerIntent = new Intent( MyCollectionActivity.this, CardViewerActivity.class);
					cardViewerIntent.putExtra("cardJson", result.toJson());
					MyCollectionActivity.this.startActivity(cardViewerIntent);
				}
			});
	        
	        
	    }

	public void onGoBackButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, MainActivity.class);
    	this.startActivity(searchIntent);
    }

}
