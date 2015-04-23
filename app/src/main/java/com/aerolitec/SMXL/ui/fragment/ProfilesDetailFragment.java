package com.aerolitec.SMXL.ui.fragment;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;
import com.aerolitec.SMXL.ui.activity.AddMeasureActivity;
import com.aerolitec.SMXL.ui.activity.DisplayGarmentActivity;
import com.aerolitec.SMXL.ui.activity.ListGarmentActivity;
import com.aerolitec.SMXL.ui.activity.SelectBrandsActivity;
import com.aerolitec.SMXL.ui.activity.UpdateProfile;
import com.aerolitec.SMXL.ui.adapter.BrandItem;
import com.aerolitec.SMXL.ui.adapter.FavoriteBrandAdapter;
import com.aerolitec.SMXL.ui.adapter.GarmentAdapter;
import com.aerolitec.SMXL.ui.adapter.GarmentItem;
import com.aerolitec.SMXL.ui.adapter.MeasureAdapter;
import com.aerolitec.SMXL.ui.adapter.MeasureItem;
import com.aerolitec.SMXL.ui.fragment.dialog.ConfirmDialogFragment;
import com.makeramen.RoundedImageView;

import java.io.File;
import java.util.ArrayList;

public class ProfilesDetailFragment extends Fragment implements ConfirmDialogFragment.ConfirmDialogListener {


    // TODO: Rename and change types of parameters
    private static User user;
    private TextView tvFirstName, tvLastName, tvAgeSexe;
    private EditText etDescription;
    private RelativeLayout addMeasure, addBrand;
    //private ListView listViewMeasures;
    //private ListView listViewGarments;
    private RoundedImageView imgAvatar;
    private LinearLayout layoutMeasure, layoutRemarque, layoutBrand;
    private RelativeLayout infosProfile;
    //private EditText etDescriptionProfil;


    //private LinearLayout layoutViewGarments;
    private RelativeLayout layoutHeaderMeasures, layoutHeaderBrands;
    //int position;

    private ArrayList<Integer> indexSize;
    private ArrayList<Brand> userBrands;

    private ArrayList<MeasureItem> listMeasures;
    private ArrayList<String> listBrandsItems;

    private MeasureAdapter adapterMeasure;
    private FavoriteBrandAdapter adapterBrand;

    private final static int DELETE_GARMENT = 1;

    // TODO: Rename and change types and number of parameters
    public static ProfilesDetailFragment newInstance(User param1, String param2) {
        ProfilesDetailFragment fragment = new ProfilesDetailFragment();
        return fragment;
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
        Log.d("User", user.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile_detail, container, false);

        if(user == null) {
            getActivity().finish();
            return null;
        }

        listMeasures = new ArrayList<>();
        adapterMeasure = new MeasureAdapter(getActivity().getApplicationContext(), listMeasures);

        listBrandsItems = new ArrayList<>();
        ArrayList<Brand> brands = SMXL.get().getDataBase().getAllBrands();
        for (Brand b : brands){
            listBrandsItems.add(b.getBrand());
        }
        adapterBrand = new FavoriteBrandAdapter(getActivity().getApplicationContext(), listBrandsItems);


        tvFirstName = (TextView) view.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) view.findViewById(R.id.tvLastName);
        tvAgeSexe = (TextView) view.findViewById(R.id.tvAgeSexe);
        layoutRemarque = (LinearLayout) view.findViewById(R.id.layoutRemarque);

        addMeasure = (RelativeLayout) view.findViewById(R.id.layoutAddMeasure);
        addBrand = (RelativeLayout) view.findViewById(R.id.layoutAddBrand);

        layoutHeaderMeasures = (RelativeLayout) view.findViewById(R.id.layoutHeaderMeasures);
        layoutMeasure = (LinearLayout) view.findViewById(R.id.layoutViewMeasure);

        layoutHeaderBrands = (RelativeLayout) view.findViewById(R.id.layoutHeaderBrands);
        layoutBrand = (LinearLayout) view.findViewById(R.id.layoutViewBrand);


        indexSize = SMXL.getDataBase().getIndexMeasureNotNull(user);
        etDescription = (EditText) view.findViewById(R.id.description);


        addMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMeasureActivity.class);
                startActivity(intent);
            }
        });



        addBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectBrandsActivity.class);
                //intent.putExtra("user_brands", user.getBrands());
                startActivity(intent);
            }
        });



        layoutHeaderMeasures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutMeasure.getVisibility() == View.GONE) {
                    layoutMeasure.setVisibility(View.VISIBLE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseMeasure);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    layoutMeasure.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseMeasure);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        layoutHeaderBrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutBrand.getVisibility() == View.GONE) {
                    layoutBrand.setVisibility(View.VISIBLE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseBrand);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                } else {
                    layoutBrand.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseBrand);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        tvFirstName.setText(user.getFirstname());
        tvLastName.setText(user.getLastname());
        String profileDescription = user.getDescription();

        if (profileDescription.length()>0) {
            etDescription.setText(profileDescription);
        }
        //tvTitle.setText(user.getLastname() + " " + user.getFirstname());
        int age = user.getAge(user.getBirthday());
        String sexe = "Femme";
        if (user.getSexe().startsWith("H")) {
            sexe = "Homme";
        }
        tvAgeSexe.setText(age + " ans / " + sexe);


        imgAvatar = (RoundedImageView) view.findViewById(R.id.imgAvatar);
        String fnAvatar = user.getAvatar();
        if (fnAvatar != null) {
            int width = getPixelsFromDip(80, getActivity());
            try {
                File file = new File(fnAvatar);
                if (file.exists()) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                    // Calculate inSampleSize
                    options.inSampleSize = ImageHelper.calculateInSampleSize(options, width, width);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    imgAvatar.setImageBitmap(ImageHelper.getCorrectBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getAbsolutePath()));
/*                    Uri uri = Uri.fromFile(file);
                    Picasso.with(getActivity())
                            .load(file)
                            .resize(width, width)
                            .transform(new RoundedTransformation(width, 0))
                            .into(imgAvatar);*/
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting Picture to File : " + e.getMessage());
            }
        }


        infosProfile = (RelativeLayout) view.findViewById(R.id.profilLayout);
        infosProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextActivity = new Intent(getActivity().getApplicationContext(), UpdateProfile.class);
                startActivity(nextActivity);
            }
        });
        /*
        etDescriptionProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(getActivity().getApplicationContext(), UpdateProfile.class);
                nextActivity.putExtra("USER", user);
                startActivity(nextActivity);
            }
        });
        */

        getActivity().getActionBar().setHomeButtonEnabled(true);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);

