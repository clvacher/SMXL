package com.aerolitec.SMXL.model;

/**
 * Created by stephaneL on 20/03/14.
 */
public class SizeConvert extends BaseObjects{

    int id;
    String type;
    String valueUS;
    String valueUK;
    String valueUE;
    String valueFR;
    String valueITA;
    String valueJAP;
    String valueAUS;

    public SizeConvert() {}

    public SizeConvert(int id, String type, String valueUS, String valueUK, String valueUE,String valueFR,
                       String valueITA, String valueJAP, String valueAUS){
        this.id = id;
        this.type = type;
        this.valueUS = valueUS;
        this.valueUK = valueUK;
        this.valueUE = valueUE;
        this.valueFR = valueFR;
        this.valueITA = valueITA;
        this.valueJAP = valueJAP;
        this.valueAUS = valueAUS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueUS() {
        return valueUS;
    }

    public void setValueUS(String valueUS) {
        this.valueUS = valueUS;
    }

    public String getValueUK() {
        return valueUK;
    }

    public void setValueUK(String valueUK) {
        this.valueUK = valueUK;
    }

    public String getValueUE() {
        return valueUE;
    }

    public void setValueUE(String valueUE) {
        this.valueUE = valueUE;
    }

    public String getValueFR() {
        return valueFR;
    }

    public void setValueFR(String valueFR) {
        this.valueFR = valueFR;
    }

    public String getValueITA() {
        return valueITA;
    }

    public void setValueITA(String valueITA) {
        this.valueITA = valueITA;
    }

    public String getValueJAP() {
        return valueJAP;
    }

    public void setValueJAP(String valueJAP) {
        this.valueJAP = valueJAP;
    }

    public String getValueAUS() {
        return valueAUS;
    }

    public void setValueAUS(String valueAUS) {
        this.valueAUS = valueAUS;
    }

    @Override
    public String toString() {
        return "SizeConvert{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", valueUS='" + valueUS + '\'' +
                ", valueUK='" + valueUK + '\'' +
                ", valueUE='" + valueUE + '\'' +
                ", valueFR='" + valueFR + '\'' +
                ", valueITA='" + valueITA + '\'' +
                ", valueJAP='" + valueJAP + '\'' +
                ", valueAUS='" + valueAUS + '\'' +
                '}';
    }
}
