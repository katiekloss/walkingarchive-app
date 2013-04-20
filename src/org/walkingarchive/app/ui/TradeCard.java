package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

public class TradeCard {
    JSONObject tradeCard;
    
    public TradeCard(JSONObject json)
    {
        this.tradeCard = json;
    }
    
    public String toString()
    {
        try
        {
            return this.tradeCard.getString("name");
        }
        catch(JSONException e)
        {
            return null;
        }
    }
    
    public String toJson()
    {
        return this.tradeCard.toString();
    }
}
