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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddMeasureActivity;
import com.aerolitec.SMXL.ui.adapter.MeasureAdapter;
import com.aerolitec.SMXL.ui.adapter.MeasureItem;

import java.io.File;
import java.util.ArrayList;


public class MeasureDetailFragment extends Fragment {

    private User user;
    private View view;
    private ArrayList<Integer> indexSize;
    private RelativeLayout addMeasure,layoutHeaderMeasures;
    private LinearLayout layoutMeasure;

    private ArrayList<MeasureItem> listMeasures;

    private MeasureAdapter adapterMeasure;

    // TODO: Rename and change types and number of parameters
    public static MeasureDetailFragment newInstance(String param1, String param2) {
        MeasureDetailFragment fragment = new MeasureDetailFragment();
        return fragment;
    }

    public MeasureDetailFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_measure_detail, container, false);
        TextView tvFirstName = (TextView) view.findViewById(R.id.firstName);
        tvFirstName.setText(user.getFirstname());
        ImageView avatar=(ImageView)view.findViewById(R.id.imgAvatar);

        indexSize = SMXL.getDataBase().getIndexMeasureNotNull(user);

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
                } else {
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

        indexSize = SMXL.getDataBase().getIndexMeasureNotNull(user);
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
