package org.walkingarchive.app;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.walkingarchive.app.api.WalkingArchiveApi;


public class ImageActivity extends Activity {

	private Uri imageUri;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    
	    imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "card.jpg"));

        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(pictureIntent, 1);        
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1) 
		{
			if (resultCode == RESULT_OK) // TODO: Handle RESULT_CANCELLED resultCode
			{
				Bitmap image = BitmapFactory.decodeFile(imageUri.toString());
				
				org.walkingarchive.app.ocr.OCR ocrRunner = new org.walkingarchive.app.ocr.OCR();
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
			} else {
				// TODO: wtf happens here?
			}
		}        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}
	
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
