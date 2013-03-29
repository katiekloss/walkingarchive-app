package org.walkingarchive.app;

import java.net.URI;
import java.net.URISyntaxException;

import org.walkingarchive.app.api.DownloadStringAsyncTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SearchActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }
	
	public void onSearchButtonDown(View view)
	{
		EditText cardName = (EditText) findViewById(R.id.cardName);
		if(cardName.getText().toString().equals(""))
		{
			// Should probably show a notification or something here.
			return;
		}
		
		DownloadStringAsyncTask task = new DownloadStringAsyncTask(
				new AsyncTaskCallback()
				{
					public void run(Object o)
					{
						searchResultsReturned((String) o);
					}
				});
		
		// TODO: Refactor all of this into a WalkingArchiveApi class or something
		URI apiUri = null;
		try
		{
			 apiUri = new URI(
					"http",
					"dev.mtgwalkingarchive.com",
					"/walkingarchive-qa/card/name/" + cardName.getText().toString(),
					null);
		}
		catch(URISyntaxException e)
		{
			// f**k off.
		}
				
		task.execute(apiUri.toString());
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
}
