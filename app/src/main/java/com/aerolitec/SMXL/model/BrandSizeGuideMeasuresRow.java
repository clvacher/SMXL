package com.aerolitec.SMXL.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Clement on 6/9/2015.
 */
public class BrandSizeGuideMeasuresRow {

    Double bust;
    int fBust=1;
    Double collar;
    int fCollar=1;
    Double chest;
    int fChest=1;
    Double feet;
    int fFeet=1;
//    Double head;
//    int fHead=1;
    Double height;
    int fHeight=1;
    Double hips;
    int fHips=1;
    Double inseam;
    int fInseam=1;
    Double sleeve;
    int fSleeve=1;
    Double thigh;
    int fThigh=1;
    Double waist;
    int fWaist=1;
    Double weight;
    int fWeight=1;
    Double other;
    int fOther=1;

    String sizeFR;
    String sizeUE;
    String sizeUK;
    String sizeUS;
    String sizeSMXL;
    String sizeSuite;

    public BrandSizeGuideMeasuresRow() {
    }


    public BrandSizeGuideMeasuresRow(Double bust, Double collar, Double chest, Double head, Double feet, Double height, Double hips, Double inseam, Double sleeve, Double thigh, Double waist, Double weight, Double other, String sizeUK, String sizeUE, String sizeFR, String sizeUS, String sizeSMXL, String sizeSuite) {
        setBust(bust);
        setCollar(collar);
        setChest(chest);
//        setHead(head);
        setFeet(feet);
        setHeight(height);
        setHips(hips);
        setInseam(inseam);
        setSleeve(sleeve);
        setThigh(thigh);
        setWaist(waist);
        setWeight(weight);
        setOther(other);
        setSizeUK(sizeUK);
        setSizeUE(sizeUE);
        setSizeFR(sizeFR);
        setSizeUS(sizeUS);
        setSizeSMXL(sizeSMXL);
        setSizeSuite(sizeSuite);
    }

    public Double getHeight() {
        return height;
    }
    public void setHeight(Double height) {
        if(height !=null) {
            this.height = height;
        }
        else{
            this.height = 0.0;
            this.fHeight = 0;
        }

    }

    public Double getBust() {
        return bust;
    }
    public void setBust(Double bust) {
        if(bust !=null) {
            this.bust = bust;
        }
        else{
            this.bust = 0.0;
            this.fBust = 0;
        }
    }

    public Double getCollar() {
        return collar;
    }
    public void setCollar(Double collar) {
        if(collar !=null && collar!=0.0) {
        this.collar = collar;
        }
        else{
            this.collar = 0.0;
            this.fCollar = 0;
        }
    }

    public Double getChest() {
        return chest;
    }
    public void setChest(Double chest) {
        if(chest !=null && chest!=0.0) {
        this.chest = chest;
        }
        else{
            this.chest = 0.0;
            this.fChest = 0;
        }
    }

    public Double getFeet() {
        return feet;
    }
    public void setFeet(Double feet) {
        if(feet !=null && feet!=0.0) {
        this.feet = feet;
        }
        else{
            this.feet = 0.0;
            this.fFeet = 0;
        }
    }

//    public Double getHead() {
//        return head;
//    }
//    public void setHead(Double head) {
//        if(head !=null) {
//        this.head = head;
//        }
//        else{
//            this.head = 0.0;
//            this.fHead = 0;
//        }
//    }

    public Double getHips() {
        return hips;
    }
    public void setHips(Double hips) {
        if(hips !=null && hips!=0.0) {
        this.hips = hips;
        }
        else{
            this.hips = 0.0;
            this.fHips = 0;
        }
    }

    public Double getInseam() {
        return inseam;
    }
    public void setInseam(Double inseam) {
        if(inseam !=null && inseam!=0.0) {
        this.inseam = inseam;
        }
        else{
            this.inseam = 0.0;
            this.fInseam = 0;
        }
    }

