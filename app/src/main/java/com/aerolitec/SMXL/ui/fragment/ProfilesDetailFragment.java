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
import com.aerolitec.SMXL.ui.activity.AddMeasureActivity;
import com.aerolitec.SMXL.ui.activity.DisplayGarmentActivity;
import com.aerolitec.SMXL.ui.activity.ListGarmentActivity;
import com.aerolitec.SMXL.ui.activity.UpdateProfile;
import com.aerolitec.SMXL.ui.adapter.GarmentAdapter;
import com.aerolitec.SMXL.ui.adapter.GarmentItem;
import com.aerolitec.SMXL.ui.adapter.MeasureAdapter;
import com.aerolitec.SMXL.ui.adapter.MeasureItem;
import com.aerolitec.SMXL.ui.fragment.dialog.ConfirmDialogFragment;
import com.aerolitec.SMXL.ui.listener.AddGarmentListener;
import com.aerolitec.SMXL.ui.listener.MesureChangeListener;
import com.makeramen.RoundedImageView;

import java.io.File;
import java.util.ArrayList;

public class ProfilesDetailFragment extends Fragment implements MesureChangeListener, AddGarmentListener, ConfirmDialogFragment.ConfirmDialogListener {


    // TODO: Rename and change types of parameters
    private static User user;
    private String mParam2;
    private TextView tvFirstName, tvLastName, tvAgeSexe, tvMeasureItem;
    private RelativeLayout addMeasure, addGarment;
    //private ListView listViewMeasures;
    private ArrayList<MeasureItem> listMeasures;
    private MeasureAdapter adapterMeasure;
    //private ListView listViewGarments;
    private ArrayList<UserClothes> listGarments;
    private GarmentAdapter adapterGarments;
    private RoundedImageView imgAvatar;
    private ImageView favColor1, favColor2, favColor3, favColor4;
    private LinearLayout layoutGarment, layoutMeasure, layoutRemarque;
    private RelativeLayout infosProfile;
    private AddGarmentListener garmentListener;
    //private EditText etDescriptionProfil;
    private ArrayList<UserClothes> userClothes;
    private ArrayList<Integer> indexSize;
    private TextView description;
    private ArrayList<Integer> listColors;
    private LinearLayout layoutViewGarments;
    private RelativeLayout layoutHeaderGarments, layoutHeaderMeasures, layoutHeaderRemarques;
    int position;

    private final static int DELETE_GARMENT = 1;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfilesFragment.
     */
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

        //listViewMeasures = (ListView)view.findViewById(R.id.listViewMeasures);
        listMeasures = new ArrayList<MeasureItem>();
        adapterMeasure = new MeasureAdapter(getActivity().getApplicationContext(), listMeasures);
        //listViewMeasures.setAdapter(adapterMeasure);
        //listViewMeasures.setOnItemClickListener(selectMeasure);

        //listViewGarments = (ListView)view.findViewById(R.id.listViewGarments);
        listGarments = new ArrayList<>();
        adapterGarments = new GarmentAdapter(getActivity().getApplicationContext(), listGarments);
        //listViewGarments.setAdapter(adapterGarments);
        //listViewGarments.setOnItemClickListener(selectGarment);
        //listViewGarments.setOnItemLongClickListener(clickListenerDeleteGarment);


