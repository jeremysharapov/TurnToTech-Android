/* 
 * This is an example of how to record video in a simple way on your Android device. 
 */

package org.turntotech.videocapture;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;
 
public class MainActivity extends Activity {
 
    private Uri fileUri; // file url to store image/video
 
    private VideoView videoPreview;
    private Button btnRecordVideo;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - VideoCapture");
        
		String outputFilePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/myvideo.mp4";
		fileUri = Uri.fromFile(new File(outputFilePath));

 
        videoPreview = (VideoView) findViewById(R.id.videoPreview);
        btnRecordVideo = (Button) findViewById(R.id.btnRecordVideo);
 
        /**
         * Record video button click event
         */
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View v) {
                // record video
                recordVideo();
            }
        });
 
        
        videoPreview.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				videoPreview.start();
				return true;
			}
  
        });
    }
 
 
    // Recording video
    private void recordVideo() {
    	
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			// this device has a camera

	        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
	        // set video quality
	        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
	 
	        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
	                                                            // name
	        // start the video capture Intent
	        startActivityForResult(intent, 1);

		} else {
			// no camera on this device
			Toast.makeText(getApplicationContext(),
					"Sorry! Your device doesn't support camera",
					Toast.LENGTH_LONG).show();
		}
		
    }
 
    // Receiving activity result method will be called after closing the camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check if the result is available
            if (resultCode == RESULT_OK) {
                // video successfully recorded
                // preview the recorded video
                videoPreview.setVideoPath(fileUri.getPath());
                // start playing
                videoPreview.start();
            }
    }
 
     

}