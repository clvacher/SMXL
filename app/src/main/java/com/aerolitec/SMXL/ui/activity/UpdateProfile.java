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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.ImageHelper;
import com.aerolitec.SMXL.tools.colorPicker.ColorPickerDialog;
import com.aerolitec.SMXL.tools.colorPicker.ColorPickerSwatch;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.widget.RoundedTransformation;
import com.makeramen.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by stephaneL on 31/03/14.
 */
public class UpdateProfile extends Activity {
    private static final int CROP_IMAGE = 2;

    EditText etPseudo, etFirstName, etLastName, etDescription;
    //DatePicker dpBirthday;
    RadioGroup radioSexe;
    RoundedImageView imgProfil;
    private String picturePath;
    private User user;
    Button datePickerButton;
    String birthday;
    RelativeLayout addColor;
    ArrayList<Integer> listColors;
    private Uri cropImagePath;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        user = UserManager.get().getUser();

        if(user == null) {
            finish();
            return;
        }

        getActionBar().setTitle(user.getNickname());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);

        listColors = new ArrayList<>();

        etPseudo = (EditText)findViewById(R.id.etPseudo);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etDescription = (EditText) findViewById(R.id.etDescriptionProfil);
        radioSexe = (RadioGroup) findViewById(R.id.radioSexe);
        RadioButton radioH = (RadioButton) findViewById(R.id.radioMale);
        RadioButton radioF = (RadioButton) findViewById(R.id.radioFemale);
        imgProfil = (RoundedImageView) findViewById(R.id.imgProfil);
        datePickerButton = (Button) findViewById(R.id.buttonBirthday);
        addColor = (RelativeLayout) findViewById(R.id.addColor);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog();
            }
        });
        addColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listColors.size() < 4)
                    openColorPickerDialog();
                else
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.too_many_color), Toast.LENGTH_LONG)
                            .show();
            }
        });
        birthday = user.getBirthday();
        datePickerButton.setText(birthday);
        etDescription.setText(user.getDescription());
        etPseudo.setText(user.getNickname());
        etFirstName.setText(user.getFirstname());
        etLastName.setText(user.getLastname());
        addColorToList();
        if (user.getSexe().startsWith("H")){
            radioH.setChecked(true);
        }
        else {
            radioF.setChecked(true);
        }
        picturePath = user.getAvatar();
        int width = imgProfil.getLayoutParams().width;
        try {
            File file = new File(picturePath);
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
        }
        catch (Exception e){
            Log.e(Constants.TAG,"Error converting picture in File : " + e.getMessage());
        }

        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectProfilPicture();
            }
        });

        /*
        if(listColors.size() == 0){
            ((LinearLayout) findViewById(R.id.layoutColorChooser)).setVisibility(View.GONE);
        } else {
            updateUI();
        }
        */

        for(int i = 0 ; i < listColors.size(); i++){
            if(!listColors.get(i).equals(0)){
                ((LinearLayout) findViewById(R.id.layoutColorChooser)).setVisibility(View.VISIBLE);
                updateUI();
            }
        }

    }

    private void addColorToList() {
        if(user.getFavoriteColor1() != 0) {
            listColors.add(user.getFavoriteColor1());
        }
        if(user.getFavoriteColor2() != 0) {
            listColors.add(user.getFavoriteColor2());
        }
        if(user.getFavoriteColor3() != 0) {
            listColors.add(user.getFavoriteColor3());
        }
        if(user.getFavoriteColor4() != 0) {
            listColors.add(user.getFavoriteColor4());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.validate, menu);
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
            if (etPseudo.getText().toString().length()<2 ||
                etFirstName.getText().toString().length() < 2 ||
                etLastName.getText().toString().length() < 2){
                Toast.makeText(this,"Vous devez indiquer pseudo, prénom et nom",Toast.LENGTH_LONG).show();
            }
            else {
                String sexe = "F";
                int idRadioButton = radioSexe.getCheckedRadioButtonId();
                if (idRadioButton == -1){
                    //no item selected
                }
                else{
                    if (idRadioButton == R.id.radioMale){
                        sexe = "H";
                    }
                }
                while(listColors.size() != 4){
                    listColors.add(0);
                }
                user.setAvatar(picturePath);
                user.setBirthday(birthday);
                user.setFirstname(etFirstName.getText().toString());
                user.setLastname(etLastName.getText().toString());
                user.setNickname(etPseudo.getText().toString());
                user.setDescription(etDescription.getText().toString());
                user.setFavoriteColor1(listColors.get(0));
                user.setFavoriteColor2(listColors.get(1));
                user.setFavoriteColor3(listColors.get(2));
                user.setFavoriteColor4(listColors.get(3));
                user.setSexe(sexe);

                try {
                    SMXL.get().getDataBase().updateUser(user);
                    Log.d(Constants.TAG, "Profile updated");
                }
                catch (Exception e) {
                   Log.d(Constants.TAG,"Update user with error : " + e.getMessage());
                }
                finish();
            }
        }

        if( id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void openColorPickerDialog() {
        int[] mColors = Constants.ColorUtils.colorChoice(this);
        ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title, mColors,
                listColors, 4,
                Constants.isTablet(this) ? ColorPickerDialog.SIZE_LARGE
                        : ColorPickerDialog.SIZE_SMALL
        );

        colorcalendar.setOnColorSelectedListener(colorcalendarListener);
        colorcalendar.show(getFragmentManager(), "cal");

    }

    // Implement listener to get selected color value
    ColorPickerSwatch.OnColorSelectedListener colorcalendarListener = new ColorPickerSwatch.OnColorSelectedListener() {

        @Override
        public void onColorSelected(int color) {
            if(!listColors.contains(color) && listColors.size() < 4) {
                listColors.add((Integer) color);
            } else if(listColors.contains(color)) {
                listColors.remove((Integer) color);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.too_many_color), Toast.LENGTH_SHORT).show();
            }

            updateUI();
        }
    };

    private void updateUI() {

        ((LinearLayout) findViewById(R.id.layoutColorChooser)).setVisibility(View.VISIBLE);
        ((LinearLayout) findViewById(R.id.layoutColorChooser)).removeAllViews();
        ((LinearLayout) findViewById(R.id.layoutColorChooser)).removeAllViewsInLayout();

        for (int i = 0; i < listColors.size(); i++) {

            final View viewToLoad = LayoutInflater.from(
                    getApplicationContext()).inflate(
                    R.layout.item_layout_color, null);
            final ImageView color = (ImageView) viewToLoad.findViewById(R.id.circleColor);
            color.setBackgroundColor(listColors.get(i));
            ((LinearLayout) findViewById(R.id.layoutColorChooser))
                    .addView(viewToLoad);
            final int position = i;
            viewToLoad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openColorPickerDialog();
                }
            });
        }

        if(listColors.size() >=  4){
            addColor.setVisibility(View.GONE);
        } else
            addColor.setVisibility(View.VISIBLE);
    }

    private void openDatePickerDialog(){
        final Calendar c = Calendar.getInstance();
        int birthdayYear = c.get(Calendar.YEAR);
        int birthdayMonth = (c.get(Calendar.MONTH))+1;
        int birthdayDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int birthdayYear,
                                          int birthdayMonth, int birthdayDay) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(""+String.valueOf((100+birthdayDay)).substring(1,3)).append("-")
                                .append((String.valueOf(100+(birthdayMonth+1))).substring(1,3)).append("-").append(birthdayYear);
                        birthday = sb.toString();
                        datePickerButton.setText(birthday);

                    }
                }, birthdayYear, birthdayMonth, birthdayDay);
        dpd.show();
    }

    private void selectProfilPicture(){
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i,77);
    }

    @Override
    public void onActivityResult(int request_code, int result_code, Intent datas) {
        if (request_code == 77) {    // Pick a picture in gallery
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

}
