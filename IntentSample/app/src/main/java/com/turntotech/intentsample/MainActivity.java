/*
 * Android Intent is an object carrying an intent ie. message from 
 * one component to another component with-in the application or outside the application.
 * This example shows you a practical use of using Intent objects to launch an Email client
 * to send an Email to the given recipients.
 */
package com.turntotech.intentsample;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {

	TextView text;
	private int PICK_IMAGE_REQUEST = 1;
	ImageView image;
	Intent emailIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project name - IntentSample");

		image = (ImageView)findViewById(R.id.Image);
		text = (TextView) findViewById(R.id.Path);
		Button startBtn = (Button) findViewById(R.id.sendEmail);		
		startBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				sendEmail();
			}
		});
		 		

		// 1. Use the ACTION_SEND action to launch an email client installed on your Android device. The user will be asked to choose the client.
		emailIntent = new Intent(Intent.ACTION_SEND);

		String[] TO = { "nyc@turntotech.io" };
		
		
		/*
		 * To send an email we need to specify mailto: as URI using setData()
		 * method and data type will be to text/plain using setType()
		 */
		//2. Set other properties on the emailIntent object such as TO, SUBJECT, BODY
		emailIntent.setData(Uri.parse("mailto:"));
		emailIntent.setType("text/plain");

		/*
		 * Android has built-in support to add TO, SUBJECT, CC, TEXT etc. fields
		 * which can be attached to the intent before sending the intent to a
		 * target email client.
		 */
		emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
		// emailIntent.putExtra(Intent.EXTRA_CC, CC);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT,
				"Email client launched from an Android app");
		emailIntent.putExtra(Intent.EXTRA_TEXT,
				"We used an Intent object to launch the Email client.");

	}

	//3. sendEmail() method will be called when user clicks the button on the UI
	private void sendEmail() {

		Log.i("Sending email", "");

		try {
			
			//4. Fire the activity which will send the email. The email intent is provided as an argument.
			startActivity(Intent.createChooser(emailIntent, "Send mail..."));
			finish();
			Log.i("Finished sending email...", "");
			
		} catch (android.content.ActivityNotFoundException ex) {
			Toast.makeText(MainActivity.this,
					"There is no email client installed..", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void choosePhoto(View view){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
			Uri uri = data.getData();
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
				text.setText(uri.toString());
				image.setImageBitmap(bitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
