package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AccountInforActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_infor);
		
		 addListenerOnButton();
		
	}
	
	
	public void addListenerOnButton() {
   	 
		final Context context = this;
		Button changePwdButton = (Button) findViewById(R.id.changePwdButton);
		Button saveButton = (Button) findViewById(R.id.saveButton);
		Button cancelButton = (Button) findViewById(R.id.cancelButton);
		Button gobackButton = (Button) findViewById(R.id.gobackButton);
		
		changePwdButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, ChangePwdActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		
		
		cancelButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			      
 
			}
 
		});
		
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
		getMenuInflater().inflate(R.menu.account_infor, menu);
		return true;
	}

}
