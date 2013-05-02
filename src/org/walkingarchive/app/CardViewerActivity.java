package org.walkingarchive.app;

import java.text.NumberFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * This renders the card viewer interface for a given chunk of Card-like JSON
 */
public class CardViewerActivity extends Activity {
    /**
     * Renders the interface
     * <p>
     * The JSON of the card we're viewing must be passed as a string Extra named "cardJson".
     * </p>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_viewer);
        
        String cardJson = getIntent().getExtras().getString("cardJson");
        
        JSONObject json;
        try
        {
            json = new JSONObject(cardJson);
            TextView cardName = (TextView) findViewById(R.id.cardName);
            cardName.setText(json.getString("name"));
            
            TextView cardType = (TextView) findViewById(R.id.cardType);
            if(json.getString("subtype").equals("null"))
                cardType.setText(json.getString("type"));
            else
                cardType.setText(json.getString("type") + " - " + json.getString("subtype"));
            
            setCardMana(json);
            
            TextView cardRules = (TextView) findViewById(R.id.cardRules);
            cardRules.setText(json.getString("text"));
            
            TextView cardFlavorText = (TextView) findViewById(R.id.cardFlavorText);
            cardFlavorText.setText(json.getString("flavortext"));
            
            TextView cardPrice = (TextView) findViewById(R.id.cardValue);
            JSONArray prices = json.getJSONArray("prices");
            if(prices.length() > 0)
            {
                Double min = Double.MAX_VALUE;
                Double max = Double.MIN_VALUE;
                
                for(int i = 0; i < prices.length(); i++)
                {
                    JSONObject price = prices.getJSONObject(i);
                    Double value = (Double) price.get("price");
                    if(value < min) min = value;
                    if(value > max) max = value;
                }
                
                min = Math.round(min * 100) / 100d;
                max = Math.round(max * 100) / 100d;
                
                // You have to compare floats like this because no two floats
                // are exactly alike (like snowflakes!)
                if(Math.abs(max - min) < 0.01)
                    cardPrice.setText(NumberFormat.getCurrencyInstance().format(min));
                else
                    cardPrice.setText(NumberFormat.getCurrencyInstance().format(min)
                            + " - " + NumberFormat.getCurrencyInstance().format(max)
                            );
            } else {
                cardPrice.setText("No price data available");
            }
        }
        catch(JSONException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a database-serialized mana cost to a String one
     * @param json  The JSON of the card this Activity is viewing
     */
    private void setCardMana(JSONObject json) {
        TextView cardMana = (TextView) findViewById(R.id.cardMana);
        try {
            String manaText = "";

            JSONObject mana = json.getJSONObject("mana");
            if (mana != null && mana.length() > 0) {
                for (int i = 0; i < mana.names().length(); i ++) {
                    if (manaText != "") {
                        manaText += ", ";
                    }
                    manaText += mana.getInt(mana.names().getString(i)) + " " + mana.names().getString(i);
                }
            }
            else {
                manaText = "none";
            }
            cardMana.setText(manaText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
    }
}