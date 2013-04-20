package helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class WebHelper {
	public static String sanitize(String fragment)
	{
		try
		{
			return URLEncoder.encode(fragment, "UTF-8").replace("+", "%20").replace("/", "%2F");
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			return null;
		}
	}
	
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
	
	public static String POST(String urlString, String payload) throws MalformedURLException
	{
	    return POST(urlString, payload, false);
	}
	
	public static String POST(String urlString, String payload, Boolean put) throws MalformedURLException
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
        
        OutputStream writer = null;
        BufferedReader reader = null;
        StringBuilder resultString = new StringBuilder();
        try
        {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            if(put)
                conn.setRequestMethod("PUT");

            writer = conn.getOutputStream();
            if(payload != null)
            {
                writer.write(payload.getBytes());
            }
            conn.getResponseCode();
            
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String buffer;
            while((buffer = reader.readLine()) != null)
                resultString.append(buffer);
            
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally
        {
            // This is f**king asinine.
            try
            {
                reader.close();
                writer.close();
            }
            catch (Exception e) { }
        }
        
        return resultString.toString();
	}
	
	public static String PUT(String urlString, String payload) throws MalformedURLException
	{
	    return POST(urlString, payload, true);
	}
}