        tvFirstName = (TextView) view.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) view.findViewById(R.id.tvLastName);
        tvAgeSexe = (TextView) view.findViewById(R.id.tvAgeSexe);
        description = (TextView) view.findViewById(R.id.description);
        //tvTitle = (TextView) view.findViewById(R.id.title);
        //etDescriptionProfil = (EditText) view.findViewById(R.id.etDescriptionProfil);
        //etDescriptionProfil.setSelected(false);

        addGarment = (RelativeLayout) view.findViewById(R.id.layoutAddGarment);
        addMeasure = (RelativeLayout) view.findViewById(R.id.layoutAddMeasure);
        layoutGarment = (LinearLayout) view.findViewById(R.id.layoutViewGarments);
        layoutHeaderGarments = (RelativeLayout) view.findViewById(R.id.layoutHeaderGarments);
        layoutHeaderMeasures = (RelativeLayout) view.findViewById(R.id.layoutHeaderMeasures);
        layoutHeaderRemarques = (RelativeLayout) view.findViewById(R.id.layoutHeaderRemarque);
        layoutGarment = (LinearLayout) view.findViewById(R.id.layoutViewGarments);
        layoutMeasure = (LinearLayout) view.findViewById(R.id.layoutViewMeasure);
        indexSize = SMXL.getDataBase().getIndexMeasureNotNull(user);
        userClothes = SMXL.getDataBase().getAllUserGarments(user);

        addGarment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Fragment fragment = AddGarmentFragment.newInstance(user, "");
                fragment.setTargetFragment(ProfilesDetailFragment.this, 42);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment, "addGarment")
                        .addToBackStack(null)
                        .commit();
                        */
                Intent intent = new Intent(getActivity(), AddGarmentActivity.class);
                startActivity(intent);
            }
        });

        addMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMeasureActivity.class);
                startActivity(intent);
            }
        });

        layoutHeaderGarments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutGarment.getVisibility() == View.GONE) {
                    layoutGarment.setVisibility(View.VISIBLE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseGarment);
                    collapse.setImageResource(R.drawable.navigation_expand);
                } else {
                    layoutGarment.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseGarment);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                }
            }
        });

        layoutHeaderMeasures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutMeasure.getVisibility() == View.GONE) {
                    layoutMeasure.setVisibility(View.VISIBLE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseMeasure);
                    collapse.setImageResource(R.drawable.navigation_expand);
                } else {
                    layoutMeasure.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseMeasure);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                }
            }
        });

        layoutHeaderRemarques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (description.getVisibility() == View.GONE) {
                    description.setVisibility(View.VISIBLE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseRemarque);
                    collapse.setImageResource(R.drawable.navigation_expand);
                } else {
                    description.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseRemarque);
                    collapse.setImageResource(R.drawable.navigation_collapse);
                }
            }
        });


        tvFirstName.setText(user.getFirstname());
        tvLastName.setText(user.getLastname());
        String profileDescription = user.getDescription();
        if (profileDescription != "") {
            description.setVisibility(View.VISIBLE);
            description.setText(profileDescription);
        }
        //tvTitle.setText(user.getLastname() + " " + user.getFirstname());
        //etDescriptionProfil.setText(user.getDescription());
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

        /*if (localGarment != null && localGarment.size() > 0) {
            listGarments.clear();
            listGarments.addAll(localGarment);
            adapterGarments.notifyDataSetChanged();
        } else {
            loadGarments();
        }*/

        loadGarments();

    }

    private void updateUI() {

        ((LinearLayout) getView().findViewById(R.id.layoutViewMeasure)).removeAllViews();
        //TODO Utiliser des des strings
        String[] listSize = {getResources().getString(R.string.libSize), getResources().getString(R.string.libWeight),
                getResources().getString(R.string.libBust), getResources().getString(R.string.libChest),
                getResources().getString(R.string.libCollar), getResources().getString(R.string.libWaist),
                getResources().getString(R.string.libHips), getResources().getString(R.string.libSleeve),
                getResources().getString(R.string.libInseam), getResources().getString(R.string.libFoot),
                getResources().getString(R.string.libUnit), getResources().getString(R.string.libUnit),
                getResources().getString(R.string.libPointure)};

        indexSize = SMXL.getDataBase().getIndexMeasureNotNull(user);
        userClothes = SMXL.getDataBase().getAllUserGarments(user);

        //POUR LES MESURES :
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

        ((LinearLayout) getView().findViewById(R.id.layoutViewGarments)).removeAllViews();
        RelativeLayout viewMore = (RelativeLayout) getView().findViewById(R.id.layoutViewMore);
        //POUR LES VETEMENTS
        //On affiche que les 4 premiers vêtement sinon "Voir plus" pour voir tt la liste
        if (userClothes.size() <= 4) {
            viewMore.setVisibility(View.GONE);
        } else {
            viewMore.setVisibility(View.VISIBLE); //Important to specify if we already set it to GONE
        }

        viewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ListGarmentActivity.class));
            }
        });

        if (userClothes.size() > 4) {
            for (int i = 4; i < userClothes.size(); i++)
                userClothes.remove(i);
        }
        for (int i = 0; i < userClothes.size(); i++) {
            View viewToLoad = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.garment_item, null);
            View separator = LayoutInflater.from(
                    getActivity().getApplicationContext()).inflate(
                    R.layout.separator_list, null);
            final UserClothes clothe = userClothes.get(i);
            TextView item = (TextView) viewToLoad.findViewById(R.id.tvNameGarment);
            TextView brand = (TextView) viewToLoad.findViewById(R.id.tvBrandGarment);
            TextView size = (TextView) viewToLoad.findViewById(R.id.tvSize);
            item.setText(clothe.getType());
            size.setText(clothe.getSize());
            brand.setText(clothe.getBrand());
            ((LinearLayout) getView().findViewById(R.id.layoutViewGarments))
                    .addView(viewToLoad);
            //position = layoutViewGarments.indexOfChild(viewToLoad);
            //layoutViewGarments.setTag(position);
            //appDelegate.setDealsList(dealsList);

            viewToLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(getActivity(), DisplayGarmentActivity.class);
                   intent.putExtra("clothe", clothe);
                   startActivity(intent);
                }
            });

            //viewToLoad.setOnClickListener(selectGarment);
            ((LinearLayout) getView().findViewById(R.id.layoutViewGarments))
                    .addView(separator);


        }


        //PROFIL :
        tvFirstName.setText(user.getFirstname());
        tvLastName.setText(user.getLastname());
        //tvTitle.setText(user.getLastname() + " " + user.getFirstname());
        //etDescriptionProfil.setText(user.getDescription());
        int age = user.getAge(user.getBirthday());
        String sexe = "Femme";
        if (user.getSexe().startsWith("H")) {
            sexe = "Homme";
        }
        tvAgeSexe.setText(age + " ans / " + sexe);

        String fnAvatar = user.getAvatar();
        /*if (fnAvatar != null) {
            int width = imgAvatar.getLayoutParams().width;
            try {
                File file = new File(fnAvatar);
                if (file.exists()) {
                    Uri uri = Uri.fromFile(file);
                    Picasso.with(getActivity().getApplicationContext())
                            .load(uri)
                            .resize(width, width)
                            .transform(new RoundedTransformation(width, 0))
                            .into(imgAvatar);
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting Picture to File : " + e.getMessage());
            }
        }*/

        //POUR LES COULEURS :

        /*
        for (int i = 0 ; i < listColors.size() ; i ++){
            if(listColors.get(i) == 0){
                switch (i) {
                    case 1:

                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
            else {
                favColor1.setBackgroundColor(user.getFavoriteColor1());
                favColor2.setBackgroundColor(user.getFavoriteColor2());
                favColor3.setBackgroundColor(user.getFavoriteColor3());
                favColor4.setBackgroundColor(user.getFavoriteColor4());
            }
        }
        */

        listColors = new ArrayList<>();
        listColors.add(user.getFavoriteColor1());
        listColors.add(user.getFavoriteColor2());
        listColors.add(user.getFavoriteColor3());
        listColors.add(user.getFavoriteColor4());

        ((LinearLayout) getView().findViewById(R.id.layoutFavoriteColor)).removeAllViews();

        for (int i = 0; i < listColors.size(); i++) {
            if (listColors.get(i) != 0) {
                final View viewToLoad = LayoutInflater.from(
                        getActivity().getApplicationContext()).inflate(
                        R.layout.item_layout_color_profile, null);
                final ImageView color = (ImageView) viewToLoad.findViewById(R.id.circleColor);
                color.setBackgroundColor(listColors.get(i));
                ((LinearLayout) getView().findViewById(R.id.layoutFavoriteColor))
                        .addView(viewToLoad);
            }
        }

    }

    /*

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profil_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return false;
    }

*/


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

    public void loadGarments() {
        // user : Get all the user's garments
        listGarments.clear();
        ArrayList<UserClothes> userClothes = SMXL.get().getDataBase().getAllUserGarments(user);
        for (UserClothes uc : userClothes) {
            adapterGarments.add(uc);
        }
        adapterGarments.notifyDataSetChanged();
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

    /*
    private AdapterView.OnClickListener selectGarment = new AdapterView.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String garmentBrand = listGarments.get((Integer) v.getTag(position)).getTypeGarment() + " - " +
                    listGarments.get((Integer) v.getTag(position)).getBrand();
            final String notes = listGarments.get((Integer) v.getTag(position)).getComment();
            final String size = listGarments.get((Integer) v.getTag(position)).getSize();
            final String country = listGarments.get((Integer) v.getTag(position)).getCountry();
            final String libSizeCountry = "Taille " + size + " - " + country.toUpperCase();
            LayoutInflater li = LayoutInflater.from(getActivity());
            View garmentInfoDialog = li.inflate(R.layout.garment_info_dialog, null);
            TextView tvTypeAndBrand = (TextView) garmentInfoDialog.findViewById(R.id.tvTypeAndBrand);
            TextView tvComments = (TextView) garmentInfoDialog.findViewById(R.id.tvComments);
            TextView tvSizeCountry = (TextView) garmentInfoDialog.findViewById(R.id.tvSizeCountry);

            tvTypeAndBrand.setText(garmentBrand);
            tvComments.setText(notes);
            tvSizeCountry.setText(libSizeCountry);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setView(garmentInfoDialog);
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
    */

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
        listGarments.remove(positionItem);
        adapterGarments.notifyDataSetChanged();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("measures", listMeasures);
        outState.putSerializable("garments", listGarments);
    }


    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    @Override
    public void updateMesures() {
        loadMeasures();
    }

    @Override
    public void updateGarments() {
        loadGarments();
    }

    @Override
    public void onConfirm(boolean confirmed, int positionItem) {
        if (confirmed) {
            deleteGarment(positionItem);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (user != null) {
            //updateMesures();
            //updateGarments();
            updateUI();
        }
    }
}
