package com.secretbiology.taboo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.secretbiology.taboo.activity.PointSystem;
import com.secretbiology.taboo.constants.GameVariables;

import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity implements View.OnClickListener {

    Button time30sec, time1min, time3min, time5min, start;
    SeekBar playerNumber;
    TextView roundTime, roundPlayers;
    SharedPreferences db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.game_action_bar);
       TextView actionBarTitle = (TextView) findViewById(R.id.game_action_bar_title);
        actionBarTitle.setText(getString(R.string.app_name));

        db = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        time30sec = (Button)findViewById(R.id.time_30sec);
        time1min = (Button)findViewById(R.id.time_1min);
        time3min = (Button)findViewById(R.id.time_3min);
        time5min = (Button)findViewById(R.id.time_5min);
        start = (Button)findViewById(R.id.start_button);
        roundTime = (TextView)findViewById(R.id.round_time_text);
        roundPlayers = (TextView)findViewById(R.id.player_number_text);

        playerNumber = (SeekBar)findViewById(R.id.player_seekBar);

        time1min.setOnClickListener(this);
        time5min.setOnClickListener(this);
        time3min.setOnClickListener(this);
        time30sec.setOnClickListener(this);
        start.setOnClickListener(this);

        makeButtonOff();


        switch (db.getInt(GameVariables.TIME_PER_ROUND,60)){
            case 30:
                time30sec.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Default: 30 Sec"); break;
            case 60:
                time1min.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Default: 1 min"); break;
            case 180:
                time3min.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Default: 3 min"); break;
            case 300:
                time5min.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Default: 5 min"); break;
            default:
                roundTime.setText("Default: "+String.valueOf(db.getInt(GameVariables.TIME_PER_ROUND,60)+ " Sec"));
        }

        int tempProgress = db.getInt(GameVariables.NUMBER_OF_ROUNDS,GameVariables.DEFAULT_NUMBER_OF_ROUNDS);
        playerNumber.setProgress(tempProgress);
        roundPlayers.setText("Generally equal to number of players in team \n (Default: "+tempProgress+")");
        playerNumber.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roundPlayers.setText("Number of rounds : "+progress);
                db.edit().putInt(GameVariables.NUMBER_OF_ROUNDS,progress).apply();
                if(progress==0){
                    db.edit().putInt(GameVariables.NUMBER_OF_ROUNDS,1).apply();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {


            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.time_30sec:
                db.edit().putInt(GameVariables.TIME_PER_ROUND,30).apply();
                makeButtonOff();
                time30sec.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Current: 30 Sec"); break;
            case R.id.time_1min:
                db.edit().putInt(GameVariables.TIME_PER_ROUND,60).apply();
                makeButtonOff();
                time1min.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Current: 1 min"); break;
            case R.id.time_3min:
                db.edit().putInt(GameVariables.TIME_PER_ROUND,180).apply();
                makeButtonOff();
                time3min.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Current: 3 min"); break;
            case R.id.time_5min:
                db.edit().putInt(GameVariables.TIME_PER_ROUND,300).apply();
                makeButtonOff();
                time5min.setBackgroundColor(getResources().getColor(R.color.home_clicked));
                roundTime.setText("Current: 3 min"); break;
            case R.id.start_button:
               final AlertDialog alertDialog = new AlertDialog.Builder(Home.this).create();
                alertDialog.setTitle("Current Point System");
                alertDialog.setMessage("Correct Answer : "+db.getInt(GameVariables.CORRECT_ANSWER,GameVariables.CORRECT_ANSWER_DEFAULT) + "\nSkip : "
                        + db.getInt(GameVariables.SKIP,GameVariables.SKIP_DEFAULT)
                        + " \nTaboo : "+ db.getInt(GameVariables.TABOO,GameVariables.TABOO_DEFAULT));
                       alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Home.this, Game.class));
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Home.this, PointSystem.class));
                    }
                });
                alertDialog.show();



        }

    }

    public void makeButtonOff(){
        time30sec.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        time3min.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        time5min.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        time1min.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();

    }
}
