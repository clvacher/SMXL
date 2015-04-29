package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;

public class CSVCreationActivity extends Activity {
    SQLiteDatabase myDatabase = null;
    String DataBase_Name = "SIZEGUIDE_DB";
    User user;

    /*
    ArrayList<Group> groups;
    ArrayList<String> listSize;
    ExpandableListView listView;
    EListAdapter adapter;
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_profil);

        user = UserManager.get().getUser();

        if(user == null) {
            finish();
            return;
        }

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(user.getNickname());

        /*
        groups = new ArrayList<Group>();
        listSize = new ArrayList<>();

        listSize = SMXL.getDataBase().getUserSizes(user);
        getJSONObject();
        listView = (ExpandableListView) findViewById(R.id.listView);
        adapter = new EListAdapter(this, groups);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(adapter);

        */



        try {
            myDatabase = this.openOrCreateDatabase(DataBase_Name, MODE_PRIVATE, null);
            new ExportDatabaseCSVTask().execute("");

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Log.e("Error in MainActivity", ex.toString());
        }

    }



    /*
    private void getJSONObject() {
        String jsonShare ="";
        String jsonStr = "{'CommunityUsersResult':[{'CommunityUsersList':[{'fullname':'a111','userid':11,'username':'a1'}"
                + ",{'fullname':'b222','userid':12,'username':'b2'}],'id':1,'title':'Mesure'},{'CommunityUsersList':[{'fullname':"
                + "'c333','userid':13,'username':'c3'},{'fullname':'d444','userid':14,'username':'d4'},{'fullname':'e555','userid':"
                + "15,'username':'e5'}],'id':2,'title':'Vetement'}]}";


        if(listSize.size() != 0) {
            jsonShare = "{'Share':[{'User':[{'sexe':'" + user.getSexe() + "','birthday':'" +user.getBirthday()
                    + "','size':'" + listSize.get(0) + "','avatar':'" + user.getAvatar() + "','pictureDressingRoom':'" + user.getPictureDressingRoom()
                    + "','weight':'" + listSize.get(1) + "','bust':'" + listSize.get(2) + "','chest':'" + listSize.get(3)
                    + "','collar':'" + listSize.get(4) + "','waist':'" + listSize.get(5) + "','hips':'" + listSize.get(6)
                    + "','sleeve':'" + listSize.get(7) + "','inseam':'" + listSize.get(8) + "','feet':'" + listSize.get(9)
                    + "','unitL':'" + listSize.get(10) + "','unitW':'" + listSize.get(11) + "','pointure':'" + listSize.get(12) +  "'}],'id':1,'title':'" + user.getNickname() + "'}]}";
        }
        else {
            Toast.makeText(this, "Completer votre profil avant de l'exporter", Toast.LENGTH_SHORT).show();
            finish();
        }

        try {
            JSONObject values = new JSONObject(jsonShare);
            JSONArray groupList = values.getJSONArray("Share");

            for (int i = 0; i < groupList.length(); i++) {
                JSONObject groupObj = (JSONObject) groupList.get(i);
                Group group = new Group(groupObj.getString("id"), groupObj.getString("title"));
                JSONArray childrenList = groupObj.getJSONArray("User");

                for (int j = 0; j < childrenList.length(); j++) {
                    JSONObject childObj = (JSONObject) childrenList.get(j);
                    Child child = new Child(childObj.getString("birthday"), childObj.getString("avatar"),
                            childObj.getString("pictureDressingRoom"), childObj.getString("size"),
                            childObj.getString("weight"), childObj.getString("bust"), childObj.getString("chest"),
                            childObj.getString("collar"), childObj.getString("waist"), childObj.getString("hips"),
                            childObj.getString("sleeve"), childObj.getString("inseam"), childObj.getString("feet"),
                            childObj.getString("unitL"), childObj.getString("unitW"), childObj.getString("pointure"));

                    group.addChildrenItem(child);
                }

                groups.add(group);
            }
        } catch (JSONException e) {
            Log.d("allenj", e.toString());
        }
    }
    */


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profil_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getFragmentManager().findFragmentByTag("addMesure") != null || getFragmentManager().findFragmentByTag("addGarment") != null) {
                    getFragmentManager().popBackStack();
                } else {
                    finish();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(CSVCreationActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            File dbFile = getDatabasePath("myDatabase.db");
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");

            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, user.getNickname() + ".smxl");
            try {
                String tableUser = "user";
                String tableGarment = "user_clothes";
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                String requestUser = "SELECT * FROM " + tableUser + " WHERE userid=" + String.valueOf(user.getId_user());
                String requestGarment = "SELECT * FROM " + tableGarment + " WHERE userid=" + String.valueOf(user.getId_user());

                while (myDatabase == null) {
                    dialog.show();
                }


                SMXL.getUserDBManager().getUser(user.getId_user());



                Cursor curGarment = myDatabase.rawQuery(requestGarment, null);
                Cursor curUser = myDatabase.rawQuery(requestUser, null);


                while (curUser.moveToNext()) {
                    String arrStr[] = {tableUser, curUser.getString(0), curUser.getString(1), curUser.getString(2), curUser.getString(3), curUser.getString(4),
                            curUser.getString(5), curUser.getString(6), curUser.getString(7), curUser.getString(8), curUser.getString(9), curUser.getString(10),
                            curUser.getString(11), curUser.getString(12), curUser.getString(13), curUser.getString(14), curUser.getString(15), curUser.getString(16),
                            curUser.getString(17), curUser.getString(18), curUser.getString(19), curUser.getString(20), curUser.getString(21), curUser.getString(22),
                            curUser.getString(23), curUser.getString(24), curUser.getString(25)};
                    csvWrite.writeNext(arrStr);
                }


                while (curGarment.moveToNext()){
                    String arrStr[] = {tableGarment, curGarment.getString(0), curGarment.getString(1), curGarment.getString(2), curGarment.getString(3), curGarment.getString(4),
                            curGarment.getString(5), curGarment.getString(6)};
                    csvWrite.writeNext(arrStr);
                }

                csvWrite.close();
                curGarment.close();
                curUser.close();
                send(file);
                return true;

            } catch (SQLException sqlEx) {
                Log.e("CSVCreation", sqlEx.getMessage(), sqlEx);
                return false;
            } catch (IOException e) {
                Log.e("CSVCreation", e.getMessage(), e);
                return false;
            }
        }

        private void send(File file) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                Toast.makeText(CSVCreationActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(CSVCreationActivity.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

}