package org.walkingarchive.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.graphics.Bitmap;
import android.net.Uri;
import java.io.File;
import java.io.IOException;

import org.walkingarchive.app.api.WalkingArchiveApi;

/**
 * Displays the Android camera and performs OCR on the captured image
 */
public class ImageActivity extends Activity {

    public static final int REQUEST_IMAGE_CAPTURE = 150;
    
	private Uri imageUri;

	/**
	 * Initializes the Android camera and ensures it has a file to write the image to.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    File outputFile = new File(Environment.getExternalStorageDirectory(), "card.jpg");

        try
        {
            outputFile.createNewFile();
        }
        catch (IOException e) {
            return;
        }
        
	    imageUri = Uri.fromFile(outputFile);

        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(pictureIntent, REQUEST_IMAGE_CAPTURE);
	}

	/**
	 * Callback for when the Camera finishes. Performs a search with the OCR-ed text.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_IMAGE_CAPTURE)
		{
			if (resultCode == RESULT_OK) // TODO: Handle RESULT_CANCELLED resultCode
			{
			    Bitmap image = null;
                try
                {
                    image = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                }
                catch (IOException e) { }

				org.walkingarchive.app.ocr.OCR ocrRunner = new org.walkingarchive.app.ocr.OCR(getFilesDir().toString());
				String ocrResults = ocrRunner.runOCR(image);

				WalkingArchiveApi api = new WalkingArchiveApi();
				AsyncTaskCallback callback = new AsyncTaskCallback()
				{
					public void run(Object o)
					{
						onSearchResults((String) o);
					}
				};
				api.searchAsync(ocrResults, callback);
			} else { }
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image, menu);
        return true;
    }

    /**
     * Callback for when the API returns search results. Launches the Search Results activity.
     * @param jsonResults
     */
    public void onSearchResults(String jsonResults)
    {
        if(jsonResults != null)
        {
            Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
            searchResultsIntent.putExtra("resultString", jsonResults);
            this.startActivity(searchResultsIntent);
        } else {
            // TODO: log it
        }
    }

}
