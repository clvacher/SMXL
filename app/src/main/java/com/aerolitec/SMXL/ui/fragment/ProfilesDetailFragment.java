package com.aerolitec.SMXL.ui.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;
import com.aerolitec.SMXL.ui.adapter.FavoriteBrandAdapter;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.util.ArrayList;

public class ProfilesDetailFragment extends Fragment{


    // TODO: Rename and change types of parameters
    private static User user;
    private TextView tvFirstName, tvLastName, tvAgeSexe,nbBrands;
    private EditText etDescription;
    private ProfilePictureRoundedImageView imgAvatar;
    private ImageView imgQuicksize;
    private RelativeLayout layoutHeaderBrands;

    ListView brandListView;

    private ArrayList<Brand> userBrands;
    
    // TODO: Rename and change types and number of parameters
    public static ProfilesDetailFragment newInstance(User param1, String param2) {
        return new ProfilesDetailFragment();
    }

    public ProfilesDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        user = UserManager.get().getUser();
        if(user==null)
            Log.d("TestOnCreate","user null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);

        if(user == null) {
            getActivity().finish();
            return null;
        }

        tvFirstName = (TextView) view.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) view.findViewById(R.id.tvLastName);
        tvAgeSexe = (TextView) view.findViewById(R.id.tvAgeSexe);
        etDescription = (EditText) view.findViewById(R.id.description);

        imgAvatar = (ProfilePictureRoundedImageView) view.findViewById(R.id.imgAvatar);

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","update");
                startActivity(intent);
            }
        });

        RelativeLayout infosProfile = (RelativeLayout) view.findViewById(R.id.profilLayout);
        infosProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","update");
                startActivity(intent);
            }
        });


        brandListView = (ListView) view.findViewById(R.id.layoutViewBrand);
        nbBrands=(TextView) view.findViewById(R.id.nbBrands);

        layoutHeaderBrands = (RelativeLayout) view.findViewById(R.id.layoutHeaderBrands);
        LinearLayout layoutBrands=(LinearLayout) view.findViewById(R.id.layoutBrand);
        FrameLayout addBrand= (FrameLayout) view.findViewById(R.id.tmp);


        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brandListView.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","brands");
                startActivity(intent);
            }
        });
        layoutBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseBrand);
                if (brandListView.getVisibility() == View.GONE) {
                    fillListView(brandListView, userBrands);
                    brandListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    brandListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        final RelativeLayout quicksizeLayout=(RelativeLayout) view.findViewById(R.id.quicksize);
        ImageView noClickZone=(ImageView) view.findViewById(R.id.noClickZone);

        quicksizeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        quicksizeLayout.setBackgroundResource(android.R.color.holo_blue_light);
                        break;
                }
                return false;
            }
        });
        noClickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    //Fills the listView with the ArrayList of UserClothes provided, using a GarmentAdapter
    private void fillListView(ListView v,ArrayList<Brand> userBrandList){
        FavoriteBrandAdapter adapter = new FavoriteBrandAdapter(this.getActivity(),R.layout.brand_item,userBrandList);
        v.setAdapter(adapter);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO a completer pour les details des marques (jerome) --> website
            }
        });
        setListViewHeightBasedOnChildren(v);
        adapter.notifyDataSetChanged();
    }

    //Allows the ListView to adapt to its content
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onPause(){
        String desc= etDescription.getText().toString();
        if(!desc.equals(user.getDescription())){
            user.setDescription(desc);
            UserManager.get().setUser(user);
            SMXL.getUserDBManager().updateUser(user);
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        user=UserManager.get().getUser();

        userBrands=SMXL.getUserBrandDBManager().getAllUserBrands(user);


        updateProfile();

        //updates the number of brands
        int tmp;
        tmp=userBrands.size();
        nbBrands.setText("(" + tmp + ")");
        if(tmp==0){
            layoutHeaderBrands.setVisibility(View.GONE);
        }
        else{
            layoutHeaderBrands.setVisibility(View.VISIBLE);
        }
    }

    private void updateProfile(){

        tvFirstName.setText(user.getFirstname());
        tvLastName.setText(user.getLastname());
        String profileDescription = user.getDescription();
        if (profileDescription!=null) {
            etDescription.setText(profileDescription);
        }

        String fnAvatar = user.getAvatar();
        Log.d("useravatar detail fragm", fnAvatar.toString());
        imgAvatar.setImage(fnAvatar);

        //TODO "H"
        int age = user.getAge(user.getBirthday());
        String sexe = getResources().getString(R.string.woman);
        if (user.getSexe().startsWith("H")) {
            sexe = getResources().getString(R.string.man);
        }
        tvAgeSexe.setText(age +" "+getResources().getString(R.string.years)+ " / " + sexe);
    }

}
