package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

public class AccountInforSearchResult {
	
	JSONObject searchResult;
	
	
	public AccountInforSearchResult(JSONObject json)
	{
		this.searchResult = json;
	}
	
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
	
	public String toJson()
	{
		return this.searchResult.toString();
	}		
	
}
