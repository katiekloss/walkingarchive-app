package org.walkingarchive.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.walkingarchive.app.api.WalkingArchiveApi;

public class SearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }

    
    public void onSearchButtonDown(View view)
    {
        EditText cardName = (EditText) findViewById(R.id.cardName);
        if(cardName.getText().toString().equals(""))
        {
            // Should probably show a notification or something here.
            return;
        }
        
        AsyncTaskCallback resultsCallback = new AsyncTaskCallback()
        {
            public void run(Object o)
            {
                searchResultsReturned((String) o);
            }
        };
        
        WalkingArchiveApi api = new WalkingArchiveApi();
        api.getCardByNameAsync(cardName.getText().toString(), resultsCallback);
    }
    
    public void searchResultsReturned(String resultString)
    {
        if(resultString != null)
        {
            Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
            searchResultsIntent.putExtra("resultString", resultString);
            this.startActivity(searchResultsIntent);
        } else {
            // I have no idea what to do here.
        }
    }

/*    
    public void onOpenCamera(View v) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        
        File file = new File(Environment.getExternalStorageDirectory(), "app-test.jpg");
        
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
                   //Call Tesseract on the Image here
               }
            }        
        }
    
*/
/*    public void onOpenCamera(View v) {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        this.startActivityForResult(pictureIntent, 2);
    }
    */

    
    public void onOpenCamera(View v) {
        Intent intent = new Intent(this, ImageActivity.class);
        this.startActivity(intent);
    }
}

