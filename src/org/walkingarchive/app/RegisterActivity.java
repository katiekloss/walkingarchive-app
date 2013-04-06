package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class RegisterActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		 TextView toLogin = (TextView) findViewById(R.id.link_to_login);
	        
	        // Listening to Login Screen link
		        toLogin.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View arg0) {
					// Switching to Login Screen/closing register screen
					Intent i = new Intent(getApplicationContext(), LoginActivity.class);
					startActivity(i);
				}
			});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
