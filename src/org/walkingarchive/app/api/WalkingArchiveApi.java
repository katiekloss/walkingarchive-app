package org.walkingarchive.app.api;

import helpers.WebHelper;

import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.AsyncTaskCallback;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Abstracts the API server into synchronous and asynchronous requests
 */
public class WalkingArchiveApi {
    String urlBase;
    private final String TAG = "WalkingArchiveApi";
    
    /**
     * Initialize a new API object for an API server located at the given URL
     * @param urlBase  The URL to the root of the API server
     */
    public WalkingArchiveApi(String urlBase)
    {
        this.urlBase = urlBase;
    }
    
    /**
     * Initialize a new API object with the default API server.
     */
    public WalkingArchiveApi()
    {
        // TODO: I can't decide whether this should be hardcoded or not.
        // I mean, obviously not, but you can't really configure an app
        // the same way as editing a config file (where it should go).
        this("http://dev.mtgwalkingarchive.com:8080");
    }
    
    /**
     * Return the absolute URL of the given path on the API server
     * @param path  The path on the API server
     * @return      The absolute URL of this resource
     */
    public String getAbsoluteUrl(String path)
    {
        return urlBase + path;
    }
    
    //**********************************************************************************************
        // CARD & SEARCH
        //**********************************************************************************************
    
    /**
     * Return the JSON for the cards with the given name prefix
     * @param name  The name prefix to search with
     * @param page  The page of results to start returning from
     * @return      JSON for this page of results
     */
    public String getCardByName(String name, int page)
    {
        try
        {
            return WebHelper.GET(getAbsoluteUrl("/card/name/" + WebHelper.sanitize(name) + "/" + page));
        }
        catch(MalformedURLException e)
        {
            // TODO: Log it
            return null;
        }
    }
    