/*
        ArrayList<MeasureItem> localMeasure = null;
        ArrayList<GarmentItem> localGarment = null;

        if (savedInstanceState != null) {
            localMeasure = (ArrayList<MeasureItem>) savedInstanceState.getSerializable("measures");
            localGarment = (ArrayList<GarmentItem>) savedInstanceState.getSerializable("garments");

        }

        if (localMeasure != null && localMeasure.size() > 0) {
            listMeasures.clear();
            listMeasures.addAll(localMeasure);
            adapterMeasure.notifyDataSetChanged();
        } else {
            loadMeasures();
        }

        if (localGarment != null && localGarment.size() > 0) {
            listGarments.clear();
            listGarments.addAll(localGarment);
            adapterGarments.notifyDataSetChanged();
        } else {
            loadGarments();
        }
*/

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<MeasureItem> localMeasure = null;
        ArrayList<GarmentItem> localGarment = null;
        ArrayList<BrandItem> localBrand = null;


        if (savedInstanceState != null) {
            localMeasure = (ArrayList<MeasureItem>) savedInstanceState.getSerializable("measures");
            localGarment = (ArrayList<GarmentItem>) savedInstanceState.getSerializable("garments");
            localBrand =   (ArrayList<BrandItem>) savedInstanceState.getSerializable("brands");

        }

        if (localMeasure != null && localMeasure.size() > 0) {
            listMeasures.clear();
            listMeasures.addAll(localMeasure);
            adapterMeasure.notifyDataSetChanged();
        } else {
            loadMeasures();
        }

        /*if (localGarment != null && localGarment.size() > 0) {
            listGarments.clear();
            listGarments.addAll(localGarment);
            adapterGarments.notifyDataSetChanged();
        } else {
            loadGarments();
        }*/


        loadBrands();

    }

    private void updateUI() {

        //TODO Utiliser des strings
        String[] listSize = {getResources().getString(R.string.libSize), getResources().getString(R.string.libWeight),
                getResources().getString(R.string.libBust), getResources().getString(R.string.libChest),
                getResources().getString(R.string.libCollar), getResources().getString(R.string.libWaist),
                getResources().getString(R.string.libHips), getResources().getString(R.string.libSleeve),
                getResources().getString(R.string.libInseam), getResources().getString(R.string.libFoot),
                getResources().getString(R.string.libUnit), getResources().getString(R.string.libUnit),
                getResources().getString(R.string.libPointure)};

        indexSize = SMXL.getDataBase().getIndexMeasureNotNull(user);
        userBrands = SMXL.getDataBase().getAllUserBrands(user);

        //POUR LES MESURES :
        ((LinearLayout) getView().findViewById(R.id.layoutViewMeasure)).removeAllViews();

        for (int i = 0; i < indexSize.size(); i++) {
            ArrayList<String> sizes = SMXL.getDataBase().getUserSizes(user);
            View viewToLoad = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.measure_item, null);
            View separator = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.separator_list, null);
            LinearLayout clickItem = (LinearLayout) viewToLoad.findViewById(R.id.layoutItemMeasure);

            clickItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AddMeasureActivity.class);
                    startActivity(intent);
                }
            });

            TextView item = (TextView) viewToLoad.findViewById(R.id.tvNameMeasure);
            TextView value = (TextView) viewToLoad.findViewById(R.id.tvValueMeasure);
            item.setText(listSize[indexSize.get(i)]);
            value.setText(sizes.get(indexSize.get(i)));
            ((LinearLayout) getView().findViewById(R.id.layoutViewMeasure))
                    .addView(viewToLoad);
            //Pas de separator pour le dernier item
            if (i != indexSize.size() - 1) {
                ((LinearLayout) getView().findViewById(R.id.layoutViewMeasure))
                        .addView(separator);
            }
        }

        //POUR LES MARQUES :
        ((LinearLayout) getView().findViewById(R.id.layoutViewBrand)).removeAllViews();

        for (int i = 0; i < userBrands.size(); i++) {
            View viewToLoad = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.brand_item, null);
            View separator = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.separator_list, null);


            TextView item = (TextView) viewToLoad.findViewById(R.id.tvBrandName);

            item.setText(userBrands.get(i).getBrand());

            ((LinearLayout) getView().findViewById(R.id.layoutViewBrand))
                    .addView(viewToLoad);
            //Pas de separator pour le dernier item
            if (i != userBrands.size() - 1) {
                ((LinearLayout) getView().findViewById(R.id.layoutViewBrand))
                        .addView(separator);
            }
        }


        //PROFIL :
        tvFirstName.setText(user.getFirstname());
        tvLastName.setText(user.getLastname());
        //tvTitle.setText(user.getLastname() + " " + user.getFirstname());
        etDescription.setText(user.getDescription());

        int age = user.getAge(user.getBirthday());
        String sexe = "Femme";
        if (user.getSexe().startsWith("H")) {
            sexe = "Homme";
        }
        tvAgeSexe.setText(age + " ans / " + sexe);

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
                    imgAvatar.setImageBitmap(ImageHelper.getCorrectBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getAbsolutePath()));
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting Picture to File : " + e.getMessage());
            }
        }
    }


    public void loadMeasures() {
        // load all the profiles from the database

        listMeasures.clear();

        // user : Récupérer les infos du User qui sont dans user
        user = SMXL.get().getDataBase().getUserById(this.user.getUserid());
        double coeff = 1D;
        double coeffW = 1D;
        if (user.getUnitLength() == Constants.INCH) {
            coeff = Constants.inch;
        }
        if (user.getUnitWeight() == Constants.POUNDS) {
            coeffW = Constants.pounds;
        }

        if (user.getSize() > 0)
            listMeasures.add(new MeasureItem(1, getResources().getString(R.string.libSize), user.getSize() / coeff));
        if (user.getWeight() > 0)
            listMeasures.add(new MeasureItem(2, getResources().getString(R.string.libWeight), user.getWeight() / coeffW));
        if (user.getImc() > 0)
            listMeasures.add(new MeasureItem(3, getResources().getString(R.string.libImc), user.getImc()));
        if (user.getSexe().startsWith("F")) {
            if (user.getBust() > 0) {
                listMeasures.add(new MeasureItem(3, getResources().getString(R.string.libBust), user.getBust() / coeff));
            }
        } else if (user.getSexe().startsWith("H")) {
            if (user.getChest() > 0) {
                listMeasures.add(new MeasureItem(4, getResources().getString(R.string.libChest), user.getChest() / coeff));
            }
        }
        if (user.getCollar() > 0)
            listMeasures.add(new MeasureItem(5, getResources().getString(R.string.libCollar), user.getCollar() / coeff));
        if (user.getWaist() > 0)
            listMeasures.add(new MeasureItem(6, getResources().getString(R.string.libWaist), user.getWaist() / coeff));
        if (user.getHips() > 0)
            listMeasures.add(new MeasureItem(7, getResources().getString(R.string.libHips), user.getHips() / coeff));
        if (user.getInseam() > 0)
            listMeasures.add(new MeasureItem(8, getResources().getString(R.string.libInseam), user.getInseam() / coeff));
        if (user.getSleeve() > 0)
            listMeasures.add(new MeasureItem(9, getResources().getString(R.string.libSleeve), user.getSleeve() / coeff));
        if (user.getFeet() > 0) {
            listMeasures.add(new MeasureItem(10, getResources().getString(R.string.libFoot), user.getFeet() / coeff));
            listMeasures.add(new MeasureItem(11, getResources().getString(R.string.ShoeSize), computePointure(user.getFeet())));
        }
        adapterMeasure.notifyDataSetChanged();
    }

    public double computePointure(double sizeCm) {
        double pointure = 0D;

        if (sizeCm != 0) {
            sizeCm += 1;
            pointure = sizeCm * 1.5;
            pointure = Math.round(pointure);
        }
        return pointure;
    }



    public void loadBrands() {
        // user : Get all the user's favorite brands
        listBrandsItems.clear();
        userBrands = SMXL.get().getDataBase().getAllUserBrands(user);
        Log.d("brandsuser", userBrands.toString());
        for (Brand b : userBrands) {
            adapterBrand.add(b.getBrand());
        }
        adapterBrand.notifyDataSetChanged();
    }

    private AdapterView.OnItemClickListener selectMeasure = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            final int computeSize = 42;
            final String type = listMeasures.get(arg2).getTypeMeasure();
            final String val = String.valueOf(listMeasures.get(arg2).getValueMeasure());
            LayoutInflater li = LayoutInflater.from(getActivity());
            View measureInfoDialog = li.inflate(R.layout.measure_info_dialog, null);
            TextView displaySize = (TextView) measureInfoDialog.findViewById(R.id.tvLibTaille);
            //displaySize.setText(displaySize.getText().toString() + val);
            displaySize.setText(R.string.unimplemented);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(measureInfoDialog);
            builder.setCancelable(true);
            builder.setPositiveButton(getResources().getString(R.string.libBoutonRetour),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }
            );
            final AlertDialog dialog = builder.create();
            dialog.show();
        }
    };


    private AdapterView.OnItemLongClickListener clickListenerDeleteGarment = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            DialogFragment confirmDialog = new ConfirmDialogFragment();
            Bundle args = new Bundle();
            args.putString("confirm", getResources().getString(R.string.remove));
            args.putString("message", getResources().getString(R.string.message_delete_garment));
            args.putInt("id", position);
            confirmDialog.setArguments(args);
            confirmDialog.setTargetFragment(ProfilesDetailFragment.this, DELETE_GARMENT);
            confirmDialog.show(getFragmentManager(), "remove");

            return true;
        }
    };

    private void deleteGarment(int positionItem) {
        String profilId = String.valueOf(user.getUserid());
        String request = "DELETE FROM user_clothes WHERE userid='" + profilId + "'";

        String DataBaseName = "SIZEGUIDE_DB";
        SQLiteDatabase db = null;
        db = getActivity().openOrCreateDatabase(DataBaseName, getActivity().MODE_PRIVATE, null);
        db.beginTransaction();
        db.execSQL(request);
        db.setTransactionSuccessful();
        db.endTransaction();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("measures", listMeasures);
        outState.putSerializable("brands", listBrandsItems);

        super.onSaveInstanceState(outState);
    }


    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }


    @Override
    public void onConfirm(boolean confirmed, int positionItem) {
        if (confirmed) {
            deleteGarment(positionItem);
        }
    }

    @Override
    public void onPause(){
        String desc= etDescription.getText().toString();
        if(!desc.equals(user.getDescription())){
            user.setDescription(desc);
            UserManager.get().setUser(user);
            SMXL.getDataBase().updateUser(user);
        }

        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user != null) {
            user=UserManager.get().getUser();
            updateUI();
        }
    }
}
