package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.MainUserManager;

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
 *
 * /!\ Classes using this class should implement LoginCreateAccountInterface
 *
 */
public class GetMainUserHttpAsyncTask extends AsyncTask<String,Void,String>{

    public static final String SERVER_ADDRESS_GET_MAIN_USER = "http://api.smxl-app.com/users/logs.json";
    public static final String SERVER_ADDRESS_GET_MAIN_USER_FACEBOOK = "http://api.smxl-app.com/users/facebooks.json";

    public static final String SERVER_ADDRESS_GET_MAIN_USER_DEV = "http://smxl-api-dev-mhubsvde5h.elasticbeanstalk.com/users/logs.json";
    public static final String SERVER_ADDRESS_GET_MAIN_USER_FACEBOOK_DEV = "http://smxl-api-dev-mhubsvde5h.elasticbeanstalk.com/users/facebooks.json";
    //used for the ability of displaying toasts
    private Context context;

    //used for interaction with the activity
    private LoginCreateAccountInterface loginCreateAccountInterface;

    public GetMainUserHttpAsyncTask(Activity context) {
        super();
        this.context=context;

        //Casting of the calling activity to the correct interface. Raises exception if it fails
        try {
            if (context instanceof LoginCreateAccountInterface)
                loginCreateAccountInterface = (LoginCreateAccountInterface) context;
            else
                throw new Exception("Activity using GetMainUserHttpAsyncTask must implement LoginCreateAccountInterface");
        }
        catch (Exception e){
            e.printStackTrace();
            cancel(true);
        }

    }

    public GetMainUserHttpAsyncTask(Fragment fragment) {
        super();
        this.context=fragment.getActivity();

        //Casting of the calling activity to the correct interface. Raises exception if it fails
        try {
            if (fragment instanceof LoginCreateAccountInterface)
                loginCreateAccountInterface = (LoginCreateAccountInterface) fragment;
            else
                throw new Exception("Fragment using GetMainUserHttpAsyncTask must implement LoginCreateAccountInterface");
        }
        catch (Exception e){
            e.printStackTrace();
            cancel(true);
        }
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch (result){
            case "wrong email":
                loginCreateAccountInterface. nonExistingAccount();
            case "null":
                loginCreateAccountInterface.nonExistingAccount();
                break;
            case "wrong password":
                loginCreateAccountInterface.wrongPassword();
                break;
            case "Did not work!":
                loginCreateAccountInterface.serverError("Error retrieving Account");
                break;
            default:
                JSONObject jsonMainUser;
                MainUser mainUser;
                try {
                    Log.d("resultValue",result);

                    //jsonMainUser = new JSONObject(result);

                    JSONObject jsonResult = new JSONObject(result);
                    jsonMainUser = jsonResult.getJSONObject("0");
                    int idMainProfile = jsonResult.optInt("mainprofile");

                    // jsonMainUser = new JSONObject(jsonMainUser.optString("0"));

                    Log.d("GetUser AsyncTask",jsonMainUser.toString());

                    /* Obtainment of the user's birthday */
                    /*
                    //creation of a Date corresponding to the JSON object timestamp
                    Date birthDate=new Date(jsonMainUser.getJSONObject("birthdate").getLong("timestamp")*1000);
                    //conversion to String (birthDate.getMonth() is deprecated)
                    Calendar cal=Calendar.getInstance();
                    cal.setTime(birthDate);

                    String birthdayString = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))+"-"+String.format("%02d", cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.YEAR);
                    */
                    mainUser = new MainUser(jsonMainUser.optString("email"),
                            jsonMainUser.optString("password"),
                            (int) jsonMainUser.optInt("social"),
                            null //UserManager.get().getUser()
                            );
                    mainUser.setIdMainProfile(idMainProfile);
                    mainUser.setAvatar(jsonMainUser.optString("avatar"));
                    mainUser.setFirstname(jsonMainUser.optString("firstname"));
                    mainUser.setLastname(jsonMainUser.optString("name"));
                    mainUser.setFacebookId(jsonMainUser.optString("idFacebook"));
                    mainUser.setServerId(jsonMainUser.optInt("id"));
                    mainUser.setSex(jsonMainUser.optInt("sex"));
                    Log.d("GetUser AsyncTask", mainUser.toString());
                    loginCreateAccountInterface.accountRetrieved(mainUser);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
        }
    }

    protected String GET(String url, String json){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null){
                result = UtilityMethodsv2.convertInputStreamToString(inputStream);

                //converts the result to a JSON-convertible String
//                if(!result.equals("wrong email") && !result.equals("wrong password") && !result.equals("null"))
//                    result = result.substring(1,result.length()-1);
            }

            else{
                result = "Did not work!";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        Log.d("Result GET", result);

        return result;
    }

    // params[0] : email
    // params[1] : password
    @Override
    protected String doInBackground(String... params) {
        String json = "";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("email", params[0]);
            jsonObject.accumulate("password", params[1]);
            json = jsonObject.toString();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return GET(SERVER_ADDRESS_GET_MAIN_USER_DEV,json);
    }
}
