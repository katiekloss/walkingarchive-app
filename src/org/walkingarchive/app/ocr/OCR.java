package org.walkingarchive.app.ocr;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;

/**
 * Abstracts the Tesseract OCR functionality
 * @author Katie
 *
 */
public class OCR {

	private String baseDir;
	
	/**
	 * Initialize the OCR with the given data directory.
	 * <p>
	 * The directory must have a subfolder named "tessdata" that contains the language-specific training files.
	 * </p>
	 * @param baseDir  Absolute String path to the data directory
	 */
	public OCR(String baseDir) 
	{
		this.baseDir = baseDir;
	}
	
	/**
	 * Perform OCR on the passed Bitmap and return any recognized text.
	 * @param image  The Bitmap to recognize. Must be non-null (obviously).
	 * @return       Recognized text, with any non-ASCII text replaced with whitespace.
	 */
	public String runOCR(Bitmap image)
	{
		TessBaseAPI tesseract = new TessBaseAPI();
		tesseract.init(this.baseDir, "eng");
		tesseract.setImage(image);
		String text = tesseract.getUTF8Text();
		tesseract.end();
		return text.replaceAll("[^a-zA-Z0-9]", " "); 
	}
}