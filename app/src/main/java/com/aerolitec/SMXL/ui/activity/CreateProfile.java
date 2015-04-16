package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.SelectBrandsActivity;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.ui.SMXL;
import com.makeramen.RoundedImageView;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by stephaneL on 21/03/14.
 */
public class CreateProfile extends Activity {
    private static final int CROP_IMAGE = 2;

    EditText etFirstName, etLastName, etNotes;
    RadioGroup radioSexe;
    RoundedImageView imgProfil;
    Button datePickerButton;
    private String picturePath;
    String birthday;
    private Uri cropImagePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowTitleEnabled(false);

        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etNotes = (EditText) findViewById(R.id.etNotesProfil);
        datePickerButton = (Button) findViewById(R.id.buttonBirthday);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });

        radioSexe = (RadioGroup) findViewById(R.id.radioSexe);
        imgProfil = (RoundedImageView) findViewById(R.id.imgProfil);
        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectProfilPicture();
            }
        });


    }


    private void openDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int birthdayYear = c.get(Calendar.YEAR);
        int birthdayMonth = c.get(Calendar.MONTH) + 1;
        int birthdayDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int birthdayYear,
                                          int birthdayMonth, int birthdayDay) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("" + String.valueOf((100 + birthdayDay)).substring(1, 3)).append("-")
                                .append((String.valueOf(100 + (birthdayMonth + 1))).substring(1, 3)).append("-").append(birthdayYear);
                        birthday = sb.toString();
                        datePickerButton.setText(birthday);
                    }
                }, birthdayYear, birthdayMonth, birthdayDay
        );
        dpd.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.create_profil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.validate) {

            /// Add Profile in DataBase ///
            if (etFirstName.getText().toString().length() < 2 ||
                    etLastName.getText().toString().length() < 2) {
                Toast.makeText(this, getResources().getString(R.string.incompleteProfile), Toast.LENGTH_LONG).show();
            } else {
                String sexe = "F";
                int idRadioButton = radioSexe.getCheckedRadioButtonId();
                if (idRadioButton == -1) {
                    //no item selected
                } else {
                    if (idRadioButton == R.id.radioMale) {
                        sexe = "H";
                    }
                }

                User user = null;
                try {
                    user = SMXL.get().getDataBase().createUser(etFirstName.getText().toString(),
                            etLastName.getText().toString(), birthday, sexe, picturePath, etNotes.getText().toString());
                    Log.d(Constants.TAG, "New profile created : " + user.toString());
                } catch (Exception e) {
                    Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
                }
                if (user != null)
                    setResult(user.getUserid());
                else
                    setResult(0);
                finish();
            }
        } else if (id == R.id.newPicture) {
            selectProfilPicture();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActionCreateProfile (View v){

        /// Add Profile in DataBase ///
        if (etFirstName.getText().toString().length() < 2 ||
                etLastName.getText().toString().length() < 2) {
            Toast.makeText(this, getResources().getString(R.string.incompleteProfile), Toast.LENGTH_LONG).show();
        } else {
            String sexe = "F";
            int idRadioButton = radioSexe.getCheckedRadioButtonId();
            if (idRadioButton == -1) {
                //no item selected
            } else {
                if (idRadioButton == R.id.radioMale) {
                    sexe = "H";
                }
            }


            User user = null;
            try {
                user = SMXL.get().getDataBase().createUser(etFirstName.getText().toString(),
                        etLastName.getText().toString(), birthday, sexe, picturePath, etNotes.getText().toString());
                Log.d(Constants.TAG, "New profile created : " + user.toString());
            } catch (Exception e) {
                Log.d(Constants.TAG, "Create user with error : " + e.getMessage());
            }
            if (user != null)
                setResult(user.getUserid());
            else
                setResult(0);

            //finish();
            Intent intent = new Intent(getApplicationContext(), SelectBrandsActivity.class);
            startActivity(intent);
        }
    }

    private void selectProfilPicture() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 77);
    }


    @Override
    public void onActivityResult(int request_code, int result_code, Intent datas) {
        if (request_code == 77) {
            if (result_code != RESULT_OK)
                return;
            Uri selectedImage = datas.getData();
            String fpCol[] = {MediaStore.MediaColumns.DATA};
            Cursor c = getContentResolver().query(selectedImage, fpCol, null, null, null);
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

        if(request_code == CROP_IMAGE && result_code == RESULT_OK) {
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




    private boolean performCropImage(Uri selectedImageUri) {
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
                //cropIntent.putExtra("outputX", 500);
                //cropIntent.putExtra("outputY", 500);
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
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return false;
    }

    private File getTempFile(String fileName) {
        final File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + "SMXL");
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, fileName);
    }

    public int getPixelsFromDip(int dip, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed(){
        if(etFirstName.getText().toString().length() > 2 || etLastName.getText().toString().length() > 2 || picturePath!=null) {
            onActionCreateProfile(findViewById(R.id.activity_create_profile));
            String savedProfile = "Profile saved";
            Toast toast = Toast.makeText(this, savedProfile, Toast.LENGTH_SHORT);
        }
        else {
            super.onBackPressed();
        }
    }

}
