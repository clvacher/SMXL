package com.aerolitec.SMXL.tools.serverConnexion;

import android.os.AsyncTask;
import android.util.Log;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.InputStream;

/**
 * Created by Nelson on 05/08/2015.
 */
public class PostAvatarProfileHttpAsyncTask extends AsyncTask<Integer, Void, String> {

    public static final String SERVER_ADDRESS_IMAGE_TO_USER_PART_1 = "http://api.smxl-app.com/joins/";
    public static final String SERVER_ADDRESS_IMAGE_TO_USER_PART_1_DEV = "http://smxl-api-dev-mhubsvde5h.elasticbeanstalk.com/joins/";

    public static final String SERVER_ADDRESS_IMAGE_TO_USER_PART_2 = "/images/profiles.html";

    private String filePath;
    public PostAvatarProfileHttpAsyncTask(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected String doInBackground(Integer... params) {
        Integer profileId = params[0];
        return POST(SERVER_ADDRESS_IMAGE_TO_USER_PART_1_DEV + profileId + SERVER_ADDRESS_IMAGE_TO_USER_PART_2);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("resultValue PostAvatar", result);
        switch(result) {
            case "Did not work!":
                break;
            default:
                break;
        }

    }

    protected String POST(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            FileBody fileBody = new FileBody(new File(filePath)); //image should be a String
            builder.addPart("avatar", fileBody);

            httpPost.setEntity(builder.build());

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            HttpResponse httpResponse = httpclient.execute(httpPost);
            inputStream = httpResponse.getEntity().getContent();
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
        Log.d("Result POSTAvatar", result);
        return result;
    }
}
