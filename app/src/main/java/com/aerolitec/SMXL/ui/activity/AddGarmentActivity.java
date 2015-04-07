package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.SizeHelper;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.CategoryGarmentAdapter;

import java.util.ArrayList;

public class AddGarmentActivity extends Activity {

    private static User user;
    private static CategoryGarment selectedCategory;
    private static String selectedGarment;
    private static String selectedBrand;
    private static boolean canValidate;
    private static String mComputeSize = "";
    private static ArrayList<TabSizes> sizes;
    private static EditText editComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garment);

        user = UserManager.get().getUser();

        if(user == null) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            Fragment fragment = new SelectCategoryGarmentFragment();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_garment, menu);

        if(!canValidate) {
            menu.findItem(R.id.validate).setVisible(false);
        }

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
        UserClothes userClothe = new UserClothes();
        userClothe.setType(selectedGarment);
        userClothe.setBrand(selectedBrand);
        userClothe.setCountry("UE");
        userClothe.setSize(mComputeSize);
        userClothe.setComment(editComments.getText().toString());
        userClothe.setUserid(user.getUserid());
        userClothe.setSizes(sizes);
        userClothe.setCategory(selectedCategory);
        SMXL.get().getDataBase().AddUserGarments(userClothe);
    }

    public static class SelectCategoryGarmentFragment extends Fragment {

        private GridView gridViewCategories;
        private ArrayList<CategoryGarment> categories;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_add_garment_category, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            categories = new ArrayList<>();
            getCategories();

            gridViewCategories = (GridView) view.findViewById(R.id.gridViewCategories);

            CategoryGarmentAdapter adapterCategory = new CategoryGarmentAdapter(getActivity(), R.layout.item_garment_category, categories);

            gridViewCategories.setAdapter(adapterCategory);

            gridViewCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    selectedCategory = categories.get(position);
                    Fragment fragment = new SelectGarmentFragment();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, fragment, "garment")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        private void getCategories() {
            categories.add(new CategoryGarment(1, R.drawable.robe, "Robes / Jupes"));
            categories.add(new CategoryGarment(2, R.drawable.tshirt, "T-shirts / Sweats"));
            categories.add(new CategoryGarment(3, R.drawable.pantalon, "Pantalons / Shorts"));
            categories.add(new CategoryGarment(4, R.drawable.chemise, "Chemises / Blouses"));
            categories.add(new CategoryGarment(5, R.drawable.blouson, "Manteaux / Blousons"));
            categories.add(new CategoryGarment(6, R.drawable.pull, "Pulls / Gilets"));
            categories.add(new CategoryGarment(7, R.drawable.veste, "Vestes"));
            categories.add(new CategoryGarment(8, R.drawable.chaussure, "Chaussures"));
            categories.add(new CategoryGarment(9, R.drawable.costume, "Costumes"));
            categories.add(new CategoryGarment(10, R.drawable.sousvet, "Sous-vêtements"));
        }
    }

    public static class SelectGarmentFragment extends Fragment {

        private ImageView imageChooseGarment;
        private ListView listViewGarments;
        private ArrayList<String> garmentItems;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_add_garment_type, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            listViewGarments = (ListView) view.findViewById(R.id.listViewGarments);
            imageChooseGarment = (ImageView) view.findViewById(R.id.imageChooseGarment);

            imageChooseGarment.setImageResource(selectedCategory.getIcon());

            garmentItems = new ArrayList<>();

            getGarmentsFromCategory();

            ArrayAdapter<String> adapterGarments = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, garmentItems);
            listViewGarments.setAdapter(adapterGarments);

            listViewGarments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    selectedGarment = garmentItems.get(position);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new SelectBrandFragment(), "brand")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        /**
         * Robes/jupes  ==>  Robes et Jupes
         Tshirt/Sweat  ==>  Tshirt, Sweat [Polos, débardeur, Tunique]*
         Pantalons/Shorts ==> Pantalons, Jeans, Shorts
         Chemises/Blouses ==> Chemises, Chemisiers, [Blouses]*
         Manteaux/Blousons ==> Manteaux, Blousons
         Pulls/Gilets ==> Pulls [Gilets]*
         Vestes       ==> Vestes
         Chaussures ==> Chaussures
         Costumes   ==> Costumes
         Sous-vêtements ==> Slips [caleçon, culotte, soutien-gorge]*
         */

        private void getGarmentsFromCategory() {
            switch (selectedCategory.getId()) {
                case 1:
                    garmentItems.add("Robe");
                    garmentItems.add("Jupe");
                    break;
                case 2:
                    garmentItems.add("Tshirt");
                    garmentItems.add("Sweat");
                    break;
                case 3:
                    garmentItems.add("Pantalon");
                    garmentItems.add("Jean");
                    garmentItems.add("Short");
                    break;
                case 4:
                    garmentItems.add("Chemise");
                    garmentItems.add("Chemisier");
                    break;
                case 5:
                    garmentItems.add("Manteau");
                    garmentItems.add("Blouson");
                    break;
                case 6:
                    garmentItems.add("Pull");
                    break;
                case 7:
                    garmentItems.add("Veste");
                    break;
                case 8:
                    garmentItems.add("Chaussure");
                    break;
                case 9:
                    garmentItems.add("Costume");
                    break;
                case 10:
                    garmentItems.add("Slip");
                    break;
            }
        }
    }

    public static class SelectBrandFragment extends Fragment {

        private ListView listViewBrands;
        private ImageView imageChooseBrand;
        private TextView textGarment;
        private ArrayList<String> brandItems;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_add_garment_brand, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            listViewBrands = (ListView) view.findViewById(R.id.listViewBrand);
            imageChooseBrand = (ImageView) view.findViewById(R.id.imageChooseBrand);
            textGarment = (TextView) view.findViewById(R.id.textGarment);

            imageChooseBrand.setImageResource(selectedCategory.getIcon());
            textGarment.setText(selectedGarment);

            brandItems = new ArrayList<>();

            loadBrands();

            ArrayAdapter<String> adapterBrands = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, brandItems);
            listViewBrands.setAdapter(adapterBrands);

            listViewBrands.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    selectedBrand = brandItems.get(position);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, new SummaryGarmentFragment(), "summary")
                            .addToBackStack(null)
                            .commit();
                }
            });
        }

        private void loadBrands(){

            brandItems.clear();
            String recherche = selectedGarment;

            if(recherche.equalsIgnoreCase("sweat")) {
                recherche = "sweater";
            }

            if(recherche.equalsIgnoreCase("robe") || recherche.equalsIgnoreCase("jupe")) {
                recherche = recherche.toUpperCase();
            } else {
                recherche = recherche.toUpperCase() + "-" + user.getSexe().substring(0,1);
            }

            ArrayList<BrandsSizeGuide> brands = SMXL.get().getDataBase().getAllBrandsByGarment(recherche);
            for (BrandsSizeGuide b : brands){
                brandItems.add(b.getBrand());
            }
        }
    }

    public static class SummaryGarmentFragment extends Fragment {

        private ImageView imageSummary;
        private TextView textGarmentSummary;
        private TextView textBrandSummary;
        private TextView textSizes;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_add_garment_summary, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            editComments = (EditText) view.findViewById(R.id.editComments);
            imageSummary = (ImageView) view.findViewById(R.id.imageSummary);
            textGarmentSummary = (TextView) view.findViewById(R.id.textGarmentSummary);
            textBrandSummary = (TextView) view.findViewById(R.id.textBrandSummary);

            textSizes = (TextView) view.findViewById(R.id.textSizes);

            imageSummary.setImageResource(selectedCategory.getIcon());
            textGarmentSummary.setText(selectedGarment);
            textBrandSummary.setText(selectedBrand);

            canValidate = true;

            sizes = new ArrayList<>();

            SizeHelper helper = new SizeHelper(user, selectedBrand, selectedGarment, mComputeSize, textSizes, sizes);
            helper.computeSize();


            getActivity().invalidateOptionsMenu();
        }



    }

}
