package org.walkingarchive.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.walkingarchive.app.api.WalkingArchiveApi;

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
	    
	}
	
	public void onSearchButtonDown(View view)
	{
		EditText cardName = (EditText) findViewById(R.id.cardName);
		if(cardName.getText().toString().equals(""))
		{
			// Should probably show a notification or something here.
			return;
		}
		
		AsyncTaskCallback resultsCallback = new AsyncTaskCallback()
		{
			public void run(Object o)
			{
				searchResultsReturned((String) o);
			}
		};
		
		WalkingArchiveApi api = new WalkingArchiveApi();
		api.getCardByNameAsync(cardName.getText().toString(), resultsCallback);
	}
	
	public void searchResultsReturned(String resultString)
	{
		if(resultString != null)
		{
			Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
			searchResultsIntent.putExtra("resultString", resultString);
	    	this.startActivity(searchResultsIntent);
		} else {
			// I have no idea what to do here.
		}
	}
	

	public void onGoBackButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, MainActivity.class);
    	this.startActivity(searchIntent);
    }
	
	
}

