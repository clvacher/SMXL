package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.aerolitec.SMXL.model.User;
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
 *
 * parameters: 0 -> userid
 *             1 -> profileid
 */
public class GetSharingCodeHttpAsyncTask extends AsyncTask<Integer, Void, String> {


    public static final String SERVER_ADDRESS_GET_SHARING_CODE = "http://api.smxl-app.com/shares/profiles.json";

    private GetSharingCodeInterface getSharingCodeInterface;

    public GetSharingCodeHttpAsyncTask(Activity activity) {

        //Casting of the calling activity to the correct interface. Raises exception if it fails
        try {
            if (activity instanceof GetSharingCodeInterface)
                getSharingCodeInterface = (GetSharingCodeInterface) activity;
            else
                throw new Exception("Activity using GetSharingCodeHttpAsyncTask must implement GetSharingCodeInterface");
        }
        catch (Exception e){
            e.printStackTrace();
            cancel(true);
        }
    }

    public GetSharingCodeHttpAsyncTask(Fragment fragment) {

        //Casting of the calling activity to the correct interface. Raises exception if it fails
        try {
            if (fragment instanceof GetSharingCodeInterface)
                getSharingCodeInterface = (GetSharingCodeInterface) fragment;
            else
                throw new Exception("Fragment using GetSharingCodeHttpAsyncTask must implement GetSharingCodeInterface");
        }
        catch (Exception e){
            e.printStackTrace();
            cancel(true);
        }
    }

    @Override
    protected String doInBackground(Integer... params) {
        int userid = params[0];
        int profileid = params[1];

        String json = "";
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.accumulate("profile",profileid);
            jsonObject.accumulate("user",userid);

            json = jsonObject.toString();
            Log.d("jsonObject getsharing",json);
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return POST(SERVER_ADDRESS_GET_SHARING_CODE,json);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        switch(result){
            case "Did not work!":
                getSharingCodeInterface.onServerError("An error occured while trying to get the sharing code");
                break;
            default:
                if(result.startsWith("{\"error\"")){
                    Log.d("error","something happened");
                    break;
                }
                int sharingCode = Integer.parseInt(result.substring(1,result.length()-1));
                Log.d("sharing code :",sharingCode+"");

                getSharingCodeInterface.onCodeRetrieved(sharingCode);
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
