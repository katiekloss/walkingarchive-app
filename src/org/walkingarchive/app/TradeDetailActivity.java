package org.walkingarchive.app;

import static org.walkingarchive.app.adapter.Constant.FIRST_COLUMN;
import static org.walkingarchive.app.adapter.Constant.SECOND_COLUMN;
import static org.walkingarchive.app.adapter.Constant.THIRD_COLUMN;
import static org.walkingarchive.app.adapter.Constant.FOURTH_COLUMN;

import org.walkingarchive.app.adapter.TradeDetailListViewAdapter;


import java.util.ArrayList;
import java.util.HashMap;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class TradeDetailActivity extends Activity {

	private ArrayList<HashMap<String,String>> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade_detail);
		
		ListView lview = (ListView) findViewById(R.id.tradeDetailList);
        generateList();
        TradeDetailListViewAdapter adapter = new TradeDetailListViewAdapter(this, list);
        lview.setAdapter(adapter);
				
	}
	
	
private void generateList() {
		
    	list = new ArrayList<HashMap<String,String>>();
    	
    	for(int i =0; i < 5; i++){
    		HashMap<String,String> temp = new HashMap<String,String>();
    		temp.put(FIRST_COLUMN,"Buy Card Name");
			temp.put(SECOND_COLUMN, "1$");
			temp.put(THIRD_COLUMN, "Sell Card Name");
			temp.put(FOURTH_COLUMN, "2$");
			list.add(temp);    		
    	}    	
	}

	public void onGoBackButtonDown(View v){
		Intent searchIntent = new Intent(this, MainActivity.class);
		this.startActivity(searchIntent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trade_detail, menu);
		return true;
	}

}
