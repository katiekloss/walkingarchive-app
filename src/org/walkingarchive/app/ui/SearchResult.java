package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates a Card's JSON for rendering as a search result
 */
public class SearchResult {
	JSONObject searchResult;
	
	/**
	 * Instantiate a new object with the given Card JSON
	 * @param json  The JSON of the card
	 */
	public SearchResult(JSONObject json)
	{
		this.searchResult = json;
	}
	
	/**
	 * Returns the name of the Card
	 * @return  The name of the Card
	 */
	public String toString()
	{
		try
		{
			return this.searchResult.getString("name");
		}
		catch(JSONException e)
		{
			return null;
		}
	}
	
	/**
	 * Returns the JSON for the Card
	 * @return  The JSON for the card
	 */
	public String toJson()
	{
		return this.searchResult.toString();
	}
}
