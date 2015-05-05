package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.aerolitec.SMXL.ui.fragment.SelectGarmentTypeFragmentTmp;

/**
 * Created by Clément on 5/4/2015.
 * class temporaire en remplacement de addGarmentActivity
 */
public class tmp extends Activity {

    private User user;
    private GarmentType selectedGarmentType;
    private Brand selectedBrand;
    private CategoryGarment selectedCategory;

    private TextView tvBrand,tvGarmentType,tvSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garment);

        //gets the selected garment category from the intent
        selectedCategory=(CategoryGarment)getIntent().getExtras().get("category");

        user = UserManager.get().getUser();
        if(user == null) {
            finish();
            return;//TODO ?
        }

        tvBrand=(TextView)findViewById(R.id.textBrand);
        tvGarmentType=(TextView)findViewById(R.id.textGarment);
        tvSize=(TextView)findViewById(R.id.textSize);


        ((ImageView)findViewById(R.id.garmentIcon)).setImageResource(selectedCategory.getIcon());

        if (savedInstanceState == null) {
            Fragment fragment = new SelectGarmentTypeFragmentTmp();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, fragment, "category")
                    .commit();
        }

        getActionBar().setTitle(getResources().getString(R.string.add_garment));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
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
        userClothes.setCountry("UE");
//        userClothes.setSize(mComputeSize);
//        userClothes.setComment(editComments.getText().toString());
//        userClothes.setUser(user);
//        userClothes.setSizes(sizes);
        SMXL.getUserClothesDBManager().addUserClothes(userClothes);
    }

    public User getUser() {return user;}

    public GarmentType getSelectedGarmentType() {return selectedGarmentType;}
    public void setSelectedGarmentType(GarmentType selectedGarmentType) {this.selectedGarmentType = selectedGarmentType;}

    public Brand getSelectedBrand() {return selectedBrand;}
    public void setSelectedBrand(Brand selectedBrand) {this.selectedBrand = selectedBrand;}

    public CategoryGarment getSelectedCategory() {return selectedCategory;}
    public void setSelectedCategory(CategoryGarment selectedCategory) {this.selectedCategory = selectedCategory;}

    public void setTvGarmentType(TextView tvGarmentType) {this.tvGarmentType = tvGarmentType;}

    public void setTvSize(TextView tvSize) {this.tvSize = tvSize;}

    public void setTvBrand(TextView tvBrand) {this.tvBrand = tvBrand;}
}
