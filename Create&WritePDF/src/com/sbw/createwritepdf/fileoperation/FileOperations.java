/**
 * 
 */
package com.sbw.createwritepdf.fileoperation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Sumanta
 *
 */
public class FileOperations {
	

	public FileOperations(Context mContext) {
	}

	public Boolean writeView(String fname, View view) {
		try {
			String fpath = "/sdcard/" + fname + ".pdf";
			File file = new File(fpath);
			// If file does not exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			Bitmap bitmap = getBitmapFromView(view);// .getDrawingCache();
			
			Matrix matrix = new Matrix();
		    matrix.postRotate(180);
		    Bitmap bitMap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			
			byte[] byteArray = convertBitmapToByteArray(bitMap);

			// step 1
			Document document = new Document();
			// step 2
			PdfWriter.getInstance(document,
					new FileOutputStream(file.getAbsoluteFile()));
			// step 3
			document.open();
			// step 4

			Image image1 = Image.getInstance(byteArray);
			document.add(image1);

			// step 5
			document.close();

			Log.d("Suceess", "Sucess");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (DocumentException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @category converter
	 * @param View
	 * @return bitmap
	 * */
	public static Bitmap getBitmapFromView(View view) {
		Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(),
				view.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = view.getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		view.draw(canvas);
		return returnedBitmap;
	}

	/**
	 * @param bitmap
	 *            Bitmap object from which you want to get bytes
	 * @return byte[] array of bytes by compressing the bitmap to PNG format <br/>
	 *         null if bitmap passed is null (or) failed to get bytes from the
	 *         bitmap
	 */
	public static byte[] convertBitmapToByteArray(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		} else {
			byte[] b = null;
			try {
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				bitmap.compress(CompressFormat.PNG, 0, byteArrayOutputStream);
				b = byteArrayOutputStream.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return b;
		}
	}
}
