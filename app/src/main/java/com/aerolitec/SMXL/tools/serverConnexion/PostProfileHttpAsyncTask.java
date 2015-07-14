package com.aerolitec.SMXL.tools.serverConnexion;

import android.os.AsyncTask;
import android.util.Log;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Clement on 5/18/2015.
 */

//TODO link avec le USER
public class PostProfileHttpAsyncTask extends AsyncTask<User, Void, String> {

    public static final String SERVER_ADDRESS_CREATE_PROFILE = "http://api.smxl-app.com/profiles.json";

    @Override
    protected String doInBackground(User... params) {
        User user = params[0];
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("nickname", user.getNickname());
            jsonObject.accumulate("firstname", user.getFirstname());
            jsonObject.accumulate("lastname", user.getLastname());
            String birthday = user.getBirthday();
            if (birthday != null) {
                jsonObject.accumulate("birthday", UtilityMethodsv2.reverseBirthdayOrder(birthday));
            }
            jsonObject.accumulate("sexe", user.getSexe());
            jsonObject.accumulate("avatar", user.getAvatar());//TODO à changer avec l'adresse obtenue sur le serveur
            jsonObject.accumulate("description", user.getDescription());
            jsonObject.accumulate("size", user.getHeight()); // TODO a changer sur le serveur
            jsonObject.accumulate("weight", user.getWeight());
            jsonObject.accumulate("chest", user.getChest());
            jsonObject.accumulate("collar", user.getCollar());
            jsonObject.accumulate("bust", user.getBust());
            jsonObject.accumulate("waist", user.getWaist());
            jsonObject.accumulate("hips", user.getHips());
            jsonObject.accumulate("sleeve", user.getSleeve());
            jsonObject.accumulate("inseam", user.getInseam());//TODO ne marche pas sur le serveur
            jsonObject.accumulate("feat", user.getFeet());//TODO a chnager sur le serveur
            jsonObject.accumulate("unitL", user.getUnitLength());
            jsonObject.accumulate("unitW", user.getUnitWeight());
            jsonObject.accumulate("pointure", user.getPointure());


            json = jsonObject.toString();

        }
        catch (Exception e){
            e.printStackTrace();
        }

        return POST(SERVER_ADDRESS_CREATE_PROFILE,json);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch(result){
            case "Did not work!":
                //error message with interface?
                break;
            default:
                //recuperation de l'id
                JSONObject jsonUser;
                User user = null;
                try {
                    Log.d("resultValue", result);
                    jsonUser = new JSONObject(result);
                    Log.d("PostProfile AsyncTask", jsonUser.toString());

                    //ajout au MainUser pour pouvoir les recuperer par la suite
                    Integer userId = Integer.parseInt(jsonUser.optString("id"));
                    MainUserManager.get().getMainUser().addProfile(userId);

                    new PostLinkHttpAsyncTask().execute(userId);

                }
                catch(Exception e){
                    e.printStackTrace();
                }
        }
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


            // 7. Set some headers to inform server about the type of the content
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
        Log.d("Result POSTProfile", result);
        return result;
    }

}
