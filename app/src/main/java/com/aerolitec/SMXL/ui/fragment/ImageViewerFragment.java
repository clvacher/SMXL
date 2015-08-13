package com.aerolitec.SMXL.ui.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.aerolitec.SMXL.R;

import java.net.URI;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageViewerFragment extends Fragment {
    private static final String IMAGE_URI = "image_URI";
    private Uri mImageUri = null;



    public static ImageViewerFragment newInstance(Uri uri) {
        ImageViewerFragment fragment = new ImageViewerFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_URI, uri);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageViewerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mImageUri = getArguments().getParcelable(IMAGE_URI);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_image_viewer, container, false);
        if(mImageUri!=null){
            ((ImageView) view.findViewById(R.id.img_expanded)).setImageURI(mImageUri);
        }
        return view;
    }


}
