package com.goodfolks.eight.eight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class HomePage extends AppCompatActivity {
    ImageButton play;
    private FirebaseAnalytics mFirebaseAnalytics;
    TextView highestScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        play = findViewById(R.id.play);
        highestScore = findViewById(R.id.textViewHighestScore);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String oldHighest = sp.getString("highestScore", "0");
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Eight");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        highestScore.setText(oldHighest);
        play.setBackgroundColor(Color.TRANSPARENT);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation pulse = AnimationUtils.loadAnimation(HomePage.this, R.anim.pulse);
                play.startAnimation(pulse);
                Intent intent = new Intent(HomePage.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
