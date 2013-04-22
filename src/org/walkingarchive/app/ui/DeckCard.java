package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

public class DeckCard {
    JSONObject deckCard;
    
    public DeckCard(JSONObject json)
    {
        this.deckCard = json;
    }
    
    public String toString()
    {
        try
        {
            return this.deckCard.getString("name");
        }
        catch(JSONException e)
        {
            return null;
        }
    }
    
    public String toJson()
    {
        return this.deckCard.toString();
    }
}
