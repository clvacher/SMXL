package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Clement on 5/18/2015.
 */
public class PostMainUserHttpAsyncTask extends AsyncTask<Void, Void, String> {

    public static final String SERVER_ADDRESS_CREATE_MAIN_USER_DEV = "http://smxl-api-dev-mhubsvde5h.elasticbeanstalk.com/users.json";
    public static final String SERVER_ADDRESS_CREATE_MAIN_USER = "http://api.smxl-app.com/users.json";

    protected Activity activity;

    public PostMainUserHttpAsyncTask(Activity activity) {
        super();
        this.activity=activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        MainUser mainUser = MainUserManager.get().getMainUser();
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name", mainUser.getLastname());
            jsonObject.accumulate("firstname", mainUser.getFirstname());
            jsonObject.accumulate("email", mainUser.getEmail());
            jsonObject.accumulate("password", mainUser.getPassword());
            jsonObject.accumulate("sex", mainUser.getSex());
            String birthday = mainUser.getMainProfile().getBirthday();
            if (birthday != null) {
                jsonObject.accumulate("birthdate", UtilityMethodsv2.reverseBirthdayOrder(birthday));
            }
            jsonObject.accumulate("social", mainUser.getAccountType());

            json = jsonObject.toString();

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return POST(SERVER_ADDRESS_CREATE_MAIN_USER_DEV, json);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(activity, "Data Sent!", Toast.LENGTH_LONG).show();
        if(!result.equals("Did not work!")) {
            JSONObject jsonMainUser;

            try {
                jsonMainUser = new JSONObject(result);

                //ajout de l'Id server associe
                Integer id = Integer.parseInt(jsonMainUser.optString("id"));
                Log.d("idMainUserPost",id+"");
                MainUserManager.get().getMainUser().setServerId(id);

                FileOutputStream fos = activity.openFileOutput(Constants.MAIN_USER_FILE, Context.MODE_PRIVATE);
                fos.flush();
                fos.write(MainUserManager.get().getMainUser().getBytes());

                int mainProfileServerId = UserManager.get().getUser().getServer_id();
                new PostLinkIdMainProfileToUserHttpAsyncTask().execute(id, mainProfileServerId);

                Intent intent = new Intent(activity.getApplicationContext(), MainNavigationActivity.class);
                activity.startActivity(intent);
                activity.finish();

                }
                catch(Exception e){
                    e.printStackTrace();
                }

                activity.setResult(Activity.RESULT_OK);

        }
        else{
            activity.setResult(Activity.RESULT_CANCELED);
        }
        activity.finish();
    }


    protected String POST(String url, String json){
        InputStream inputStream = null;
        String result = "";
        try {
            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);



            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);


            // 6. set httpPost Entity
            httpPost.setEntity(se);


            // 7. Set some headers to inform server about the type of the content� �
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);


            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();


            // 10. convert inputstream to string
            if(inputStream != null){
                result = UtilityMethodsv2.convertInputStreamToString(inputStream);
            }
            else{
                result = "Did not work!";
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        Log.d("Result POSTMainUser", result);
        return result;
    }
}