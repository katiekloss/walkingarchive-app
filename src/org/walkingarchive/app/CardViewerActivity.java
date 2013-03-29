package org.walkingarchive.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CardViewerActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_viewer);
        
        // TODO: Use Intent.getStringExtra() to pull the card's properties and
        // set the Views' text properties appropriately
        
        final Context context = this;
		Button gobackButton = (Button) findViewById(R.id.gobackButton);
		
		gobackButton.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, SearchActivity.class);
                            startActivity(intent);   
 
			}
 
		});
		
        
    }
}