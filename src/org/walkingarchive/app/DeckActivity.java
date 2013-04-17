package org.walkingarchive.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class DeckActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deck);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.deck, menu);
        return true;
    }
    
    public void addCard (View v) {
        //go to search page
    }

}
