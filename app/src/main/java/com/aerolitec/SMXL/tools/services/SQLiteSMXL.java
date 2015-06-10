package com.aerolitec.SMXL.tools.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SQLiteSMXL extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "SMXL_DATABASE.sqlite";
    public static final int DATABASE_VERSION = 17;
    private File DATABASE_FILE;
    private String DATABASE_DIR;
    private static final String FILE_CHARSET = "UTF-8";
    private Context context;


    public SQLiteSMXL(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;


        String filesDir = context.getFilesDir().getPath(); // /data/data/com.package.nom/files/
        File dirDatabases = new File(context.getFilesDir().getParentFile().getAbsolutePath(), "databases");
        DATABASE_DIR = dirDatabases.getAbsolutePath(); // /data/data/com.package.nom/databases/

        // Si la bdd n'existe pas dans le dossier de l'app
        if (!checkdatabase()) {
            // copy db de 'assets' vers DATABASE_DIR
            copydatabase();
        }

    }


    private boolean checkdatabase() {
        // retourne true/false si la bdd existe dans le dossier de l'app
        DATABASE_FILE = new File(DATABASE_DIR, DATABASE_NAME);
        return DATABASE_FILE.exists();
    }

    private void copydatabase() {

        //final File outFileName = new File(DATABASE_DIR, DATABASE_NAME);

        DATABASE_FILE = new File(DATABASE_DIR, DATABASE_NAME);

        InputStream myInput;
        try {
            // Ouvre la bdd de 'assets' en lecture
            myInput = context.getAssets().open(DATABASE_NAME);

            // dossier de destination
            File pathFile = new File(DATABASE_DIR);
            if (!pathFile.exists()) {
                if (!pathFile.mkdirs()) {
                    Toast.makeText(context, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Ouverture en écriture du fichier bdd de destination
            OutputStream myOutput = new FileOutputStream(DATABASE_FILE);

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
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DATABASE_FILE.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion(DATABASE_VERSION);
            checkdb.close();
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
                b.setHeight(c.getString(6));
                if (!b.getHeight().equals("0")) {
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




}
