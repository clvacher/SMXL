package com.aerolitec.SMXL.model;


import java.util.HashMap;

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
    String valueBRA;
    String valueCH;

    public SizeConvert() {}

    public SizeConvert(int id_size_convert, String garment, String sex, String valueUS, String valueUK, String valueUE,String valueFR,
                       String valueITA, String valueJAP, String valueSMXL,String valueBRA,String valueRUS){
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
        this.valueBRA = valueBRA;
        this.valueRUS = valueRUS;
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
    public String getValueBRA() {
        return valueBRA;
    }

    public void setValueBRA(String valueBRA) {
        this.valueBRA = valueBRA;
    }

    public HashMap<String,String> getCorrespondingSizes(){
        HashMap<String,String> result = new HashMap<>();
        result.put("FR",valueFR);
        result.put("UE", valueUE);
        result.put("UK", valueUK);
        result.put("US", valueUS);
        result.put("BRA", valueBRA);
        result.put("JAP", valueJAP);
        result.put("RUS", valueRUS);
        result.put("SMXL", valueSMXL);
        return result;
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
