package com.aerolitec.SMXL.tools.serverConnexion;

import android.os.AsyncTask;
import android.util.Log;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Clément on 7/10/2015.
 */
public class GetProfileHttpAsyncTask extends AsyncTask<Integer,Void,String> {

    public static final String SERVER_ADDRESS_GET_PROFILE_PART1 = "http://api.smxl-app.com/profiles/";
    public static final String SERVER_ADDRESS_GET_PROFILE_PART2 = "/id.html";


    @Override
    protected String doInBackground(Integer... params) {
        int profileId = params[0];
        String url = SERVER_ADDRESS_GET_PROFILE_PART1+profileId+SERVER_ADDRESS_GET_PROFILE_PART2;
        return GET(url);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch (result){
            case "null":
                //error message with interface
                break;
            default :
                JSONObject jsonUser;
                try {
                    Log.d("resultValue",result);
                    jsonUser = new JSONObject(result);
                    Log.d("GetUser AsyncTask",jsonUser.toString());

                    /* Obtainment of the user's birthday */

                    //creation of a Date corresponding to the JSON object timestamp
                    Date birthDate=new Date(jsonUser.getJSONObject("birthday").getLong("timestamp")*1000);
                    //conversion to String (birthDate.getMonth() is deprecated)
                    Calendar cal=Calendar.getInstance();
                    cal.setTime(birthDate);
                    String birthdayString = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH))+"-"+String.format("%02d", cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.YEAR);

                    //jsonMainUser.optString("name");

                    User user = null;
                    try {
                        user = SMXL.getUserDBManager().createUser(jsonUser.optString("firstname"),
                                jsonUser.optString("lastname"), birthdayString, jsonUser.optInt("sexe"), jsonUser.optString("avatar"), jsonUser.optString("description"));
                        Log.d(Constants.TAG, "New profile created : " + user.toString());
                    } catch (Exception e) {
                        Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
        }
    }

    protected String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);


            HttpResponse httpResponse = httpclient.execute(httpGet);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null){
                result = UtilityMethodsv2.convertInputStreamToString(inputStream);

                //converts the result to a JSON-convertible String
//                if(!result.equals("null"))
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
}
