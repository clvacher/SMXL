package com.aerolitec.SMXL.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jerome on 20/04/2015.
 */
public abstract class SuperCreateUpdateProfileFragment extends Fragment {

    protected static final int CROP_IMAGE = 2;
    protected static final int CHOOSE_CAMERA_GALLERY = 5;


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



    //Source : http://stackoverflow.com/questions/4455558/allow-user-to-select-camera-or-gallery-for-image
    private Uri outputFileUri;

    protected void openImageIntent() {

        // Determine Uri of camera image to save.
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root, "SMXL");
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        final String fname = etFirstName.getText().toString()+"_"+etLastName.getText().toString()+"_"+timeStamp+".png";
        final File sdImageMainDirectory = new File(myDir, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);

        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        //galleryIntent.setAction(Intent.ACTION_GET_CONTENT);  Rajoute toutes les applis possédant des images

        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));


        startActivityForResult(chooserIntent, CHOOSE_CAMERA_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CHOOSE_CAMERA_GALLERY) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }

                //Uri selectedImageUri;
                if (isCamera) {
                    //selectedImageUri = outputFileUri;
                    picturePath = outputFileUri.getPath();

                    try {
                        File file = new File(picturePath);
                        if (file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            rotateImage(file);
                            performCropImage(uri);
                        }
                    } catch (Exception e) {
                        Log.e(Constants.TAG, "Error converting Picture in File While trying to take a picture and rotate it: " + e.getMessage());
                    }
                } else {
                    //Récupéré de l'ancien --> si selection gallery
                    Uri selectedImageUri = data.getData();
                    String fpCol[] = {MediaStore.MediaColumns.DATA};
                    Cursor c = getActivity().getContentResolver().query(selectedImageUri, fpCol, null, null, null);
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
                        Log.e(Constants.TAG, "Error converting Picture in File while trying to get a image from the gallery" + e.getMessage());
                    }
                    //selectedImageUri = data == null ? null : data.getData();
                }
            }

            if(requestCode == CROP_IMAGE) {
                try {

                    File file = new File(cropImagePath.getPath());
                    if (file.exists()) {
                        final String picturePath = file.getAbsolutePath();
                        imgProfil.setImage(picturePath);
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG, "Error converting Picture to File While trying to crop : " + e.getMessage());
                }
            }
        }
    }

    private Bitmap rotate(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        // return new bitmap rotated using matrix
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }
    private void rotateImage(File file) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = UtilityMethodsv2.calculateInSampleSize(file.getAbsolutePath(),640,360);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
            ExifInterface exifReader = new ExifInterface(file.getAbsolutePath());

            int orientation = exifReader.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap =  rotate(bitmap, 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotate(bitmap, 180);break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    bitmap = rotate(bitmap, 270);
                    break;
            }
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100	, fOut);
            fOut.flush();
            fOut.close();
        }catch (IOException e ){
            Log.e(Constants.TAG, "Error while trying to open file");
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            outputFileUri = savedInstanceState.getParcelable("file");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file", outputFileUri);
    }
}
