package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Jerome on 20/04/2015.
 */
public abstract class SuperCreateUpdateProfileFragment extends Fragment {

    protected static final int CROP_IMAGE = 2;

    protected EditText etFirstName, etLastName, etNotes;
    protected RadioGroup radioSexe;
    protected ProfilePictureRoundedImageView imgProfil;
    protected Button datePickerButton,validationButton;
    protected String picturePath;
    protected String birthday;
    protected Uri cropImagePath;


    protected boolean performCropImage(Uri selectedImageUri) {
        try {
            if (selectedImageUri != null) {
                //call the standard crop action intent (the user device may not support it)
                Intent cropIntent = new Intent("com.android.camera.action.CROP");
                //indicate image type and Uri
                cropIntent.setDataAndType(selectedImageUri, "image/*");
                //set crop properties
                cropIntent.putExtra("crop", "true");
                //indicate aspect of desired crop
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("scale", true);
                cropIntent.putExtra("scaleUpIfNeeded", true);
                //indicate output X and Y
                cropIntent.putExtra("outputX", 400);
                cropIntent.putExtra("outputY", 400);
                //retrieve data on return
                cropIntent.putExtra("return-data", false);

                File f = getTempFile(new File(selectedImageUri.getPath()).getName());
                boolean created = false;
                try {
                    created = f.createNewFile();
                } catch (IOException ex) {
                    Log.e("io", ex.getMessage());
                }

                cropImagePath = Uri.fromFile(f);
                picturePath=cropImagePath.getPath();
                cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, cropImagePath);
                //start the activity - we handle returning in onActivityResult
                startActivityForResult(cropIntent, CROP_IMAGE);
                return true;
            }
        } catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return false;
    }


    protected File getTempFile(String fileName) {
        final File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "SMXL");
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, fileName);
    }



    protected void openDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int birthdayYear = c.get(Calendar.YEAR);
        int birthdayMonth = c.get(Calendar.MONTH);
        int birthdayDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int birthdayYear,
                                          int birthdayMonth, int birthdayDay) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("" + String.valueOf((100 + birthdayDay)).substring(1, 3)).append("-")
                                .append((String.valueOf(100 + (birthdayMonth+1))).substring(1, 3)).append("-").append(birthdayYear);
                        birthday = sb.toString();
                        datePickerButton.setText(birthday);
                    }
                }, birthdayYear, birthdayMonth, birthdayDay
        );
        dpd.show();
    }


    @Override
    public void onActivityResult(int request_code, int result_code, Intent datas) {
        if (request_code == 77) {    // Pick a picture in gallery
            if (result_code != Activity.RESULT_OK)
                return;
            Uri selectedImage = datas.getData();
            String fpCol[] = {MediaStore.MediaColumns.DATA};
            Cursor c = getActivity().getContentResolver().query(selectedImage, fpCol, null, null, null);
            picturePath = "";
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(fpCol[0]);
                picturePath = c.getString(columnIndex);
            }
            c.close();

            try {
                File file = new File(picturePath);
                if (file.exists()) {
                    Uri uri = Uri.fromFile(file);
                    performCropImage(uri);
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting Picture in File : " + e.getMessage());
            }
        }

        if(request_code == CROP_IMAGE && result_code == Activity.RESULT_OK) {
            int width = imgProfil.getLayoutParams().width;
            try {
                File file = new File(cropImagePath.getPath());
                if (file.exists()) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                    // Calculate inSampleSize
                    options.inSampleSize = ImageHelper.calculateInSampleSize(options, width, width);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    imgProfil.setImageBitmap(ImageHelper.getCorrectBitmap(BitmapFactory.decodeFile(file.getAbsolutePath(), options), file.getAbsolutePath()));
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Error converting Picture to File : " + e.getMessage());
            }
        }
    }



}
