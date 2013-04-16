package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeHistoryResult {
	JSONObject tradeHistoryResult;
	
	public TradeHistoryResult(JSONObject json)
	{
		this.tradeHistoryResult = json;
	}
	
	public String toString()
	{
		try
		{
			return this.tradeHistoryResult.getString("date");
		}
		catch(JSONException e)
		{
			return null;
		}
	}
	
	public String toJson()
	{
		return this.tradeHistoryResult.toString();
	}
}
