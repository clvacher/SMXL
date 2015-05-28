package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.customLayout.CustomGlobalGarmentWardrobeLayout;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;


public class WardrobeDetailFragment extends Fragment {

    private User user;
    private View view;

    LinearLayout linearLayout;

    public WardrobeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= UserManager.get().getUser();

        if(user==null)
            Log.d("WardrobeFrag", "user null");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wardrobe_detail, container, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutWardrobe);

        TextView tvFirstName = (TextView) view.findViewById(R.id.firstName);
        ProfilePictureRoundedImageView avatar=(ProfilePictureRoundedImageView)view.findViewById(R.id.imgAvatar);

        //Initial setup of the name and picture of the user
        avatar.setImage(user.getAvatar());
        tvFirstName.setText(user.getFirstname());

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        user=UserManager.get().getUser();
        initLinearLayout();
    }

    private void initLinearLayout(){
        linearLayout.removeAllViews();
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(1)));
        if(user.getSexe()==2){
            linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(2)));
        }
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(3)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(4)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(5)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(6)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(7)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(8)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(9)));
        linearLayout.addView(new CustomGlobalGarmentWardrobeLayout(getActivity().getApplicationContext(), SMXL.getCategoryGarmentDBManager().getCategoryGarment(10)));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

}
