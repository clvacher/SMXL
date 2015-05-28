package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.aerolitec.SMXL.model.MainUser;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.manager.MainUserManager;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
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

    @Override
    protected String doInBackground(String... params) {
        return GET(SERVER_ADDRESS_GET_MAIN_USER, MainUserManager.get().getMainUser().getEmail(),MainUserManager.get().getMainUser().getPassword());
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //TODO tester la valeur de result
        switch (result){
            case "null":
                loginCreateAccountInterface.nonExistingAccount();
                break;
            case "Did not work!":
                Toast.makeText(context, "Error retrieving Account", Toast.LENGTH_LONG).show();
                break;
            default:
                JSONObject jsonMainUser;
                MainUser mainUser = null;
                try {
                    Log.d("resultValue",result);
                    jsonMainUser = new JSONObject(result);
                    Log.d("GetUser AsyncTask",jsonMainUser.toString());

                    /* Obtainment of the user's birthday */

                    //creation of a Date corresponding to the JSON object timestamp
                    Date birthDate=new Date(jsonMainUser.getJSONObject("birthdate").getLong("timestamp")*1000);
                    //conversion to String (birthDate.getMonth() is deprecated)
                    Calendar cal=Calendar.getInstance();
                    cal.setTime(birthDate);

                    String birthdayString = cal.get(Calendar.DAY_OF_MONTH)+"-"+String.format("%02d", cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.YEAR);

                    UserManager.get().setUser(new User(
                            jsonMainUser.optString("firstname"),
                            jsonMainUser.optString("name"),
                            birthdayString,
                            jsonMainUser.optInt("sex")
                    ));

                    mainUser = new MainUser(jsonMainUser.optString("email"),
                            jsonMainUser.optString("password"),
                            (int) jsonMainUser.get("social"),
                            UserManager.get().getUser()
                            );
                    Log.d("GetUser AsyncTask",mainUser.toString());

                }
                catch(Exception e){
                    e.printStackTrace();
                }
                loginCreateAccountInterface.alreadyExistingAccount(mainUser);
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
                result = PostMainUserHttpAsyncTask.convertInputStreamToString(inputStream);

                //converts the result to a JSON-convertible String
                if(!result.equals("null"))
                    result = result.substring(1,result.length()-1);
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
