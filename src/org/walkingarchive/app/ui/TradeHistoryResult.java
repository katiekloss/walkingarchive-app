package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates a Trade's JSON for rendering
 */
public class TradeHistoryResult {
    JSONObject tradeHistoryResult;
    
    /**
     * Instantiates a new object with the given Trade JSON
     * @param json  The JSON of the Trade
     */
    public TradeHistoryResult(JSONObject json)
    {
        this.tradeHistoryResult = json;
    }
    
    /**
     * Returns the date of the Trade as a String
     */
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
    
    /**
     * Returns the JSON of the Trade as a String
     * @return  The JSON of the Trade
     */
    public String toJson()
    {
        return this.tradeHistoryResult.toString();
    }
}
