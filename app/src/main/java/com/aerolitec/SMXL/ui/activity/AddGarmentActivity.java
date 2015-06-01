package com.aerolitec.SMXL.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import com.aerolitec.SMXL.ui.fragment.SelectGarmentSummaryFragment;
import com.aerolitec.SMXL.ui.fragment.SelectGarmentTypeFragment;

/**
 * Created by Clement on 5/4/2015.
 *
 */
public class AddGarmentActivity extends FragmentActivity {

    private User user;
    private GarmentType selectedGarmentType;
    private Brand selectedBrand;
    private String selectedSize;
    private CategoryGarment selectedCategory;
    private int selectedIdUserClothes=-1;
    private UserClothes userClothes;

    private String comment;

    private TextView tvBrand,tvGarmentType,tvSize;
    private RelativeLayout smxlLayout;

    private Boolean validation=false;
    private Boolean update=false;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garment);


        tvBrand=(TextView)findViewById(R.id.garmentBrand);
        tvGarmentType = (TextView)findViewById(R.id.garmentType);
        tvSize=(TextView)findViewById(R.id.textSize);

        //gets the selected garment category from the intent
        Bundle extras=getIntent().getExtras();
        if(extras != null) {
            if((selectedCategory = (CategoryGarment) extras.get("category")) == null){
                existingGarment(extras);
            }
            else{
                Fragment fragment = new SelectGarmentTypeFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.containerAddGarmentActivity, fragment, "type")
                        .commit();

                Log.d("Fragment", fragment.toString());
                //getActionBar().setTitle(getResources().getString(R.string.add_garment));
            }
        }
        else{
            finish();
        }

        user = UserManager.get().getUser();
        if(user == null) {
            Log.d("AddGarmentActivity","userNull");
            finish();
        }

        ((ImageView)findViewById(R.id.garmentIcon)).setImageResource(selectedCategory.getIcon());


        /*getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_garment, menu);
        this.menu=menu;
        Log.d("MENU BEFORE",this.menu.toString()+"");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.validate:
                saveGarment();
                finish();
                break;
            case R.id.update:
                updateGarment();
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


    public int getSelectedIdUserClothes() {return selectedIdUserClothes;}
    public void setSelectedIdUserClothes(int selectedIdUserClothes) {this.selectedIdUserClothes = selectedIdUserClothes;}

    public void setValidation(Boolean b){
        validation = b;
        onPrepareOptionsMenu(menu);
    }
    public void setUpdate(Boolean b){
        update = b;
        onPrepareOptionsMenu(menu);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        Log.d("MENU", menu.toString());
        (menu.findItem(R.id.validate)).setVisible(validation);
        (menu.findItem(R.id.update)).setVisible(update);
        return true;
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
        update=true;

        Fragment fragment = new SelectGarmentSummaryFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerAddGarmentActivity, fragment, "type")
                .commit();

        //getActionBar().setTitle(getResources().getString(R.string.edit_garment));
    }


    private void updateGarment(){
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
