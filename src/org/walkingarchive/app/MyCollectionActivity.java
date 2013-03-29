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
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MyCollectionActivity extends ListActivity {
	
	final Context context = this;
	final String[] list = new String[] { "Abandon Hope", "bandoned Outpost", "Abattoir Ghoul", "Abbey Gargoyles",  
            "Abbey Griffin", "Backfire", "Backlash", "Backslide", "Dakkon", "Dakmor", "Dakmor Lancer"};   
	private ListView lv;
	private ArrayAdapter<String> listAdapter ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_collection);		
		
		addListenerOnButton();
		
		lv = getListView();
		
		ArrayList<String> cardList = new ArrayList<String>();  
		cardList.addAll( Arrays.asList(list) );  
		
		listAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.listText, cardList);
		
		 for(int i=0; i < list.length; i++ ){
		        
	        	listAdapter.add( list[i]);  
	  
	        } 
		 
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
		getMenuInflater().inflate(R.menu.my_collection, menu);
		return true;
	}

}
