package org.walkingarchive.app;

import java.util.ArrayList;
import java.util.Arrays;

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
	final String[] list = new String[] { "Abandon Hope", "bandoned Outpost", "Abattoir Ghoul", "Abbey Gargoyles",  
            "Abbey Griffin", "Backfire", "Backlash", "Backslide", "Dakkon", "Dakmor", "Dakmor Lancer"};   
	private ListView lv;
	private ArrayAdapter<String> listAdapter ; 
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
                
        
        
        lv = getListView(); 
        
        // Create and populate a List of planet names.  
         
        ArrayList<String> cardList = new ArrayList<String>();  
        
        cardList.addAll( Arrays.asList(list) );  
          
        // Create ArrayAdapter using the planet list.  
        listAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.listText, cardList);  
          
        // Add more planets. If you passed a String[] instead of a List<String>   
        // into the ArrayAdapter constructor, you must not add more items.   
        // Otherwise an exception will occur.  
        
        for(int i=0; i < list.length; i++ ){
        
        	listAdapter.add( list[i]);  
  
        } 
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
        	        	
        	        	for(int i = 0; i < list.length; i++){
        	        		if (list[i].contains(name)){
        	        			listAdapter.add(list[i]);
        	        		}
        	        	}       	
        	     }
        	   }
        	  });       
        
     
        lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				
				Intent intent = new Intent(context, CardViewerActivity.class);
                startActivity(intent); 				
	
			}
		});
		
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				
				Intent intent = new Intent(context, CardViewerActivity.class);
                startActivity(intent); 				
	
			}
		});
       
        
        addListenerOnButton();
        
        
		
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
	
	
	public void addListenerOnButton() {
	   	 
		final Context context = this;
		
		Button gobackButton = (Button) findViewById(R.id.gobackButton);
		
		gobackButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, SearchActivity.class);
                            startActivity(intent);   
 
			}
 
		});
	}
	
	
}