    /**
     * Starts the getCardByName method and returns immediately
     * @param name      The name prefix to search with
     * @param page      The page of results to start returning from
     * @param callback  A callback to execute when the request finishes
     */
    public void getCardByNameAsync(final String name, final int page, final AsyncTaskCallback callback)
    {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String result = getCardByName(name, page);
                if(callback != null) callback.run(result);
            }
        };

        AsyncTask.execute(asyncRunner);
    }
    
    /**
     * Starts the getCardByNameManaTypeAsync method and returns
     * @param name      The name prefix to search with
     * @param type      The type string to search with
     * @param mana      The mana to search with
     * @param page      The results page to start returning from
     * @param callback  A callback to execute when the method returns
     */
    public void getCardByNameManaTypeAsync(final String name, final String type, 
            final String mana, final int page, final AsyncTaskCallback callback)
    {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String result = getCardByNameManaType(name, type, mana, page);
                if(callback != null) callback.run(result);
            }
        };

        AsyncTask.execute(asyncRunner);
    }
    
    /**
     * Retrieves a list of cards with matching name, type, and mana.
     * @param name      The name prefix to search with
     * @param type      The type string to search with
     * @param mana      The mana to search with
     * @param page      The results page to start returning from
     * @return          JSON for this page of results
     */
    public String getCardByNameManaType(String name, String type, String mana, int page)
    {
        try
        {
            if (name.isEmpty() || name == null) {
                if (type.equalsIgnoreCase("any type") && mana.equalsIgnoreCase("any color")) {
                    return null;
                }
                else if (type.equalsIgnoreCase("any type")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/mana/" + WebHelper.sanitize(mana) + "/" + page));
                }
                else if (mana.equalsIgnoreCase("any color")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/type/" + WebHelper.sanitize(type) + "/" + page));
                }
                else if (!type.equalsIgnoreCase("any type") && !mana.equalsIgnoreCase("any color")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/type/" + WebHelper.sanitize(type) + "/mana/" + WebHelper.sanitize(mana) + "/" + page)); 
                }
                else {
                    //unexpected, return null
                    Log.e(TAG, "Unexpected State");
                    return null;
                }
            }
            else {
                if (type.equalsIgnoreCase("any type") && mana.equalsIgnoreCase("any color")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/name/" + WebHelper.sanitize(name) + "/" + page));
                }
                else if (type.equalsIgnoreCase("any type")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/name/" + WebHelper.sanitize(name) + "/mana/" + WebHelper.sanitize(mana) + "/" + page));
                }
                else if (mana.equalsIgnoreCase("any color")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/name/" + WebHelper.sanitize(name) + "/type/" + WebHelper.sanitize(type) + "/" + page));
                }
                else if (!type.equalsIgnoreCase("any type") && !mana.equalsIgnoreCase("any color")) {
                    return WebHelper.GET(getAbsoluteUrl("/card/name/" + WebHelper.sanitize(name) + "/type/" + WebHelper.sanitize(type) + "/mana/" + WebHelper.sanitize(mana) + "/" + page));
                }
                else {
                    //unexpected, return null
                    Log.e(TAG, "Unexpected State");
                    return null;
                }
            }
        }
        catch(MalformedURLException e)
        {
            Log.e(TAG, "Caught MalformedURLException attempting to get a card");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Executes a full text search.
     * @param query  The text to search for
     * @return       The JSON of the search results
     */
    public String search(String query)
    {
        try
        {
            return WebHelper.GET(getAbsoluteUrl("/card/search/" + WebHelper.sanitize(query)));
        }
        catch(MalformedURLException e)
        {
            // TODO: Log this
            return null;
        }
    }
    
    /**
     * Starts the search() method and returns
     * @param query     The text to search for
     * @param callback  A callback to execute when the search returns
     */
    public void searchAsync(final String query, final AsyncTaskCallback callback)
    {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String result = search(query);
                if(callback != null) callback.run(result);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }
    
    
    //**********************************************************************************************
    // TRADE
    //**********************************************************************************************
    /**
     * Retrieves the list of Trades that the user owns
     * @param userId  The user to return Trades for
     * @return        The JSON of the list of Trades
     */
    public String getTradeHistory(Integer userId)
    {
        try
        {
            return WebHelper.GET(getAbsoluteUrl("/trade/user/" +userId.toString()));
        }
        catch (MalformedURLException e)
        {
            // TODO: handle exception
            return null;
        }
    }
    
    /**
     * Starts the getTradeHistory method and returns
     * @param userId    The user to return Trades for
     * @param callback  A callback method to execute when the call returns
     */
    public void getTradeHistoryAsync(final Integer userId, final AsyncTaskCallback callback)
    {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String result = getTradeHistory(userId);
                if(callback != null) callback.run(result);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }
    
    /**
     * Overwrites the cards in the Trade with those passed in.
     * @param id         The Trade to update
     * @param giving     The cards being given in this Trade
     * @param receiving  The cards being received in this Trade
     * @return           The updated Trade JSON
     */
    public String updateTrade(Integer id, List<Integer> giving, List<Integer> receiving)
    {        
        JSONObject json = new JSONObject();
        try
        {
            json.put("id", id);
            json.put("givingCards", new JSONArray(giving));
            json.put("receivingCards", new JSONArray(receiving));
        }
        catch (JSONException e) { }
        
        try
        {
            return WebHelper.POST(getAbsoluteUrl("/trade/update"), json.toString());
        }
        catch (MalformedURLException e)
        {
            return null;
        }
    }
    
    /**
     * Starts the updateTrade method and returns
     * @param id         The Trade to update
     * @param giving     The cards being given in this Trade
     * @param receiving  The cards being received in this Trade
     * @param callback   A callback to execute when the call finishes
     */
    public void updateTradeAsync(final Integer id, final List<Integer> giving, final List<Integer> receiving, final AsyncTaskCallback callback)
    {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String response = updateTrade(id, giving, receiving);
                if(callback != null) callback.run(response);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }
    
    /**
     * Creates a new Trade for the given user
     * @param userId  The user to create a Trade for
     * @return        The new Trade's JSON
     */
    public String createTrade(int userId)
    {
        JSONObject json = new JSONObject();
        try
        {
            json.put("user", userId);
        }
        catch (JSONException e) { }
        
        try
        {
            return WebHelper.PUT(getAbsoluteUrl("/trade/add"), json.toString());
        }
        catch (MalformedURLException e)
        {
            return null;
        }
    }
    
    /**
     * Starts the createTrade method and returns
     * @param userId    The user to create a Trade for
     * @param callback  A callback to execute when the call returns
     */
    public void createTradeAsync(final int userId, final AsyncTaskCallback callback)
    {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String response = createTrade(userId);
                if(callback != null) callback.run(response);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }
    
    //**********************************************************************************************
    // DECK
    //**********************************************************************************************
    
    /**
     * Retrieves a list of Decks that the user owns
     * @param userId    The user whose Decks we're returning
     * @return          JSON list of Decks
     */
    public String getDecks(Integer userId) {
        try {
            return WebHelper.GET(getAbsoluteUrl("/deck/user/" +userId.toString()));
        }
        catch (MalformedURLException e) {
            // TODO: handle exception
            return null;
        }
    }
    
    /**
     * Starts the getDecks method and returns
     * @param userId    The user whose Decks we're returning
     * @param callback  A callback method to execute when the call finishes
     */
    public void getDecksAsync(final int userId, final AsyncTaskCallback callback) {
        Runnable asyncRunner = new Runnable() {
            public void run() {
                String result = getDecks(userId);
                if(callback != null) callback.run(result);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }

    /**
     * Creates a new named deck for a user
     * @param userId    The user who owns the new Deck
     * @param name      The name of the Deck
     * @return          The Deck's JSON
     */
    public String createDeck(int userId, String name) {
        JSONObject json = new JSONObject();
        try {
            json.put("user", userId);
            json.put("name", name);
        }
        catch (JSONException e) {
            //TODO - handle exception
        }
        
        try {
            return WebHelper.PUT(getAbsoluteUrl("/deck/add"), json.toString());
        }
        catch (MalformedURLException e) {
            //log
            return null;
        }
    }
    
    /**
     * Starts the createDeck method and returns
     * @param userId    The user who owns the new Deck
     * @param name      The name of the Deck
     * @param callback  A callback to execute when the call returns
     */
    public void createDeckAsync(final Integer userId, final String name, final AsyncTaskCallback callback) {
        Runnable asyncRunner = new Runnable()
        {
            public void run()
            {
                String response = createDeck(userId, name);
                if(callback != null) callback.run(response);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }

    /**
     * Starts the updateDeck method and returns
     * @param deckId    The deck to update
     * @param name      The name of the Deck (possibly updated)
     * @param cards     The cards to place in the Deck (overwriting the old ones)
     * @param callback  A callback method to execute when the call finishes
     */
    public void updateDeckAsync(final int deckId, final String name, final List<Integer> cards, final AsyncTaskCallback callback) {
        Runnable asyncRunner = new Runnable() {
            public void run() {
                String response = updateDeck(deckId, name, cards);
                if(callback != null) callback.run(response);
            }
        };
        
        AsyncTask.execute(asyncRunner);
    }
    
    /**
     * Starts the updateDeck method and returns
     * @param deckId    The deck to update
     * @param name      The name of the Deck (possibly updated)
     * @param cards     The cards to place in the Deck (overwriting the old ones)
     * @return          The JSON of the updated Deck
     */
    public String updateDeck(Integer id, String name, List<Integer> cards) {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("name", name);
            json.put("collection", new JSONArray(cards));
        }
        catch (JSONException e) { }
        
        try {
            return WebHelper.POST(getAbsoluteUrl("/deck/update"), json.toString());
        }
        catch (MalformedURLException e) {
            return null;
        }
    }
}
