package com.aerolitec.SMXL.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;
import com.aerolitec.SMXL.ui.activity.QuickSizeActivityv2;
import com.aerolitec.SMXL.ui.adapter.FavoriteBrandAdapter;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.util.ArrayList;

public class ProfilesDetailFragment extends Fragment{

    private static User user;
    private TextView tvFirstName, tvLastName, tvAgeSexe, nbBrands;
    private EditText etDescription;
    private ProfilePictureRoundedImageView imgAvatar;
    private ImageView imgQuicksize, collapseBrands;
    private RelativeLayout layoutHeaderBrands;
    private LinearLayout layoutBrands;


    ListView brandListView;

    private ArrayList<Brand> userBrands;

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
            Log.d("ProfilDetailFrag","user null");
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

                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new UpdateProfileDetailsFragment()).commit();
                /*Intent intent = new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","update");
                startActivity(intent);*/
            }
        });

        RelativeLayout infosProfile = (RelativeLayout) view.findViewById(R.id.profilLayout);
        infosProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new UpdateProfileDetailsFragment()).commit();
                /*Intent intent = new Intent(getActivity().getApplicationContext(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","update");
                startActivity(intent);*/
            }
        });


        brandListView = (ListView) view.findViewById(R.id.layoutViewBrand);
        nbBrands=(TextView) view.findViewById(R.id.nbBrands);

        layoutHeaderBrands = (RelativeLayout) view.findViewById(R.id.layoutHeaderBrands);
        layoutBrands=(LinearLayout) view.findViewById(R.id.layoutBrand);
        FrameLayout addBrand= (FrameLayout) view.findViewById(R.id.tmp);
        collapseBrands = (ImageView) view.findViewById(R.id.collapseBrand);



        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brandListView.setVisibility(View.GONE);
                collapseBrands.setImageResource(R.drawable.navigation_expand);

                getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new SelectBrandsFragment()).commit();
                /*Intent intent = new Intent(getActivity(), CreateUpdateProfileActivity.class);
                intent.putExtra("fragmentType","brands");
                startActivity(intent);*/
            }
        });

        ImageView noClickZone=(ImageView) view.findViewById(R.id.noClickZone);
        RelativeLayout quicksizeLayout=(RelativeLayout) view.findViewById(R.id.quicksize);

        quicksizeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), QuickSizeActivityv2.class);
                startActivity(intent);
            }
        });

        noClickZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }

    private void fillListView(ListView v, final ArrayList<Brand> userBrandList){
        FavoriteBrandAdapter adapter = new FavoriteBrandAdapter(this.getActivity(),R.layout.brand_item,userBrandList);
        v.setAdapter(adapter);
        Log.d("userBrand", userBrandList.toString());
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String urlBrand = userBrandList.get(position).getBrandWebsite();
                if(urlBrand != null){
                    if (!urlBrand.startsWith("http://") && !urlBrand.startsWith("https://"))
                    {
                        urlBrand = "http://" + urlBrand;
                    }
                    //TODO webview ne pas ouvrir le navigateur
                    //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlBrand));
                    Intent browserIntent = new Intent(getActivity(), BrowserActivity.class);
                    browserIntent.putExtra("URL", urlBrand);
                    browserIntent.putExtra("TITLE", userBrandList.get(position).getBrand_name());
                    startActivity(browserIntent);
                }
            }
        });
        UtilityMethodsv2.setListViewHeightBasedOnChildren(v);
        adapter.notifyDataSetChanged();
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
            layoutBrands.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    brandListView.setVisibility(View.GONE);
                    collapseBrands.setImageResource(R.drawable.navigation_expand);

                    getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.frame_container, new SelectBrandsFragment()).commit();
                    /*Intent intent = new Intent(getActivity(), CreateUpdateProfileActivity.class);
                    intent.putExtra("fragmentType","brands");
                    startActivity(intent);*/
                }
            });

        }
        else{
            layoutHeaderBrands.setVisibility(View.VISIBLE);
            layoutBrands.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (brandListView.getVisibility() == View.GONE) {
                        fillListView(brandListView, userBrands);
                        brandListView.setVisibility(View.VISIBLE);
                        collapseBrands.setImageResource(R.drawable.navigation_collapse);
                    } else {
                        brandListView.setVisibility(View.GONE);
                        collapseBrands.setImageResource(R.drawable.navigation_expand);
                    }
                }
            });
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
        imgAvatar.setImage(fnAvatar);

        int age = user.getAge(user.getBirthday());
        String sexe = getResources().getString(R.string.woman);
        if (user.getSexe()==1) {
            sexe = getResources().getString(R.string.man);
        }
        tvAgeSexe.setText(age +" "+getResources().getString(R.string.years)+ " / " + sexe);
    }

    public void updateBrandCounter(){
        nbBrands.setText("(" + userBrands.size() + ")");
        if(userBrands.size()==0){
            layoutHeaderBrands.setVisibility(View.GONE);
        }
        else{
            layoutHeaderBrands.setVisibility(View.VISIBLE);
        }
    }

}
