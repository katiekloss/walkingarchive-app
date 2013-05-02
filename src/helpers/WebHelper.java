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


/**
 * Provides a set of utility methods for interacting with HTTP servers.
 */
public class WebHelper {
    /**
     * Returns a percent-encoded copy of the passed String
     * @param  fragment  A String to percent-encode
     * @return           The percent-encoded String
     */
    public static String sanitize(String fragment)
    {
        try
        {
            return URLEncoder.encode(fragment, "UTF-8").replace("+", "%20").replace("/", "%2F");
        }
        catch (UnsupportedEncodingException e)
        {
            // This will probably never happen (all data coming into this method is UTF-8)
            return null;
        }
    }
    
    /**
     * Performs a synchronous HTTP request, returning any response data as a String
     * @param urlString  The absolute URL to retrieve
     * @return           The response data from the remote server
     * @throws MalformedURLException
     */
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
            return null;
        }
        
        return resultString.toString();
    }
    
    /**
     * Performs a synchronous HTTP POST and returns any response data
     * @param urlString  The absolute URL to POST to
     * @param payload    A pre-formatted payload to POST
     * @return           The response from the remote server, if any
     * @throws MalformedURLException
     */
    public static String POST(String urlString, String payload) throws MalformedURLException
    {
        return POST(urlString, payload, false);
    }
    
    /**
     * Performs a synchronous HTTP POST or PUT and returns any response data
     * @param urlString  The absolute URL to request
     * @param payload    A pre-formatted payload
     * @param put        If true, the request will be executed as a PUT instead of POST
     * @return           The response from the remote server, if any
     * @throws MalformedURLException
     */
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
            conn.setRequestProperty("Content-Type", "application/json");
            if(put)
                conn.setRequestMethod("PUT");

            writer = conn.getOutputStream();
            if(payload != null)
            {
                writer.write(payload.getBytes());
            }
            
            // This forces a buffer flush so that there's a response to read afterward
            conn.getResponseCode();
            
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String buffer;
            while((buffer = reader.readLine()) != null)
                resultString.append(buffer);
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
                writer.close();
            }
            catch (Exception e) { }
        }
        
        return resultString.toString();
    }
    
    /**
     * Performs a synchronous HTTP PUT and returns any response data
     * @param urlString  The absolute URL to PUT to
     * @param payload    A pre-formatted payload to PUT
     * @return           The response from the remote server, if any
     * @throws MalformedURLException
     */
    public static String PUT(String urlString, String payload) throws MalformedURLException
    {
        return POST(urlString, payload, true);
    }
}
