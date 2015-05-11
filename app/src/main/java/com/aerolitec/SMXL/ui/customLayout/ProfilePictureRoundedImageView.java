package com.aerolitec.SMXL.ui.customLayout;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.makeramen.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Jerome on 11/05/2015.
 */
public class ProfilePictureRoundedImageView extends RoundedImageView {

    public ProfilePictureRoundedImageView(Context context) {
        super(context);
    }

    public ProfilePictureRoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfilePictureRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImage(final String urlImage){

        final String urlImageFinal = urlImage;

        if(urlImage=="createNewProfile"){
            setImageResource(R.drawable.ic_menu_btn_add);
        }
        else if(urlImage!=null) {
            if (urlImage.contains("https")){
                AsyncTask asyncTask = new AsyncTask() {

                    Drawable drawable;
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        try {
                            drawable = Drawable.createFromStream((InputStream)new URL(urlImageFinal).getContent(), "facebook");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }

                    @Override
                    protected void onPostExecute(Object o) {
                        super.onPostExecute(o);
                        if(drawable!=null){
                            setImageDrawable(drawable);
                        }
                        else{
                            setImageResource(R.drawable.avatar);
                        }

                    }

                };
                asyncTask.execute();
            }
            else{
                try {
                    File file = new File(urlImage);

                    if (file.exists()) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                        // Calculate inSampleSize
                        options.inSampleSize = ImageHelper.calculateInSampleSize(options, getLayoutParams().width, getLayoutParams().width);

                        // Decode bitmap with inSampleSize set
                        options.inJustDecodeBounds = false;
                        setImageBitmap(ImageHelper.getCorrectBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getAbsolutePath()));
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG, "Error converting picture to file : " + e.getMessage());
                }
            }
        }
        else{
            setImageResource(R.drawable.avatar);
        }


    }
}
