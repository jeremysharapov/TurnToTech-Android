package com.example.jeremy.guessthenumber;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {
    int numGuesses;
    EditText Guess;
    int Num;
    Button PlayAgain;
    TextView Hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Guess = (EditText)findViewById(R.id.Guess);
        PlayAgain = (Button)findViewById(R.id.PlayAgain);
        Hint = (TextView)findViewById(R.id.Hint);
    }

    @Override
    protected void onStart(){
        super.onStart();
        numGuesses = 3;
        PlayAgain.setVisibility(View.INVISIBLE);
        PlayAgain.setText("Play Again");
        Hint.setVisibility(View.INVISIBLE);
        Num = (int) Math.ceil(Math.random() * 10);
    }

    public void Restart(View view){
        onStart();
    }

    public void Submit(View view){
        numGuesses--;
        if (Integer.valueOf(Guess.getText().toString()) == Num){
            Hint.setVisibility(View.VISIBLE);
            Hint.setText("You Win!!");
            PlayAgain.setVisibility(View.VISIBLE);
            PlayAgain.setText("Play Again, Champ");
        }
        else if(PlayAgain.getText() == "Play Again, Champ" || PlayAgain.getText() == "Play Again, Loser"){
            return;
        }
        else if (Integer.valueOf(Guess.getText().toString()) > 10 || Integer.valueOf(Guess.getText().toString()) == 0){
            numGuesses++;
            Toast.makeText(this, "Error: Invalid Number!!", Toast.LENGTH_LONG).show();
        }
        else if (numGuesses == 0){
            Hint.setText("You Lose!! The number was " + Num);
            PlayAgain.setVisibility(View.VISIBLE);
            PlayAgain.setText("Play Again, Loser");
        }
        else if (Integer.valueOf(Guess.getText().toString()) > Num){
            Hint.setVisibility(View.VISIBLE);
            Hint.setText("You guessed too high. You have " + numGuesses + " tries left.");
        }
        else {
            Hint.setVisibility(View.VISIBLE);
            Hint.setText("You guessed too low. You have " + numGuesses + " tries left.");
        }
    }



}
