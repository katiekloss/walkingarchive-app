package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

public class DeckListResult {
JSONObject deckListResult;
    
    public DeckListResult(JSONObject json)
    {
        this.deckListResult = json;
    }
    
    public String toString()
    {
        try
        {
            return this.deckListResult.getString("name");
        }
        catch(JSONException e)
        {
            return null;
        }
    }
    
    public String toJson()
    {
        return this.deckListResult.toString();
    }
}
