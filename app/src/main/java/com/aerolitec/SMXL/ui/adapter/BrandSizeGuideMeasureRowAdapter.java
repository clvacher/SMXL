package com.aerolitec.SMXL.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Nelson on 13/08/2015.
 */
public class BrandSizeGuideMeasureRowAdapter extends ArrayAdapter<BrandSizeGuideMeasuresRow> {

    private String columnSelected = "";

    public BrandSizeGuideMeasureRowAdapter(Context context, int resource, int textViewResourceId, List<BrandSizeGuideMeasuresRow> objects,String columnSelected) {
        super(context, resource, textViewResourceId, objects);
        this.columnSelected = columnSelected;
    }

    public void setColumnSelected(String columnSelected) {
        this.columnSelected = columnSelected;
    }

    public String getColumnSelected() {
        return columnSelected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        HashMap<String,String> measuresRow = getItem(position).getCorrespondingSizes();
        if(measuresRow.containsKey(columnSelected)){
            ((TextView)convertView).setText(measuresRow.get(columnSelected));
        }
        return convertView;
    }
}
