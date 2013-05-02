package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates a Card's JSON for rendering as part of a Trade
 * @author Katie
 *
 */
public class TradeCard {
    JSONObject tradeCard;
    
    /**
     * Instantiates a new object with the given JSON
     * @param json  The Card's JSON
     */
    public TradeCard(JSONObject json)
    {
        this.tradeCard = json;
    }
    
    /**
     * Returns the name of the Card
     */
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
    
    /**
     * Converts the JSON to a String
     * @return  The Card's JSON
     */
    public String toJson()
    {
        return this.tradeCard.toString();
    }
}
