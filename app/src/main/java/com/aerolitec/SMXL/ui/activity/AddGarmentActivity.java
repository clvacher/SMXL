package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.view.menu.MenuView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.fragment.SelectGarmentTypeFragment;

/**
 * Created by Clement on 5/4/2015.
 *
 */
public class AddGarmentActivity extends Activity {

    private User user;
    private GarmentType selectedGarmentType;
    private Brand selectedBrand;
    private String selectedSize;
    private CategoryGarment selectedCategory;
    
    private String comment;

    private TextView tvBrand,tvGarmentType,tvSize;
    private RelativeLayout smxlLayout;

    private Boolean validation=false;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garment);

        //gets the selected garment category from the intent
        Bundle extras=getIntent().getExtras();
        if(extras != null) {
            if((selectedCategory = (CategoryGarment) extras.get("category")) == null){
                UserClothes userClothes = (UserClothes) extras.get("userClothes");
                selectedGarmentType = userClothes.getGarmentType();
                selectedBrand = userClothes.getBrand();
                selectedSize = userClothes.getSize();
            }
        }
        else{
            finish();
        }

        user = UserManager.get().getUser();
        if(user == null) {
            finish();
            return;//TODO ?
        }

        tvBrand=(TextView)findViewById(R.id.garmentBrand);
        tvGarmentType = (TextView)findViewById(R.id.garmentType);
        tvSize=(TextView)findViewById(R.id.textSize);
        smxlLayout=(RelativeLayout)findViewById(R.id.layoutSMXL);


        ((ImageView)findViewById(R.id.garmentIcon)).setImageResource(selectedCategory.getIcon());

        if (savedInstanceState == null) {
            Fragment fragment = new SelectGarmentTypeFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, "type")
                    .commit();
        }

        smxlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SizeSetter","Not implemented");
                //TODO
            }
        });

        getActionBar().setTitle(getResources().getString(R.string.add_garment));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_garment, menu);
        this.menu=menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate:
                saveGarment();
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
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

    public User getUser() {return user;}

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

    public CategoryGarment getSelectedCategory() {return selectedCategory;}
    public void setSelectedCategory(CategoryGarment selectedCategory) {this.selectedCategory = selectedCategory;}

    public void setValidation(Boolean b){
        validation = b;
        onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        (menu.findItem(R.id.validate)).setVisible(validation);
        return true;
    }

}
