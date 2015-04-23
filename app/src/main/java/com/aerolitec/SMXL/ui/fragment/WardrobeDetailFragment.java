package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;
import com.aerolitec.SMXL.ui.activity.DisplayGarmentActivity;
import com.aerolitec.SMXL.ui.activity.ListGarmentActivity;
import com.aerolitec.SMXL.ui.adapter.GarmentAdapter;
import com.makeramen.RoundedImageView;

import java.io.File;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WardrobeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WardrobeDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WardrobeDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    private User user;
    private View view;
    private TextView tvFirstName, nbGarments;
    private ImageView avatar;
    private RelativeLayout addGarment, layoutHeaderGarments;
    LinearLayout layoutGarment;

    private OnFragmentInteractionListener mListener;
    private GarmentAdapter adapterGarments;

    private ArrayList<UserClothes> userClothes,listClothes;

    // TODO: Rename and change types and number of parameters
    public static WardrobeDetailFragment newInstance(String param1, String param2) {
        WardrobeDetailFragment fragment = new WardrobeDetailFragment();
        return fragment;
    }

    public WardrobeDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= UserManager.get().getUser();
        if(user==null)
            Log.d("TestOnCreate", "user null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wardrobe_detail, container, false);
        tvFirstName = (TextView) view.findViewById(R.id.firstName);
        tvFirstName.setText(user.getFirstname());
        avatar=(ImageView)view.findViewById(R.id.imgAvatar);
        nbGarments=(TextView) view.findViewById(R.id.nbGarments);
        userClothes = SMXL.getDataBase().getAllUserGarments(user);

        addGarment=(RelativeLayout) view.findViewById(R.id.layoutAddGarment);
        layoutHeaderGarments = (RelativeLayout) view.findViewById(R.id.layoutHeaderGarments);
        layoutGarment = (LinearLayout) view.findViewById(R.id.layoutViewGarments);

        listClothes = new ArrayList<>();
        adapterGarments = new GarmentAdapter(getActivity().getApplicationContext(), listClothes);

        avatar = (RoundedImageView) view.findViewById(R.id.imgAvatar);
        String fnAvatar = user.getAvatar();
        if (fnAvatar != null) {
            int width = getPixelsFromDip(80, getActivity());
            try {
                File file = new File(fnAvatar);
                if (file.exists()) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                    options.inSampleSize = ImageHelper.calculateInSampleSize(options, width, width);
                    options.inJustDecodeBounds = false;
                    avatar.setImageBitmap(ImageHelper.getCorrectBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getAbsolutePath()));
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting Picture to File : " + e.getMessage());
            }
        }



        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        layoutHeaderGarments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutGarment.getVisibility() == View.GONE) {
                    layoutGarment.setVisibility(View.VISIBLE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseGarment);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    layoutGarment.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseGarment);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(user != null){
            user=UserManager.get().getUser();
            fillListGarments();
        }
    }

    private void fillListGarments() {
        ((LinearLayout) getView().findViewById(R.id.layoutViewGarments)).removeAllViews();
        userClothes = SMXL.getDataBase().getAllUserGarments(user);

        nbGarments.setText("("+userClothes.size()+")");

        for (UserClothes uc : userClothes) {
            View viewToLoad = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.garment_item, null);
            View separator = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.separator_list, null);
            ((TextView) viewToLoad.findViewById(R.id.tvNameGarment)).setText(uc.getType());
            ((TextView) viewToLoad.findViewById(R.id.tvSize)).setText(uc.getSize());
            ((TextView) viewToLoad.findViewById(R.id.tvBrandGarment)).setText(uc.getBrand());
            ((LinearLayout) getView().findViewById(R.id.layoutViewGarments)).addView(viewToLoad);

            final UserClothes ucC=uc;
            viewToLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DisplayGarmentActivity.class);
                    Log.d("test",ucC.toString());
                    intent.putExtra("clothes", ucC);
                    startActivity(intent);
                }
            });

            ((LinearLayout) getView().findViewById(R.id.layoutViewGarments))
                    .addView(separator);

            adapterGarments.add(uc);
        }
        adapterGarments.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("garments", listClothes);

        super.onSaveInstanceState(outState);
    }

    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
