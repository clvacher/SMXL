package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;
import android.widget.ImageView;

import com.aerolitec.SMXL.R;


public class IntroActivity extends Activity {
    // Splash screen timerS
    private static int SPLASH_TIME_OUT = 2000;
    RelativeLayout rlIntro;
    ImageView launchIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crashlytics.start(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_intro);

        launchIcon = (ImageView)findViewById(R.id.imgLogo);

        AnimationSet scal1 = new AnimationSet(true);

        scal1.addAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_0_85));
        scal1.addAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_85_75));
        scal1.addAnimation(AnimationUtils.loadAnimation(this, R.anim.zoom_75_100));

        launchIcon.setAnimation(scal1);

        launchIcon.startAnimation(scal1);

        /*Animation scal1 = new ScaleAnimation(0, 0.75f, 0.1f, 0.75f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        ScaleAnimation scal2 = new ScaleAnimation(0.75f, 0.50f, 0.75f, 0.50f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scal2.setDuration(1000);
        launchIcon.startAnimation(scal2);

        ScaleAnimation scal3 = new ScaleAnimation(0.50f, 1.0f, 0.50f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scal3.setDuration(1000);
        launchIcon.startAnimation(scal3);
        TranslateAnimation trans = new TranslateAnimation(0, 0, 0, -(getWindowManager().getDefaultDisplay().getHeight()));
        trans.setDuration(5000);


        rlIntro = (RelativeLayout) findViewById(R.id.rlIntro);
        TranslateAnimation trans = new TranslateAnimation(0, 0, 0, -(getWindowManager().getDefaultDisplay().getHeight()));
        trans.setDuration(5000);
        trans.setInterpolator(new AccelerateInterpolator(1.0f));
        rlIntro.startAnimation(trans);*/

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), ProfilActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

}
