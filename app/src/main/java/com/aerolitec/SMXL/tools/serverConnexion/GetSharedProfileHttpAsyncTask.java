package com.aerolitec.SMXL.tools.serverConnexion;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.MainNavigationActivity;
import com.aerolitec.SMXL.ui.activity.SuperNavigationActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Clement on 7/17/2015.
 */
public class GetSharedProfileHttpAsyncTask extends AsyncTask<Integer, Void, String> {

    public static final String SERVER_ADDRESS_GET_SHARED_PROFILE_PART_1 = "http://api.smxl-app.com/profiles/";
    public static final String SERVER_ADDRESS_GET_SHARED_PROFILE_PART_2 = "/obtain.json";
    private Context context ;
    public GetSharedProfileHttpAsyncTask(){

    }
    public GetSharedProfileHttpAsyncTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Integer... params) {
        int sharedCode = params[0];

        String url = SERVER_ADDRESS_GET_SHARED_PROFILE_PART_1+sharedCode+SERVER_ADDRESS_GET_SHARED_PROFILE_PART_2;

        return GET(url);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch(result){
            case "null":
                Log.d("resultValue", result);
                Toast.makeText(context,R.string.profile_import_not_exist,Toast.LENGTH_SHORT).show();
                break;
            case "Did not work":
                Log.d("resultValue",result);
                break;
            default:

                JSONObject jsonUser;
                try{
                    Log.d("resultValue",result);
                    jsonUser = new JSONObject(result);
                    Log.d("GetUser AsyncTask",jsonUser.toString());

                    if(SMXL.getUserDBManager().getUser(jsonUser.getLong("id")) == null) {
                        /* Obtainment of the user's birthday */

                        //creation of a Date corresponding to the JSON object timestamp
                        Date birthDate = new Date(jsonUser.getJSONObject("birthday").getLong("timestamp") * 1000);
                        //conversion to String (birthDate.getMonth() is deprecated)
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(birthDate);
                        String birthdayString = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)) + "-" + String.format("%02d", cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.YEAR);

                        //jsonMainUser.optString("name");

                        User user = null;
                        try {
                            user = new User(jsonUser,birthdayString);
                            SMXL.getUserDBManager().createUser(user);
                            //user = SMXL.getUserDBManager().createUser(jsonUser.optString("firstname"),
                            //        jsonUser.optString("lastname"), birthdayString, jsonUser.optInt("sexe"), jsonUser.optString("avatar"), jsonUser.optString("description"), jsonUser.optInt("id"));
                            Log.d(Constants.TAG, "New profile created : " + user.toString());
                            Toast.makeText(context, R.string.profile_import_success, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
                        }
                    }
                    else {
                        Toast.makeText(context, R.string.profile_import_already_exist, Toast.LENGTH_SHORT).show();
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
