package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.services.OnProfileSelected;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.ProfileItem;
import com.aerolitec.SMXL.ui.adapter.ProfilesAdapter;
import com.aerolitec.SMXL.ui.fragment.dialog.ConfirmDialogFragment;

import java.util.ArrayList;

public class ProfilesFragment extends Fragment implements ConfirmDialogFragment.ConfirmDialogListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static int DELETE_PROFILE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private ArrayList<ProfileItem> profileItem;
    private ProfilesAdapter adapter;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfilesFragment newInstance(String param1, String param2) {
        ProfilesFragment fragment = new ProfilesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ProfilesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profiles, container, false);
        GridView gridViewProfiles = (GridView)view.findViewById(R.id.listViewProfiles);
        profileItem = new ArrayList<>();

        adapter = new ProfilesAdapter(getActivity().getApplicationContext(),profileItem);
        gridViewProfiles.setAdapter(adapter);
        gridViewProfiles.setOnItemClickListener(selectProfile);

        gridViewProfiles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
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
            adapter.notifyDataSetChanged();
        } else {
            loadProfiles();
        }
        return view;
    }

    public void onConfirm(boolean confirmed, int positionItem) {
        if (confirmed) {
            deleteCurrentProfile(positionItem);
        }
    }

    private void deleteCurrentProfile(int positionItem) {
        String profilId = String.valueOf(profileItem.get(positionItem).getId());
        String request = "DELETE FROM user WHERE userid='"+ profilId + "'";

        String DataBaseName = "SIZEGUIDE_DB";
        SQLiteDatabase db = null;
        db = getActivity().openOrCreateDatabase(DataBaseName, getActivity().MODE_PRIVATE, null);
        db.beginTransaction();
        db.execSQL(request);
        db.setTransactionSuccessful();
        db.endTransaction();
        profileItem.remove(positionItem);
        adapter.notifyDataSetChanged();
    }

    private void loadProfiles(){
        // load all the profiles from the database

        ArrayList<User> users = SMXL.get().getDataBase().getAllUser();
        if(!users.isEmpty()) {
            for (User u : users) {
                profileItem.add(new ProfileItem(u.getUserid(), u.getLastname(), u.getFirstname(), u.getAvatar()));
            }
            adapter.notifyDataSetChanged();
        }
        else{
            (view.findViewById(R.id.listViewProfiles)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.TextNoProfile)).setVisibility(View.VISIBLE);
        }
    }

    private AdapterView.OnItemClickListener selectProfile = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            itemProfileListener.profileSelect(profileItem.get(arg2));
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

}