    public Double getSleeve() {
        return sleeve;
    }
    public void setSleeve(Double sleeve) {
        if(sleeve !=null && sleeve!=0.0) {
        this.sleeve = sleeve;
        }
        else{
            this.sleeve = 0.0;
            this.fSleeve = 0;
        }
    }

    public Double getThigh() {
        return thigh;
    }
    public void setThigh(Double thigh) {
        if(thigh !=null && thigh!=0.0) {
        this.thigh = thigh;
        }
        else{
            this.thigh = 0.0;
            this.fThigh = 0;
        }
    }

    public Double getWaist() {
        return waist;
    }
    public void setWaist(Double waist) {
        if(waist !=null && waist!=0.0) {
        this.waist = waist;
        }
        else{
            this.waist = 0.0;
            this.fWaist = 0;
        }
    }

    public Double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        if(weight !=null && weight!=0.0) {
        this.weight = weight;
        }
        else{
            this.weight = 0.0;
            this.fWeight = 0;
        }
    }

    public Double getOther() {
        return other;
    }
    public void setOther(Double other) {
        if(other !=null && other!=0.0) {
        this.other = other;
        }
        else{
            this.other = 0.0;
            this.fOther = 0;
        }
    }

    public String getSizeFR() {
        return sizeFR;
    }
    public void setSizeFR(String sizeFR) {
        this.sizeFR = sizeFR;
    }

    public String getSizeUE() {
        return sizeUE;
    }
    public void setSizeUE(String sizeUE) {
        this.sizeUE = sizeUE;
    }

    public String getSizeUK() {
        return sizeUK;
    }
    public void setSizeUK(String sizeUK) {
        this.sizeUK = sizeUK;
    }

    public String getSizeUS() {
        return sizeUS;
    }
    public void setSizeUS(String sizeUS) {
        this.sizeUS = sizeUS;
    }

    public String getSizeSMXL() {
        return sizeSMXL;
    }
    public void setSizeSMXL(String sizeSMXL) {
        this.sizeSMXL = sizeSMXL;
    }

    public String getSizeSuite() {
        return sizeSuite;
    }
    public void setSizeSuite(String sizeSuite) {
        this.sizeSuite = sizeSuite;
    }


    public Double getOffsetWithMeasures(User user){
        return fBust*Math.abs(bust-user.getBust())+
                fCollar*Math.abs(collar-user.getCollar())+
                fChest*Math.abs(chest-user.getChest())+
                fFeet*Math.abs(feet-user.getFeet())+
                fHeight*Math.abs(height-user.getHeight())+
                fHips*Math.abs(hips-user.getHips())+
                fInseam*Math.abs(inseam-user.getInseam())+
                fSleeve*Math.abs(sleeve-user.getSleeve())+
                fThigh*Math.abs(thigh-user.getThigh())+
                fWaist*Math.abs(waist-user.getWaist())+
                fWeight*Math.abs(weight-user.getWeight());

    }

    public HashMap<String,String> getCorrespondingSizes(){
        HashMap<String,String> result = new HashMap<>();
        result.put("FR",sizeFR);
        result.put("UE", sizeUE);
        result.put("UK", sizeUK);
        result.put("US", sizeUS);
        result.put("SMXL", sizeSMXL);
        return result;
    }


    public static BrandSizeGuideMeasuresRow getClosestRowToMeasures(ArrayList<BrandSizeGuideMeasuresRow> brandSizeGuideMeasuresRows,User user){
        BrandSizeGuideMeasuresRow resultRow = null;
        Double bestOffset = null;
        Log.d("Arraylist getClosest",brandSizeGuideMeasuresRows+"");
        for(BrandSizeGuideMeasuresRow brandSizeGuideMeasuresRow : brandSizeGuideMeasuresRows){
            Double offset = brandSizeGuideMeasuresRow.getOffsetWithMeasures(user);
            Log.d("toto1",offset+"");

            if(bestOffset == null || bestOffset>offset){
                bestOffset = offset;
                Log.d("toto2",bestOffset+"");
                resultRow = brandSizeGuideMeasuresRow;
            }

        }
        Log.d("result GetClosest",resultRow+"");
        return resultRow;
    }

}
