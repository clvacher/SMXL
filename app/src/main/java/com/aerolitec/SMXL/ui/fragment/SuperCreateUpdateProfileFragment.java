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
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Surface;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.customLayout.ProfilePictureRoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

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
        Random rand = new Random();
        int rndInt = rand.nextInt(13) + 1;
        final String fname = UserManager.get().getUser().getFirstname()+"_"+UserManager.get().getUser().getLastname()+"_"+rndInt+".png";
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
                            performCropImage(uri);
                        }
                    } catch (Exception e) {
                        Log.e(Constants.TAG, "Error converting Picture in File : " + e.getMessage());
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
                        Log.e(Constants.TAG, "Error converting Picture in File : " + e.getMessage());
                    }
                    //selectedImageUri = data == null ? null : data.getData();
                }
            }

            if(requestCode == CROP_IMAGE) {
                int width = imgProfil.getLayoutParams().width;
                try {
                    File file = new File(cropImagePath.getPath());
                    if (file.exists()) {
                        final BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;

                        String picturePath = file.getAbsolutePath();
                        Bitmap pictureBitmap = BitmapFactory.decodeFile(picturePath, options);

                        // Calculate inSampleSize
                        options.inSampleSize = ImageHelper.calculateInSampleSize(options, width, width);

                        // Decode bitmap with inSampleSize set
                        options.inJustDecodeBounds = false;

                        imgProfil.setImageBitmap(ImageHelper.getCorrectBitmap(pictureBitmap, picturePath));
                    }
                } catch (Exception e) {
                    Log.e(Constants.TAG, "Error converting Picture to File : " + e.getMessage());
                }
            }
        }
    }


    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


}
