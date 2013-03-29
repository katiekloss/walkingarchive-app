package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePwdActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);
		
		addListenerOnButton();
		
		final EditText newPassword1 = (EditText) findViewById(R.id.newPassword1);
		final EditText newPassword2 = (EditText) findViewById(R.id.newPassword2);
		
		
		
		newPassword2.addTextChangedListener(new TextWatcher() {
	        	 
        public void afterTextChanged(Editable s) {
        	TextView  newPwd = (TextView) findViewById(R.id.newPwd);
        	
       		if(! newPassword1.equals(newPassword2)){ 		   
       			   
       			   newPwd.setText("The passwords don't match. Please try again.");
       		}else{
       			newPwd.setText("");
       		}
       		   
        }
       	 
       	   public void beforeTextChanged(CharSequence s, int start, 
       	     int count, int after) {
       	   }
       	 
       	   public void onTextChanged(CharSequence s, int start, 
       	     int before, int count) {
       	   }
       	  });       
		
	}
	
	
	public void addListenerOnButton() {
   	 
		final Context context = this;
		Button confirmButton = (Button) findViewById(R.id.confirmButton);
		Button clearButton = (Button) findViewById(R.id.clearButton);
		Button gobackButtonToAcct = (Button) findViewById(R.id.gobackButtonToAcct);
		
		confirmButton.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
				Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);   
 
			}
 
		});
		
		
		clearButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
				Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);   
 
			}
 
		});
		
		gobackButtonToAcct.setOnClickListener(new OnClickListener() {
			 
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
		getMenuInflater().inflate(R.menu.change_pwd, menu);
		return true;
	}

}
