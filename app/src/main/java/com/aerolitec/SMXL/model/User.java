package com.aerolitec.SMXL.model;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.io.Serializable;

/**
 * Created by stephaneL on 20/03/14.
 */
public class User extends BaseObjects implements Serializable {

    private int userid;
    private String nickname;
    private String firstname;
    private String lastname;
    private String birthday;
    private String sexe;
    private String avatar;
    private String pictureDressingRoom;
    private String description;
    private int favoriteColor1;
    private int favoriteColor2;
    private int favoriteColor3;
    private int favoriteColor4;
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

    public User() {
    }

    /**
     * Create a new User
     *
     * @param userid
     * @param nickname
     * @param firstname
     * @param lastname
     * @param birthday
     * @param sexe                [M/W/B/G] = Man, Woman, Boy, Girl
     * @param avatar              (File name)
     * @param pictureDressingRoom (File name)
     * @param description
     * @param size                (cm)
     * @param weight              (cm)
     * @param bust                (cm)
     * @param chest               (cm)
     * @param collar              (cm)
     * @param bust                (cm)
     * @param waist               (cm)
     * @param hips                (cm)
     * @param sleeve              (cm)
     * @param inseam              (cm)
     * @param feet                (cm)
     * @param pointure            (taille)
     */
    public User(int userid, String nickname, String firstname, String lastname, String birthday, String sexe,
                String avatar, String pictureDressingRoom, String description, int favoriteColor1, int favoriteColor2,
                int favoriteColor3, int favoriteColor4, double size, double weight, double bust,
                double chest, double collar, double waist, double hips, double sleeve, double inseam,
                double feet, int unitL, int unitW, double pointure) {

        this.userid = userid;
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sexe = sexe;
        this.avatar = avatar;
        this.pictureDressingRoom = pictureDressingRoom;
        this.description = description;
        this.favoriteColor1 = favoriteColor1;
        this.favoriteColor2 = favoriteColor2;
        this.favoriteColor3 = favoriteColor3;
        this.favoriteColor4 = favoriteColor4;
        this.size = size;
        this.weight = weight;
        this.bust = bust;
        this.chest = chest;
        this.collar = collar;
        this.waist = waist;
        this.hips = hips;
        this.sleeve = sleeve;
        this.inseam = inseam;
        this.feet = feet;
        this.unitLength = unitL;
        this.unitWeight = unitW;
        this.pointure = pointure;
    }

    public User(String userid, String nickname, String firstname, String lastname, String birthday, String sexe,
                String avatar, String pictureDressingRoom, String description, String favoriteColor1, String favoriteColor2,
                String favoriteColor3, String favoriteColor4, String size, String weight, String bust,
                String chest, String collar, String waist, String hips, String sleeve, String inseam,
                String feet, String unitL, String unitW, String pointure) {


        this.userid = convertToInt(userid);
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sexe = sexe;
        this.avatar = avatar;
        this.pictureDressingRoom = pictureDressingRoom;
        this.description = description;
        this.favoriteColor1 = convertToInt(favoriteColor1);
        this.favoriteColor2 = convertToInt(favoriteColor2);
        this.favoriteColor3 = convertToInt(favoriteColor3);
        this.favoriteColor4 = convertToInt(favoriteColor4);
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

    private int convertToInt(String arg) {
        if (arg.equals("")) {
            return 0;
        } else {
            return Integer.parseInt(arg);
        }
    }

    private double convertToDouble(String arg) {
        if (arg.equals("")) {
            return 0.0;
        } else {
            return Double.parseDouble(arg);
        }
    }

    public int getFavoriteColor1() {
        return favoriteColor1;
    }

    public void setFavoriteColor1(int favoriteColor1) {
        this.favoriteColor1 = favoriteColor1;
    }

    public int getFavoriteColor2() {
        return favoriteColor2;
    }

    public void setFavoriteColor2(int favoriteColor2) {
        this.favoriteColor2 = favoriteColor2;
    }

    public int getFavoriteColor3() {
        return favoriteColor3;
    }

    public void setFavoriteColor3(int favoriteColor3) {
        this.favoriteColor3 = favoriteColor3;
    }

    public int getFavoriteColor4() {
        return favoriteColor4;
    }

    public void setFavoriteColor4(int favoriteColor4) {
        this.favoriteColor4 = favoriteColor4;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public double getImc() {
        if (this.size > 0 && this.weight > 0) {
            double imcD = weight / ((size / 100) * (size / 100));
            imc = (Math.rint(imcD * 100)) / 100;
        } else {
            imc = 0;
        }
        return imc;
    }

    public double getPointure() {
        return pointure;
    }

    public void setPointure(double pointure) {
        this.pointure = pointure;
    }

    @Override
    public String toString() {
        return
                "'" + userid + "'" +
                        ",'" + nickname + '\'' +
                        ", '" + firstname + '\'' +
                        ", '" + lastname + '\'' +
                        ", '" + birthday + '\'' +
                        ", '" + sexe + '\'' +
                        ", '" + avatar + '\'' +
                        ", '" + pictureDressingRoom + '\'' +
                        ", " + size +
                        ", " + weight +
                        ", " + imc +
                        ", " + bust +
                        ", " + chest +
                        ", " + collar +
                        ", " + bust +
                        ", " + waist +
                        ", " + hips +
                        ", " + sleeve +
                        ", " + inseam +
                        ", " + feet +
                        ", " + unitLength +
                        ", " + unitWeight;
    }

    /**
     * Uses Joda Library
     *
     * @param birthday (String jj-mm-aaaa)
     * @return age (int)
     */
    public int getAge(String birthday) {
        int age = 0;
        try {
            int year = Integer.valueOf(birthday.substring(6, 10));
            int month = Integer.valueOf(birthday.substring(3, 5));
            int day = Integer.valueOf(birthday.substring(0, 2));
            LocalDate birthDate = new LocalDate(year, month, day);
            LocalDate now = new LocalDate();
            Years years = Years.yearsBetween(birthDate, now);
            age = years.getYears();
        } catch (Exception e) {
        }
        return age;
    }
}
