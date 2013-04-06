package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		TextView toRegister = (TextView) findViewById(R.id.to_register);
        
        // Listening to register new account link
		toRegister.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// Switching to Register screen
				Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(i);
			}
		});
		
	}

	 public void onLoginButtonDown(View v)
	    {
		 
		 
		 
	    	Intent searchIntent = new Intent(this, MainActivity.class);
	    	this.startActivity(searchIntent);
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
