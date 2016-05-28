package com.secretbiology.taboo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.secretbiology.taboo.activity.ResultPage;
import com.secretbiology.taboo.constants.GameVariables;
import com.secretbiology.taboo.words.WordSet1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {

    int currentWord = 0;
    int totalPoints = 0;
    int timeLeft, roundNumber, totalRounds, rounds;
    int redTeamRound, bluTeamRound;
    TextView mainWord, subWord1, subWord2, subWord3, subWord4, subWord5;
    TextView actionBarTitle, timeText, pointText;
    int TotalWords, currentTeam;
    LinearLayout card, mainWordHolder;
    ActionBar actionBar;
    String[][] wordData;
    Button nextButton, skipButton, tabooButton;
    SharedPreferences db;
    Timer timeLeftTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game);

        db = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        //Reset Game Variables
        db.edit().putInt(GameVariables.TEAM_RED_POINTS, 0).apply();
        db.edit().putInt(GameVariables.TEAM_BLUE_POINTS, 0).apply();
        db.edit().putInt(GameVariables.RED_CORRECT, 0).apply();
        db.edit().putInt(GameVariables.RED_SKIP, 0).apply();
        db.edit().putInt(GameVariables.RED_TABOO, 0).apply();
        db.edit().putInt(GameVariables.BLUE_CORRECT, 0).apply();
        db.edit().putInt(GameVariables.BLUE_SKIP, 0).apply();
        db.edit().putInt(GameVariables.BLUE_TABOO, 0).apply();

        roundNumber = 1;
        redTeamRound = 0;
        bluTeamRound = 0;
        rounds = db.getInt(GameVariables.NUMBER_OF_ROUNDS, GameVariables.DEFAULT_NUMBER_OF_ROUNDS);
        totalRounds =  rounds* 2;

        //Colors
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.game_action_bar);
        actionBar = getSupportActionBar();
        actionBarTitle = (TextView) findViewById(R.id.game_action_bar_title);
        timeLeft = db.getInt(GameVariables.TIME_PER_ROUND, GameVariables.DEFAULT_TIME_PER_ROUND);
        wordData = new WordSet1().words();
        TotalWords = wordData.length;
        currentWord = (int) (Math.random() * TotalWords);

        card = (LinearLayout) findViewById(R.id.card_layout);
        mainWordHolder = (LinearLayout) findViewById(R.id.main_word_layout);
        timeText = (TextView) findViewById(R.id.time_text);
        pointText = (TextView) findViewById(R.id.points_text);
        mainWord = (TextView) findViewById(R.id.main_word);
        subWord1 = (TextView) findViewById(R.id.sub_word1);
        subWord2 = (TextView) findViewById(R.id.sub_word2);
        subWord3 = (TextView) findViewById(R.id.sub_word3);
        subWord4 = (TextView) findViewById(R.id.sub_word4);
        subWord5 = (TextView) findViewById(R.id.sub_word5);
        changeWord(wordData[currentWord]);

        nextButton = (Button) findViewById(R.id.Button_Next);
        if (nextButton != null) {
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentWord = (int) (Math.random() * TotalWords);
                    changeWord(wordData[currentWord]);
                    totalPoints = totalPoints + GameVariables.CORRECT_ANSWER;
                    pointText.setText(String.valueOf(totalPoints));
                    savePoints();
                    saveCorrect();
                }
            });
        }

        skipButton = (Button) findViewById(R.id.Button_Skip);
        if (skipButton != null) {
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentWord = (int) (Math.random() * TotalWords);
                    changeWord(wordData[currentWord]);
                    totalPoints = totalPoints + GameVariables.SKIP;
                    pointText.setText(String.valueOf(totalPoints));
                    savePoints();
                    saveSkipped();
                }
            });
        }

        tabooButton = (Button) findViewById(R.id.Button_Taboo);
        if (tabooButton != null) {
            tabooButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentWord = (int) (Math.random() * TotalWords);
                    changeWord(wordData[currentWord]);
                    totalPoints = totalPoints + GameVariables.TABOO;
                    pointText.setText(String.valueOf(totalPoints));
                    savePoints();
                    saveTaboo();
                }
            });
        }

        changeTeam(1);
        mainWord.setText("???");
        subWord1.setText("???");
        subWord2.setText("???");
        subWord3.setText("???");
        subWord4.setText("???");
        subWord5.setText("???");
        pointText.setText("0");
        timeText.setText(String.valueOf(db.getInt(GameVariables.TIME_PER_ROUND,GameVariables.DEFAULT_TIME_PER_ROUND)));
        final AlertDialog alertDialog = new AlertDialog.Builder(Game.this).create();
        redTeamRound++;
        alertDialog.setTitle("Team RED ready ?");
        alertDialog.setMessage("Press Ready to start round "+redTeamRound+ " of "+ rounds);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ready", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                timeLeft = db.getInt(GameVariables.TIME_PER_ROUND, GameVariables.DEFAULT_TIME_PER_ROUND);
                alertDialog.dismiss();
                timeLeftTimer = new Timer();
                timeLeftTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runTimer();
                    }

                }, 0, 1000); //1000 is milliseconds for each time tick

                changeTeam(1);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Go Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                startActivity(new Intent(Game.this, Home.class));
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();



    }
    public void savePoints(){
        if (currentTeam==1){
            db.edit().putInt(GameVariables.TEAM_RED_POINTS,totalPoints).apply();
        }
        else {
            db.edit().putInt(GameVariables.TEAM_BLUE_POINTS,totalPoints).apply();
        }
    }

    public void saveCorrect(){
        if (currentTeam==1){
            db.edit().putInt(GameVariables.RED_CORRECT, db.getInt(GameVariables.RED_CORRECT,0)+1).apply();
        }
        else {
            db.edit().putInt(GameVariables.BLUE_CORRECT, db.getInt(GameVariables.BLUE_CORRECT,0)+1).apply();
        }
    }

    public void saveSkipped(){
        if (currentTeam==1){
            db.edit().putInt(GameVariables.RED_SKIP, db.getInt(GameVariables.RED_SKIP,0)+1).apply();
        }
        else {
            db.edit().putInt(GameVariables.BLUE_SKIP, db.getInt(GameVariables.BLUE_SKIP,0)+1).apply();
        }
    }

    public void saveTaboo(){
        if (currentTeam==1){
            db.edit().putInt(GameVariables.RED_TABOO, db.getInt(GameVariables.RED_TABOO,0)+1).apply();
        }
        else {
            db.edit().putInt(GameVariables.BLUE_TABOO, db.getInt(GameVariables.BLUE_TABOO,0)+1).apply();
        }
    }

    public void changeWord(String[] words){

        //This assumes 5 sub-words

        mainWord.setText(words[0]);
        subWord1.setVisibility(View.VISIBLE);
        subWord2.setVisibility(View.VISIBLE);
        subWord3.setVisibility(View.VISIBLE);
        subWord4.setVisibility(View.VISIBLE);
        subWord5.setVisibility(View.VISIBLE);

        List<String> subWordList = new ArrayList<>();
        subWordList.addAll(Arrays.asList(words).subList(1, words.length));
        Collections.shuffle(subWordList);
        if(subWordList.size()==5) {
            subWord1.setText(subWordList.get(0));
            subWord2.setText(subWordList.get(1));
            subWord3.setText(subWordList.get(2));
            subWord4.setText(subWordList.get(3));
            subWord5.setText(subWordList.get(4));
        }
        else if (subWordList.size()==4){
            subWord1.setText(subWordList.get(0));
            subWord2.setText(subWordList.get(1));
            subWord3.setText(subWordList.get(2));
            subWord4.setText(subWordList.get(3));
        }
        else if (subWordList.size()==3){
            subWord1.setText(subWordList.get(0));
            subWord2.setText(subWordList.get(1));
            subWord3.setText(subWordList.get(2));
        }
        else
        {
            Log.i("Smaller words","Smaller lists not allowed");
        }

    }

    public void changeTeam(int team){

        currentWord = (int) (Math.random() * TotalWords);
        changeWord(wordData[currentWord]);

        switch (team) {
            case 1:
                currentTeam = 1;
                totalPoints = db.getInt(GameVariables.TEAM_RED_POINTS,0);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.team_red_color)));
                mainWordHolder.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                card.setBackgroundColor(getResources().getColor(R.color.team_red_background));
                nextButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                skipButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                tabooButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                actionBarTitle.setText("Team RED".toUpperCase());
                pointText.setText(String.valueOf(totalPoints));
                break;

            case 2:
                currentTeam = 2;
                totalPoints = db.getInt(GameVariables.TEAM_BLUE_POINTS,0);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.team_blue_color)));
                mainWordHolder.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                card.setBackgroundColor(getResources().getColor(R.color.team_blue_background));
                nextButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                skipButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                tabooButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                actionBarTitle.setText("Team BLUE".toUpperCase());
                pointText.setText(String.valueOf(totalPoints));
                break;

        }

    }

    private void runTimer() {
        this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            startCountDown();
        }
    };

    public void startCountDown(){
        timeLeft = timeLeft -1 ;
        timeText.setText(String.valueOf(timeLeft));

       if (timeLeft == 0) {
            roundNumber++;

           if(roundNumber>totalRounds){
               timeLeftTimer.cancel();
               startActivity(new Intent(Game.this, ResultPage.class));
           }
           else {

               timeLeftTimer.cancel();

               if (currentTeam == 1) {
                   changAppearance(2);
               } else {
                   changAppearance(1);
               }

               mainWord.setText("???");
               subWord1.setText("???");
               subWord2.setText("???");
               subWord3.setText("???");
               subWord4.setText("???");
               subWord5.setText("???");

               final AlertDialog alertDialog = new AlertDialog.Builder(Game.this).create();
               if (currentTeam == 1) {
                   bluTeamRound++;
                   alertDialog.setTitle("Team BLUE ready?");
                   alertDialog.setMessage("Press Ready to start the round " + bluTeamRound + " of " + rounds);
               } else {
                   redTeamRound++;
                   alertDialog.setTitle("Team RED ready ?");
                   alertDialog.setMessage("Press Ready to start the round " + redTeamRound + " of " + rounds);
               }

               alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ready", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {

                       timeLeft = db.getInt(GameVariables.TIME_PER_ROUND, GameVariables.DEFAULT_TIME_PER_ROUND);
                       alertDialog.dismiss();
                       timeLeftTimer = new Timer();
                       timeLeftTimer.schedule(new TimerTask() {
                           @Override
                           public void run() {
                               runTimer();
                           }

                       }, 0, 1000); //1000 is milliseconds for each time tick

                       if (currentTeam == 1) {
                           changeTeam(2);
                       } else {
                           changeTeam(1);
                       }
                   }
               });
               alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "End Game", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       alertDialog.dismiss();
                       startActivity(new Intent(Game.this, Home.class));
                   }
               });
               alertDialog.setCanceledOnTouchOutside(false);
               alertDialog.setCancelable(false);
               alertDialog.show();
           }
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        db.edit().putInt(GameVariables.TEAM_RED_POINTS,0).apply();
        db.edit().putInt(GameVariables.TEAM_BLUE_POINTS,0).apply();
        timeLeftTimer.cancel();
    }


    public void changAppearance(int team){

        currentWord = (int) (Math.random() * TotalWords);
        changeWord(wordData[currentWord]);

        switch (team) {
            case 1:
                totalPoints = db.getInt(GameVariables.TEAM_RED_POINTS,0);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.team_red_color)));
                mainWordHolder.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                card.setBackgroundColor(getResources().getColor(R.color.team_red_background));
                nextButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                skipButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                tabooButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
                actionBarTitle.setText("Team RED".toUpperCase());
                pointText.setText(String.valueOf(totalPoints));
                timeText.setText(String.valueOf(db.getInt(GameVariables.TIME_PER_ROUND,GameVariables.DEFAULT_TIME_PER_ROUND)));
                break;

            case 2:
                totalPoints = db.getInt(GameVariables.TEAM_BLUE_POINTS,0);
                actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.team_blue_color)));
                mainWordHolder.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                card.setBackgroundColor(getResources().getColor(R.color.team_blue_background));
                nextButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                skipButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                tabooButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
                actionBarTitle.setText("Team BLUE".toUpperCase());
                pointText.setText(String.valueOf(totalPoints));
                timeText.setText(String.valueOf(db.getInt(GameVariables.TIME_PER_ROUND,GameVariables.DEFAULT_TIME_PER_ROUND)));
                break;

        }

    }

}
