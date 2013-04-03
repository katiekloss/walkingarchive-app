package org.walkingarchive.app;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardViewerActivity extends Activity {
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
            
            TextView cardMana = (TextView) findViewById(R.id.cardMana);
            // cardMana.setText(json.getString("mana"));
            // TODO: Actually assign it a value when this is supported by the API
            
            TextView cardValue = (TextView) findViewById(R.id.cardValue);
            // TODO: Same for this, however the API does it...
            
            TextView cardRules = (TextView) findViewById(R.id.textView9);
            cardRules.setText(json.getString("text"));
            
            TextView cardFlavorText = (TextView) findViewById(R.id.textView13);
            cardFlavorText.setText(json.getString("flavortext"));
            
            TextView cardPrice = (TextView) findViewById(R.id.cardValue);
            JSONArray prices = json.getJSONArray("prices");
            if(prices.length() > 0)
            {
	            Float min = Float.MAX_VALUE;
	            Float max = Float.MIN_VALUE;
	            
	            for(int i = 0; i < prices.length(); i++)
	            {
	            	JSONObject price = prices.getJSONObject(i);
	            	Float value = Float.valueOf((Integer) price.get("price"));
	            	if(value < min) min = value;
	            	if(value > max) max = value;
	            }
	            
	            min = Math.round(min * 100) / 100f;
	            max = Math.round(max * 100) / 100f;
	            
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
        
        //addListenerOnButton();
    }
	
	
	
	
	
	public void onGoBackButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, MainActivity.class);
    	this.startActivity(searchIntent);
    }
	
}