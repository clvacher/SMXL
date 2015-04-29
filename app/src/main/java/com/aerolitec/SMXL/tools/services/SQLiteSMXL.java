package com.aerolitec.SMXL.tools.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class SQLiteSMXL extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "SMXL_DATABASE.sql";
    public static final int DATABASE_VERSION = 1;
    private String DATABASE_PATH;
    private static final String FILE_CHARSET = "UTF-8";
    private Context context;


    public SQLiteSMXL(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;


        String filesDir = context.getFilesDir().getPath(); // /data/data/com.package.nom/files/
        DATABASE_PATH = filesDir.substring(0, filesDir.lastIndexOf("/")) + "/databases/"; // /data/data/com.package.nom/databases/

        // Si la bdd n'existe pas dans le dossier de l'app
        if (!checkdatabase()) {
            // copy db de 'assets' vers DATABASE_PATH
            copydatabase();
        }

    }


    private boolean checkdatabase() {
        // retourne true/false si la bdd existe dans le dossier de l'app
        File dbfile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbfile.exists();
    }

    private void copydatabase() {

        final String outFileName = DATABASE_PATH + DATABASE_NAME;

        InputStream myInput;
        try {
            // Ouvre la bdd de 'assets' en lecture
            myInput = context.getAssets().open(DATABASE_NAME);

            // dossier de destination
            File pathFile = new File(DATABASE_PATH);
            if (!pathFile.exists()) {
                if (!pathFile.mkdirs()) {
                    Toast.makeText(context, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Ouverture en écriture du fichier bdd de destination
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfert de inputfile vers outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Fermeture
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Erreur : copydatabase()", Toast.LENGTH_SHORT).show();
        }

        // on greffe le numéro de version
        try {
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion(DATABASE_VERSION);
        } catch (SQLiteException e) {
            // bdd n'existe pas
        }
    }


    public void onCreate(SQLiteDatabase db) {}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion){
            Log.d("debug db version", "onUpgrade() : oldVersion=" + oldVersion + ",newVersion=" + newVersion);
            context.deleteDatabase(DATABASE_NAME);
            copydatabase();
        }
    }


        public User createUser(String firstName, String lastName, String birthday,
                               String sexe, String avatar, String description){
            SQLiteDatabase db = this.getWritableDatabase();
            User user;
            String nickname = firstName+lastName+birthday;
            try {
                ContentValues values = new ContentValues(19);
                values.put("nickname", nickname);
                values.put("firstname", firstName);
                values.put("lastname", lastName);
                values.put("birthday", birthday);
                values.put("sexe", sexe);
                values.put("avatar", avatar);
                values.put("description", description);
                values.put("size", 0);
                values.put("weight", 0);
                values.put("chest", 0);
                values.put("collar", 0);
                values.put("bust", 0);
                values.put("waist", 0);
                values.put("hips", 0);
                values.put("sleeve", 0);
                values.put("inseam", 0);
                values.put("feet", 0);
                values.put("unitL", 0);
                values.put("unitW", 0);
                values.put("pointure", 0);
                db.insert("user", "userid", values);
            }
            catch (SQLiteException e) {
                Log.d(Constants.TAG,"Error creating user : " + e.getMessage() );
            }
            db.close();
            user = getUserByNickname(nickname);
            return user;
    }

    /*
    public User importUser(String nickname, String firstName, String lastName, String birthday,
                           String sexe, String avatar,  double size, double weight, double imc,
                           double chest, double collar, double bust, double waist, double hips,
                           double sleeve, double inseam, double feet, int unitLength, int unitWeight, double pointure){
        SQLiteDatabase db = this.getWritableDatabase();
        User user;
        try {
            ContentValues values = new ContentValues(18);
            values.put("nickname", nickname);
            values.put("firstname", firstName);
            values.put("lastname", lastName);
            values.put("birthday", birthday);
            values.put("sexe", sexe);
            values.put("avatar", avatar);
            values.put("size", size);
            values.put("weight", weight);
            values.put("imc", imc);
            values.put("chest", chest);
            values.put("collar", collar);
            values.put("bust", bust);
            values.put("waist", waist);
            values.put("hips", hips);
            values.put("sleeve", sleeve);
            values.put("inseam", inseam);
            values.put("feet", feet);
            values.put("unitL", unitLength);
            values.put("unitW", unitWeight);
            values.put("pointure", pointure);
            db.insert("user", "userid", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"Error creating user : " + e.getMessage() );
        }
        db.close();
        user = getUserByNickname(nickname);
        return user;
    }
    */

    public User importUser(String nickname, String firstName, String lastName, String birthday, String sexe,
                String avatar, String description, String size, String weight, String bust,
                String chest, String collar, String waist, String hips, String sleeve, String inseam,
                String feet, String unitL, String unitW, String pointure){


        SQLiteDatabase db = this.getWritableDatabase();
        User user;
        try {
            ContentValues values = new ContentValues(25);
            values.put("nickname", nickname);
            values.put("firstname", firstName);
            values.put("lastname", lastName);
            values.put("birthday", birthday);
            values.put("sexe", sexe);
            values.put("avatar", avatar);
            values.put("description", description);
            values.put("size", size);
            values.put("weight", weight);
            //values.put("imc", imc);
            values.put("chest", chest);
            values.put("collar", collar);
            values.put("bust", bust);
            values.put("waist", waist);
            values.put("hips", hips);
            values.put("sleeve", sleeve);
            values.put("inseam", inseam);
            values.put("feet", feet);
            values.put("unitL", unitL);
            values.put("unitW", unitW);
            values.put("pointure", pointure);
            db.insert("user", "userid", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"Error creating user : " + e.getMessage() );
        }
        db.close();
        user = getUserByNickname(nickname);
        return user;


        /*
        this.userid = convertToInt(userid);
        this.nickname = nickname;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.sexe = sexe;
        this.avatar = avatar;
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
        */
    }

    public ArrayList<String> getUserSizes(User user){

        ArrayList<String> mesures = new ArrayList<>();
        int userId = user.getId_user();
        String size = String.valueOf(user.getSize());
        String weight = String.valueOf(user.getWeight());
        String bust = String.valueOf(user.getBust());
        String chest = String.valueOf(user.getChest());
        String collar = String.valueOf(user.getCollar());
        String waist = String.valueOf(user.getWaist());
        String hips = String.valueOf(user.getHips());
        String sleeve = String.valueOf(user.getSleeve());
        String inseam = String.valueOf(user.getInseam());
        String feet = String.valueOf(user.getFeet());
        String unitLength = String.valueOf(user.getUnitLength());
        String unitWeight = String.valueOf(user.getUnitWeight());
        String pointure = String.valueOf(user.getPointure());

        //TODO test si != null :

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


    public User getUserByNickname(String nickname){
        User user = new User();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user WHERE nickname = ?",new String[] {nickname} );
            boolean eof = c.moveToFirst();
            user.setId_user(c.getInt(0));
            user.setNickname(c.getString(1));
            user.setFirstname(c.getString(2));
            user.setLastname(c.getString(3));
            user.setBirthday(c.getString(4));
            user.setSexe(c.getString(5));
            user.setAvatar(c.getString(6));
            user.setDescription(c.getString(7));
            user.setSize(c.getDouble(8));
            user.setWeight(c.getDouble(9));
            user.setChest(c.getDouble(10));
            user.setCollar(c.getDouble(11));
            user.setBust(c.getDouble(12));
            user.setWaist(c.getDouble(13));
            user.setHips(c.getDouble(14));
            user.setSleeve(c.getDouble(15));
            user.setInseam(c.getDouble(16));
            user.setFeet(c.getDouble(17));
            user.setUnitLength(c.getInt(18));
            user.setUnitWeight(c.getInt(19));
            user.setPointure(c.getDouble(20));

            user.setBrands(getAllUserBrands(user));
            c.close();
            }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user : " + e.getMessage());
            user = null;
            }
        db.close();
        return user;
        }

    public User getUserById(int userid){
        SQLiteDatabase db = this.getReadableDatabase();
        User user = new User();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user WHERE userid = ?",new String[] {String.valueOf(userid)} );
            boolean eof = c.moveToFirst();
            user.setId_user(c.getInt(0));
            user.setNickname(c.getString(1));
            user.setFirstname(c.getString(2));
            user.setLastname(c.getString(3));
            user.setBirthday(c.getString(4));
            user.setSexe(c.getString(5));
            user.setAvatar(c.getString(6));
            user.setDescription(c.getString(7));
            user.setSize(c.getDouble(8));
            user.setWeight(c.getDouble(9));
            user.setChest(c.getDouble(10));
            user.setCollar(c.getDouble(11));
            user.setBust(c.getDouble(12));
            user.setWaist(c.getDouble(13));
            user.setHips(c.getDouble(14));
            user.setSleeve(c.getDouble(15));
            user.setInseam(c.getDouble(16));
            user.setFeet(c.getDouble(17));
            user.setUnitLength(c.getInt(18));
            user.setUnitWeight(c.getInt(19));
            user.setPointure(c.getInt(20));

            user.setBrands(getAllUserBrands(user));
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user " + userid + " : " + e.getMessage());
            user = null;
        }
        db.close();
        return user;
    }

    public Brand getBrandById(int brandid){
        SQLiteDatabase db = this.getReadableDatabase();
        Brand brand = new Brand();
        //Log.d("brandid paramgetBrandId", Integer.toString(brandid));
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM brand WHERE id_brand = ?",new String[] {String.valueOf(brandid)} );
            boolean eof = c.moveToFirst();
            brand.setId(c.getInt(0));
            brand.setBrandName(c.getString(1));

            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting brand " + brandid + " : " + e.getMessage());
            brand = null;
        }
        db.close();
        return brand;
    }



    public boolean updateUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            ContentValues values = new ContentValues(17);
            values.put("nickname", user.getNickname());
            values.put("firstname", user.getFirstname());
            values.put("lastname", user.getLastname());
            values.put("birthday", user.getBirthday());
            values.put("sexe", user.getSexe());
            values.put("avatar", user.getAvatar());
            values.put("description", user.getDescription());
            values.put("size", user.getSize());
            values.put("weight", user.getWeight());
            values.put("chest", user.getChest());
            values.put("collar", user.getCollar());
            values.put("bust", user.getBust());
            values.put("waist", user.getWaist());
            values.put("hips", user.getHips());
            values.put("sleeve", user.getSleeve());
            values.put("inseam", user.getInseam());
            values.put("feet", user.getFeet());
            values.put("unitL", user.getUnitLength());
            values.put("unitW", user.getUnitWeight());
            values.put("pointure", user.getPointure());
            db.update("user", values, "userid = ?", new String[]{String.valueOf(user.getId_user())});
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Update user with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<User> getAllUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<User>();
        Cursor c;
        try {
            c = db.rawQuery("SELECT * FROM user ",null );
            boolean eof = c.moveToFirst();
            while (eof) {
                User user = new User();
                user.setId_user(c.getInt(0));
                user.setNickname(c.getString(1));
                user.setFirstname(c.getString(2));
                user.setLastname(c.getString(3));
                user.setBirthday(c.getString(4));
                user.setSexe(c.getString(5));
                user.setAvatar(c.getString(6));
                user.setDescription(c.getString(7));
                user.setSize(c.getDouble(8));
                user.setWeight(c.getDouble(9));
                user.setChest(c.getDouble(10));
                user.setCollar(c.getDouble(11));
                user.setBust(c.getDouble(12));
                user.setWaist(c.getDouble(13));
                user.setHips(c.getDouble(14));
                user.setSleeve(c.getDouble(15));
                user.setInseam(c.getDouble(16));
                user.setFeet(c.getDouble(17));
                user.setUnitLength(c.getInt(18));
                user.setUnitWeight(c.getInt(19));
                user.setPointure(c.getDouble(20));

                user.setBrands(getAllUserBrands(user));

                users.add(user);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user : " + e.getMessage());
            users = null;
        }
        db.close();
        return users;
    }

    // Garments

    public ArrayList<GarmentType> getAllGarments(String sexe){
        ArrayList<GarmentType> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;

            c = db.rawQuery("SELECT * FROM clothes_type where sexe = ? ", new String[]{sexe});

            boolean eof = c.moveToFirst();
            while (eof) {
                GarmentType g = new GarmentType();
                g.setId(c.getInt(0));
                g.setType(c.getString(1));
                g.setSex(c.getString(2));
                garments.add(g);
                eof = c.moveToNext();
            }
            c.close();
            db.close();
            return garments;
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting garments : " + e.getMessage());
            garments = null;
        }
        db.close();
        return null;
    }


    public ArrayList<BrandsSizeGuide> getAllGarmentsByBrand(String brand){
        ArrayList<BrandsSizeGuide> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand_size_guide where brand = ? AND size_suite = ?", new String[]{brand,"0"});
            boolean eof = c.moveToFirst();
            while (eof) {
                BrandsSizeGuide b = new BrandsSizeGuide();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                b.setType(c.getString(2));
                b.setDim1(c.getInt(3));
                b.setDim2(c.getInt(4));
                b.setDim3(c.getInt(5));
                b.setSize(c.getString(6));
                garments.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting garments from brand : " + e.getMessage());
            garments = null;
        }
        db.close();
        return garments;
    }

    public ArrayList<BrandsSizeGuide> getAllBrandsByGarment(String garment){
        ArrayList<BrandsSizeGuide> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand_size_guide where clothesType = ? AND size_suite = ?", new String[]{garment,"0"});
            boolean eof = c.moveToFirst();
            while (eof) {
                BrandsSizeGuide b = new BrandsSizeGuide();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                b.setType(c.getString(2));
                b.setDim1(c.getInt(3));
                b.setDim2(c.getInt(4));
                b.setDim3(c.getInt(5));
                b.setSize(c.getString(6));
                brands.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting garments from brand : " + e.getMessage());
            brands = null;
        }
        db.close();
        return brands;
    }


    public ArrayList<BrandsSizeGuide> getBrandsSizes(String garment, String brand){
        ArrayList<BrandsSizeGuide> sizes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand_size_guide where brand = ? AND clothesType = ?", new String[]{brand,garment});
            boolean eof = c.moveToFirst();
            while (eof) {
                BrandsSizeGuide b = new BrandsSizeGuide();
                b.setId(c.getInt(0));
                b.setBrand(c.getString(1));
                b.setType(c.getString(2));
                b.setDim1(c.getInt(3));
                b.setDim2(c.getInt(4));
                b.setDim3(c.getInt(5));
                b.setSize(c.getString(6));
                if (!b.getSize().equals("0")) {
                    sizes.add(b);
                }
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting sizes from brand & garment: " + e.getMessage());
            sizes = null;
        }
        db.close();
        return sizes;
    }

    // Brand

    public ArrayList<Brand> getAllBrands(){
        ArrayList<Brand> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM brand", null);
            boolean eof = c.moveToFirst();
            while (eof) {
                Brand b = new Brand();
                b.setId(c.getInt(0));
                b.setBrandName(c.getString(1));
                brands.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting brands : " + e.getMessage());
            brands = null;
        }
        db.close();
        return brands;
    }

    // UserGarments

    public ArrayList<UserClothes> getAllUserGarments(User user){
        ArrayList<UserClothes> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Gson gson = new Gson();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM user_clothes WHERE userid = ?", new String[]{String.valueOf(user.getId_user())});
            boolean eof = c.moveToFirst();
            while (eof) {
                UserClothes uc = new UserClothes();
                uc.setClotheid(c.getInt(0));
                uc.setUserid(c.getInt(1));
                uc.setType(c.getString(2));
                uc.setBrand(c.getString(3));
                uc.setCountry(c.getString(4));
                uc.setSize(c.getString(5));
                uc.setComment(c.getString(6));
                ArrayList<TabSizes> tabs = gson.fromJson(c.getString(7), new TypeToken<ArrayList<TabSizes>>() {
                }.getType());
                uc.setSizes(tabs);
                uc.setCategory(gson.fromJson(c.getString(8), CategoryGarment.class));
                garments.add(uc);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user garments : " + e.getMessage());
            garments = null;
        }
        db.close();
        return garments;
    }

    // UserBrands

    public ArrayList<Brand> getAllUserBrands(User user){
        ArrayList<Brand> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM User_Brand WHERE id_user = ?", new String[]{String.valueOf(user.getId_user())});
            boolean eof = c.moveToFirst();
            while (eof) {
                //Brand b = getBrandById(c.getInt(2));
                Brand b = getBrandById(c.getInt(1));

                brands.add(b);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG,"error getting user brand : " + e.getMessage());
            brands = null;
        }
        db.close();
        //Log.d("return db user selected", user.toString());
        //Log.d("return db user brands", brands.toString());
        return brands;
    }


    // getSizeConvert
    public ArrayList<SizeConvert> getConvertSizesByGarment(String garment){
        ArrayList<SizeConvert> sizes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM size_convert WHERE UPPER(clothesType) LIKE UPPER(?)", new String[]{garment});
            for(boolean eof = c.moveToFirst(); eof; eof=c.moveToNext())
            {
                SizeConvert s = new SizeConvert();
                s.setId(c.getInt(0));
                s.setType(c.getString(1));
                s.setSex(c.getString(2));
                s.setValueUS(c.getString(3));
                s.setValueUK(c.getString(4));
                s.setValueUE(c.getString(5));
                s.setValueFR(c.getString(6));
                s.setValueITA(c.getString(7));
                s.setValueJAP(c.getString(8));
                s.setValueSMXL(c.getString(9));
                //Log.d("type", c.getString(1).toString()+"");
                //Log.d("smxl", c.getString(8).toString()+"");
                //Log.d("baaaah", s.getType());
                sizes.add(s);
            }
            c.close();
        }
        catch (SQLiteException e){
            Log.d(Constants.TAG,"ERROR Getting Size_Convert : " + e.getMessage());
            sizes = null;
        }
        db.close();
        return sizes;
    }

    public ArrayList<SizeConvert> getConvertSizesByGarment(String garment, String sex)
    {
        ArrayList sizes = new ArrayList();
        if (sex.length() > 1) {}
        for (String str = sex.substring(0, 1);; str = sex)
        {
            SQLiteDatabase db = this.getReadableDatabase();
            try
            {
                Cursor c;
                c = db.rawQuery("SELECT * FROM size_convert WHERE UPPER(clothesType) LIKE UPPER(?) AND UPPER(sex) LIKE UPPER(?)", new String[] { garment, str });
                for (boolean bool = c.moveToFirst(); bool; bool = c.moveToNext())
                {
                    SizeConvert s = new SizeConvert();
                    s.setId(c.getInt(0));
                    s.setType(c.getString(1));
                    s.setSex(c.getString(2));
                    s.setValueUS(c.getString(3));
                    s.setValueUK(c.getString(4));
                    s.setValueUE(c.getString(5));
                    s.setValueFR(c.getString(6));
                    s.setValueITA(c.getString(7));
                    s.setValueJAP(c.getString(8));
                    s.setValueSMXL(c.getString(9));
                    sizes.add(s);
                }
                c.close();
            }
            catch (SQLiteException localSQLiteException)
            {
                for (;;)
                {
                    Log.d("SMXL", "ERROR Getting Size_Convert : " + localSQLiteException.getMessage());
                    sizes = null;
                }
            }
            db.close();
            return sizes;
        }
    }



    public ArrayList<GarmentType> getGarmentsSizeGuide(){
        ArrayList<GarmentType> garments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            Cursor c;
            c = db.rawQuery("SELECT id, clothesType, sex FROM size_convert GROUP BY clothesType, sex", new String[]{});
            boolean eof = c.moveToFirst();
            while (eof) {
                GarmentType gt = new GarmentType();
                gt.setId(c.getInt(0));
                if(c.getString(2).contentEquals(""))
                {
                    gt.setType(c.getString(1));
                }
                else{
                    gt.setType(c.getString(1));
                }
                gt.setSex(c.getString(2));

                garments.add(gt);
                eof = c.moveToNext();
            }
            c.close();
        }
        catch (SQLiteException e){
            Log.d(Constants.TAG,"ERROR Getting Garments for size guide : " + e.getMessage());
            garments = null;
        }
        db.close();
        return garments;
    }

    public boolean AddUserGarments(UserClothes uc){
        Gson gson = new Gson();
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues(8);
        values.put("userid", uc.getUserid() );
        values.put("clothesType", uc.getType());
        values.put("brand", uc.getBrand());
        values.put("country", uc.getCountry());
        values.put("size", uc.getSize());
        values.put("comment", uc.getComment());
        values.put("sizes", gson.toJson(uc.getSizes()));
        values.put("category", gson.toJson(uc.getCategory()));
        /*
        if(uc.getComment() != "") {
            values.put("comment", uc.getComment());
        } else {
            values.put("comment", " ");
        }
        */
        try {
            db.insert("user_clothes", "id", values);
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Error adding user garment : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean addUserBrand(User user, Brand brand){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values = new ContentValues(2);
        values.put("id_user", user.getId_user());
        values.put("id_brand", brand.getId());

        try {
            //db.insert("User_Brand", "id_user_brand", values);
            db.insert("User_Brand", null, values);

        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Error adding user brand : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean removeUserBrand(User user, Brand brandToRemove){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("DELETE FROM User_Brand WHERE id_user="+user.getId_user()+" AND id_brand="+brandToRemove.getId()+";");
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "delete brand user with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean removeAllUserBrand(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("DELETE FROM User_Brand WHERE id_user="+user.getId_user()+";");
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "delete all brand user with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean updateUserGarment(UserClothes clothes){
        Gson gson = new Gson();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            ContentValues values = new ContentValues(8);
            values.put("userid", clothes.getUserid());
            values.put("clothesType", clothes.getType());
            values.put("brand", clothes.getBrand());
            values.put("country", clothes.getCountry());
            values.put("size", clothes.getSize());
            values.put("comment", clothes.getComment());
            values.put("sizes", gson.toJson(clothes.getSizes()));
            values.put("category", gson.toJson(clothes.getCategory()));
            db.update("user_clothes", values, "id = ?", new String[]{String.valueOf(clothes.getClotheid())});
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "Update clothes with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public boolean removeUserGarment(UserClothes clothes){
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.delete("user_clothes", "id = ?", new String[]{String.valueOf(clothes.getClotheid())});
        }
        catch (SQLiteException e) {
            Log.d(Constants.TAG, "delete clothes with error : " + e.getMessage());
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public ArrayList<Integer> getIndexMeasureNotNull(User user){

        ArrayList<Integer> nbElement = new ArrayList<>();
        ArrayList<String> sizes;
        sizes = getUserSizes(user);
        for (int i = 0 ; i < sizes.size() ; i++){
            if(!(sizes.get(i).equals("0.0")) && !(sizes.get(i).equals("0")) && !(sizes.get(i).equals("")) ){
                    nbElement.add(i);
            }
        }

        return nbElement;
    }

    public boolean alreadyExistUserBrand(User user, Brand b) {
        SQLiteDatabase db = this.getReadableDatabase();
        boolean found = false;
        try {
            Cursor c;
            c = db.rawQuery("SELECT * FROM User_Brand WHERE id_user="+user.getId_user()+" AND id_brand="+b.getId()+";", new String[]{});
            boolean eof = c.moveToFirst();
            found = eof;
            c.close();
        }
        catch (SQLiteException e){
            Log.d(Constants.TAG,"ERROR alreadyExistUserBrand db : " + e.getMessage());
        }
        db.close();
        return found;
    }
}
