package org.walkingarchive.app.api;

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
		URL url;
		try {
			url = new URL(urls[0]);
		} catch (MalformedURLException e) {
			// I don't understand how this is even possible, but ok.
			return null;
		}
		
		BufferedReader reader;
		StringBuilder resultString;
		try
		{
			reader = new BufferedReader(
					new InputStreamReader(url.openConnection().getInputStream()));
			resultString = new StringBuilder();
			String buffer;
			while((buffer = reader.readLine()) != null)
				resultString.append(buffer);
			reader.close();
		}
		catch(IOException e)
		{
			// TODO: This too
			return null;
		}
		
		return resultString.toString();
	}
	
	protected void onPostExecute(String string)
	{
		if(this.callback != null) this.callback.run(string);
	}
}
