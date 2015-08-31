package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.io.FileOutputStream;

/**
 * Created by Clement on 5/13/2015.
 */
public class UpdateProfileDetailsFragment extends SuperCreateUpdateProfileFragment {

    private User user;

    public UpdateProfileDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = UserManager.get().getUser();

        if (user == null) {
            getActivity().finish();
            return;
        }

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_update_profile_details, container, false);


        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etNotes = (EditText) view.findViewById(R.id.etNotesProfil);
        tvBirthday = (TextView) view.findViewById(R.id.tvBirthday);
        ivBirthday = (ImageView) view.findViewById(R.id.ivBirthday);
        radioSexe = (RadioGroup) view.findViewById(R.id.radioSexe);
        RadioButton radioH = (RadioButton) view.findViewById(R.id.radioMale);
        RadioButton radioF = (RadioButton) view.findViewById(R.id.radioFemale);
        imgProfil = (ProfilePictureRoundedImageView) view.findViewById(R.id.imgProfil);

        birthday = user.getBirthday();
        tvBirthday.setText(birthday);
        etNotes.setText(user.getDescription());
        etFirstName.setText(user.getFirstname());
        etLastName.setText(user.getLastname());

        if (user.getSexe()==1){
            radioH.setChecked(true);
        }
        else {
            radioF.setChecked(true);
        }

        picturePath = user.getAvatar();
        Log.d("ImageProfil",picturePath+"");
        imgProfil.setImage(picturePath);


        ivBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        validationButton = (Button) view.findViewById(R.id.buttonValidation);
        validationButton.setText(getResources().getString(R.string.validate));
        validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        radioSexe = (RadioGroup) view.findViewById(R.id.radioSexe);
        imgProfil = (ProfilePictureRoundedImageView) view.findViewById(R.id.imgProfil);
        FrameLayout layoutImageProfil = (FrameLayout) view.findViewById(R.id.layoutImageProfil);
        layoutImageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getActivity().getMenuInflater().inflate(R.menu.create_profil, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.validate) {

            updateProfile();

        }

        if (id == android.R.id.home){
            getActivity().onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateProfile() {
        /// Add Profile in DataBase ///
        if (etFirstName.getText().toString().length() < 1 ||
                etLastName.getText().toString().length() < 1){
            Toast.makeText(getActivity(),getResources().getText(R.string.incompleteProfile),Toast.LENGTH_LONG).show();
        }
        else {
            int sexe = 2;
            int idRadioButton = radioSexe.getCheckedRadioButtonId();
            if (idRadioButton == R.id.radioMale){
                sexe = 1;
            }

            user.setAvatar(picturePath);
            user.setBirthday(birthday);
            user.setFirstname(etFirstName.getText().toString());
            user.setLastname(etLastName.getText().toString().substring(0,1).toUpperCase()+etLastName.getText().toString().substring(1));

            user.setDescription(etNotes.getText().toString());

            user.setSexe(sexe);

            if(MainUserManager.get().getMainUser().getMainProfile().getId_user() == user.getId_user()){
                MainUserManager.get().getMainUser().setFirstname(etFirstName.getText().toString());
                MainUserManager.get().getMainUser().setLastname(etLastName.getText().toString().substring(0, 1).toUpperCase() + etLastName.getText().toString().substring(1));
                MainUserManager.get().getMainUser().setAvatar(picturePath);
                MainUserManager.get().getMainUser().setSex(sexe);
                try {
                    FileOutputStream fos = null;
                    fos = getActivity().openFileOutput(Constants.MAIN_USER_FILE, Context.MODE_PRIVATE);
                    fos.flush();
                    fos.write(MainUserManager.get().getMainUser().getBytes());
                    // Apply the changes to the User in UserManager in order to update the ProfilesDetailFragment
                    UserManager.get().setUser(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            try {
                SMXL.getUserDBManager().updateUser(user);
                Log.d(Constants.TAG, "Profile updated");
            }
            catch (Exception e) {
                Log.e(Constants.TAG,"Update user with error : " + e.getMessage());
            }
            getActivity().onBackPressed();
        }
    }

    @Override
    public void onPause() {
        InputMethodManager inputManager = ( InputMethodManager ) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        super.onPause();
    }
}

