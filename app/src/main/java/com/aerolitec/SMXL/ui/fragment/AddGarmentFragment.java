package com.aerolitec.SMXL.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;


public class AddGarmentFragment extends Fragment {

    AddGarmentActivity activity;

    private User user;
    private GarmentType selectedGarmentType;
    private Brand selectedBrand;
    private String selectedSize;
    private CategoryGarment selectedCategory;
    private int selectedIdUserClothes=-1;
    private UserClothes userClothes;

    private String comment;

    private TextView tvBrand,tvGarmentType,tvSize;



    public AddGarmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (AddGarmentActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_garment, container, false);

        user = UserManager.get().getUser();
        if(user == null) {
            Log.d("AddGarmentActivity","userNull");
            activity.finish();
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gets the selected garment category from the intent

        tvBrand=(TextView)view.findViewById(R.id.garmentBrand);
        tvGarmentType = (TextView)view.findViewById(R.id.garmentType);
        tvSize=(TextView)view.findViewById(R.id.textSize);


        Bundle extras=activity.getIntent().getExtras();
        if(extras != null) {
            if((selectedCategory = (CategoryGarment) extras.get("category")) == null){
                existingGarment(extras);
            }
            else{
                Fragment fragment = new SelectGarmentTypeFragment();
                getChildFragmentManager().beginTransaction()
                        .add(R.id.containerAddGarmentFragment, fragment, "type")
                        .commit();

                //getActionBar().setTitle(getResources().getString(R.string.add_garment));
            }
        }
        else{
            activity.finish();
        }

        ((ImageView)view.findViewById(R.id.garmentIcon)).setImageResource(selectedCategory.getIcon());

    }

    private void existingGarment(Bundle extras){
        userClothes = (UserClothes) extras.get("userClothes");
        selectedGarmentType = userClothes.getGarmentType();
        selectedBrand = userClothes.getBrand();
        selectedSize = userClothes.getSize();
        selectedIdUserClothes = userClothes.getId_user_clothes();
        selectedCategory = userClothes.getGarmentType().getCategoryGarment();
        comment = userClothes.getComment();

        tvBrand.setText(userClothes.getBrand().getBrand_name());
        tvGarmentType.setText(userClothes.getGarmentType().getType());
        tvSize.setText(userClothes.getSize());


        Fragment fragment = new SelectGarmentSummaryFragment();
        getChildFragmentManager().beginTransaction()
                .add(R.id.containerAddGarmentFragment, fragment, "type")
                .commit();

        activity.setValueUpdate(true);

        //getActionBar().setTitle(getResources().getString(R.string.edit_garment));
    }

    public void saveGarment(){
        UserClothes userClothes = new UserClothes();
        userClothes.setGarmentType(selectedGarmentType);
        userClothes.setBrand(selectedBrand);
        //TODO Hardcoded!
        userClothes.setCountry("UE");
        userClothes.setSize(selectedSize);
        userClothes.setComment(comment);
        userClothes.setUser(user);
//        userClothes.setSizes(sizes);
        SMXL.getUserClothesDBManager().addUserClothes(userClothes);
    }


    public GarmentType getSelectedGarmentType() {return selectedGarmentType;}
    public void setSelectedGarmentType(GarmentType selectedGarmentType) {
        if(selectedGarmentType!=null) {
            tvGarmentType.setText(selectedGarmentType.getType());
        }
        else{
            tvGarmentType.setText("");
        }
        this.selectedGarmentType = selectedGarmentType;
    }

    public Brand getSelectedBrand() {return selectedBrand;}
    public void setSelectedBrand(Brand selectedBrand) {
        if(selectedBrand!=null) {
            tvBrand.setText(selectedBrand.getBrand_name());
        }
        else{
            tvBrand.setText("");
        }
        this.selectedBrand = selectedBrand;
    }

    public String getSelectedSize() {return selectedSize;}
    public void setSelectedSize(String selectedSize) {
        if(selectedSize!=null) {
            tvSize.setText(selectedSize);
        }
        else{
            tvSize.setText("");
        }
        this.selectedSize = selectedSize;
    }

    public String getComment(){return comment;}
    public void setComment(String comment){
        this.comment=comment;
    }

    public User getUser(){
        return user;
    }

    public CategoryGarment getSelectedCategory() {return selectedCategory;}
    public void setSelectedCategory(CategoryGarment selectedCategory) {this.selectedCategory = selectedCategory;}


    public int getSelectedIdUserClothes() {return selectedIdUserClothes;}
    public void setSelectedIdUserClothes(int selectedIdUserClothes) {this.selectedIdUserClothes = selectedIdUserClothes;}


    public void updateGarment(){
        Log.d("update", "en cours");
        userClothes.setGarmentType(selectedGarmentType);
        userClothes.setBrand(selectedBrand);
        //TODO Hardcoded!
        userClothes.setCountry("UE");
        Log.d("size",selectedSize);
        userClothes.setSize(selectedSize);
        userClothes.setComment(comment);
        userClothes.setUser(user);
//        userClothes.setSizes(sizes);
        SMXL.getUserClothesDBManager().updateUserClothes(userClothes);
    }


}
