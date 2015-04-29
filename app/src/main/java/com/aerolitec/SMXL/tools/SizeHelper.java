package com.aerolitec.SMXL.tools;

import android.widget.TextView;

import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.ui.SMXL;

import java.util.ArrayList;
import java.util.Iterator;


public class SizeHelper {

        private String mComputeSize;
        private String selectedBrand;
        private String selectedGarment;
        private ArrayList<TabSizes> sizes;
        private TextView textSizes;
        private User user;

        public SizeHelper(User paramUser, String paramString1, String paramString2, String paramString3, TextView paramTextView, ArrayList<TabSizes> paramArrayList)
        {
            this.user = paramUser;
            this.selectedBrand = paramString1;
            this.selectedGarment = paramString2;
            this.mComputeSize = paramString3;
            this.textSizes = paramTextView;
            this.sizes = paramArrayList;
        }

    private StringBuilder formatedStingSizes(ArrayList<TabSizes> paramArrayList)
    {
        StringBuilder localStringBuilder = new StringBuilder("");
        if (paramArrayList.size() == 0) {}
        for (;;)
        {
            return localStringBuilder;
            /*
            Iterator localIterator = paramArrayList.iterator();
            while (localIterator.hasNext())
            {
                TabSizes localTabSizes = (TabSizes)localIterator.next();
                localStringBuilder.append(localTabSizes.getPays()).append(" : ").append(String.valueOf(localTabSizes.getValeur())).append("\n");
            }
            */
        }
    }

    public void computeSize() {}

    public ArrayList<TabSizes> convertAllSize(double paramDouble)
    {
        String str1 = this.selectedGarment.toUpperCase();
        if (str1.equals("SWEAT")) {
            str1 = "SWEATER";
        }
        if (str1.contains("CHAUSSURE")) {
            str1 = str1 + "-" + this.user.getSexe().substring(0, 1);
        }
        SMXL.get();
        ArrayList localArrayList = SMXL.getDataBase().getConvertSizesByGarment(str1);
        if (this.sizes == null) {
            return null;
        }
        Iterator localIterator = localArrayList.iterator();
        SizeConvert localSizeConvert;
        while (localIterator.hasNext())
        {
            localSizeConvert = (SizeConvert)localIterator.next();
            String str2 = localSizeConvert.getValueUE();
            if (str1.contains("CHAUSSURE")) {
                str2 = localSizeConvert.getValueUS();
            }
            if (Double.valueOf(str2).doubleValue() >= paramDouble)
            {
                this.sizes.add(new TabSizes("US", String.valueOf(localSizeConvert.getValueUS())));
                this.sizes.add(new TabSizes("UK", String.valueOf(localSizeConvert.getValueUK())));
                this.sizes.add(new TabSizes("UE", String.valueOf(localSizeConvert.getValueUE())));
                if ((localSizeConvert.getValueSMXL() == null) || (localSizeConvert.getValueSMXL().isEmpty())) {
                    break;
                }
                this.sizes.add(new TabSizes("SMXL", String.valueOf(localSizeConvert.getValueSMXL())));
            }
        }
        for (;;)
        {
            return this.sizes;
            //this.sizes.add(new TabSizes("SMXL", String.valueOf(localSizeConvert.getValueUE())));
        }
    }

    public double convertSize(double paramDouble)
    {
        String str = this.selectedGarment.toUpperCase();
        if (str.equals("SWEAT")) {
            str = "SWEATER";
        }
        if (str.equalsIgnoreCase("CHAUSSURE")) {
            str = str + "-" + this.user.getSexe().substring(0, 1);
        }
        SMXL.get();
        ArrayList localArrayList = SMXL.getDataBase().getConvertSizesByGarment(str);
        if (localArrayList == null) {
            return paramDouble;
        }
        double d1 = 0.0D;
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
            SizeConvert localSizeConvert = (SizeConvert)localIterator.next();
            double d2 = Double.valueOf(localSizeConvert.getValueUE()).doubleValue();
            double d3 = Double.valueOf(localSizeConvert.getValueUK()).doubleValue();
            if (str.contains("CHAUSSURE")) {
                d3 = Double.valueOf(localSizeConvert.getValueUS()).doubleValue();
            }
            if ((d1 == 0.0D) && (d3 >= paramDouble)) {
                d1 = d2;
            }
        }
        return d1;
    }
}