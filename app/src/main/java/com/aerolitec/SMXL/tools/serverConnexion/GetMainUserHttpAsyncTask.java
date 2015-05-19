package com.aerolitec.SMXL.tools.serverConnexion;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.aerolitec.SMXL.model.User;

/**
 * Created by Clement on 5/18/2015.
 */
public class GetMainUserHttpAsyncTask extends AsyncTask<String,Void,User>{

    private Activity activity;

    public GetMainUserHttpAsyncTask(Activity activity) {
        super();
        this.activity=activity;
    }

    @Override
    protected User doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(User user) {
        Toast.makeText(activity, "Data Received!", Toast.LENGTH_LONG).show();
        super.onPostExecute(user);
        activity.setResult(Activity.RESULT_OK);
        activity.finish();
    }
}
