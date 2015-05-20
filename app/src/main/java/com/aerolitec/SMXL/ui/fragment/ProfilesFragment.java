package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.adapter.ProfilesAdapter;
import com.aerolitec.SMXL.ui.fragment.dialog.ConfirmDialogFragment;

import java.util.ArrayList;

public class ProfilesFragment extends Fragment implements ConfirmDialogFragment.ConfirmDialogListener{

    private final static int DELETE_PROFILE = 1;

    private View view;
    private ArrayList<ProfileItem> profileItem;
    private ProfilesAdapter profilesAdapter;
    private GridView gridViewProfiles;



    public static ProfilesFragment newInstance() {
        ProfilesFragment fragment = new ProfilesFragment();
        return fragment;
    }
    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profiles, container, false);

        //getFragmentManager().beginTransaction().add(R.id.containerSlideSizeGuide, new SizeGuideFragment()).commit();
        //Fragment sizeGuideFragment = new SizeGuideFragment();
        //getFragmentManager().beginTransaction().add(R.id.containerSlideSizeGuide, new SizeGuideFragment()).commit();

        gridViewProfiles = (GridView)view.findViewById(R.id.listViewProfiles);
        profileItem = new ArrayList<>();

        profilesAdapter = new ProfilesAdapter(getActivity().getApplicationContext(),profileItem);
        gridViewProfiles.setAdapter(profilesAdapter);
        gridViewProfiles.setOnItemClickListener(selectProfile);

        gridViewProfiles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == 1)
                    return false;

                DialogFragment confirmDialog = new ConfirmDialogFragment();
                Bundle args = new Bundle();
                args.putString("confirm", getResources().getString(R.string.remove));
                args.putString("message", getResources().getString(R.string.removeGarment));
                args.putInt("id", position);
                confirmDialog.setArguments(args);
                confirmDialog.setTargetFragment(ProfilesFragment.this, DELETE_PROFILE);
                confirmDialog.show(getFragmentManager(), "remove");
                return true;
            }
        });


        ArrayList<ProfileItem> local = null;
        if(savedInstanceState != null) {
            local = (ArrayList<ProfileItem>) savedInstanceState.getSerializable("profiles");
        }

        if(local != null && local.size() > 0) {
            profileItem.clear();
            profileItem.addAll(local);
            profilesAdapter.notifyDataSetChanged();
        } else {
            loadProfiles();
        }
        return view;
    }

    public void onConfirm(boolean confirmed, int positionItem) {
        if (confirmed) {
            //deleteCurrentProfile(positionItem);
            SMXL.getUserDBManager().deleteUser(SMXL.getUserDBManager().getUser(profileItem.get(positionItem).getId()));
            profileItem.remove(positionItem);
            profilesAdapter.notifyDataSetChanged();
        }
    }


    private void loadProfiles(){
        // load all the profiles from the database
        ArrayList<User> users = SMXL.getUserDBManager().getAllUsers();

        ProfileItem createNewProfile = new ProfileItem(0,getResources().getString(R.string.newProfile2), getResources().getString(R.string.newProfile1), "createNewProfile");
        profileItem.add(createNewProfile);

        for (User u : users) {
            profileItem.add(new ProfileItem(u.getId_user(), u.getLastname(), u.getFirstname(), u.getAvatar()));
        }
        profilesAdapter.notifyDataSetChanged();

    }

    private AdapterView.OnItemClickListener selectProfile = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(position>0) {
                itemProfileListener.profileSelect(profileItem.get(position));
            }
            else{
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","create");
                startActivity(intent);
            }
        }
    };

    private OnProfileSelected itemProfileListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            itemProfileListener = (OnProfileSelected) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " doit implementer OnProfileSelected ");
        }
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("profiles", profileItem);
    }

    @Override
    public void onResume(){
        super.onResume();
        profileItem.clear();
        loadProfiles();
    }

}
