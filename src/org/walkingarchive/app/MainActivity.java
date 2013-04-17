package org.walkingarchive.app;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onSearchButtonDown(View v) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        this.startActivity(searchIntent);
    }
    
    public void onTradeButtonDown(View v) {
        Intent intent = new Intent(this, TradeHistoryActivity.class);
        this.startActivity(intent);
    }
    
    public void onCollectionButtonDown(View v) {
        Intent intent = new Intent(this, DeckListActivity.class);
        this.startActivity(intent);
    }
}
