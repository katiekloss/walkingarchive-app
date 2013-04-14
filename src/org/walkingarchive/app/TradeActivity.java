package org.walkingarchive.app;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class TradeActivity extends Activity {
    ArrayList<String> crListItems=new ArrayList<String>();
    ArrayList<String> cgListItems=new ArrayList<String>();
    ArrayAdapter crListAdapter;
    ArrayAdapter cgListAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trade);
        ListView crList = (ListView) findViewById(R.id.cardsReceivingList);
        ListView cgList = (ListView) findViewById(R.id.cardsGivingList);
//        TextView tv = new TextView();
        crListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                crListItems);
        crList.setAdapter(crListAdapter);
        
        cgListAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                cgListItems);
        cgList.setAdapter(cgListAdapter);
        
        Button addReceiving = (Button) findViewById(R.id.cardsReceivingButton);
        Button addGiving = (Button) findViewById(R.id.cardsGivingButton);
        
        addReceiving.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                addToReceivingList(v);
            }
        });
        
        addGiving.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View v) {
                addToGivingList(v);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trade, menu);
        return true;
    }
    
    public void addToReceivingList (View v) {
        crListItems.add("test");
        crListAdapter.notifyDataSetChanged();
    }
    
    public void addToGivingList (View v) {
        cgListItems.add("test");
        cgListAdapter.notifyDataSetChanged();
    }

}
