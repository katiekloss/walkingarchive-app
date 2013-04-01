package org.walkingarchive.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.ui.SearchResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountInforActivity extends Activity {
	JSONArray json;
	JSONObject userJson;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_infor);
		
		 String resultString = getIntent().getExtras().getString("resultString");
	        
			try
			{
				json = new JSONArray(resultString);
			}
			catch(JSONException e)
			{
				// Hmm...
				return;
			}

	        try{
	        	    userJson = json.getJSONObject(0);
					EditText userName = (EditText) findViewById(R.id.userName);
			        userName.setText(userJson.getString("name"));
			        EditText passWord = (EditText) findViewById(R.id.emailAddress);
			        passWord.setText(userJson.getString("name"));
					
			}catch (JSONException e)
	        {
	        		// TODO: Log/whatever this
	        }

	}

	
	 public void onSaveDown(View v){
		 	/////////////////
		 	/////////////////
		 	/////////////////
		 	/////////////////
	 }
	 
	 public void onChangePassWordsDown(View v){
		 	SearchResult sr = new SearchResult(userJson);
		 	Intent cardViewerIntent = new Intent(AccountInforActivity.this, ChangePwdActivity.class);
			cardViewerIntent.putExtra("userJson", sr.toJson());
	    	AccountInforActivity.this.startActivity(cardViewerIntent); 
	 }
	
	 public void onClearButtonDown(View v){
		  EditText userName = (EditText) findViewById(R.id.userName);
	      userName.setText("");
	      EditText emailAddress = (EditText) findViewById(R.id.emailAddress);
	      emailAddress.setText("");
		 
	 }
	 
	 public void onGoBackDown(View v){
		 Intent intent = new Intent(this, MainActivity.class);
         startActivity(intent);  
		 
	 }

}
