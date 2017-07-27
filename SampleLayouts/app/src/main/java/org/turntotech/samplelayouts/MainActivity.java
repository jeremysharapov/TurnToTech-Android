/*
 * In this example we create simple layout programmatically.
 * We also create buttons in the layout.
 */


package org.turntotech.samplelayouts;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout linerLayout;
	EditText[] texts = new EditText[3];
	Button submit;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Log.i("TurnToTech", "Project Name - Sample Layouts");
		// create linear layout 
		linerLayout = new LinearLayout(this);

		// Create 3 buttons in the layout
		for (int i = 0; i < texts.length; i++) {
			texts[i] = new EditText(this);
			//buttons[i].setText("Button " + (i+1));
			//buttons[i].setOnClickListener(this);
			linerLayout.addView(texts[i]);
		}
		submit = new Button(this);
		submit.setText("Submit");
		submit.setOnClickListener(this);
		linerLayout.addView(submit);
		LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.addContentView(linerLayout, params);
		onClick(null);
	}

	@Override
	// When we click the layout orientation changed to horizontal 
	public void onClick(View v) {
		/*LayoutParams bparams = null;
		if(linerLayout.getOrientation()==LinearLayout.HORIZONTAL){
			bparams = new LayoutParams(LayoutParams.WRAP_CONTENT, 0, 1);
			linerLayout.setOrientation(LinearLayout.VERTICAL);
		}
		else{
			bparams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
			linerLayout.setOrientation(LinearLayout.HORIZONTAL);
		}
		for(Button button: buttons){
			button.setLayoutParams(bparams);
		}*/
		Toast.makeText(this, "Hello " + texts[0].getText() + " " + texts[1].getText() + " of the city " + texts[2].getText(), Toast.LENGTH_LONG).show();
	}
}
