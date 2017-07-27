package org.turntotech.simplenavigate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class OtherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Second view
		setContentView(R.layout.activity_other);
	}

	// Back to main view
	public void buttonClicked(View view) {
		startActivity(new Intent(this, MainActivity.class));
	}

	public void gotoLast(View view){
		startActivity(new Intent(this, LastActivity.class));
	}
}
