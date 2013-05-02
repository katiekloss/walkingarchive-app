package org.walkingarchive.app;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.ui.TradeCard;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Formats a List<TradeCard> for rendering
 */
public class TradeCardAdapter extends BaseAdapter {
    private ArrayList<TradeCard> cardList;
    private Context context;
    
    public TradeCardAdapter (Context context, ArrayList<TradeCard> cards) {
        super();
        this.context = context;
        cardList = cards;
    }
    
    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;
        if (result == null) {
            result = View.inflate(context, R.layout.trade_history_row, null);
        }
        
        TextView nameView = (TextView) result.findViewById(R.id.cardName);
        nameView.setText(((TradeCard) getItem(position)).toString());
        
        TextView valueView = (TextView) result.findViewById(R.id.cardValue);
        try {
            JSONObject card = new JSONObject(((TradeCard) getItem(position)).toJson());
            JSONArray prices = card.getJSONArray("prices");
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
                    valueView.setText(NumberFormat.getCurrencyInstance().format(min));
                else
                    valueView.setText(NumberFormat.getCurrencyInstance().format(min)
                            + " - " + NumberFormat.getCurrencyInstance().format(max)
                            );
            } else {
                valueView.setText("N/A");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return result;
    }

}
