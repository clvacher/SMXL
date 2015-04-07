package com.aerolitec.SMXL.model;

/**
 * Created by gautierragueneau on 28/05/2014.
 */
public class Child {
    private String birthday;
    private String sexe;
    private String avatar;
    private String pictureDressingRoom;
    private double size;
    private double weight;
    private double imc;
    private double chest;
    private double collar;
    private double bust;
    private double waist;
    private double hips;
    private double sleeve;
    private double inseam;
    private double feet;
    private int unitLength;
    private int unitWeight;
    private double pointure;
    private boolean isChecked;

    public Child(String birthday, String avatar, String pictureDressingRoom, String size, String weight,
                 String bust, String chest, String collar, String waist, String hips, String sleeve,
                 String inseam, String feet, String unitL, String unitW, String pointure) {
        this.isChecked = false;
        this.birthday = birthday;
        this.avatar = avatar;
        this.pictureDressingRoom = pictureDressingRoom;
        this.size = convertToDouble(size);
        this.weight = convertToDouble(weight);
        this.bust = convertToDouble(bust);
        this.chest = convertToDouble(chest);
        this.collar = convertToDouble(collar);
        this.waist = convertToDouble(waist);
        this.hips = convertToDouble(hips);
        this.sleeve = convertToDouble(sleeve);
        this.inseam = convertToDouble(inseam);
        this.feet = convertToDouble(feet);
        this.unitLength = convertToInt(unitL);
        this.unitWeight = convertToInt(unitW);
        this.pointure = convertToDouble(pointure);
    }


    private int convertToInt(String arg){
        if (arg.equals("")){
            return 0;
        } else {
            return Integer.parseInt(arg);
        }
    }

    private double convertToDouble(String arg){
        if (arg.equals("")){
            return 0.0;
        } else {
            return Double.parseDouble(arg);
        }
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPictureDressingRoom() {
        return pictureDressingRoom;
    }

    public void setPictureDressingRoom(String pictureDressingRoom) {
        this.pictureDressingRoom = pictureDressingRoom;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

    public double getChest() {
        return chest;
    }

    public void setChest(double chest) {
        this.chest = chest;
    }

    public double getCollar() {
        return collar;
    }

    public void setCollar(double collar) {
        this.collar = collar;
    }

    public double getBust() {
        return bust;
    }

    public void setBust(double bust) {
        this.bust = bust;
    }

    public double getWaist() {
        return waist;
    }

    public void setWaist(double waist) {
        this.waist = waist;
    }

    public double getHips() {
        return hips;
    }

    public void setHips(double hips) {
        this.hips = hips;
    }

    public double getSleeve() {
        return sleeve;
    }

    public void setSleeve(double sleeve) {
        this.sleeve = sleeve;
    }

    public double getInseam() {
        return inseam;
    }

    public void setInseam(double inseam) {
        this.inseam = inseam;
    }

    public double getFeet() {
        return feet;
    }

    public void setFeet(double feet) {
        this.feet = feet;
    }

    public int getUnitLength() {
        return unitLength;
    }

    public void setUnitLength(int unitLength) {
        this.unitLength = unitLength;
    }

    public int getUnitWeight() {
        return unitWeight;
    }

    public void setUnitWeight(int unitWeight) {
        this.unitWeight = unitWeight;
    }

    public double getPointure() {
        return pointure;
    }

    public void setPointure(double pointure) {
        this.pointure = pointure;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void toggle() {
        this.isChecked = !this.isChecked;
    }

    public boolean getChecked() {
        return this.isChecked;
    }

}