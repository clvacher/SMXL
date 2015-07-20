package com.aerolitec.SMXL.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.PostProfileHttpAsyncTask;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateProfileDetailsFragment extends SuperCreateUpdateProfileFragment {

    SuperNavigationActivity superNavigationActivity;

    public CreateProfileDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        superNavigationActivity = (SuperNavigationActivity)getActivity();
        setHasOptionsMenu(true);
        superNavigationActivity.setBarAsNextFragment();
        superNavigationActivity.updateTitle(R.string.createProfil);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_create_update_profile_details, container, false);



        etFirstName = (EditText) view.findViewById(R.id.etFirstName);
        etLastName = (EditText) view.findViewById(R.id.etLastName);
        etNotes = (EditText) view.findViewById(R.id.etNotesProfil);
        datePickerButton = (Button) view.findViewById(R.id.buttonBirthday);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        validationButton = (Button) view.findViewById(R.id.buttonValidation);
        validationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProfile();
            }
        });

        radioSexe = (RadioGroup) view.findViewById(R.id.radioSexe);
        imgProfil = (ProfilePictureRoundedImageView) view.findViewById(R.id.imgProfil);
        FrameLayout layoutImageProfil=(FrameLayout) view.findViewById(R.id.layoutImageProfil);
        layoutImageProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageIntent();
            }
        });

        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.create_profil, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.validate :
                createProfile();
                return true;

            case android.R.id.home:
                // your code for order here
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void createProfile(){

        /// Add Profile in DataBase ///
        if (etFirstName.getText().toString().length() < 2 ||
                etLastName.getText().toString().length() < 2) {
            Toast.makeText(getActivity(), getResources().getString(R.string.incompleteProfile), Toast.LENGTH_LONG).show();
        } else {
            int sexe = 2;
            int idRadioButton = radioSexe.getCheckedRadioButtonId();
            if (idRadioButton == -1) {
                //no item selected
            } else {
                if (idRadioButton == R.id.radioMale) {
                    sexe = 1;
                }
            }


            User user = null;
            try {
                user = SMXL.getUserDBManager().createUser(etFirstName.getText().toString(),
                        etLastName.getText().toString().substring(0,1).toUpperCase()+etLastName.getText().toString().substring(1), birthday, sexe, picturePath, etNotes.getText().toString());
                Log.d(Constants.TAG, "New profile created : " + user.toString());
            } catch (Exception e) {
                Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
            }
            if (user != null) {
                getActivity().setResult(Activity.RESULT_OK);
                UserManager.get().setUser(user);
            }
            else {
                getActivity().setResult(Activity.RESULT_CANCELED);
            }

            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try {
                mgr.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
            catch(Exception e){
                e.printStackTrace();
            }

            getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).add(R.id.frame_container,new SelectBrandsCreateProfileFragment()).commit();

            superNavigationActivity.updateHamburger();
            superNavigationActivity.restoreDefaultTitleCurrentSection();

        }
    }



}
