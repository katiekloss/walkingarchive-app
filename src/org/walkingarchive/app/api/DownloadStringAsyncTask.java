package org.walkingarchive.app.api;

import helpers.WebHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

import org.walkingarchive.app.AsyncTaskCallback;

import android.os.AsyncTask;

public class DownloadStringAsyncTask extends AsyncTask<String, Void, String>
{
	private AsyncTaskCallback callback;
	
	public DownloadStringAsyncTask(AsyncTaskCallback cb)
	{
		this.callback = cb;
	}
	
	protected String doInBackground(String... urls)
	{
		if(urls.length <= 0) return null;
		try
		{
			return WebHelper.GET(urls[0]);
		}
		catch (MalformedURLException e)
		{
			// TODO: Log it
		}
		return null;
	}
	
	protected void onPostExecute(String string)
	{
		if(this.callback != null) this.callback.run(string);
	}
}
