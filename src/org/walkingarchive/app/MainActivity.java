package org.walkingarchive.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.content.Intent;

/**
 * Renders the main menu and runs app startup code
 * @author Katie
 *
 */
public class MainActivity extends Activity {

    /**
     * Displays the menu items and copies Tesseract assets into application storage
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyAsset("eng.traineddata", "tessdata");
        copyAsset("eng.user-words", "tessdata");
    }

    /**
     * Copies a file from the APK "assets" folder into the app-specific storage location
     * @param filename  Name of the file to copy, relative to "assets/"
     * @param destDir   The directory to copy into, relative to the top-level app storage folder
     */
    public void copyAsset(String filename, String destDir)
    {
        try
        {
            new File(getFilesDir().toString() + "/" + destDir).mkdir();
            File outputFile = new File(getFilesDir().toString() + "/" + destDir + "/" + filename);
            if(outputFile.exists())
                return;

            FileOutputStream output = new FileOutputStream(outputFile);
            InputStream input = getAssets().open(filename);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0)
            {
                output.write(buffer, 0, length);
            }
            output.close();
            input.close();
            Log.i("copyAsset", "Copied " + filename);
        }
        catch (FileNotFoundException e) { }
        catch (IOException e) { }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Runs the Search activity when the Search button is clicked
     * @param v  The Search button
     */
    public void onSearchButtonDown(View v) {
        Intent searchIntent = new Intent(this, SearchActivity.class);
        this.startActivity(searchIntent);
    }
    
    /**
     * Runs the Trade activity when the Trade button is clicked
     * @param v  The Trade button
     */
    public void onTradeButtonDown(View v) {
        Intent intent = new Intent(this, TradeHistoryActivity.class);
        this.startActivity(intent);
    }
    
    /**
     * Runs the DeckList activity when the Decks button is clicked
     * @param v  The button
     */
    public void onCollectionButtonDown(View v) {
        Intent intent = new Intent(this, DeckListActivity.class);
        this.startActivity(intent);
    }
}
