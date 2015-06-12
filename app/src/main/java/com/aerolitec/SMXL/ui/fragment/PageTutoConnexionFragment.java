package com.aerolitec.SMXL.ui.fragment;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.PageTuto;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageTutoConnexionFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "pagetuto";

    private PageTuto pageTuto;


    public static PageTutoConnexionFragment newInstance(PageTuto param1) {
        PageTutoConnexionFragment fragment = new PageTutoConnexionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, param1);
        fragment.setArguments(args);

        return fragment;
    }

    public PageTutoConnexionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageTuto = (PageTuto)getArguments().get(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_page_tuto_connexion, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.mainTitlePageTuto);
        TextView subtitle = (TextView) rootView.findViewById(R.id.subtitlePageTuto);
        ImageView icon = (ImageView) rootView.findViewById(R.id.imageViewLogoPageTuto);

        title.setText(pageTuto.getTitle());
        subtitle.setText(pageTuto.getSubtitle());
        icon.setImageResource(pageTuto.getIdIcon());


        return rootView;
    }



}
