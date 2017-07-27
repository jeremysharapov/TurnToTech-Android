package com.example.jeremy.clock;
import android.content.Intent;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.MAGENTA;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;
import static android.graphics.Color.YELLOW;

public class Clock extends AppCompatActivity {
    ImageView dotOne;
    ImageView dotTwo;
    static int back;
    static int dig;
    static int minutes;
    static int hoursEST;
    static int offSet;
    static int hours;
    CustomView One;
    CustomView Two;
    CustomView Three ;
    CustomView Four;
    TextView Text;
    ConstraintLayout lay;
//    CheckBox check;
//    Spinner backColor;
//    Spinner clockColor;
//    Spinner timeZone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        One = (CustomView) findViewById(R.id.digitOne);
        Two = (CustomView) findViewById(R.id.digitTwo);
        Three = (CustomView) findViewById(R.id.digitThree);
        Four = (CustomView) findViewById(R.id.digitFour);
        dotOne = (ImageView)findViewById(R.id.imageView);
        dotTwo = (ImageView)findViewById(R.id.imageView2);
        Text = (TextView)findViewById(R.id.textView);
        lay = (ConstraintLayout)findViewById(R.id.lay1);
//        check = (CheckBox)findViewById(R.id.checkBox);
//        backColor = (Spinner) findViewById(R.id.spinner);
//        clockColor = (Spinner) findViewById(R.id.spinner2);
//        timeZone = (Spinner) findViewById(R.id.spinner3);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.colors_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        backColor.setAdapter(adapter);
//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
//                R.array.colors_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        clockColor.setAdapter(adapter2);
//        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this,
//                R.array.time_array, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        timeZone.setAdapter(adapter3);
    }

    Runnable r2=new Runnable() {
        @Override
        public void run(){
            offSet = 4;
            switch (String.valueOf(Settings.backColor.getSelectedItem())){
                case "BLUE": back = BLUE;
                    break;
                case "RED": back = RED;
                    break;
                case "YELLOW": back = YELLOW;
                    break;
                case "GREEN": back = GREEN;
                    break;
                case "BLACK": back = BLACK;
                    break;
                case "WHITE": back = WHITE;
                    break;
                case "MAGENTA": back = MAGENTA;
                    break;
                case "CYAN": back = CYAN;
                    break;
            }

            switch (String.valueOf(Settings.clockColor.getSelectedItem())){
                case "BLUE": dig = BLUE;
                    break;
                case "RED": dig = RED;
                    break;
                case "YELLOW": dig = YELLOW;
                    break;
                case "GREEN": dig = GREEN;
                    break;
                case "BLACK": dig = BLACK;
                    break;
                case "WHITE": dig = WHITE;
                    break;
                case "MAGENTA": dig = MAGENTA;
                    break;
                case "CYAN": dig = CYAN;
                    break;
            }

            switch (String.valueOf(Settings.timeZone.getSelectedItem())){
                case "-12": offSet += -12;
                    break;
                case "-11": offSet += -11;
                    break;
                case "-10": offSet += -10;
                    break;
                case "-9": offSet += -9;
                    break;
                case "-8": offSet += -8;
                    break;
                case "-7": offSet += -7;
                    break;
                case "-6": offSet += -6;
                    break;
                case "-5": offSet += -5;
                    break;
                case "-4": offSet += -4;
                    break;
                case "-3": offSet += -3;
                    break;
                case "-2": offSet += -2;
                    break;
                case "-1": offSet += -1;
                    break;
                case "+-0": offSet += 0;
                    break;
                case "+1": offSet += 1;
                    break;
                case "+2": offSet += 2;
                    break;
                case "+3": offSet += 3;
                    break;
                case "+4": offSet += 4;
                    break;
                case "+5": offSet += 5;
                    break;
                case "+6": offSet += 6;
                    break;
                case "+7": offSet += 7;
                    break;
                case "+8": offSet += 8;
                    break;
                case "+9": offSet += 9;
                    break;
                case "+10": offSet += 10;
                    break;
                case "+11": offSet += 11;
                    break;
                case "+12": offSet += 12;
                    break;
                case "+13": offSet += 13;
                    break;
                case "+14": offSet += 14;
                    break;
            }


            Calendar rightNow = Calendar.getInstance();
            int s = rightNow.get(Calendar.AM_PM);
            if(s == 1){
                Text.setVisibility(View.VISIBLE);
                Text.setText("PM");
            }
            else{
                Text.setVisibility(View.VISIBLE);
                Text.setText("AM");
            }
            hoursEST = rightNow.get(Calendar.HOUR);
            hours = hoursEST + offSet;
            while (hours > 12){
                if (Text.getText() == "PM") {
                    Text.setText("AM");
                }
                else{
                    Text.setText("PM");
                }
                hours -= 12;
            }
            while (hours < 1){
                if (Text.getText() == "PM") {
                    Text.setText("AM");
                }
                else{
                    Text.setText("PM");
                }
                hours += 12;
            }
            minutes = rightNow.get(Calendar.MINUTE);
            int secs = rightNow.get(Calendar.SECOND);

            lay.setBackgroundColor(back);

            if(secs%2 == 0){
                dotOne.setVisibility(View.VISIBLE);
                dotTwo.setVisibility(View.VISIBLE);
            }else{
                dotOne.setVisibility(View.INVISIBLE);
                dotTwo.setVisibility(View.INVISIBLE);

            }
            if (hours == 12){
                if (Text.getText() == "PM") {
                    Text.setText("AM");
                }
                else{
                    Text.setText("PM");
                }
            }

            if (Settings.check.isChecked() == true){
                Text.setVisibility(View.INVISIBLE);
                if (hours == 12 && Text.getText() == "AM"){
                    hours = 0;
                }
                if(Text.getText() == "PM" && hours != 12) {
                    hours += 12;
                }

            }

            One.digit = (hours / 10);
            Two.digit = (hours % 10);
            Three.digit = (minutes / 10);
            Four.digit = (minutes % 10);

            One.customDigit();
            Two.customDigit();
            Three.customDigit();
            Four.customDigit();
            h2.postDelayed(r2,1000);
        }
    };

    Handler h2=new Handler();

    protected void onResume(){
        super.onResume();
        h2.postDelayed(r2, 2000);
    }

    protected void onPause(){
        super.onPause();
        h2.removeCallbacks(r2);
    }

    public void buttonClicked(View view){
        startActivity(new Intent(this, Settings.class));
    }
}
