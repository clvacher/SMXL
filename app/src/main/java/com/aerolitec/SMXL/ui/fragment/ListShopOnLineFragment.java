package com.aerolitec.SMXL.ui.fragment;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.ShopOnLine;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.BrowserActivity;
import com.aerolitec.SMXL.ui.adapter.ShopOnLineAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListShopOnLineFragment extends Fragment {

    private ArrayList<ShopOnLine> shops;

    private GridView gridViewShops;
    private ShopOnLineAdapter adapter;

    public ListShopOnLineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shops = SMXL.getShopOnLineDBManager().getAllShops();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_shop_on_line, container, false);
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridViewShops = (GridView) view.findViewById(R.id.gridViewShops);

        adapter = new ShopOnLineAdapter(getActivity(), R.layout.shop_item, shops);
        gridViewShops.setAdapter(adapter);

        gridViewShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = shops.get(position).getShoponline_website();
                if (url != null) {
                    if (!url.startsWith("http://") && !url.startsWith("https://")) {
                        url = "http://" + url;
                    }
                    Intent browserIntent = new Intent(getActivity(), BrowserActivity.class);
                    browserIntent.putExtra("URL", url);
                    browserIntent.putExtra("TITLE", shops.get(position).getShoponline_name());
                    startActivity(browserIntent);
                }
            }
        });

    }
}
