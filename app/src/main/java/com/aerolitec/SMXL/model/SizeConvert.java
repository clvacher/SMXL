package com.aerolitec.SMXL.model;


public class SizeConvert extends BaseObjects{

    int id_size_convert;
    String garment;
    String sex;
    String valueUS;
    String valueUK;
    String valueUE;
    String valueFR;
    String valueITA;
    String valueJAP;
    String valueSMXL;
    String valueRUS;
    String valueCH;

    public SizeConvert() {}

    public SizeConvert(int id_size_convert, String garment, String sex, String valueUS, String valueUK, String valueUE,String valueFR,
                       String valueITA, String valueJAP, String valueSMXL){
        this.id_size_convert = id_size_convert;
        this.garment = garment;
        this.sex = sex;
        this.valueUS = valueUS;
        this.valueUK = valueUK;
        this.valueUE = valueUE;
        this.valueFR = valueFR;
        this.valueITA = valueITA;
        this.valueJAP = valueJAP;
        this.valueSMXL = valueSMXL;
    }

    public int getId_size_convert() {
        return id_size_convert;
    }

    public void setId_size_convert(int id_size_convert) {
        this.id_size_convert = id_size_convert;
    }

    public String getGarment() {
        return garment;
    }

    public void setGarment(String garment) {
        this.garment = garment;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getValueSMXL() {
        return valueSMXL;
    }

    public void setValueSMXL(String valueSMXL) {
        this.valueSMXL = valueSMXL;
    }

    public String getValueRUS() {
        return valueRUS;
    }

    public void setValueRUS(String valueRUS) {
        this.valueRUS = valueRUS;
    }

    public String getValueCH() {
        return valueCH;
    }

    public void setValueCH(String valueCH) {
        this.valueCH = valueCH;
    }

    @Override
    public String toString() {
        return "SizeConvert{" +
                "id_size_convert=" + id_size_convert +
                ", garment='" + garment + '\'' +
                ", valueUS='" + valueUS + '\'' +
                ", valueUK='" + valueUK + '\'' +
                ", valueUE='" + valueUE + '\'' +
                ", valueFR='" + valueFR + '\'' +
                ", valueITA='" + valueITA + '\'' +
                ", valueJAP='" + valueJAP + '\'' +
                ", valueSMXL='" + valueSMXL + '\'' +
                ", valueRUS='" + valueRUS + '\'' +
                ", valueCH='" + valueCH + '\'' +
                '}';
    }
}
