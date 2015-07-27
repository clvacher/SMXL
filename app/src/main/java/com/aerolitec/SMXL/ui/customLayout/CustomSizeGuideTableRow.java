package com.aerolitec.SMXL.ui.customLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TableRow;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.GarmentType;

/**
 * Created by NelsonGay on 27/07/2015.
 */
public class CustomSizeGuideTableRow extends TableRow{
    private String sizeType;
    private String sizeValue;
    TextView tvSizeType, tvSizeValue;
    public CustomSizeGuideTableRow(Context context, String sizeType , String sizeValue) {
        super(context);
        this.sizeType = sizeType;
        this.sizeValue = sizeValue;
        init();
    }


    public CustomSizeGuideTableRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.custom_size_guide_table_row, this);
        tvSizeType =(TextView) findViewById(R.id.sizeType);
        tvSizeValue = (TextView) findViewById(R.id.sizeValue);
        tvSizeType.setText(sizeType+" :");
        tvSizeValue.setText(sizeValue);
    }
}
