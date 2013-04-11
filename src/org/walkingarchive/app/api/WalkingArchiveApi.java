package org.walkingarchive.app.api;

import helpers.WebHelper;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import org.walkingarchive.app.AsyncTaskCallback;

import android.os.AsyncTask;

public class WalkingArchiveApi {
	String urlBase;
	
	public WalkingArchiveApi(String urlBase)
	{
		this.urlBase = urlBase;
	}
	
	public WalkingArchiveApi()
	{
		// TODO: I can't decide whether this should be hardcoded or not.
		// I mean, obviously not, but you can't really configure an app
		// the same way as editing a config file (where it should go).
		this("http://dev.mtgwalkingarchive.com/walkingarchive-qa");
	}
	
	public String getAbsoluteUrl(String path, String parameter)
	{
		try
		{
			return urlBase + path + "/" + URLEncoder.encode(parameter, "UTF-8").replace("+", "%20");
		}
		catch (UnsupportedEncodingException e)
		{
			// As usual, f**k you Java.
			return null;
		}
	}
	
	public String getCardByName(String name)
	{
		try
		{
			return WebHelper.GET(getAbsoluteUrl("/card/name", name));
		}
		catch(MalformedURLException e)
		{
			// TODO: Log it
			return null;
		}
	}
	
	public void getCardByNameAsync(final String name, final AsyncTaskCallback callback)
	{
		Runnable asyncRunner = new Runnable()
		{
			public void run()
			{
				String result = getCardByName(name);
				if(callback != null) callback.run(result);
			}
		};

		AsyncTask.execute(asyncRunner);
	}
	
	public String search(String query)
	{
		try
		{
			return WebHelper.GET(getAbsoluteUrl("/card/search", query));
		}
		catch(MalformedURLException e)
		{
			// TODO: Log this
			return null;
		}
	}
	
	public void searchAsync(final String query, final AsyncTaskCallback callback)
	{
		Runnable asyncRunner = new Runnable()
		{
			public void run()
			{
				String result = search(query);
				if(callback != null) callback.run(result);
			}
		};
		
		AsyncTask.execute(asyncRunner);
	}
}
