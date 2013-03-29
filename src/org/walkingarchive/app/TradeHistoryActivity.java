package org.walkingarchive.app;

import java.util.ArrayList;
import java.util.Arrays;

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
	final String[] list = new String[] {
		"May 15, 2012",
        "Apr. 6, 2012",
        "Oct. 10, 2012",
        "Sep. 10, 2012",
        "Mar. 10, 2012",
        "Feb. 25, 2002",
        "May 15, 20011",
        "Sep. 6, 2011",
        "Oct. 10, 2011",
        "Sep. 10, 2011",
        "Mar. 10, 2010",
        "Feb. 25, 2010",
        "May 15, 2009",
        "Sep. 6, 2009",

    };
	
	private ListView lv;
	private ArrayAdapter<String> listAdapter ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade_history);
		
        lv = getListView();
		
		ArrayList<String> cardList = new ArrayList<String>();  
		cardList.addAll( Arrays.asList(list) );  
		
		listAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.listText, cardList);
		
		 for(int i=0; i < list.length; i++ ){
		        
	        	listAdapter.add( list[i]);  
	  
	        } 
		 
		 lv.setAdapter( listAdapter ); 
		 
		 final EditText cardName = (EditText) findViewById(R.id.cardName);
	     //   cardName.setText("");
	        
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

		addListenerOnButton();
		ListView lv = getListView();
		
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				
				Intent intent = new Intent(context, CardViewerActivity.class);
                startActivity(intent); 				
	
			}
		});
		
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
 
			    Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);   
 
			}
 
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trade_history, menu);
		return true;
	}

}
