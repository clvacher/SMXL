package com.aerolitec.SMXL.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.QuickContactBadge;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.colorPicker.ColorStateDrawable;

/**
 * Created by gautierragueneau on 04/06/2014.
 */
public class CalendarColorSquare extends QuickContactBadge {

    public CalendarColorSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CalendarColorSquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setBackgroundColor(int color) {
        Drawable[] colorDrawable = new Drawable[] {
                getContext().getResources().getDrawable(R.drawable.calendar_color_square) };
        setImageDrawable(new ColorStateDrawable(colorDrawable, color));
    }
}