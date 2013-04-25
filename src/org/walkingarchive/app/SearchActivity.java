package org.walkingarchive.app;

import org.json.JSONException;
import org.json.JSONObject;
import org.walkingarchive.app.api.WalkingArchiveApi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
    }

    
    public void onSearchButtonDown(View view)
    {
        EditText cardName = (EditText) findViewById(R.id.cardName);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        
        AsyncTaskCallback resultsCallback = new AsyncTaskCallback()
        {
            public void run(Object o)
            {
                searchResultsReturned((String) o);
            }
        };
        
        WalkingArchiveApi api = new WalkingArchiveApi();
        //get the first page of results
        api.getCardByNameManaTypeAsync(cardName.getText().toString(), 
                typeSpinner.getSelectedItem().toString(), 
                colorSpinner.getSelectedItem().toString(), 
                1,
                resultsCallback);
    }
    
    public void searchResultsReturned(String resultString)
    {

        EditText cardName = (EditText) findViewById(R.id.cardName);
        Spinner typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        Spinner colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
        if(resultString != null)
        {
            JSONObject parameters = new JSONObject();
            
            try {
                parameters.put("name", cardName.getText().toString());
                parameters.put("type", typeSpinner.getSelectedItem().toString());
                parameters.put("mana", colorSpinner.getSelectedItem().toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Intent searchResultsIntent = new Intent(this, SearchResultsActivity.class);
            searchResultsIntent.putExtra("resultString", resultString);
            searchResultsIntent.putExtra("searchParameters", parameters.toString());
            if(getIntent().hasExtra("forwardResult"))
            {
                searchResultsIntent.putExtra("forwardResult", true);
                this.startActivityForResult(searchResultsIntent, 0);
            } else {
                this.startActivity(searchResultsIntent);
            }
        } else {
            // Create an alert to let the user know no results were found
        }
    }

    public void onOpenCamera(View v)
    {
        Intent intent = new Intent(this, ImageActivity.class);
        this.startActivity(intent);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        setResult(resultCode, data);
        finish();
    }
}

