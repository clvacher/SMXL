package com.aerolitec.SMXL.tools.serverConnexion;

import android.os.AsyncTask;
import android.util.Log;

import com.aerolitec.SMXL.tools.UtilityMethodsv2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Clement on 7/10/2015.
 */
public class GetCorrespondingProfilesHttpAsyncTask extends AsyncTask<Integer,Void,String> {

    public static final String SERVER_ADDRESS_GET_CORRESPONDING_LINK_PART_1 = "http://api.smxl-app.com/users/";
    public static final String SERVER_ADDRESS_GET_CORRESPONDING_LINK_PART_2 = "/profile.json";

    public static final String SERVER_ADDRESS_GET_CORRESPONDING_LINK_PART_1_DEV = "http://smxl-api-dev-mhubsvde5h.elasticbeanstalk.com/users/";

    @Override
    protected String doInBackground(Integer... params) {
        Integer mainUserId = params[0];
        return GET(SERVER_ADDRESS_GET_CORRESPONDING_LINK_PART_1_DEV+mainUserId+SERVER_ADDRESS_GET_CORRESPONDING_LINK_PART_2);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        switch(result){
            case "Did not work!":
                //code de l'interface pour echec
                break;
            default:
                //code de l'interface pour reussite?

                //obtention de tous les profils associ�s
                ArrayList<Integer> profileIds = getArrayListFromJsonString(result);
                for(Integer i :profileIds){
                    new GetProfileHttpAsyncTask().execute(i);
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

    protected ArrayList<Integer> getArrayListFromJsonString(String jsonString){
        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            ArrayList<Integer> list= new ArrayList<>();

            int len = jsonArray.length();
            for (int i = 0; i < len; i++) {
                list.add((Integer) jsonArray.get(i));
            }
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
