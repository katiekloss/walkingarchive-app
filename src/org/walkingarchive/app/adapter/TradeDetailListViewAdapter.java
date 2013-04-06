package org.walkingarchive.app.adapter;

import static org.walkingarchive.app.adapter.Constant.FIRST_COLUMN;
import static org.walkingarchive.app.adapter.Constant.SECOND_COLUMN;
import static org.walkingarchive.app.adapter.Constant.THIRD_COLUMN;
import static org.walkingarchive.app.adapter.Constant.FOURTH_COLUMN;

import java.util.ArrayList;
import java.util.HashMap;

import org.walkingarchive.app.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author Paresh N. Mayani
 * http://pareshnmayani.wordpress.com
 */
public class TradeDetailListViewAdapter extends BaseAdapter
{
	public ArrayList<HashMap<String,String>> list;
	Activity activity;
	
	public TradeDetailListViewAdapter(Activity activity, ArrayList<HashMap<String,String>> list) {
		super();
		this.activity = activity;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	  
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
			//	ViewHolder holder;
				LayoutInflater inflater =  activity.getLayoutInflater();
				View row;
				row = inflater.inflate(R.layout.trade_detail_list, null);
				HashMap<String, String> map = list.get(position);
				
				TextView textview = (TextView) row.findViewById(R.id.buyCardName);
	    		TextView textview2 = (TextView) row.findViewById(R.id.buyCardPrice);
	    		TextView textview3 = (TextView) row.findViewById(R.id.sellCardName);
	    		TextView textview4 = (TextView) row.findViewById(R.id.sellCardPrice);
				
	    		textview.setText(map.get(FIRST_COLUMN));
	    		textview2.setText(map.get(SECOND_COLUMN));
	    		textview3.setText(map.get(THIRD_COLUMN));
	    		textview4.setText(map.get(FOURTH_COLUMN));

			return row;
	}

}
