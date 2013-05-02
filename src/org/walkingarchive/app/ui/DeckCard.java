package org.walkingarchive.app.ui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Encapsulates a Card entry present in a Deck for rendering
 * @author Katie
 *
 */
public class DeckCard {
    JSONObject deckCard;
    
    /**
     * Initialize a new DeckCard with the given Card JSON
     * @param json  JSON representation of the Card
     */
    public DeckCard(JSONObject json)
    {
        this.deckCard = json;
    }
    
    /**
     * Returns the name of the encapsulated Card
     */
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
    
    /**
     * Converts the JSONObject to a String
     * @return  The JSON of the Card
     */
    public String toJson()
    {
        return this.deckCard.toString();
    }
}
