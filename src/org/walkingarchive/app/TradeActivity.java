package org.walkingarchive.app;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import java.util.ArrayList;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;

public class TradeActivity extends Activity {

	EditText editText1;
    ListView buyCardsList;
    ListView sellCardsList;

    Context context = this;
    MyCustomAdapter buyCardsListAdapter = new MyCustomAdapter();
    MyCustomAdapter sellCardsListAdapter = new MyCustomAdapter();

    
    String[] text = { "Card1", "Card2", "Card3", "Card4", "Card5"};

    int[] image = { R.drawable.ic_launcher , R.drawable.ic_launcher, R.drawable.ic_launcher,
                                             R.drawable.ic_launcher, R.drawable.ic_launcher };
    String[] buy_price = {"1$", "2$", "3$", "4$", "5$"};
    
    String[] text2 = { "Sell1", "Sell2", "Sell3", "Sell4", "Sell5"};

    int[] image2 = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
                                             R.drawable.ic_launcher, R.drawable.ic_launcher };
    
    String[] sell_price = {"1$", "2$", "3$", "4$", "5$"};
    
    
    
    
    int textlength = 0;
    public static ArrayList<String> text_sort_buy = new ArrayList<String>();
    public static ArrayList<Integer> image_sort_buy = new ArrayList<Integer>();
    public static ArrayList<String> price_sort_buy = new ArrayList<String>();
    public static ArrayList<String> text_sort_sell = new ArrayList<String>();
    public static ArrayList<Integer> image_sort_sell = new ArrayList<Integer>();
    public static ArrayList<String> price_sort_sell = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trade);
		
		buyCardsList = (ListView) findViewById(R.id.buyCardsList);
		sellCardsList = (ListView) findViewById(R.id.sellCardsList);
		editText1 = (EditText) findViewById(R.id.editText1);
		
		 for(int i =0; i< buy_price.length; i++){
	           text_sort_buy.add(text[i]);
	           image_sort_buy.add(image[i]);
	           price_sort_buy.add(buy_price[i]);
	       }
		 
		 for(int i =0; i< sell_price.length; i++){
	           text_sort_sell.add(text2[i]);
	           image_sort_sell.add(image2[i]);
	           price_sort_sell.add(sell_price[i]);
	       }
	       
		 
		 buyCardsListAdapter.set(text_sort_buy, image_sort_buy, price_sort_buy);
		 buyCardsList.setAdapter(buyCardsListAdapter);
		 
		 sellCardsListAdapter.set(text_sort_sell, image_sort_sell, price_sort_sell);
		 sellCardsList.setAdapter(sellCardsListAdapter);		
	}


	class MyCustomAdapter extends BaseAdapter{

    	String[] data_text;
    	int[] data_image;
    	String[] data_price;

    	MyCustomAdapter(){

    	}

    	MyCustomAdapter(ArrayList<String> text, ArrayList<Integer> image, ArrayList<String> price){
    			data_text = new String[text.size()];
    			data_image = new int[image.size()];
                data_price = new String[price.size()];
    			for (int i = 0; i < text.size(); i++) {
                    data_text[i] = text.get(i);
                    data_image[i] = image.get(i);
                    data_price[i] = price.get(i);
    			}

    	}
    	public void set (ArrayList<String> text, ArrayList<Integer> image, ArrayList<String> price){

    		data_text = new String[text.size()];
			data_image = new int[image.size()];
			data_price = new String[price.size()];

			for (int i = 0; i < text.size(); i++) {
                data_text[i] = text.get(i);
                data_image[i] = image.get(i);
                data_price[i] = price.get(i);
			}
			
    	}

    	
    	public int getCount(){
    		return data_text.length;
    	}

    	public String getItem(int position){
    			return null;
    	}

    	public long getItemId(int position){
    			return position;
                                }

    	public View getView(final int position, View convertView, final ViewGroup parent){

    			LayoutInflater inflater = getLayoutInflater();
    			View row;

    			row = inflater.inflate(R.layout.trade_list, parent, false);

    			TextView textview = (TextView) row.findViewById(R.id.TextView01);
    			TextView textview2 = (TextView) row.findViewById(R.id.TextView02);
    			final ImageButton imagButton = (ImageButton) row.findViewById(R.id.ImageButton01);
      
    			imagButton.setOnClickListener(
    					new Button.OnClickListener() {
    						@Override
    						public void onClick(View v) {

    							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
               
    							// set title
    							alertDialogBuilder.setTitle("Delete the card ");
               
    							// set dialog message
    							alertDialogBuilder
    							.setMessage("Click yes to delete the card!")
    							.setCancelable(false)
    							.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
    								public void onClick(DialogInterface dialog,int id) {
              						
    									if(parent.getId() == 2131296297){

    										text_sort_buy.remove(position);
    										image_sort_buy.remove(position);
    										price_sort_buy.remove(position);
    										buyCardsListAdapter = new MyCustomAdapter(text_sort_buy, image_sort_buy, price_sort_buy);
    										buyCardsList.setAdapter(buyCardsListAdapter);
    							        
    									}else{
    										
    										text_sort_sell.remove(position);
    										image_sort_sell.remove(position);
    										price_sort_sell.remove(position);
    										sellCardsListAdapter = new MyCustomAdapter(text_sort_sell, image_sort_sell, price_sort_sell);
    										sellCardsList.setAdapter(sellCardsListAdapter);
    										   										
    									}    										
    									
    								dialog.cancel();
    								}
              				  	})
              				  	.setNegativeButton("No",new DialogInterface.OnClickListener() {
              				  		public void onClick(DialogInterface dialog,int id) {
              						// if this button is clicked, just close
              						// the dialog box and do nothing
              						dialog.cancel();
              					}
              				  	});
               
              				// create alert dialog
              				AlertDialog alertDialog = alertDialogBuilder.create();
               
              				// show it
              				alertDialog.show();
              			}
              }
          );
      

    			textview.setText(data_text[position]);
    			imagButton.setImageResource(data_image[position]);
    			textview2.setText(data_price[position]);
    			return (row);}
    }


	
	
	public void onGoBackButtonDown(View v)
    {
    	Intent searchIntent = new Intent(this, MainActivity.class);
    	this.startActivity(searchIntent);
    }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trade, menu);
		return true;
	}

}
