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
import android.net.Uri;
import android.widget.Button;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.googlecode.leptonica.android.Pix;
import com.googlecode.tesseract.android.TessBaseAPI;

import bin.classes.*;
import bin.classes.com.googlecode.leptonica.android.ReadFile;

public class ImageActivity extends Activity {

	public static File file;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    
	    file = new File(Environment.getExternalStorageDirectory(), "card.jpg");
	    
	    Uri outputFileUri = Uri.fromFile(file);
        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(pictureIntent, 1);     
     

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
				if (requestCode == 1){
			Uri imageUri = null;
			if (data!=null) {
				if (data.hasExtra("data")) {
					Bitmap thumbnail = data.getParcelableExtra("data");
				}	
			} else {
				
			}
			
			
			//call tesseract
			
			//This is using the tess-two library
			//Pix pix = ReadFile.readFile(file);
			
			
			
			//This should work, but it gives an error 
			/*
			File myDir = getExternalFilesDir(Environment.MEDIA_MOUNTED);

			TessBaseAPI baseApi = new TessBaseAPI();
			baseApi.init(myDir.toString(), "eng"); // myDir + "/tessdata/eng.traineddata" must be present
			baseApi.setImage(file);

			String recognizedText = baseApi.getUTF8Text(); // Log or otherwise display this string...
			baseApi.end();
			*/
			
			//save file to enviornment
			
			
			//intent to show output of text file
		/*	Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),"text/*");
			startActivity(intent);
			*/
			
			
			
			//intent to show output of picture
			/*
			Intent intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file),"image/*");
			startActivity(intent);
			*/
					
		}        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image, menu);
		return true;
	}

}
