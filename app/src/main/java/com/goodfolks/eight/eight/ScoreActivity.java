package com.goodfolks.eight.eight;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
import android.graphics.Color;
//import android.media.MediaScannerConnection;
//import android.net.Uri;
//import android.os.Environment;
import android.preference.PreferenceManager;
//import android.support.v4.content.FileProvider;
//import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
//import android.widget.Button;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//import java.io.File;
//import java.io.FileOutputStream;
//import java.util.Date;

public class ScoreActivity extends AppCompatActivity {
    TextView scoreDisplay;
    TextView highestScore;
    Button globalRanking;
    ImageButton replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        scoreDisplay = findViewById(R.id.textViewDisplayScore);
        highestScore = findViewById(R.id.textViewHighestScore);
        //share = findViewById(R.id.shareSS);
        globalRanking = findViewById(R.id.globalRankingButton);
        globalRanking.setBackgroundColor(Color.TRANSPARENT);
        replay = findViewById(R.id.replay);
        replay.setBackgroundColor(Color.TRANSPARENT);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String oldHighest = sp.getString("highestScore", "0");
        highestScore.setText(oldHighest);
        Intent intent = getIntent();
        String finalScore = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        scoreDisplay.setText(finalScore);
            replay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Animation pulse = AnimationUtils.loadAnimation(ScoreActivity.this, R.anim.pulse);
                    replay.startAnimation(pulse);
                    Intent intent = new Intent(ScoreActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        if(Integer.parseInt(highestScore.getText().toString())<Integer.parseInt(finalScore)) {
            sp = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("highestScore", finalScore);
            editor.apply();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.btn_star_big_on)
                    .setTitle("CONGRATULATIONS")
                    .setMessage("New High Score: " + finalScore)
                    .setPositiveButton("OK", null)
                    .show();
            highestScore.setText(finalScore);
        }
        globalRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ScoreActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("COMING SOON")
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
        /*share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot();

            }
        });*/
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
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

    /*private void takeScreenshot() {

        try {
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/Screenshots/" + "now" + ".png";
            Bitmap b = getScreenShot(rootView);
            store(b,mPath);
            // create bitmap screen capture
            //View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }*/

    /*public static Bitmap getScreenShot(View view) {
        //view.measure(DrawerLayout.LayoutParams.WRAP_CONTENT, DrawerLayout.LayoutParams.WRAP_CONTENT);
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);
        return b;
    }

        private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri photoURI = FileProvider.getUriForFile(ScoreActivity.this,
                BuildConfig.APPLICATION_ID + ".provider",
                imageFile);
        intent.setDataAndType(photoURI, "image/*");
        startActivity(intent);
    }

    public static void store(Bitmap bm, String fileName){
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
