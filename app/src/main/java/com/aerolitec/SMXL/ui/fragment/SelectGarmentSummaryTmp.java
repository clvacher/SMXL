package com.aerolitec.SMXL.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.ui.activity.tmp;

/**
 * Created by Clement on 5/6/2015.
 */
public class SelectGarmentSummaryTmp extends Fragment {
    private tmp activity;
    private TextView tvWarning;
    private EditText etComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_garment_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        activity=(tmp)getActivity();

        EditText etSize=(EditText) view.findViewById(R.id.editText2);
        etComment=(EditText) view.findViewById(R.id.editText);
        Button addToWardrobe=(Button) view.findViewById(R.id.button3);


        etSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                activity.setSelectedSize(editable.toString());
            }
        });

        addToWardrobe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setComment((etComment.getText()).toString());
                activity.saveGarment();
                activity.finish();
            }
        });
    }

}
