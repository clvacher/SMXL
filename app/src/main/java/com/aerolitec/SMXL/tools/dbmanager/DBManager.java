package com.aerolitec.SMXL.tools.dbmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.aerolitec.SMXL.tools.services.SQLiteSMXL;

/**
 * Created by Jerome on 28/04/2015.
 */
public abstract class DBManager {


    protected SQLiteSMXL maBaseSQLite; // notre gestionnaire du fichier SQLite
    protected SQLiteDatabase db;
    protected Context context;

    private int openedConnections = 0;

    // Constructeur
    public DBManager(Context context)
    {
        maBaseSQLite = new SQLiteSMXL(context);
        this.context = context;
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        openedConnections++;
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD si personne n'est encore connecté
        openedConnections--;
        if(openedConnections == 0){
            db.close();
        }
    }

    protected int convertToInt(String arg){
        if (arg.equals("")){
            return 0;
        } else {
            return Integer.parseInt(arg);
        }
    }

    protected double convertToDouble(String arg){
        if (arg.equals("")){
            return 0.0;
        } else {
            return Double.parseDouble(arg);
        }
    }


}
