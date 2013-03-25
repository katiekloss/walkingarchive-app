package org.walkingarchive.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class SearchActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }
	
	public void onSearchButtonDown(View view)
	{
		// TODO: Perform search and pass results with Intent.putExtra()
		Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
    	this.startActivity(searchResultsIntent);
	}
}
