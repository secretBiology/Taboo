package com.secretbiology.taboo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.secretbiology.taboo.Game;
import com.secretbiology.taboo.R;
import com.secretbiology.taboo.constants.GameVariables;

public class PointSystem extends AppCompatActivity implements View.OnClickListener{

    Button correct0, correct10, correct20, correctCustom;
    Button skipm10, skipm3, skip0, skipCustom;
    Button taboom10, taboom5, taboo0, tabooCustom;
    Button startGame;
    TextView correct, skip, taboo;
    SharedPreferences db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point_system);

        db = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        correct = (TextView) findViewById(R.id.correct_text);
        skip = (TextView) findViewById(R.id.skip_text);
        taboo = (TextView) findViewById(R.id.taboo_text);

        startGame = (Button)findViewById(R.id.start_game);

        correct0 = (Button)findViewById(R.id.correct_0);
        correct10 = (Button)findViewById(R.id.correct_10);
        correct20 = (Button)findViewById(R.id.correct_20);
        correctCustom = (Button)findViewById(R.id.correct_custom);

        skipm10 = (Button)findViewById(R.id.skip_m10);
        skipm3 = (Button)findViewById(R.id.skip_m3);
        skip0 = (Button)findViewById(R.id.skip_0);
        skipCustom = (Button)findViewById(R.id.skip_custom);

        taboom10 = (Button)findViewById(R.id.taboo_m10);
        taboom5 = (Button)findViewById(R.id.taboo_m5);
        taboo0 = (Button)findViewById(R.id.taboo_0);
        tabooCustom = (Button)findViewById(R.id.taboo_custom);

        startGame.setOnClickListener(this);

        correct0.setOnClickListener(this);
        correct10.setOnClickListener(this);
        correct20.setOnClickListener(this);

        skipm10.setOnClickListener(this);
        skipm3.setOnClickListener(this);
        skip0.setOnClickListener(this);

        taboom10.setOnClickListener(this);
        taboom5.setOnClickListener(this);
        taboo0.setOnClickListener(this);

        correctCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(PointSystem.this).create();
                final EditText editText = new EditText(PointSystem.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);
                alertDialog.setView(editText);
                alertDialog.setMessage("Enter in following input field");
                alertDialog.setTitle("Correct Answer");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(editText.getText().length()!=0) {
                            db.edit().putInt(GameVariables.CORRECT_ANSWER, Integer.parseInt(editText.getText().toString())).apply();
                            correct.setText(getResources().getString(R.string.current_value,Integer.parseInt(editText.getText().toString())));
                            setCorrectClick(4);
                        }

                    }
                });
                alertDialog.show();
            }
        });

        skipCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(PointSystem.this).create();
                final EditText editText = new EditText(PointSystem.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);
                alertDialog.setView(editText);
                alertDialog.setMessage("Enter in following input field");
                alertDialog.setTitle("Skip points");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(editText.getText().length()!=0) {
                            db.edit().putInt(GameVariables.SKIP, Integer.parseInt(editText.getText().toString())).apply();
                            skip.setText(getResources().getString(R.string.current_value,Integer.parseInt(editText.getText().toString())));
                            setSkipClick(4);
                        }

                    }
                });
                alertDialog.show();
            }
        });

        tabooCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(PointSystem.this).create();
                final EditText editText = new EditText(PointSystem.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED);
                alertDialog.setView(editText);
                alertDialog.setMessage("Enter in following input field");
                alertDialog.setTitle("Taboo points");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(editText.getText().length()!=0) {
                            db.edit().putInt(GameVariables.TABOO, Integer.parseInt(editText.getText().toString())).apply();
                            taboo.setText(getResources().getString(R.string.current_value,Integer.parseInt(editText.getText().toString())));
                            setTabooClick(4);
                        }

                    }
                });
                alertDialog.show();
            }
        });


        initialClicks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.correct_0: db.edit().putInt(GameVariables.CORRECT_ANSWER,0).apply();
                correct.setText(getResources().getString(R.string.current_value,0)); setCorrectClick(1);break;
            case R.id.correct_10: db.edit().putInt(GameVariables.CORRECT_ANSWER,10).apply();
                correct.setText(getResources().getString(R.string.current_value,10)); setCorrectClick(2); break;
            case R.id.correct_20: db.edit().putInt(GameVariables.CORRECT_ANSWER,20).apply();
                correct.setText(getResources().getString(R.string.current_value,20)); setCorrectClick(3); break;
            case R.id.skip_m10: db.edit().putInt(GameVariables.SKIP,-10).apply();
                skip.setText(getResources().getString(R.string.current_value,-10)); setSkipClick(1); break;
            case R.id.skip_m3: db.edit().putInt(GameVariables.SKIP,-3).apply();
                skip.setText(getResources().getString(R.string.current_value,-3)); setSkipClick(2); break;
            case R.id.skip_0: db.edit().putInt(GameVariables.SKIP,0).apply();
                skip.setText(getResources().getString(R.string.current_value,0)); setSkipClick(3); break;
            case R.id.taboo_m10: db.edit().putInt(GameVariables.TABOO,-10).apply();
                taboo.setText(getResources().getString(R.string.current_value,-10)); setTabooClick(1); break;
            case R.id.taboo_m5: db.edit().putInt(GameVariables.TABOO,-5).apply();
                taboo.setText(getResources().getString(R.string.current_value,-5)); setTabooClick(2); break;
            case R.id.taboo_0: db.edit().putInt(GameVariables.TABOO,0).apply();
                taboo.setText(getResources().getString(R.string.current_value,0)); setTabooClick(3); break;
            case R.id.start_game:
                startActivity(new Intent(PointSystem.this, Game.class)); break;

        }

    }

    public void setCorrectClick(int position){
        correct0.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        correct10.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        correct20.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        correctCustom.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        switch (position){
            case 1: correct0.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 2: correct10.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 3: correct20.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 4: correctCustom.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
        }

    }

    public void setSkipClick(int position){
        Log.i("Position ",position+"");
        skipm10.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        skipm3.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        skip0.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        skipCustom.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        switch (position){
            case 1: skipm10.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 2: skipm3.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 3: skip0.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 4: skipCustom.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
        }

    }

    public void setTabooClick(int position){
        taboom10.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        taboom5.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        taboo0.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        tabooCustom.setBackgroundColor(getResources().getColor(R.color.home_notclicked));
        switch (position){
            case 1: taboom10.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 2: taboom5.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 3: taboo0.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
            case 4: tabooCustom.setBackgroundColor(getResources().getColor(R.color.home_clicked)); break;
        }

    }

    public void initialClicks(){

        int correctInt = db.getInt(GameVariables.CORRECT_ANSWER,GameVariables.CORRECT_ANSWER_DEFAULT);
        int skiInt = db.getInt(GameVariables.SKIP,GameVariables.SKIP_DEFAULT);
        int tabooInt = db.getInt(GameVariables.TABOO,GameVariables.TABOO_DEFAULT);

        correct.setText(getResources().getString(R.string.current_value,correctInt));
        skip.setText(getResources().getString(R.string.current_value,skiInt));
        taboo.setText(getResources().getString(R.string.current_value,tabooInt));

        switch (correctInt){
            case 0: setCorrectClick(1); break;
            case 10: setCorrectClick(2); break;
            case 20: setCorrectClick(3); break;
            default:
                setCorrectClick(4);
        }

        switch (skiInt){
            case -10: setSkipClick(1); break;
            case -3: setSkipClick(2); break;
            case 0: setSkipClick(3); break;
            default:
                setSkipClick(4);
        }

        switch (tabooInt){
            case -10: setTabooClick(1); break;
            case -5: setTabooClick(2); break;
            case 0: setTabooClick(3); break;
            default:
                setTabooClick(4);
        }
    }
}
