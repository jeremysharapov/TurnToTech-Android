/*
 * This example downloads the google logo asynchronously.
 */
package org.turntotech.asyncdownload;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	long startSec;
	long curSec;
	TextView t;
	private ImageView downloadedImg;
	// Using this url we download the image

	// if you want to see the actual difference use following url
	//private String downloadUrl = "https://course_report_production.s3.amazonaws.com/rich/rich_files/rich_files/1212/original/mobile-development-turntotech-header.png";


	private String downloadUrl = "https://farm6.staticflickr.com/5524/14540237013_9e31e5b6a2_o.png";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - Asynchronous Download");
		downloadedImg = (ImageView) findViewById(R.id.imageView);
		t = (TextView)findViewById(R.id.textView2);
	}

	public void clickSync(View view) {

		try {
			Bitmap result = new ImageDownloader().execute(downloadUrl).get();
			downloadedImg.setImageBitmap(result);
		} catch (Exception e) {
			e.printStackTrace();

			Log.i("Async-Example", e.getMessage());

		}
	}

	public void clickAsync(View view) {
		// 2. This kicks off the download.

		// 3. The execute method internally calls onPreExecute() right away.
		// Next, doInBackground(...) is called by the runtime in a separate
		// thread and
		// onPostExecute(...) in the UI thread after doInBackground(...) is
		// done.

		new ImageDownloader().execute(downloadUrl);

		// 4. Technical note: A single separate thread is used for the
		// background task. The User Interface cannot be updated from
		// that thread.

		Log.i("Async-Example",
				"This line will print after onPreExecute() finishes");

	}

	public void clickActivity(View view) {
		// if(view.getBackground())
		Button button = (Button) view;
		button.setText("Clicked at " + new Date());
	}

	private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

		@Override
		/*
		 * onPreExecute(), invoked on the UI thread before the background task
		 * is executed. This is on the UI thread so you can show something on
		 * the UI
		 */
		protected void onPreExecute() {
			startSec = (System.currentTimeMillis());
			Log.i("Async-Example", "onPreExecute Called");
		}




		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
			t.setText(String.valueOf(curSec - startSec));
		}

		@Override
		/*
		 * onPostExecute(Result), is also invoked on the UI thread after the
		 * background computation finishes. Here you get a chance to populate
		 * the UI with the results of the background task
		 */
		protected void onPostExecute(Bitmap result) {
			Log.i("Async-Example",
					"onPostExecute Called after doInBackground(...) finishes ");

			// Since we are on the main UI thread, we can update the user
			// interface
			downloadedImg.setImageBitmap(result);
            //t.setText(String.valueOf(curSec - startSec));

		}

		@Override
		/*
		 * This runs in background i.e. in parallel with the UI. Your users can
		 * still work with your app while this method is executing. This method
		 * cannot update the UI. For that, you should use the onPostExecute(...)
		 * method
		 */
		protected Bitmap doInBackground(String... param) {
			// TODO Auto-generated method stub


			return downloadBitmap(param[0]);
		}

		// This is a typical implementation for downloading a resource from the
		// Internet using HTTP

		private Bitmap downloadBitmap(String urlString) {
			try {
				URL url = new URL(urlString);
				HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();

				InputStream is = httpCon.getInputStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead, totalBytesRead = 0;
				byte[] data = new byte[5000];

				while ((nRead = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
                    curSec =  (System.currentTimeMillis() );
                    publishProgress();
					totalBytesRead += nRead;
				}

				byte[] image = buffer.toByteArray();
				Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
				return bitmap;
			} catch (Exception e) {
				// here
				e.printStackTrace();
			}
			return null;
		}

	}

}
