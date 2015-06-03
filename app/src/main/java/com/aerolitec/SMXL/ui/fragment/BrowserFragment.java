package com.aerolitec.SMXL.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowserFragment extends Fragment {



    SuperNavigationActivity activity;
    WebView webView;
    public WebView getWebView() {
        return webView;
    }

    public BrowserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (SuperNavigationActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_browser, container, false);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBarWebView);
        progressBar.setMax(100);
        progressBar.setProgress(0);

        webView = (WebView)view.findViewById(R.id.WebView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);

        //Full webpage visible
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        webView.loadUrl(getActivity().getIntent().getStringExtra("URL"));

        activity.updateTitle(getActivity().getIntent().getStringExtra("TITLE"));

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
                if (newProgress==100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
