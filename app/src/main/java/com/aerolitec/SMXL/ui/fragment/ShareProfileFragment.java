package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.tools.serverConnexion.GetSharedProfileHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.GetSharingCodeHttpAsyncTask;
import com.aerolitec.SMXL.tools.serverConnexion.GetSharingCodeInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareProfileFragment extends Fragment implements GetSharingCodeInterface{


    TextView tvShareProfile;
    EditText etObtainProfile;

    public ShareProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_profile, container, false);

        tvShareProfile = (TextView) view.findViewById(R.id.textViewShareProfile);
        etObtainProfile = (EditText) view.findViewById(R.id.etObtainProfile);
        Button getProfile = (Button) view.findViewById(R.id.buttonGetProfile);

        MainUser mainUser = MainUserManager.get().getMainUser();
        User user = UserManager.get().getUser();

        int userServerId = MainUserManager.get().getMainUser().getServerId();
        int profileId = UserManager.get().getUser().getServer_id();

        new GetSharingCodeHttpAsyncTask(this).execute(userServerId,profileId);
        //new GetSharingCodeHttpAsyncTask(this).execute(MainUserManager.get().getMainUser().getServerId(),MainUserManager.get().getMainUser().getProfiles().get(0));

        tvShareProfile.setText("Obtaining...");


        getProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etObtainProfile.getText().toString().trim().isEmpty()) {
                    new GetSharedProfileHttpAsyncTask(getActivity()).execute(Integer.parseInt(etObtainProfile.getText().toString()));
                }
                }
        });


        return view;
    }


    @Override
    public void onServerError(String ErrorMsg) {
        tvShareProfile.setText("Error");
    }

    @Override
    public void onCodeRetrieved(int sharingCode) {
        tvShareProfile.setText(sharingCode+"");
    }
}
