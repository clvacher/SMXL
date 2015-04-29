package com.aerolitec.SMXL.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import com.aerolitec.SMXL.ui.adapter.GarmentAdapter;
import com.makeramen.RoundedImageView;

import java.io.File;
import java.util.ArrayList;


public class WardrobeDetailFragment extends Fragment {

    private User user;
    private View view;
    private ListView tShirtsListView,dressesListView,pantsListView,blousesListView,jacketsListView,coatsListView,shoesListView,sweatersListView,underwearListView,suitsListView;

    private ArrayList<UserClothes> userTShirts,userDresses,userPants,userBlouses,userJackets,userCoats,userShoes,userSweaters,userUnderwear,userSuits;

    private TextView nbTShirts,nbDresses,nbPants,nbBlouses,nbJackets,nbCoats,nbShoes,nbSweaters,nbUnderwear,nbSuits;

    RelativeLayout layoutHeaderTShirt,layoutHeaderDresses,layoutHeaderPants,layoutHeaderBlouses,layoutHeaderJackets,layoutHeaderCoats,layoutHeaderShoes,layoutHeaderSweaters,layoutHeaderUnderwear,layoutHeaderSuits;


    public static WardrobeDetailFragment newInstance(String param1, String param2) {
        return new WardrobeDetailFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wardrobe_detail, container, false);


        TextView tvFirstName = (TextView) view.findViewById(R.id.firstName);
        ImageView avatar=(RoundedImageView)view.findViewById(R.id.imgAvatar);

        //Initial setup of the name and picture of the user
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
        tvFirstName.setText(user.getFirstname());

        //setup of the class view attributes with findViewById
        setViews();

        //"Add" Layouts
        setAddListeners();

        //Setup of the listeners on the expand buttons (Minimize to see clearer)
        setExpandListeners();

