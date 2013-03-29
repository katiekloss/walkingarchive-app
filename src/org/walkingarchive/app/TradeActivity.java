package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TradeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade);
		
		addListenerOnButton();
		
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
		getMenuInflater().inflate(R.menu.trade, menu);
		return true;
	}

}
