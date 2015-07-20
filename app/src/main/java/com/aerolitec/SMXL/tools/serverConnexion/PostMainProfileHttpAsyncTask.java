package com.aerolitec.SMXL.tools.serverConnexion;

import android.os.AsyncTask;
import android.util.Log;

import com.aerolitec.SMXL.tools.UtilityMethodsv2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Clement on 7/17/2015.
 */
public class PostMainProfileHttpAsyncTask extends AsyncTask<Integer,Void,String>{


    public static final String SERVER_ADDRESS_POST_MAIN_PROFILE = "http://api.smxl-app.com/users/mains/profiles.json";

    @Override
    protected String doInBackground(Integer... params) {
        int userId = params[0];
        int profileId = params[1];


        String json = "";
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.accumulate("mainprofile",profileId);
            jsonObject.accumulate("user",userId);

            json = jsonObject.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return POST(SERVER_ADDRESS_POST_MAIN_PROFILE,json);
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
