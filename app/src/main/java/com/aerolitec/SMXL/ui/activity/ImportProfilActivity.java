package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.ui.SMXL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImportProfilActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_profil);
        // Get intent, action and MIME type

        String action = null, type = null;
        File profil = null;

        if (getIntent().getExtras() != null) {
            profil = (File) getIntent().getExtras().get("PROFIL");
        }

        if (getIntent().getType() != null) {
            Intent intent = getIntent();
            action = intent.getAction();
            type = intent.getType();
            Uri UriFileProfil = intent.getData();
            profil = new File(UriFileProfil.getPath());
        }


        if (profil != null) {
            ExtractCSV(profil);
            Intent accueilIntent = new Intent(this, ProfilActivity.class);
            startActivity(accueilIntent);

        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    private int getLastUserID() {

        ArrayList<User> users = SMXL.get().getDataBase().getAllUser();
        int sizeList = users.size();
        return sizeList;
    }

    private void ExtractCSV(File profil) {
        try {
            FileReader file = new FileReader(profil);
            BufferedReader buffer = new BufferedReader(file);
            String line = "";

            while ((line = buffer.readLine()) != null) {

                String values = line.replaceAll("\"", " ");
                String[] str = values.split(",");
                for (int i = 0; i < str.length; i++) {
                    str[i] = str[i].replaceAll("\\s+", "");
                }
                String type = str[0];


                if (type.equals("user")) {

                    if (str.length == 27) {

                        SMXL.getDataBase().importUser(str[2], str[3], str[4], str[5], str[6], str[7], str[8],
                                str[9], str[10], str[11], str[12], str[13], str[14], str[15], str[16], str[17],
                                str[18], str[19], str[20], str[21]);
                    } else {
                        Toast.makeText(this, "Le profile est corrompu (User) ... \nEssayez de le réexporter puis réimportez le", Toast.LENGTH_LONG).show();
                        finish();
                    }

                } else if (type.equals("user_clothes")) {
                    ArrayList<User> users;
                    users = SMXL.getDataBase().getAllUser();
                    int userId = 0;
                    if (users.size() != 0) {
                        //Get last user ( For get its ID for add garment )
                        User user = users.get(users.size() - 1);
                        userId = user.getUserid();
                    }

                    UserClothes userClothes = new UserClothes(Integer.parseInt(str[1]), userId,
                            str[3], str[4], str[5], str[6], str[7], null, null); // TODO AJOUTER ICI LE TABLEAU DES TAILLES POUR CHAQUE VETEMENT
                    SMXL.getDataBase().AddUserGarments(userClothes);

                } else {
                    Toast.makeText(this, "Erreur lors de l'importation du profil ...", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.import_profil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
