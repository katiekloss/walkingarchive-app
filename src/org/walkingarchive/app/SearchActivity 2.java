package org.walkingarchive.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        Spinner colorSpinner = (Spinner)findViewById(R.id.colorSpinner);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	                R.array.search_color_spinner_array,
	                R.layout.spinner_text);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    colorSpinner.setAdapter(adapter);
	    
	    Spinner setSpinner = (Spinner)findViewById(R.id.setSpinner);
	    ArrayAdapter<CharSequence> set_adapter = ArrayAdapter.createFromResource(this,
                R.array.search_set_spinner_array,
                R.layout.spinner_text);
	    set_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    setSpinner.setAdapter(set_adapter);
	    
	    Spinner typeSpinner = (Spinner)findViewById(R.id.typeSpinner);
	    ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this,
                R.array.search_type_spinner_array,
                R.layout.spinner_text);
	    type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    typeSpinner.setAdapter(type_adapter);
	    
	    addListenerOnButton();
	    
	    
	    
	}
	
	 public void addListenerOnButton() {
    	 
			final Context context = this;
			Button searchButton = (Button) findViewById(R.id.searchButton);
			Button gobackButton = (Button) findViewById(R.id.gobackButton);
			
			searchButton.setOnClickListener(new OnClickListener() {
	 
				@Override
				public void onClick(View arg0) {
	 
				    Intent intent = new Intent(context, SearchResultsActivity.class);
	                            startActivity(intent);   
	 
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

}
