package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.ui.activity.CreateAccountActivity;
import com.aerolitec.SMXL.ui.activity.CreateUpdateProfileActivity;
import com.aerolitec.SMXL.ui.activity.LoginActivity;
import com.aerolitec.SMXL.ui.activity.SuperLoginCreateAccountActivity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;

/**
 * Created by Clement on 5/18/2015.
 */
public class GetMainUserHttpAsyncTask extends AsyncTask<String,Void,String>{

    public static final String SERVER_ADDRESS_GET_MAIN_USER = "http://api.smxl-app.com/users/logs.json";
    private Activity activity;

    public GetMainUserHttpAsyncTask(Activity activity) {
        super();
        this.activity=activity;
    }

    @Override
    protected String doInBackground(String... params) {
        return GET(SERVER_ADDRESS_GET_MAIN_USER, MainUserManager.get().getMainUser().getEmail(),MainUserManager.get().getMainUser().getPassword());
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //TODO tester avec SuperLoginCreateAccountActivity.class
        //TODO tester la valeur de result
        boolean activityTypeIsRight = activity.getClass()== LoginActivity.class || activity.getClass() == CreateAccountActivity.class;
        if(!result.equals("Did not work!")) {
            if(activityTypeIsRight) {
                ((SuperLoginCreateAccountActivity) activity).alreadyExistingAccount();
            }
        }
        else{
            //Toast.makeText(activity, "Error retrieving Account", Toast.LENGTH_LONG).show();
            if(activityTypeIsRight)
                ((SuperLoginCreateAccountActivity) activity).nonExistingAccount();
        }
    }

    protected String GET(String url, String email,String password){
        InputStream inputStream = null;
        String result = "";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("email", email);
            jsonObject.accumulate("password", password);

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);
            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if(inputStream != null){
                if((result = PostMainUserHttpAsyncTask.convertInputStreamToString(inputStream)).equals("null"))
                    result = "Did not work!";
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
