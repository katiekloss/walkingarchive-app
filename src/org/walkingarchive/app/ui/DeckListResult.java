package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates a Deck's JSON for rendering
 */
public class DeckListResult {
JSONObject deckListResult;
    
    /**
     * Instantiate a new DeckListResult with the given Deck JSON
     * @param json
     */
    public DeckListResult(JSONObject json)
    {
        this.deckListResult = json;
    }
    
    /**
     * Returns the name of the Deck
     * @return  The name of the Deck
     */
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
    
    /**
     * Converts the JSON to a String
     * @return  The JSON representation as a String
     */
    public String toJson()
    {
        return this.deckListResult.toString();
    }
}