        return view;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(user != null){
            user=UserManager.get().getUser();
        }
        updateClothesLists();
        updateClothesCounters();
    }

    private void setViews(){
        //"Expand and Collapse" Layouts
        layoutHeaderTShirt = (RelativeLayout) view.findViewById(R.id.layoutHeaderTShirts);
        layoutHeaderPants = (RelativeLayout) view.findViewById(R.id.layoutHeaderPants);
        layoutHeaderBlouses = (RelativeLayout) view.findViewById(R.id.layoutHeaderBlouses);
        layoutHeaderJackets = (RelativeLayout) view.findViewById(R.id.layoutHeaderJackets);
        layoutHeaderCoats = (RelativeLayout) view.findViewById(R.id.layoutHeaderCoats);
        layoutHeaderShoes = (RelativeLayout) view.findViewById(R.id.layoutHeaderShoes);
        layoutHeaderSweaters = (RelativeLayout) view.findViewById(R.id.layoutHeaderSweaters);
        layoutHeaderUnderwear = (RelativeLayout) view.findViewById(R.id.layoutHeaderUnderwear);
        layoutHeaderSuits = (RelativeLayout) view.findViewById(R.id.layoutHeaderSuits);

        //ListViews containing the garments
        tShirtsListView = (ListView) view.findViewById(R.id.layoutViewTShirts);
        pantsListView = (ListView) view.findViewById(R.id.layoutViewPants);
        blousesListView = (ListView) view.findViewById(R.id.layoutViewBlouses);
        jacketsListView = (ListView) view.findViewById(R.id.layoutViewJackets);
        coatsListView = (ListView) view.findViewById(R.id.layoutViewCoats);
        shoesListView = (ListView) view.findViewById(R.id.layoutViewShoes);
        sweatersListView = (ListView) view.findViewById(R.id.layoutViewSweaters);
        underwearListView = (ListView) view.findViewById(R.id.layoutViewUnderwear);
        suitsListView = (ListView) view.findViewById(R.id.layoutViewSuits);

        //clothes counters
        nbTShirts=(TextView) view.findViewById(R.id.nbTShirts);
        nbPants=(TextView) view.findViewById(R.id.nbPants);
        nbBlouses=(TextView) view.findViewById(R.id.nbBlouses);
        nbJackets=(TextView) view.findViewById(R.id.nbJackets);
        nbCoats=(TextView) view.findViewById(R.id.nbCoats);
        nbShoes=(TextView) view.findViewById(R.id.nbShoes);
        nbSweaters=(TextView) view.findViewById(R.id.nbSweaters);
        nbUnderwear=(TextView) view.findViewById(R.id.nbUnderwear);
        nbSuits=(TextView) view.findViewById(R.id.nbSuits);


        if(user.getSexe().equals("F")){
            layoutHeaderDresses = (RelativeLayout) view.findViewById(R.id.layoutHeaderDresses);
            dressesListView = (ListView) view.findViewById(R.id.layoutViewDresses);
            nbDresses=(TextView) view.findViewById(R.id.nbDresses);
        }
        else{
            view.findViewById(R.id.layoutCategoryDress).setVisibility(View.GONE);
        }
    }

    private void setAddListeners(){
        RelativeLayout addGarment=(RelativeLayout) view.findViewById(R.id.addTShirtLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tShirtsListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseTShirt)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addPantsLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pantsListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapsePants)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addBlouseLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blousesListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseBlouse)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addJacketLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jacketsListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseJacket)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addCoatLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coatsListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseCoat)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addShoesLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shoesListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseShoes)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addSweaterLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweatersListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseSweater)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addUnderwearLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                underwearListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseUnderwear)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addGarment=(RelativeLayout) view.findViewById(R.id.addSuitLayout);
        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suitsListView.setVisibility(View.GONE);
                ((ImageView) view.findViewById(R.id.collapseSuit)).setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        if(user.getSexe().equals("F")){
            addGarment=(RelativeLayout) view.findViewById(R.id.addDressLayout);
            addGarment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dressesListView.setVisibility(View.GONE);
                    ((ImageView) view.findViewById(R.id.collapseDress)).setImageResource(R.drawable.navigation_expand);
                    Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    private void setExpandListeners(){
        layoutHeaderTShirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseTShirt);
                if (tShirtsListView.getVisibility() == View.GONE) {
                    fillListView(tShirtsListView, userTShirts);
                    tShirtsListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    tShirtsListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderPants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapsePants);
                if (pantsListView.getVisibility() == View.GONE) {
                    fillListView(pantsListView, userPants);
                    pantsListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    pantsListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderBlouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseBlouse);
                if (blousesListView.getVisibility() == View.GONE) {
                    fillListView(blousesListView, userBlouses);
                    blousesListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    blousesListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderJackets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseJacket);
                if (jacketsListView.getVisibility() == View.GONE) {
                    fillListView(jacketsListView, userPants);
                    jacketsListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    jacketsListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderCoats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseCoat);
                if (coatsListView.getVisibility() == View.GONE) {
                    fillListView(coatsListView, userCoats);
                    coatsListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    coatsListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderShoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseShoes);
                if (shoesListView.getVisibility() == View.GONE) {
                    fillListView(shoesListView, userShoes);
                    shoesListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    shoesListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderSweaters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseSweater);
                if (sweatersListView.getVisibility() == View.GONE) {
                    fillListView(sweatersListView, userSweaters);
                    sweatersListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    sweatersListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderUnderwear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseUnderwear);
                if (underwearListView.getVisibility() == View.GONE) {
                    fillListView(underwearListView, userUnderwear);
                    underwearListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    underwearListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderSuits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView collapse = (ImageView) view.findViewById(R.id.collapseSuit);
                if (suitsListView.getVisibility() == View.GONE) {
                    fillListView(suitsListView, userSuits);
                    suitsListView.setVisibility(View.VISIBLE);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    suitsListView.setVisibility(View.GONE);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });
        if(user.getSexe().equals("F")){
            layoutHeaderDresses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseDress);
                    if (dressesListView.getVisibility() == View.GONE) {
                        fillListView(dressesListView, userDresses);
                        dressesListView.setVisibility(View.VISIBLE);
                        collapse.setImageResource(R.drawable.navigation_collapse);
                    } else {
                        dressesListView.setVisibility(View.GONE);
                        collapse.setImageResource(R.drawable.navigation_expand);
                    }
                }
            });
        }
    }

    //updates the user's lists of clothes
    //TODO change clotheType name (french)
    private void updateClothesLists(){
        userTShirts=SMXL.getDataBase().getUserGarmentsByGarment(user,"Tshirt","");
        userPants=SMXL.getDataBase().getUserGarmentsByGarment(user,"Pantalon","Jean");//ajouter short
        userBlouses=SMXL.getDataBase().getUserGarmentsByGarment(user,"Chemise","Chemisier");
        userJackets=SMXL.getDataBase().getUserGarmentsByGarment(user,"Manteau","Blouson");
        userCoats=SMXL.getDataBase().getUserGarmentsByGarment(user,"Veste","");
        userShoes=SMXL.getDataBase().getUserGarmentsByGarment(user,"Chaussure","");
        userSweaters=SMXL.getDataBase().getUserGarmentsByGarment(user,"Pull","Sweat");
        userUnderwear=SMXL.getDataBase().getUserGarmentsByGarment(user,"Slip","");
        userSuits=SMXL.getDataBase().getUserGarmentsByGarment(user,"Costume","");
        if(user.getSexe().equals("F")){
            userDresses=SMXL.getDataBase().getUserGarmentsByGarment(user,"Robe","Jupe");
        }
    }

    //fills the clothes counters
    private void updateClothesCounters(){
        //temporary variable
        int tmp;

        tmp=userTShirts.size();
        nbTShirts.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderTShirt.setVisibility(View.GONE);
        }
        else{
            layoutHeaderTShirt.setVisibility(View.VISIBLE);
        }
        tmp=userPants.size();
        nbPants.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderPants.setVisibility(View.GONE);
        }
        else{
            layoutHeaderPants.setVisibility(View.VISIBLE);
        }
        tmp=userBlouses.size();
        nbBlouses.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderBlouses.setVisibility(View.GONE);
        }
        else{
            layoutHeaderBlouses.setVisibility(View.VISIBLE);
        }
        tmp=userJackets.size();
        nbJackets.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderJackets.setVisibility(View.GONE);
        }
        else{
            layoutHeaderJackets.setVisibility(View.VISIBLE);
        }
        tmp=userCoats.size();
        nbCoats.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderCoats.setVisibility(View.GONE);
        }
        else{
            layoutHeaderCoats.setVisibility(View.VISIBLE);
        }
        tmp=userShoes.size();
        nbShoes.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderShoes.setVisibility(View.GONE);
        }
        else{
            layoutHeaderShoes.setVisibility(View.VISIBLE);
        }
        tmp=userSweaters.size();
        nbSweaters.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderSweaters.setVisibility(View.GONE);
        }
        else{
            layoutHeaderSweaters.setVisibility(View.VISIBLE);
        }
        tmp=userUnderwear.size();
        nbUnderwear.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderUnderwear.setVisibility(View.GONE);
        }
        else{
            layoutHeaderUnderwear.setVisibility(View.VISIBLE);
        }
        tmp=userSuits.size();
        nbSuits.setText("("+tmp+")");
        if(tmp==0){
            layoutHeaderSuits.setVisibility(View.GONE);
        }
        else{
            layoutHeaderSuits.setVisibility(View.VISIBLE);
        }

        if(user.getSexe().equals("F")){
            tmp=userDresses.size();
            nbDresses.setText("("+tmp+")");
            if(tmp==0){
                layoutHeaderDresses.setVisibility(View.GONE);
            }
            else{
                layoutHeaderDresses.setVisibility(View.VISIBLE);
            }
        }
    }

    //Fills the listView with the ArrayList of UserClothes provided, using a GarmentAdapter
    private void fillListView(ListView v,ArrayList<UserClothes> userClothesList){
        GarmentAdapter adapter = new GarmentAdapter(this.getActivity(),R.layout.garment_item,userClothesList);
        v.setAdapter(adapter);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DisplayGarmentActivity.class);
                intent.putExtra("clothes", (UserClothes) adapterView.getItemAtPosition(i));
                startActivity(intent);
            }
        });
        setListViewHeightBasedOnChildren(v);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putSerializable("garments", userTShirts);

        super.onSaveInstanceState(outState);
    }

    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
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
}
