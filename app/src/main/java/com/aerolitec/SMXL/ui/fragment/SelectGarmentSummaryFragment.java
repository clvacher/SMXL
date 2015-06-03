package com.aerolitec.SMXL.ui.fragment;

import android.support.v4.app.Fragment;
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
import com.aerolitec.SMXL.ui.activity.AddGarmentActivity;

/**
 * Created by Clement on 5/6/2015.
 */
public class SelectGarmentSummaryFragment extends Fragment {
    private AddGarmentActivity activity;
    private TextView tvWarning; //TODO warn in case of non-existing size
    private EditText etComment;
    private Button addToWardrobe;

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
        activity=(AddGarmentActivity)getActivity();

        EditText etSize=(EditText) view.findViewById(R.id.editText2);
        etComment=(EditText) view.findViewById(R.id.editText);
        addToWardrobe=(Button) view.findViewById(R.id.button3);
        addToWardrobe.setText(getResources().getString(R.string.validate));


        if(activity.getAddGarmentFragment().getSelectedIdUserClothes() != -1){
            etSize.setText(activity.getAddGarmentFragment().getSelectedSize());
            etComment.setText(activity.getAddGarmentFragment().getComment());
            addToWardrobe.setVisibility(View.VISIBLE);

            etSize.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    activity.getAddGarmentFragment().setSelectedSize(editable.toString());
                    Boolean b = editable.toString().length() > 0;
                    if (b) {
                        addToWardrobe.setVisibility(View.VISIBLE);
                    } else {
                        addToWardrobe.setVisibility(View.GONE);
                    }
                    activity.setUpdate(b);
                    activity.getAddGarmentFragment().setSelectedSize(editable.toString());
                }
            });
            addToWardrobe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.getAddGarmentFragment().updateGarment();
                    activity.finish();
                }
            });
        }
        else {
            etSize.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Boolean b = editable.toString().length() > 0;
                    if (b) {
                        addToWardrobe.setVisibility(View.VISIBLE);
                    } else {
                        addToWardrobe.setVisibility(View.GONE);
                    }
                    activity.setValidation(b);
                    activity.getAddGarmentFragment().setSelectedSize(editable.toString());
                }
            });

            addToWardrobe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.getAddGarmentFragment().setComment((etComment.getText()).toString());
                    activity.getAddGarmentFragment().saveGarment();
                    activity.finish();
                }
            });
        }

        etComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                activity.getAddGarmentFragment().setComment(editable.toString());
            }
        });
    }

}
