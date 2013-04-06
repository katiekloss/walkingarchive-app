package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;

import java.net.URI;
import java.net.URISyntaxException;

import org.walkingarchive.app.api.DownloadStringAsyncTask;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onSearchButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, SearchActivity.class);
    	this.startActivity(searchIntent);
    }
    
    public void onTradeButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, TradeActivity.class);
    	this.startActivity(searchIntent);
    }
    
    public void onMyCollectionButtonDown(View v)
    {
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
					"/walkingarchive-qa/card/name/" + "name",
					null);
		}
		catch(URISyntaxException e)
		{
			// f**k off.
		}
				
		task.execute(apiUri.toString());
		
    }
    
    public void onTradeHistoryButtonDown(View v)
    {
    	DownloadStringAsyncTask task = new DownloadStringAsyncTask(
				new AsyncTaskCallback()
				{
					public void run(Object o)
					{
						historyResultsReturned((String) o);
					}
				});
		
		// TODO: Refactor all of this into a WalkingArchiveApi class or something
		URI apiUri = null;
		try
		{
			 apiUri = new URI(
					"http",
					"dev.mtgwalkingarchive.com",
					"/walkingarchive-qa/card/name/" + "name",
					null);
		}
		catch(URISyntaxException e)
		{
			// f**k off.
		}
				
		task.execute(apiUri.toString());
    }
    
    public void onAccountInforButtonDown(View v)
    {
    	DownloadStringAsyncTask task = new DownloadStringAsyncTask(
				new AsyncTaskCallback()
				{
					public void run(Object o)
					{
						accountResultsReturned((String) o);
					}
				});
		
		// TODO: Refactor all of this into a WalkingArchiveApi class or something
		URI apiUri = null;
		try
		{
			 apiUri = new URI(
					"http",
					"dev.mtgwalkingarchive.com",
					"/walkingarchive-qa/card/name/" + "name",
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
			Intent searchResultsIntent = new Intent(this, MyCollectionActivity.class);
			searchResultsIntent.putExtra("resultString", resultString);
	    	this.startActivity(searchResultsIntent);
		} else {
			// I have no idea what to do here.
		}
	}
    
    public void historyResultsReturned(String resultString)
	{
		if(resultString != null)
		{
			Intent historyResultsIntent = new Intent(this, TradeHistoryActivity.class);
			historyResultsIntent.putExtra("resultString", resultString);
	    	this.startActivity(historyResultsIntent);
		} else {
			// I have no idea what to do here.
		}
	}
    
    public void accountResultsReturned(String resultString)
	{
		if(resultString != null)
		{
			Intent accountResultsIntent = new Intent(this, AccountInforActivity.class);
			accountResultsIntent.putExtra("resultString", resultString);
	    	this.startActivity(accountResultsIntent);
		} else {
			// I have no idea what to do here.
		}
	}
    
}
