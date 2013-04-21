package org.walkingarchive.app.ocr;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;

public class OCR {

	private String baseDir;
	
	public OCR(String baseDir) 
	{
		this.baseDir = baseDir;
	}
	
	public String runOCR(Bitmap image)
	{
	    Bitmap tesseractImage = image.copy(Bitmap.Config.ARGB_8888, true);
		TessBaseAPI tesseract = new TessBaseAPI();
		tesseract.init(this.baseDir, "eng");
		tesseract.setImage(tesseractImage);
		String text = tesseract.getUTF8Text();
		tesseract.end();
		return text;
		//return "Defender (This creature can't attack.) Walking Archive comes into play with a +1/+1 counter on it. At the beginning of each player's upkeep, that player draws a card for each +1/+1 counter on Walking Archive. : Put a +1/+1 counter on Walking Archive"; 
	}
}