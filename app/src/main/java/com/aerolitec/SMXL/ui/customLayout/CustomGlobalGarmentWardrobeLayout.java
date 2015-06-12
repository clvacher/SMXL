package com.aerolitec.SMXL.ui.customLayout;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;
import com.aerolitec.SMXL.ui.adapter.GarmentAdapter;

import java.util.ArrayList;

/**
 * Created by Jerome on 28/05/2015.
 */
public class CustomGlobalGarmentWardrobeLayout extends LinearLayout {

    CategoryGarment categoryGarment;
    RelativeLayout addGarment;
    LinearLayout mainLinearLayout;
    ImageView imageView;
    TextView textViewGarmentName, textViewNbGarment;
    ListView listView;
    ArrayList<UserClothes> userClothesArrayList;



    public CustomGlobalGarmentWardrobeLayout(Context context, CategoryGarment categoryGarment) {
        super(context);
        this.categoryGarment = categoryGarment;
        init();
    }

    public CustomGlobalGarmentWardrobeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomGlobalGarmentWardrobeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        inflate(getContext(), R.layout.custom_global_garment_wardrobe, this);
        userClothesArrayList = SMXL.getUserClothesDBManager().getUserGarmentsByGarment(UserManager.get().getUser(), categoryGarment);
        this.mainLinearLayout = (LinearLayout)findViewById(R.id.mainLinearLayoutCustomGlobalGarment);
        this.imageView = (ImageView)findViewById(R.id.imageViewCustomGlobalGarment);
        this.textViewGarmentName = (TextView)findViewById(R.id.textViewGarmentNameCustomGlobalGarment);
        this.textViewNbGarment = (TextView)findViewById(R.id.textViewNbGarmentCustomGlobalGarment);
        this.listView = (ListView) findViewById(R.id.listViewCustomGlobalGarment);
        this.addGarment = (RelativeLayout) findViewById(R.id.AddCustomGlobalGarment);

        initView();
        initListener();
    }

    private void initView(){
        imageView.setImageResource(categoryGarment.getIcon());
        textViewGarmentName.setText(getResources().getIdentifier(categoryGarment.getCategory_garment_name(), "string", getContext().getPackageName()));
        updateCounter();
    }

    private void initListener(){
        listView.setOnHierarchyChangeListener(new OnHierarchyChangeListener() {
            @Override
            public void onChildViewAdded(View parent, View child) {
                updateCounter();
                UtilityMethodsv2.setListViewHeightBasedOnChildren(listView);
            }

            @Override
            public void onChildViewRemoved(View parent, View child) {
                updateCounter();
                UtilityMethodsv2.setListViewHeightBasedOnChildren(listView);
            }
        });

        mainLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userClothesArrayList.size() == 0) {
                    listView.setVisibility(View.GONE);
                    Intent intent = new Intent(getContext(), AddGarmentActivity.class);
                    intent.putExtra("category", categoryGarment);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                } else {
                    if (listView.getVisibility() == View.GONE) {
                        fillListView(listView, userClothesArrayList);
                        listView.setVisibility(View.VISIBLE);
                    } else {
                        listView.setVisibility(View.GONE);
                    }
                }

            }
        });


        addGarment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.GONE);
                //collapse.setImageResource(R.drawable.navigation_expand);
                Intent intent = new Intent(getContext(), AddGarmentActivity.class);
                intent.putExtra("category", categoryGarment);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    private void fillListView(ListView v,ArrayList<UserClothes> userClothesList){
        GarmentAdapter adapter = new GarmentAdapter(getContext(),R.layout.garment_item,userClothesList);
        v.setAdapter(adapter);
        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), AddGarmentActivity.class);
                intent.putExtra("userClothes", (UserClothes) adapterView.getItemAtPosition(i));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
        UtilityMethodsv2.setListViewHeightBasedOnChildren(v);
        adapter.notifyDataSetChanged();
    }

    public void updateCounter(){
        textViewNbGarment.setText("(" + userClothesArrayList.size() + ")");
    }
}
