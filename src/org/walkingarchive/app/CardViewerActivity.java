package org.walkingarchive.app;

import android.app.Activity;
import android.os.Bundle;

public class CardViewerActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_viewer);
        
        // TODO: Use Intent.getStringExtra() to pull the card's properties and
        // set the Views' text properties appropriately
    }
}