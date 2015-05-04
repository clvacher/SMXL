package com.aerolitec.SMXL.tools.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandsSizeGuide;
import com.aerolitec.SMXL.model.CategoryGarment;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.SizeConvert;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.ui.SMXL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


public class SQLiteSMXL extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "SMXL_DATABASE.sqlite";
    public static final int DATABASE_VERSION = 8;
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


    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion){
            Log.d("debug db version", "onUpgrade() : oldVersion=" + oldVersion + ",newVersion=" + newVersion);
            context.deleteDatabase(DATABASE_NAME);
            copydatabase();
        }
    }

//#########################################################################################################################################################################################
//#########################################################################################################################################################################################
    // Garments


/*
//A VOIR SI UTILE OU PAS
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
*/



    public ArrayList<Integer> getIndexMeasureNotNull(User user){

        ArrayList<Integer> nbElement = new ArrayList<Integer>();
        ArrayList<String> sizes;
        sizes = user.getUserSizes();
        for (int i = 0 ; i < sizes.size() ; i++){
            if(!(sizes.get(i).equals("0.0")) && !(sizes.get(i).equals("0")) && !(sizes.get(i).equals("")) ){
                    nbElement.add(i);
            }
        }

        return nbElement;
    }
}
