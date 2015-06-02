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
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.adapter.MeasureItem;

import java.util.HashMap;


public class MeasureDetailFragment extends Fragment {

    private User user;
    private View view;
    private TextView tvNeck, tvChest, tvWaist, tvHips, tvSleeve, tvThigh, tvHeight, tvInseam, tvFeet;
    private FrameLayout sizesImageLayout;

    private HashMap<TextView,MeasureItem> measureItems;

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
        loadMeasureItems();
        setListeners();


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

        /* OnViewCreated? */



        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void loadMeasureItems(){
        measureItems= new HashMap<>();
        measureItems.put(tvNeck, new MeasureItem(getResources().getString(R.string.libCollar),user.getCollar()));
        measureItems.put(tvChest, new MeasureItem(getResources().getString(R.string.libChest),user.getCollar()));
        measureItems.put(tvWaist, new MeasureItem(getResources().getString(R.string.libWaist),user.getCollar()));
        measureItems.put(tvSleeve, new MeasureItem(getResources().getString(R.string.libSleeve),user.getCollar()));
        measureItems.put(tvHips, new MeasureItem(getResources().getString(R.string.libHips),user.getCollar()));
        measureItems.put(tvHeight, new MeasureItem(getResources().getString(R.string.libHeight),user.getCollar()));
        measureItems.put(tvThigh, new MeasureItem(getResources().getString(R.string.libthigh),user.getCollar()));
        measureItems.put(tvInseam, new MeasureItem(getResources().getString(R.string.libInseam),user.getCollar()));
        measureItems.put(tvFeet, new MeasureItem(getResources().getString(R.string.libFeet), user.getCollar()));
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
        tvNeck.setText(user.getCollar()+"");
        tvChest.setText(user.getChest()+"");
        tvWaist.setText(user.getWaist()+"");
        tvSleeve.setText(user.getSleeve()+"");
        tvHips.setText(user.getSleeve()+"");
        tvHeight.setText(user.getHeight()+"");
        tvThigh.setText(user.getThigh()+"");
        tvInseam.setText(user.getInseam()+"");
        tvFeet.setText(user.getFeet()+"");
    }

    @Override
    public void onPause() {
        saveMeasures();
        super.onPause();
    }

    private void setListeners(){
        setPersonalizedListener(tvNeck);
        setPersonalizedListener(tvChest);
        setPersonalizedListener(tvWaist);
        setPersonalizedListener(tvSleeve);
        setPersonalizedListener(tvHips);
        setPersonalizedListener(tvHeight);
        setPersonalizedListener(tvThigh);
        setPersonalizedListener(tvInseam);
        setPersonalizedListener(tvFeet);
    }

    private void setPersonalizedListener(View v){
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(v);
            }
        });
    }


    private void openDialog(final View v){
        View measureDialog = (LayoutInflater.from(getActivity())).inflate(R.layout.measure_dialog, null);

        final MeasureItem measureItem = measureItems.get(v);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(measureDialog);

        ((TextView) measureDialog.findViewById(R.id.textView1)).setText(measureItem.getTypeMeasure());
        final EditText userInput = (EditText) measureDialog.findViewById(R.id.etDialogUserInput);
        if (measureItem.getValueMeasure() != 0){
            userInput.setText(String.valueOf(measureItem.getValueMeasure()));
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                double value = 0;
                if (userInput.getText().toString().length() > 0) {
                    value = Double.valueOf(userInput.getText().toString());
                }
                measureItem.setValueMeasure(value);
                ((TextView)v).setText(value+"");
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
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

        tvHeight.setX(totalWidth * 85 / 100);

        tvThigh.setY(totalHeight * 56f / 100);
        tvThigh.setX(totalWidth * 52.7f / 100);

        tvInseam.setX(totalWidth * 24.5f / 100);
        tvInseam.setY(totalHeight * 70 / 100);

        tvFeet.setX(totalWidth * 60 / 100);
        tvFeet.setY(totalHeight * 93.5f / 100);
    }

    private void saveMeasures(){
        user.setCollar(Double.parseDouble(tvNeck.getText().toString()));
        user.setChest(Double.parseDouble(tvChest.getText().toString()));
        user.setWaist(Double.parseDouble(tvWaist.getText().toString()));
        user.setSleeve(Double.parseDouble(tvSleeve.getText().toString()));
        user.setHips(Double.parseDouble(tvHips.getText().toString()));
        user.setHeight(Double.parseDouble(tvHeight.getText().toString()));
        user.setThigh(Double.parseDouble(tvThigh.getText().toString()));
        user.setInseam(Double.parseDouble(tvInseam.getText().toString()));
        user.setFeet(Double.parseDouble(tvFeet.getText().toString()));
        SMXL.getUserDBManager().updateUser(user);
    }

}