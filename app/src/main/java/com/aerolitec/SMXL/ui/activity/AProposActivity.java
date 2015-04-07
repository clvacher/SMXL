package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.aerolitec.SMXL.R;

public class AProposActivity extends Activity {
    TextView tvAPropos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);
    }

}
