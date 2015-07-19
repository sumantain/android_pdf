package com.sbw.createwritepdf;

import java.io.ByteArrayOutputStream;
import java.util.Random;

import com.sbw.createwritepdf.fileoperation.FileOperations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private EditText content_data;
	private Button save_send;
	Random rand;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		init();
	}

	private void init() {
		save_send = (Button)findViewById(R.id.save_send);
		content_data = (EditText)findViewById(R.id.content_data);
		rand = new Random();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		save_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.save_send:
			createRight(content_data.getText().toString());
			break;

		default:
			break;
		}
	}
	
	public byte[] getByteArray(Bitmap bitmap) {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
	    return bos.toByteArray();
	}

	private void createRight(String contentBody) {
		 if(contentBody != null && contentBody.length() > 0){
			 String name = String.valueOf(rand.nextInt(99999 - 9999) + 9999);
			 FileOperations fileOpt = new FileOperations(MainActivity.this);
			 Boolean status = fileOpt.writeView(name, content_data);
			 if(status){
				 Toast.makeText(getApplicationContext(), "Pdf generated successfully", Toast.LENGTH_LONG).show();
			 }else{
				 Toast.makeText(getApplicationContext(), "Pdf generation error", Toast.LENGTH_LONG).show();
			 }
		 }else{
			 Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_alert), Toast.LENGTH_LONG).show();
		 }
	}
}
