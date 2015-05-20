package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.tools.manager.MainUserManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Clement on 5/18/2015.
 */
public class PostMainUserHttpAsyncTask extends AsyncTask<Void, Void, String> {

    public static final String MAIN_USER_FILE = "mainUser";
    public static final String SERVER_ADDRESS_CREATE_MAIN_USER = "http://api.smxl-app.com/users.json";

    protected Activity activity;

    public PostMainUserHttpAsyncTask(Activity activity) {
        super();
        this.activity=activity;
    }

    @Override
    protected String doInBackground(Void... params) {
        return POST(SERVER_ADDRESS_CREATE_MAIN_USER, MainUserManager.get().getMainUser());
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(activity, "Data Sent!", Toast.LENGTH_LONG).show();
        if(!result.equals("Did not work!")) {
            try {
                FileOutputStream fos = activity.openFileOutput(MAIN_USER_FILE, Context.MODE_PRIVATE);
                fos.flush();
                fos.write(MainUserManager.get().getMainUser().getBytes());
                activity.setResult(Activity.RESULT_OK);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            activity.setResult(Activity.RESULT_CANCELED);
        }
        activity.finish();
    }


    protected String POST(String url, MainUser user){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("name", user.getLastname());
            jsonObject.accumulate("firstname", user.getFirstname());
            jsonObject.accumulate("email", user.getEmail());
            jsonObject.accumulate("password", user.getPassword());

            // 4. convert JSONObject to JSON to String
            json = jsonObject.toString();

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

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
                result = convertInputStreamToString(inputStream);
            }

            else{
                result = "Did not work!";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        Log.d("Result POST", result);
        return result;
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null){
            result += line;
        }

        inputStream.close();
        return result;
    }
}