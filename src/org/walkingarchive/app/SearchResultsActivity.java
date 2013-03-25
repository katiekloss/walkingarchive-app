package org.walkingarchive.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchResultsActivity extends Activity {
	ListView resultsListView;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);
        
        resultsListView = (ListView) findViewById(R.id.resultsListView);
        resultsListView.setOnItemClickListener(
			new OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> adapter, View view, int position, long id)
				{
					// TODO: Use Intent.getStringArrayListExtra to retrieve the search results
			        // and add them to the list view
					
					Intent cardViewerIntent = new Intent(SearchResultsActivity.this, CardViewerActivity.class);
			    	SearchResultsActivity.this.startActivity(cardViewerIntent);
				}
			});
    }
	
	
}