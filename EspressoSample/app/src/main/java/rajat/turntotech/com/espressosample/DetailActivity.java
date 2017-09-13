package rajat.turntotech.com.espressosample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.common.base.Strings;


import android.content.Context;
import android.content.Intent;
import android.widget.TextView;


public class DetailActivity extends AppCompatActivity {

    // The name of the extra data sent through an {@link Intent}.
    public final static String KEY_EXTRA_MESSAGE =
            "rajat.turntotech.com.espressosample.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Get the message from the Intent.
        Intent intent = getIntent();
        String message = Strings.nullToEmpty(intent.getStringExtra(KEY_EXTRA_MESSAGE));

        // Show message.
        ((TextView)findViewById(R.id.show_text_view)).setText(message);
    }

    static protected Intent newStartIntent(Context context, String message) {
        Intent newIntent = new Intent(context, DetailActivity.class);
        newIntent.putExtra(KEY_EXTRA_MESSAGE, message);
        return newIntent;
    }
}
