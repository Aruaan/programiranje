package com.example.connectthree;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    int activePlayer = 0;
    boolean gameIsActive = true;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void dropIn(View v) {
        ImageView counter = (ImageView) v;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 & gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1500);
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.red);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 0;
            }
            counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] ==
                        gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    gameIsActive = false;
                    String winner = "Yellow has won!";
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Red has won!";
                    }
                    TextView winnerMessage = findViewById(R.id.winner_text_view);
                    winnerMessage.setText(winner);
                    winnerMessage.setVisibility(View.VISIBLE);
                    Button button = findViewById(R.id.play_again_button);
                    button.setVisibility(View.VISIBLE);
                    break;
                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2)
                            gameIsOver = false;
                    }
                    if (gameIsOver) {
                        TextView winnerMessage = findViewById(R.id.winner_text_view);
                        winnerMessage.setText("It is a draw!");
                        winnerMessage.setVisibility(View.VISIBLE);
                        Button button = findViewById(R.id.play_again_button);
                        button.setVisibility(View.VISIBLE);

                    }
                }
            }
        }
    }

    public void playAgain(View v) {
        gameIsActive = true;
        Button button = findViewById(R.id.play_again_button);
        button.setVisibility(View.INVISIBLE);
        TextView textView = findViewById(R.id.winner_text_view);
        textView.setVisibility(View.INVISIBLE);
        activePlayer = 1;
        Arrays.fill(gameState, 2);
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }
    }
}