// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.samples.communication.calling.views.activities;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.azure.samples.communication.calling.R;
import com.bumptech.glide.Glide;


public class SplashActivity extends AppCompatActivity {
    //Initialize variable
    ImageView ivTop, ivHeart, ivBeat, ivBottom;
    TextView textView;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            //when runable is run
            //set text
            textView.setText(charSequence.subSequence(0, index++));

            //check condition
            if (index <= charSequence.length()) {
                //when index is equal to text length
                //Run handler
                handler.postDelayed(runnable, delay);
            }

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Assign variable
        ivTop = findViewById(R.id.iv_top);
        ivHeart = findViewById(R.id.iv_heart);
        ivBeat = findViewById(R.id.iv_beat);
        ivBottom = findViewById(R.id.iv_bottom);
        textView = findViewById(R.id.text_view);

        //set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initialize top animation
        final Animation animation1 = AnimationUtils.loadAnimation(this,
                R.anim.top_wave);

        //start top animation
        ivTop.setAnimation(animation1);

        //Initialize object animator
        final ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                ivHeart,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)

        );
        //Set duration
        objectAnimator.setDuration(500);

        //Set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);

        //set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);

        //start animator
        objectAnimator.start();

        // Set animate text
        animatText("Welcome");

        //Load Gif
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/"
                        + "demoapp-ae96a.appspot.com/o/heart_beat.gif?alt=media&token=b21dddd8"
                        + "-782c-457c-babd-f2e922ba172b")
                .into(ivBeat);
        // Initialize bottom animation
        final Animation animation2 = AnimationUtils.loadAnimation(this,
                R.anim.bottom_wave);

        //start bottom animation
        ivBottom.setAnimation(animation2);

        //Initialize handler
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //redirect to
                startActivity(new Intent(SplashActivity.this,
                        VCHLoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                //finish activity
                finish();

            }
        }, 4000);
    }

    //create animated text method
    public void animatText(final CharSequence cs) {
        // set text
        charSequence = cs;
        // clear index
        index = 0;
        //clear text
        textView.setText("");
        //Remove call back
        handler.removeCallbacks(runnable);
        //Run handler
        handler.postDelayed(runnable, delay);

    }
}
