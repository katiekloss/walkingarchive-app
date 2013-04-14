package org.walkingarchive.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
        try
        {
        	new File(getFilesDir().toString() + "/tessdata").mkdir();
        	File outputFile = new File(getFilesDir().toString() + "/tessdata/eng.traineddata");
			FileOutputStream output = new FileOutputStream(outputFile);
			InputStream input = getAssets().open("eng.traineddata");
			byte[] buffer = new byte[1024];
			int length;
			while ((length = input.read(buffer)) > 0)
			{
				output.write(buffer, 0, length);
			}
			output.close();
			input.close();
		}
        catch (FileNotFoundException e)
        {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (IOException e)
        {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
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
        Intent tradeIntent = new Intent(this, TradeActivity.class);
        this.startActivity(tradeIntent);
    }
    
    public void onTradeHistoryButtonDown(View v) {
        Intent intent = new Intent(this, TradeHistoryActivity.class);
        this.startActivity(intent);
    }
}
