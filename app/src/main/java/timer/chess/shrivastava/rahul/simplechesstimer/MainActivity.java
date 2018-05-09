package timer.chess.shrivastava.rahul.simplechesstimer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvPlayer1,tvPlayer2;
    Button btnReset , btnPause;
    SeekBar seekBar;
    static CountDownTimer timer;
    int activeTimer;
    boolean gameFinished=false;
    public void startTimerForSecongs(final int seconds , final View view ){
         timer = new CountDownTimer(seconds*1000+100,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(view.getId()==R.id.tvPlayer1){
                    tvPlayer2.setText(getTime(millisUntilFinished));
                }
                else {
                    tvPlayer1.setText(getTime(millisUntilFinished));
                }
            }

            @Override
            public void onFinish() {
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.buzzer);
                mediaPlayer.start();
               if(activeTimer==1){
                   gameFinished=true;
                   tvPlayer1.setText("You Lost!");
                   tvPlayer2.setBackgroundColor(getResources().getColor(R.color.MediumSeaGreen));
                   tvPlayer2.setText("You Won!");
                   btnPause.setEnabled(false);

               }else if(activeTimer==2){
                   gameFinished=true;
                   btnPause.setEnabled(false);
                   tvPlayer2.setText("You Lost!");
                   tvPlayer1.setText("You Won!");
                   tvPlayer1.setBackgroundColor(getResources().getColor(R.color.MediumSeaGreen));
               }
                Log.i("finished","Timer is Finished");
            }
        };
       timer.start();

    }

    public int getSecondsFromString(String str){
        int secs=0;
        String[] list = str.split(":");
        secs = (Integer.parseInt(list[0])*3600)+ (Integer.parseInt(list[1])*60)+
                Integer.parseInt(list[2]);

        return secs;

    }

    public String getTime(long millisecs){
        String  str="";
        millisecs = millisecs/1000;
        long hour= millisecs/3600;
        long minute = (millisecs%3600)/60;
        long sec = millisecs%60;
        if(hour<=9){
            str=str+"0"+hour+":";
        }else{
            str=str+hour+":";
        }

        if(minute<=9){
            str=str+"0"+minute+":";
        }else{
            str=str+minute+":";
        }

        if(sec<=9){
            str=str+"0"+sec;
        }else{
            str=str+sec;
        }
        Log.i("Time:",str);
        return str;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
        setContentView(R.layout.activity_main);

        //Get Views from the Xml file
        tvPlayer1 =  findViewById(R.id.tvPlayer1);
        tvPlayer2 =  findViewById(R.id.tvPlayer2);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(59);
        seekBar.setProgress(5);
        btnReset = findViewById(R.id.btnReset);
        btnPause = findViewById(R.id.btnPause);

        tvPlayer1.setOnClickListener(this);
        tvPlayer2.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnPause.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){progress=1;}
                String minute="";
                if(progress<=9)
                    minute ="0"+progress;
                else
                    minute=""+progress;
                tvPlayer1.setText("00:"+minute+":00");
                tvPlayer2.setText("00:"+minute+":00");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
*/
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            if (hasFocus) {
                decorView.setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvPlayer2:
                if(!gameFinished && activeTimer!=1){
                        activeTimer=1;  seekBar.setEnabled(false);
                        if(timer!=null) timer.cancel();
                        tvPlayer1.setBackgroundColor(getResources().getColor(R.color.colorTvActive));
                        tvPlayer2.setBackgroundColor(getResources().getColor(R.color.colorTvInactive));
                        startTimerForSecongs(getSecondsFromString(tvPlayer1.getText().toString()),v);
                }
                break;
            case R.id.tvPlayer1:
                if(!gameFinished && activeTimer!=2){
                    activeTimer=2;seekBar.setEnabled(false);
                    if(timer!=null) timer.cancel();
                    tvPlayer2.setBackgroundColor(getResources().getColor(R.color.colorTvActive));
                    tvPlayer1.setBackgroundColor(getResources().getColor(R.color.colorTvInactive));
                    startTimerForSecongs(getSecondsFromString(tvPlayer2.getText().toString()),v);
                }
                break;
            case R.id.btnPause:
               if( activeTimer!=0){
                if(timer!=null) timer.cancel();
                tvPlayer1.setBackgroundColor(getResources().getColor(R.color.colorTvInactive));
                tvPlayer2.setBackgroundColor(getResources().getColor(R.color.colorTvInactive));
                activeTimer=0;
               }
                break;
            case R.id.btnReset:
                activeTimer=0;gameFinished=false;
                seekBar.setEnabled(true);
                seekBar.setProgress(5);
                btnPause.setEnabled(true);
                if(timer!=null) timer.cancel();
                tvPlayer1.setBackgroundColor(getResources().getColor(R.color.colorTvInactive));
                tvPlayer2.setBackgroundColor(getResources().getColor(R.color.colorTvInactive));
                tvPlayer1.setText("00:05:00");
                tvPlayer2.setText("00:05:00");
                break;
        }
    }

    public void infoBtnClick(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        View view1 = getLayoutInflater().inflate(R.layout.information,null);
        alertDialog.setView(view1);
        AlertDialog dialog = alertDialog.show();

    }
}
