package com.aerolitec.SMXL.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.adapter.MeasureItem;

import java.util.ArrayList;
import java.util.HashMap;


public class MeasureDetailFragment extends Fragment {

    private User user;
    private View view;
    private TextView tvNeck, tvChest, tvWaist, tvHips, tvSleeve, tvThigh, tvHeight, tvInseam, tvFeet;
    private FrameLayout sizesImageLayout;

    private ArrayList<MeasureItem> measureItems;

    private RelativeLayout test2;

    public MeasureDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = UserManager.get().getUser();
        if (user == null)
            Log.d("MeasureDetailFragment", "user null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_measure_detail, container, false);
        sizesImageLayout = (FrameLayout) getActivity().findViewById(R.id.container);

        findMeasureItemsInView(view);
        loadMeasures();


        test2 = (RelativeLayout) view.findViewById(R.id.sizesImageLayout);

        ViewTreeObserver vto = test2.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                placeMeasureItems();

                ViewTreeObserver obs = test2.getViewTreeObserver();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    obs.removeOnGlobalLayoutListener(this);
                } else {
                    obs.removeGlobalOnLayoutListener(this);
                }
            }

        });

        /* Test de taille d'image

        final Activity activity = getActivity();
        final RelativeLayout test2 = (RelativeLayout) view.findViewById(R.id.sizesImageLayout);
        AsyncTask test = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                try {
                    Thread.sleep(2000);
                    Log.d("atHeight", test2.getHeight() + "");
                    Log.d("atMeasuredHeight", test2.getMeasuredHeight() + "");
                    Log.d("atWidth", test2.getWidth() + "");
                    Log.d("atMeasuredWidth",test2.getMeasuredWidth()+"");
                }
                catch (Exception e){

                }
                return null;
            }
        };
        test.execute();
        */

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void findMeasureItemsInView(View v) {
        tvNeck = (TextView) v.findViewById(R.id.tvNeck);
        tvChest = (TextView) v.findViewById(R.id.tvChest);
        tvWaist = (TextView) v.findViewById(R.id.tvWaist);
        tvSleeve = (TextView) v.findViewById(R.id.tvSleeve);
        tvHips = (TextView) v.findViewById(R.id.tvHips);
        tvHeight = (TextView) v.findViewById(R.id.tvHeight);
        tvThigh = (TextView) v.findViewById(R.id.tvThigh);
        tvInseam = (TextView) v.findViewById(R.id.tvInseam);
        tvFeet = (TextView) v.findViewById(R.id.tvFeet);
    }

    private void loadMeasures(){
//        tvNeck.setText(user.getCollar()+"");
//        tvChest.setText(user.getChest()+"");
//        tvWaist.setText(user.getWaist()+"");
//        tvSleeve.setText(user.getSleeve()+"");
//        tvHips.setText(user.getSleeve()+"");
//        tvHeight.setText(user.getSize()+"");
//        tvThigh.setText(user.getThigh()+"");
//        tvInseam.setText(user.getInseam()+"");
//        tvFeet.setText(user.getFeet()+"");
    }

    private void setListeners(){
        tvNeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View measureDialog = (LayoutInflater.from(getActivity())).inflate(R.layout.measure_dialog, null);

                (new AlertDialog.Builder(getActivity())).setView(measureDialog);

                ((TextView) measureDialog.findViewById(R.id.textView1)).setText("");
            }
        });
    }


    private void placeMeasureItems() {
        test2 = (RelativeLayout) view.findViewById(R.id.sizesImageLayout);
        int totalHeight = test2.getHeight(), totalWidth = test2.getWidth();
        Log.d("getHeight", totalHeight + "");
        Log.d("getWidth", totalWidth + "");

        tvNeck.setY(totalHeight * 13.1f / 100);

        tvChest.setY(totalHeight * 28 / 100);

        tvWaist.setY(totalHeight * 39 / 100);

        tvSleeve.setY(totalHeight * 30 / 100);
        tvSleeve.setX(totalWidth * 18 / 100);

        tvHeight.setX(totalWidth * 91 / 100);

        tvThigh.setY(totalHeight * 56 / 100);
        tvThigh.setX(totalWidth * 53 / 100);

        tvHeight.setX(totalWidth * 85 / 100);

        tvInseam.setX(totalWidth * 26 / 100);
        tvInseam.setY(totalHeight * 70 / 100);

        tvFeet.setX(totalWidth * 60 / 100);
        tvFeet.setY(totalHeight * 93.5f / 100);
    }

}
/*
    private User user;
    private View view;
    private ArrayList<Integer> indexSize;
    private RelativeLayout addMeasure,layoutHeaderMeasures;
    private LinearLayout layoutMeasure;

    private ArrayList<MeasureItem> listMeasures;

    private MeasureAdapter adapterMeasure;

    public MeasureDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= UserManager.get().getUser();
        if(user==null)
            Log.d("MeasureDetailFragment", "user null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_measure_detail, container, false);
        TextView tvFirstName = (TextView) view.findViewById(R.id.firstName);
        tvFirstName.setText(user.getFirstname());
        ImageView avatar=(ImageView)view.findViewById(R.id.imgAvatar);

        indexSize = user.getIndexMeasureNotNull();

        addMeasure=(RelativeLayout) view.findViewById(R.id.layoutAddMeasure);
        layoutHeaderMeasures = (RelativeLayout) view.findViewById(R.id.layoutHeaderMeasures);
        layoutMeasure = (LinearLayout) view.findViewById(R.id.layoutViewMeasure);

        addMeasure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMeasureActivity.class);
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
                }
                else {
                    layoutMeasure.setVisibility(View.GONE);
                    ImageView collapse = (ImageView) view.findViewById(R.id.collapseMeasure);
                    collapse.setImageResource(R.drawable.navigation_expand);
                }
            }
        });

        listMeasures = new ArrayList<>();
        adapterMeasure = new MeasureAdapter(getActivity().getApplicationContext(), listMeasures);

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

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (user != null) {
            user=UserManager.get().getUser();
            fillListMeasures();
        }
    }

    private void fillListMeasures() {

        String[] listSize = {getResources().getString(R.string.libSize), getResources().getString(R.string.libWeight),
                getResources().getString(R.string.libBust), getResources().getString(R.string.libChest),
                getResources().getString(R.string.libCollar), getResources().getString(R.string.libWaist),
                getResources().getString(R.string.libHips), getResources().getString(R.string.libSleeve),
                getResources().getString(R.string.libInseam), getResources().getString(R.string.libFoot),
                getResources().getString(R.string.libUnit), getResources().getString(R.string.libUnit),
                getResources().getString(R.string.libPointure)};

        indexSize = user.getIndexMeasureNotNull();
        ((LinearLayout) getView().findViewById(R.id.layoutViewMeasure)).removeAllViews();

        for (int i = 0; i < indexSize.size(); i++) {
            ArrayList<String> sizes = user.getUserSizes();
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
    }

    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

}
*/
