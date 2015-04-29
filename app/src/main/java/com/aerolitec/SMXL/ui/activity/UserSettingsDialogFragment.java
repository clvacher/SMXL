package com.aerolitec.SMXL.ui.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

public class UserSettingsDialogFragment extends DialogFragment {
    RadioGroup rgSettingLength;
    RadioGroup rgSettingWeight;
    Button btnAccept;
    public int weight;
    public int length;

    private EditText mEditText;
    private User user;

    public UserSettingsDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    static UserSettingsDialogFragment newInstance(User user) {
        UserSettingsDialogFragment f = new UserSettingsDialogFragment();

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_settings, container);

        user = UserManager.get().getUser();

        if(user == null) {
            dismiss();
            return null;
        }

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        length = user.getUnitLength();
        weight = user.getUnitWeight();

        if(user.getUnitLength() == Constants.INCH) {
            ((RadioButton) view.findViewById(R.id.radioInch)).setChecked(true);
            ((RadioButton) view.findViewById(R.id.radioCm)).setChecked(false);
        }

        if(user.getUnitWeight() == Constants.POUNDS) {
            ((RadioButton) view.findViewById(R.id.radioPounds)).setChecked(true);
            ((RadioButton) view.findViewById(R.id.radioKilo)).setChecked(false);
        }

        rgSettingLength = (RadioGroup) view.findViewById(R.id.rgSettingLength);

        rgSettingLength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int idRadioButton = rgSettingLength.getCheckedRadioButtonId();
                if (idRadioButton!=-1){
                    if (idRadioButton == R.id.radioInch){
                        length = Constants.INCH;
                    }
                    else {
                        length = Constants.CM;
                    }

                }
            }
        });

        rgSettingWeight = (RadioGroup) view.findViewById(R.id.rgSettingWeight);

        rgSettingWeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int idRadioButton = rgSettingWeight.getCheckedRadioButtonId();
                if (idRadioButton!=-1){
                    if (idRadioButton == R.id.radioPounds){
                        weight = Constants.POUNDS;
                    }
                    else {
                        weight = Constants.KG;
                    }

                }
            }
        });


        btnAccept = (Button) view.findViewById(R.id.validate);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                UserSettingsDialogFragment.this.dismiss();
            }
        });

        return view;
    }


        /*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Serializable o = i.getSerializableExtra("USER");
        user = (User)o;

        rgSettingLength = (RadioGroup) findViewById(R.id.rgSettingLength);

        rgSettingLength.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int idRadioButton = rgSettingLength.getCheckedRadioButtonId();
                if (idRadioButton!=-1){
                    if (idRadioButton == R.id.radioInch){
                        length = Constants.INCH;
                    }
                    else {
                        length = Constants.CM;
                    }

                }
            }
        });

        rgSettingWeight = (RadioGroup) findViewById(R.id.rgSettingWeight);

        rgSettingWeight.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int idRadioButton = rgSettingWeight.getCheckedRadioButtonId();
                if (idRadioButton!=-1){
                    if (idRadioButton == R.id.radioPounds){
                        weight = Constants.POUNDS;
                    }
                    else {
                        weight = Constants.KG;
                    }

                }
            }
        });

        /*btnAccept = (Button) findViewById(R.id.butAccept);
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
                finish();
            }
        });
        */

        /*

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.validate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                saveSettings();
                finish();
                return true;

            case R.id.validate :
                saveSettings();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    */

    private void saveSettings(){
        user.setUnitLength(length);
        user.setUnitWeight(weight);
        SMXL.getUserDBManager().updateUser(user);
        this.dismiss();
    }

}
