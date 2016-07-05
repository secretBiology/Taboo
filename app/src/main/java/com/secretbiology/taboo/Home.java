package com.secretbiology.taboo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.secretbiology.taboo.activity.HowToPlay;
import com.secretbiology.taboo.activity.PreGame;

public class Home extends AppCompatActivity {

    Button play, howtoPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.game_action_bar);

        play = (Button) findViewById(R.id.home_playButton);
        howtoPlay = (Button) findViewById(R.id.home_howToPlay);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, PreGame.class));
            }
        });

        howtoPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, HowToPlay.class));
            }
        });


    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();

    }
}
