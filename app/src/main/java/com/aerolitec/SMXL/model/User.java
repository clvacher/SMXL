package com.aerolitec.SMXL.model;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.io.Serializable;
import java.util.ArrayList;


public class User extends BaseObjects implements Serializable {

    protected int id_user;
    protected String nickname;
    protected String firstname;
    protected String lastname;
    protected String birthday;
    protected String sexe;
    protected String avatar;
    protected String description;
    protected double size;
    protected double weight;
    protected double imc; //NON PRESENT DANS LA BDD
    protected double chest;
    protected double collar;
    protected double bust;
    protected double waist;
    protected double hips;
    protected double sleeve;
    protected double inseam;
    protected double feet;
    protected int unitLength;
    protected int unitWeight;
    protected double pointure;

    private ArrayList<Brand> brands = new ArrayList<>();


    public User() {
    }

    /**
     * Create a new User
     *
     * @param id_user
     * @param nickname
     * @param firstname
     * @param lastname
     * @param birthday
     * @param sexe                [M/W/B/G] = Man, Woman, Boy, Girl
     * @param avatar              (File name)
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
    public User(int id_user, String nickname, String firstname, String lastname, String birthday, String sexe,
                String avatar, String description, double size, double weight, double bust,
                double chest, double collar, double waist, double hips, double sleeve, double inseam,
                double feet, int unitL, int unitW, double pointure) {

        this.id_user = id_user;
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sexe = sexe;
        this.avatar = avatar;
        this.description = description;
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

    public User(String id_user, String nickname, String firstname, String lastname, String birthday, String sexe,
                String avatar, String description, String size, String weight, String bust,
                String chest, String collar, String waist, String hips, String sleeve, String inseam,
                String feet, String unitL, String unitW, String pointure) {


        this.id_user = convertToInt(id_user);
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sexe = sexe;
        this.avatar = avatar;
        this.description = description;
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


    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
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

    public String getDescription() {
        return this.description;
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

    public ArrayList<Brand> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<Brand> brands) {
        this.brands = brands;
    }

    public void addBrands (ArrayList<Brand> brands){
        this.brands.addAll(brands);
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
                "'" + id_user + "'" +
                        ",'" + nickname + '\'' +
                        ", '" + firstname + '\'' +
                        ", '" + lastname + '\'' +
                        ", '" + birthday + '\'' +
                        ", '" + sexe + '\'' +
                        ", '" + avatar + '\'' +
                        ", '" + description + '\'' +
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
                        ", " + unitWeight +
                        ", " + pointure +
                        ", " + brands;
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

    public ArrayList<String> getUserSizes (){
        ArrayList<String> mesures = new ArrayList<>();
        int userId = getId_user();
        String size = String.valueOf(getSize());
        String weight = String.valueOf(getWeight());
        String bust = String.valueOf(getBust());
        String chest = String.valueOf(getChest());
        String collar = String.valueOf(getCollar());
        String waist = String.valueOf(getWaist());
        String hips = String.valueOf(getHips());
        String sleeve = String.valueOf(getSleeve());
        String inseam = String.valueOf(getInseam());
        String feet = String.valueOf(getFeet());
        String unitLength = String.valueOf(getUnitLength());
        String unitWeight = String.valueOf(getUnitWeight());
        String pointure = String.valueOf(getPointure());
        mesures.add(size);
        mesures.add(weight);
        mesures.add(bust);
        mesures.add(chest);
        mesures.add(collar);
        mesures.add(waist);
        mesures.add(hips);
        mesures.add(sleeve);
        mesures.add(inseam);
        mesures.add(feet);
        mesures.add(unitLength);
        mesures.add(unitWeight);
        mesures.add(pointure);

        return mesures;
    }


    public ArrayList<Integer> getIndexMeasureNotNull(){

        ArrayList<Integer> nbElement = new ArrayList<Integer>();
        ArrayList<String> sizes;
        sizes = this.getUserSizes();
        for (int i = 0 ; i < sizes.size() ; i++){
            if(!(sizes.get(i).equals("0.0")) && !(sizes.get(i).equals("0")) && !(sizes.get(i).equals("")) ){
                nbElement.add(i);
            }
        }

        return nbElement;
    }
}
