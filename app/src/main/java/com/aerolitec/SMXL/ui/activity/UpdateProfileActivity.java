package com.aerolitec.SMXL.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

/**
 * Created by stephaneL on 31/03/14.
 */
public class UpdateProfileActivity extends SuperCreateUpdateProfileActivity{

    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        user = UserManager.get().getUser();

        if(user == null) {
            finish();
            return;
        }

        getActionBar().setTitle("");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etNotes = (EditText) findViewById(R.id.etNotesProfil);
        radioSexe = (RadioGroup) findViewById(R.id.radioSexe);
        RadioButton radioH = (RadioButton) findViewById(R.id.radioMale);
        RadioButton radioF = (RadioButton) findViewById(R.id.radioFemale);
        imgProfil = (ProfilePictureRoundedImageView) findViewById(R.id.imgProfil);
        datePickerButton = (Button) findViewById(R.id.buttonBirthday);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        birthday = user.getBirthday();
        datePickerButton.setText(birthday);
        etNotes.setText(user.getDescription());
        etFirstName.setText(user.getFirstname());
        etLastName.setText(user.getLastname());

        ((Button)findViewById(R.id.buttonValidation)).setText(R.string.validate);


        if (user.getSexe().startsWith("H")){
            radioH.setChecked(true);
        }
        else {
            radioF.setChecked(true);
        }

        picturePath = user.getAvatar();
        imgProfil.setImage(picturePath);

        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectProfilPicture();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.validate, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.validate) {

            onActionCreateProfile(null);
        }

        if( id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActionCreateProfile (View v){


        /// Add Profile in DataBase ///
        if (etFirstName.getText().toString().length() < 1 ||
                etLastName.getText().toString().length() < 1){
            Toast.makeText(this,getResources().getText(R.string.incompleteProfile),Toast.LENGTH_LONG).show();
        }
        else {
            String sexe = "F";
            int idRadioButton = radioSexe.getCheckedRadioButtonId();
            if (idRadioButton == R.id.radioMale){
                sexe = "H";
            }

            user.setAvatar(picturePath);
            user.setBirthday(birthday);
            user.setFirstname(etFirstName.getText().toString());
            user.setLastname(etLastName.getText().toString());

            user.setDescription(etNotes.getText().toString());

            user.setSexe(sexe);

            try {
                SMXL.getUserDBManager().updateUser(user);
                Log.d(Constants.TAG, "Profile updated");
            }
            catch (Exception e) {
                Log.d(Constants.TAG,"Update user with error : " + e.getMessage());
            }
            finish();
        }
    }

    private void selectProfilPicture(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,77);
    }
}
