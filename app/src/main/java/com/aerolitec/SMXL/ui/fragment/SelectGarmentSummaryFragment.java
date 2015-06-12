package com.aerolitec.SMXL.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.aerolitec.SMXL.ui.activity.BrowserActivity;

/**
 * Created by Clement on 5/6/2015.
 */
public class SelectGarmentSummaryFragment extends Fragment {
    private AddGarmentActivity activity;
    private TextView tvWarning; //TODO warn in case of non-existing size
    private EditText etComment;
    private Button shopButton;

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
        shopButton=(Button) view.findViewById(R.id.buttonShopGarmentSummary);

        if(activity.getAddGarmentFragment().getSelectedIdUserClothes() != -1){
            etSize.setText(activity.getAddGarmentFragment().getSelectedSize());
            etComment.setText(activity.getAddGarmentFragment().getComment());
            shopButton.setVisibility(View.VISIBLE);
            shopButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String urlBrand = activity.getAddGarmentFragment().getSelectedBrand().getBrandWebsite();
                    if (urlBrand != null) {
                        if (!urlBrand.startsWith("http://") && !urlBrand.startsWith("https://")) {
                            urlBrand = "http://" + urlBrand;
                        }
                        Intent browserIntent = new Intent(getActivity(), BrowserActivity.class);
                        browserIntent.putExtra("URL", urlBrand);
                        browserIntent.putExtra("TITLE", activity.getAddGarmentFragment().getSelectedBrand().getBrand_name());
                        startActivity(browserIntent);
                    }
                }
            });
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
                    activity.setUpdate(b);
                    activity.getAddGarmentFragment().setSelectedSize(editable.toString());
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
                    activity.setValidation(b);
                    activity.getAddGarmentFragment().setSelectedSize(editable.toString());
                }
            });

            shopButton.setVisibility(View.GONE);
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
