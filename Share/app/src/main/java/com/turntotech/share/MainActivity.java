/*
 * This is a simple example of sharing using our own app.
 * For example: When we want to share an image, we can display options for different
 * apps that might use it. In this example we can see our own app as a option of sharing in share menu.
 * On the Android platform, the Intent system allows users to share content 
 * between apps. You can send and receive multiple types of data on Android.
 * Note: In order for your app to appear in the chooser list presented when 
 * the user attempts to share data from another app, 
 * you need to alter the Project Manifest file.
 */


package com.turntotech.share;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public String getPath(Uri uri)  
	{ 
	    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
	    cursor.moveToFirst();
	    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
	    return cursor.getString(idx);
	} 
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
	{
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 2;

		if (height > reqHeight || width > reqWidth) 
		{
			if (width > height) 
			{
				inSampleSize = Math.round((float)height / (float)reqHeight);
			}
			else
			{
				inSampleSize = Math.round((float)width / (float)reqWidth);
			}
		}
		return inSampleSize;
	}
	
	public static Bitmap decodeSampledBitmapFromResource(String resId,
	        int reqWidth, int reqHeight) 
	{

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeFile(resId, options);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - Share");

		//get the image view
		ImageView picView = (ImageView)findViewById(R.id.picture);
		//get the text view
		TextView txtView = (TextView)findViewById(R.id.txt);

		//get the received intent
		Intent receivedIntent = getIntent();
		//get the action
		String receivedAction = receivedIntent.getAction();
		//find out what we are dealing with
		String receivedType = receivedIntent.getType();

		//make sure it's an action and type we can handle
		if(receivedAction.equals(Intent.ACTION_SEND))
		{
			if(receivedType.startsWith("text/"))
			{
				//hide the other ui item
				picView.setVisibility(View.GONE);
				//get the received text
				String receivedText = receivedIntent.getStringExtra(Intent.EXTRA_TEXT);
				//check we have a string
				if (receivedText != null) 
				{
					//set the text
					txtView.setText(receivedText);
				}
			}
			else if(receivedType.startsWith("image/")){
				//hide the other ui item
				txtView.setVisibility(View.GONE);
				//get the uri of the received image
				Uri receivedUri = (Uri)receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
				//check we have a uri
				if (receivedUri != null) {
					//set the picture
					//RESAMPLE YOUR IMAGE DATA BEFORE DISPLAYING
					/*
					 * picView.setImageURI(receivedUri);
					 * you can use above code but it is not efficient for large size image .
					 * so we create additional 3 functions
					 * 		1. getPath()
					 * 		2. calculateInSampleSize()
					 * 		3. decodeSampledBitmapFromResource()
					 * for sampling the image properly
					*/
					String selectedImagePath = getPath(receivedUri);
					picView.setImageBitmap(decodeSampledBitmapFromResource(selectedImagePath, 150, 150));
				}
			}
			else if(receivedType.startsWith("audio/")){
				txtView.setVisibility(View.GONE);
				picView.setVisibility(View.GONE);
				Uri receivedUri = (Uri)receivedIntent.getParcelableExtra(Intent.EXTRA_STREAM);
				if (receivedUri != null) {
					MediaPlayer mediaPlayer = MediaPlayer.create(this, receivedUri);
					mediaPlayer.start();
				}
			}
		}
		else if(receivedAction.equals(Intent.ACTION_MAIN)){
			//app has been launched directly, not from share list
			txtView.setText("Nothing has been shared!");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
