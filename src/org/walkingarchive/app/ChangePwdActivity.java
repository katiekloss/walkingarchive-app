package org.walkingarchive.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.ui.SearchResult;

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

	private String oldPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_pwd);

		final EditText newPwd1 = (EditText) findViewById(R.id.newPwd1);
		final EditText newPwd2 = (EditText) findViewById(R.id.newPwd2);

		newPwd2.addTextChangedListener(new TextWatcher() {
	        	 
        public void afterTextChanged(Editable s) {
        	TextView  newPwd = (TextView) findViewById(R.id.newPwd);
        	newPwd.setText("");
       		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}
       	  });       
		
		newPwd1.addTextChangedListener(new TextWatcher() {
       	 
	        public void afterTextChanged(Editable s) {
	        	TextView  newPwd = (TextView) findViewById(R.id.newPwd);
	        	newPwd.setText("");
	       		}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}

	       	  });       
		
		
		
		JSONObject json;
		
		String userJson = getIntent().getExtras().getString("userJson");
		 try
	        {
	        	json = new JSONObject(userJson);
	          //  oldPassword = json.getString("password");
	        }
		 catch(JSONException e)
	     {
	        	throw new RuntimeException(e);
	     }
		
	}

	
	public void onConfirmButtonDown(View v){
		EditText newPwd1 = (EditText) findViewById(R.id.newPwd1);
		EditText newPwd2 = (EditText) findViewById(R.id.newPwd2);
		TextView newPwd = (TextView ) findViewById(R.id.newPwd);
		if(!newPwd1.getText().toString().equals(newPwd2.getText().toString())){ 		   
			   
			newPwd.setText("The passwords don't match. Please try again.");	
			return;
			
		}else{
			newPwd.setText("");
		}
		
		 /////////////////
		/////////////////
		/////////////////
		 
	 }
	
	public void onClearButtonDown(View v){
		
		  EditText oldPwd = (EditText) findViewById(R.id.oldPwd);
		  oldPwd.setText("");
		  EditText newPwd1 = (EditText) findViewById(R.id.newPwd1);
		  newPwd1.setText("");
	      EditText newPwd2 = (EditText) findViewById(R.id.newPwd2);
	      newPwd2.setText("");
		 
	 }
	
	public void onGoBackButtonDown(View v){
		
		Intent intent = new Intent(this, MainActivity.class);
 		startActivity(intent);
		 
	 }
	


}
