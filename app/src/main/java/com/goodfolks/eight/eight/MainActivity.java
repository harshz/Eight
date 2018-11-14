package com.goodfolks.eight.eight;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import static java.lang.StrictMath.abs;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    GestureDetectorCompat gestureDetectorCompat;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textViewTimer;
    TextView textViewScore;
    CountDownTimer waitTimer;
    TextView textViewSlideR;
    ProgressBar progressBar;
    MediaPlayer mMediaPlayer;
    RelativeLayout r1;
    long timeleft;
    int score = 0;
    Animation animSlide_Right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        r1 = findViewById(R.id.RelativeLayout2);
        textView1 = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textViewL);
        textView3 = findViewById(R.id.textViewR);
        textViewSlideR = findViewById(R.id.textViewC);
        textViewTimer = findViewById(R.id.textViewTimer);
        textViewScore = findViewById(R.id.textViewScore);
        progressBar = findViewById(R.id.barTimer);
        progressBar.setProgress(60);
        StartBGMusic();
        Drawable draw= getResources().getDrawable(R.drawable.custom_progressbar);
        // set the drawable as progress drawable
        progressBar.setProgressDrawable(draw);
        //String text = "<font color=#0000FF>E</font><font color=#ffd800>I</font><font color=#000000>G</font><font color=#007300>H</font><font color=#80000>T</font>";
        //textViewHeader.setText(Html.fromHtml(text));
        timeleft = 60000;
        Timer(timeleft);
        this.gestureDetectorCompat = new GestureDetectorCompat(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
            @Override
            public boolean onFling(MotionEvent motionEvent1, MotionEvent motionEvent2, float X, float Y) {

                if(motionEvent1.getX() - motionEvent2.getX() > 50){
                    onSwipeLeft();
                    updateNode();
                    return true;
                }

                if(motionEvent2.getX() - motionEvent1.getX() > 50) {
                    onSwipeRight();
                    updateNode();
                    return true;
                }
                else {
                    return true ;
                }
            }

            @Override
            public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2, float arg3) {

                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public void onShowPress(MotionEvent arg0) {

                // TODO Auto-generated method stub

            }

            @Override
            public boolean onSingleTapUp(MotionEvent arg0) {

                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onDown(MotionEvent arg0) {

                // TODO Auto-generated method stub

                return false;
            }

        });

        textView1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetectorCompat.onTouchEvent(motionEvent);
                return true;
            }
        });

        /*animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        textView2.setAnimation(animation);
        animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_in);
        textView3.setAnimation(animation);*/

    }

    private void StartBGMusic() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer = MediaPlayer.create(this, R.raw.sound1);
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.start();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to Quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    private void Timer(long timeleft) {
        waitTimer = new CountDownTimer(timeleft, 1000) {

            public void onTick(long millisUntilFinished) {
                int progress = (int) (millisUntilFinished/1000);
                progressBar.setProgress(progress);
                textViewTimer.setText(Integer.toString(progress));
            }

            public void onFinish() {
                goToResults();
                //textViewTimer.setText(0);
            }
        }.start();
    }

    private void updateNode() {
        Random r = new Random();
        int [] values = {2,4,6,8};
        int val = values[r.nextInt(4)];
        //score = score + val;
        textView1.setText(Integer.toString(val));
        Animation pulse = AnimationUtils.loadAnimation(this, R.anim.pulse);
        textView1.startAnimation(pulse);
    }

    private void onSwipeRight() {
        int leftValue = getTextFieldValue(textView2);
        int rightValue = getTextFieldValue(textView3);
        int val = getTextFieldValue(textView1);
        int sum = rightValue + val;
        textView3.setText(String.valueOf(sum));
        boolean validtotal = validateSum(sum,leftValue);
        doAnimation(val,false);
        if(validtotal){
            updateScore(val);
            updateNode();
        }else{
            goToResults();
        }
        //Toast.makeText(MainActivity.this, " Swipe Right ", Toast.LENGTH_LONG).show();
    }

    private void doAnimation(int val, boolean isleft) {
        textViewSlideR.setText("+" + String.valueOf(val));
        textViewSlideR.setVisibility(View.VISIBLE);
        if(isleft){
            animSlide_Right = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_left);
        }else{
            animSlide_Right = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.slide_right);
        }
        //textViewSlideR.bringToFront();
        textViewSlideR.startAnimation(animSlide_Right);
        textViewSlideR.setVisibility(View.INVISIBLE);
    }

    private boolean validateSum(int sum, int compare) {
        /*if(abs(sum-compare)==0){
            textViewEqual.setVisibility(View.VISIBLE);
            //textViewEqual.
        }*/
        int res = abs(sum-compare);
        if(8 >= res){
            if(res == 0){
               //
                StopTimer();
                String tmleft = textViewTimer.getText().toString();
                Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                mVibrator.vibrate(300);
                long timeIncreased = Long.parseLong(tmleft)*1000 + 3000;
                Timer(timeIncreased);
            }
            return true;
        }else {
            return false;
        }
    }

    private void updateScore(int val) {
        score = score + val;
        textViewScore.setText(String.valueOf(score));
    }


    private void onSwipeLeft() {
        int leftValue = getTextFieldValue(textView2);
        int rightValue = getTextFieldValue(textView3);
        int val = getTextFieldValue(textView1);
        int sum = leftValue + val;
        textView2.setText(String.valueOf(sum));
        doAnimation(val,true);
        boolean validTotal = validateSum(sum,rightValue);
        if(validTotal){
            updateScore(val);
            updateNode();
        }else{
            goToResults();
        }
        //Toast.makeText(MainActivity.this , " Swipe Left " , Toast.LENGTH_SHORT).show();
    }

    private void goToResults() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(EXTRA_MESSAGE, String.valueOf(score)); // getText() SHOULD NOT be static!!!
        startActivity(intent);
        StopTimer();
        stopBGMusic();
        finish();
    }

    private void StopTimer() {
        if(waitTimer!=null){
            waitTimer.cancel();
            waitTimer = null;
        }
    }

    private void stopBGMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private int getTextFieldValue(TextView t) {
        return Integer.parseInt(t.getText().toString());
    }

    public static LoopMediaPlayer create(Context context, int resId) {
        return new LoopMediaPlayer(context, resId);
    }
}