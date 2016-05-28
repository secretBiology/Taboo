package com.secretbiology.taboo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.secretbiology.taboo.Game;
import com.secretbiology.taboo.Home;
import com.secretbiology.taboo.R;
import com.secretbiology.taboo.constants.GameVariables;

public class ResultPage extends AppCompatActivity {

    TextView actionBarTitle, finalResult;
    TextView team1_correct, team1_skipped, team1_taboo, team1_total;
    TextView team2_correct, team2_skipped, team2_taboo, team2_total;
    SharedPreferences db;
    Button replayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);
        db = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.game_action_bar);
        actionBarTitle = (TextView) findViewById(R.id.game_action_bar_title);
        actionBarTitle.setText(getString(R.string.app_name));

        replayButton = (Button)findViewById(R.id.replay_button);

        team1_correct = (TextView)findViewById(R.id.team1_correct);
        team1_skipped = (TextView)findViewById(R.id.team1_skip);
        team1_taboo = (TextView)findViewById(R.id.team1_taboo);
        team1_total = (TextView)findViewById(R.id.team1_total);
        team2_correct = (TextView)findViewById(R.id.team2_correct);
        team2_skipped = (TextView)findViewById(R.id.team2_skipped);
        team2_taboo = (TextView)findViewById(R.id.team2_taboo);
        team2_total = (TextView)findViewById(R.id.team2_total);

        finalResult = (TextView)findViewById(R.id.final_result_tex);

        team1_correct.setText(String.valueOf(db.getInt(GameVariables.RED_CORRECT,0)));
        team1_skipped.setText(String.valueOf(db.getInt(GameVariables.RED_SKIP,0)));
        team1_taboo.setText(String.valueOf(db.getInt(GameVariables.RED_TABOO,0)));
        team1_total.setText(String.valueOf(db.getInt(GameVariables.TEAM_RED_POINTS,0)));


        team2_correct.setText(String.valueOf(db.getInt(GameVariables.BLUE_CORRECT,0)));
        team2_skipped.setText(String.valueOf(db.getInt(GameVariables.BLUE_SKIP,0)));
        team2_taboo.setText(String.valueOf(db.getInt(GameVariables.BLUE_TABOO,0)));
        team2_total.setText(String.valueOf(db.getInt(GameVariables.TEAM_BLUE_POINTS,0)));


        int redScore = db.getInt(GameVariables.TEAM_RED_POINTS,0);
        int blueScore = db.getInt(GameVariables.TEAM_BLUE_POINTS,0);
        Window window = getWindow();

        if(redScore==blueScore){
            finalResult.setText("It is a tie !");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.tie_color)));
            replayButton.setBackgroundColor(getResources().getColor(R.color.tie_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(R.color.tie_color_dark));
            }
        }
        else if (redScore>blueScore){
            finalResult.setText("Team RED won !");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.team_red_color)));
            replayButton.setBackgroundColor(getResources().getColor(R.color.team_red_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(R.color.red_dark));
            }
        }
        else {
            finalResult.setText("Team BLUE won !");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.team_blue_color)));
            replayButton.setBackgroundColor(getResources().getColor(R.color.team_blue_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(getResources().getColor(R.color.blue_dark));
            }
        }

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultPage.this, Game.class));
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Home.class));
    }
}
