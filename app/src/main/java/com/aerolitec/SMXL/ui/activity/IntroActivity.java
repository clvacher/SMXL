package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.aerolitec.SMXL.R;
import com.crashlytics.android.Crashlytics;


public class IntroActivity extends Activity {
    // Splash screen timerS
    private static int SPLASH_TIME_OUT = 5000;
    RelativeLayout rlIntro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Crashlytics.start(this);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_intro);


        //rlIntro = (RelativeLayout) findViewById(R.id.rlIntro);
        //TranslateAnimation trans = new TranslateAnimation(0, 0, 0, -(getWindowManager().getDefaultDisplay().getHeight()));
        //trans.setDuration(5000);
        //trans.setInterpolator(new AccelerateInterpolator(1.0f));
        //rlIntro.startAnimation(trans);

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
