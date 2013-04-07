package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class WebHelper {
	public static String GET(String urlString) throws MalformedURLException
	{
		URL url;
		try
		{
			url = new URL(urlString);
		}
		catch (MalformedURLException e)
		{
			throw e;
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
			// TODO: What do we do here?
			return null;
		}
		
		return resultString.toString();
	}
}
