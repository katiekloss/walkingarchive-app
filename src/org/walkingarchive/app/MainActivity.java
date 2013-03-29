package org.walkingarchive.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
    	 
		final Context context = this;
		Button button1 = (Button) findViewById(R.id.button1);
		Button button3 = (Button) findViewById(R.id.button3);
		Button button4 = (Button) findViewById(R.id.button4);
		Button button5 = (Button) findViewById(R.id.button5);
		
		button1.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, SearchActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		
		
		button3.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, MyCollectionActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		
		button4.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, TradeHistoryActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		
		button5.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, AccountInforActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		
 
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onSearchButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, SearchActivity.class);
    	this.startActivity(searchIntent);
    }
}
