package com.aerolitec.SMXL.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.makeramen.RoundedImageView;

/**
 * Created by stephaneL on 21/03/14.
 */
public class CreateProfile extends SuperCreateUpdateProfileActivity{

    private boolean confirmExit=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etNotes = (EditText) findViewById(R.id.etNotesProfil);
        datePickerButton = (Button) findViewById(R.id.buttonBirthday);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        radioSexe = (RadioGroup) findViewById(R.id.radioSexe);
        imgProfil = (RoundedImageView) findViewById(R.id.imgProfil);
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

        getMenuInflater().inflate(R.menu.create_profil, menu);
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

        } else if (id == R.id.newPicture) {
            selectProfilPicture();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActionCreateProfile (View v){

        /// Add Profile in DataBase ///
        if (etFirstName.getText().toString().length() < 2 ||
                etLastName.getText().toString().length() < 2) {
            Toast.makeText(this, getResources().getString(R.string.incompleteProfile), Toast.LENGTH_LONG).show();
        } else {
            String sexe = "F";
            int idRadioButton = radioSexe.getCheckedRadioButtonId();
            if (idRadioButton == -1) {
                //no item selected
            } else {
                if (idRadioButton == R.id.radioMale) {
                    sexe = "H";
                }
            }


            User user = null;
            try {
                user = SMXL.getUserDBManager().createUser(etFirstName.getText().toString(),
                        etLastName.getText().toString(), birthday, sexe, picturePath, etNotes.getText().toString());
                Log.d(Constants.TAG, "New profile created : " + user.toString());
            } catch (Exception e) {
                Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
            }
            if (user != null)
                setResult(user.getId_user());
            else
                setResult(0);

            finish();
            UserManager.get().setUser(user);
            Intent intent = new Intent(getApplicationContext(), SelectBrandsActivity.class);
            startActivity(intent);
        }
    }

    private void selectProfilPicture() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 77);
    }





    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }


    @Override
    public void onBackPressed() {
        if (confirmExit == true) {
            super.onBackPressed();
        } else {
            confirmExit = true;
            Toast toast = Toast.makeText(this, getResources().getText(R.string.returnCreate), Toast.LENGTH_SHORT);
            AsyncTask task = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        Log.d("catchCreateProfile", "InterruptedException");
                    }
                    confirmExit = false;
                    return null;
                }
            };
            toast.show();
            task.execute();
        }
    }
}
