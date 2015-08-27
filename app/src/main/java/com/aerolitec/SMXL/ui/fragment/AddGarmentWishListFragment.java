package com.aerolitec.SMXL.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.model.UserWishList;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddWishListActivity;
import com.aerolitec.SMXL.ui.activity.ImageViewerActivity;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nelson on 10/08/2015.
 */
public class AddGarmentWishListFragment extends Fragment
{

    private static final String ARG_GARMENT = "garmentType";
    private static final String ARG_BRAND = "brand";
    private static final String ARG_SIZE = "size";
    private static final String ARG_IMAGE_URI = "picture";
    private static final String ARG_WISHLIST = "wishlist";

    protected static final int CHOOSE_CAMERA_GALLERY = 5;

    private UserWishList userWishlist;
    private User user;
    //private GarmentType selectedGarmentType;
    //private Brand selectedBrand;
    private BrandSizeGuideMeasuresRow selectedRow;
    //private String selectedCountry;
    private ArrayList<BrandSizeGuideMeasuresRow> arrayList;
    private int selectedIndex;

    private ImageView addImage,imgExtended;
    private TextView tvCategoryGarment,tvBrand,tvSize;
    private Button higherSize,lowerSize;

    private Uri outputFileUri;
    private boolean validation=false;

    public static AddGarmentWishListFragment newInstance(GarmentType garmentType,Brand brand,HashMap<String,String> size) {
        AddGarmentWishListFragment f = new AddGarmentWishListFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_GARMENT,garmentType);
        args.putSerializable(ARG_BRAND,brand);
        args.putSerializable(ARG_SIZE,size);
        f.setArguments(args);

        return f;
    }

    public AddGarmentWishListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_add_wishlist_new, container, false);
        addImage = (ImageView) view.findViewById(R.id.addImage);
        tvCategoryGarment = (TextView) view.findViewById(R.id.tv_category_garment);
        tvBrand = (TextView) view.findViewById(R.id.tv_brand);
        tvSize = (TextView) view.findViewById(R.id.tv_size);
        imgExtended = (ImageView) view.findViewById(R.id.iv_extended);
        higherSize = (Button) view.findViewById(R.id.buttonHigherSize);
        lowerSize = (Button) view.findViewById(R.id.buttonLowerSize);

        user = UserManager.get().getUser();
        if(user == null) {
            getActivity().finish();
        }


        Bundle extras =  getArguments();
        if(extras!=null){
            if(extras.containsKey(ARG_WISHLIST)){
                userWishlist = (UserWishList) extras.get(ARG_WISHLIST);

                selectedRow = SMXL.getBrandSizeGuideDBManager()
                        .getBrandSizeGuideMeasureRowsByBrandAndGarmentTypeAndCountryAndSize(userWishlist.getBrand(),userWishlist.getGarmentType(),userWishlist.getCountrySelected(),userWishlist.getSize()).get(0);

                ((AddWishListActivity) getActivity()).setValueUpdate(true);
                showImage(Uri.parse(userWishlist.getPicture()));
            }
            else
            {

                selectedRow = (BrandSizeGuideMeasuresRow) extras.get(ARG_SIZE);

                userWishlist = new UserWishList();
                userWishlist.setBrand((Brand) extras.get(ARG_BRAND));
                userWishlist.setGarmentType((GarmentType) extras.get(ARG_GARMENT));
                userWishlist.setCountrySelected(findSizeType());

                validation = true;
            }
            userWishlist.setUser(user);
            tvCategoryGarment.setText(userWishlist.getGarmentType().getType());
            tvBrand.setText(userWishlist.getBrand().getBrand_name());

            arrayList = filterArray(SMXL.getBrandSizeGuideDBManager().getBrandSizeGuideMeasureRowsByBrandAndGarmentType(userWishlist.getBrand(), userWishlist.getGarmentType()));
            selectedIndex = findSelectedIndex();
            setSize(selectedIndex);

            /*
            imgExtended.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (outputFileUri != null) {
                        Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
                        intent.putExtra(ARG_IMAGE_URI, outputFileUri);
                        startActivity(intent);
                    }
                }
            });*/
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageIntent();
                }
            });

            higherSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedIndex < arrayList.size()-1){
                        selectedIndex++;
                        setSize(selectedIndex);
                    }
                }
            });
            lowerSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectedIndex > 0) {
                        selectedIndex--;
                        setSize(selectedIndex);
                    }
                }
            });
        }
        return view;
    }

    private int findSelectedIndex() {
        String selectedCountry = userWishlist.getCountrySelected();
        String compare = selectedRow.getCorrespondingSizes().get(selectedCountry);
        for(int i=0;i<arrayList.size();i++){
            HashMap<String,String> map = arrayList.get(i).getCorrespondingSizes();
            if(map.get(selectedCountry).equals(compare)){
                return i;
            }
        }
        return 0;
    }

    public void saveToWishList() {
        Log.d(Constants.TAG, "Save");
        if(userWishlist.getPicture() != null){
            File source = new File(userWishlist.getPicture());
            if(source.exists()){
                String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                File myDir = new File(root, "SMXL");
                myDir.mkdirs();
                long lastId = SMXL.getUserWishListDBManager().getLastId();
                File destination = new File(myDir,"item_"+ lastId +".png");
                try {
                    Log.d(Constants.TAG, "Copying File ... ");
                    copyFile(source,destination);
                    source.delete();
                    userWishlist.setPicture(destination.getPath());
                } catch (IOException e) {
                    Log.d(Constants.TAG,"File couldn't be copied");
                }
            }
        }
        SMXL.getUserWishListDBManager().addUserWishList(userWishlist);

        Log.d(Constants.TAG, "Save Finished");
    }

    public void updateWishList() {
        Log.d(Constants.TAG,"Update");
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root, "SMXL");
        myDir.mkdirs();
        File tmpFile = new File(myDir,"temp.png");
        if(tmpFile.exists()){
            try {
                File destination = new File(myDir, "item_" + userWishlist.getId_user_wishlist() + ".png");
                copyFile(tmpFile, destination);
                userWishlist.setPicture(destination.getPath());
            }catch (IOException e) {
                Log.d(Constants.TAG,"File couldn't be copied");
            }
        }
        SMXL.getUserWishListDBManager().updateUserWishList(userWishlist);
    }

    public void setSize(int index) {
        selectedRow = arrayList.get(index);
        HashMap<String,String > map = selectedRow.getCorrespondingSizes();
        tvSize.setText(map.get(userWishlist.getCountrySelected()));
        userWishlist.setSize(map.get(userWishlist.getCountrySelected()));
    }

    protected void openImageIntent() {

        // Determine Uri of camera image to save.
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root, "SMXL");
        myDir.mkdirs();
        File tmpFile = new File(myDir,"temp.png");
        outputFileUri = Uri.fromFile(tmpFile);



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
                if (isCamera) {
                    try {
                        File file = new File(outputFileUri.getPath());
                        if (file.exists()) {
                            showImage(outputFileUri);
                        }
                    } catch (Exception e) {
                        Log.e(Constants.TAG, "Error converting Picture in File " + e.getMessage());
                    }
                } else {

                    Uri selectedImageUri = data.getData();
                    String fpCol[] = {MediaStore.MediaColumns.DATA};
                    Cursor c = getActivity().getContentResolver().query(selectedImageUri, fpCol, null, null, null);
                    String picturePath = "";
                    if (c.moveToFirst()) {
                        int columnIndex = c.getColumnIndex(fpCol[0]);
                        picturePath = c.getString(columnIndex);
                    }
                    c.close();

                    try {
                        File file = new File(picturePath);
                        if (file.exists()) {
                            Uri uri = Uri.fromFile(file);
                            showImage(uri);
                        }
                    } catch (Exception e) {
                        Log.e(Constants.TAG, "Error converting Picture in File while trying to get a image from the gallery" + e.getMessage());
                    }
                }
            }
        }
    }

    private void copyFile(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        FileInputStream fileInputStreamSource = null;
        FileOutputStream fileOutputStreamDest = null;
        try {
            fileInputStreamSource = new FileInputStream(source);
            fileOutputStreamDest =  new FileOutputStream(dest);
            inputChannel = fileInputStreamSource.getChannel();
            outputChannel = fileOutputStreamDest.getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    private String findSizeType() {
        HashMap<String,String> selectedSize = selectedRow.getCorrespondingSizes();
        if (selectedSize.containsKey("SMXL")  && !selectedSize.get("SMXL").equals("")) {
            return "SMXL";
        } else if (selectedSize.containsKey("UE") && !selectedSize.get("UE").equals("")) {
            return "UE";
        } else {
            for (Map.Entry<String, String> entry : selectedSize.entrySet()) {
                if (selectedSize.get(entry.getKey()).equals("")) {
                    return entry.getKey();
                }
            }
            //Ne devrait jamais passer par la
            return null;
        }
    }

    private ArrayList<BrandSizeGuideMeasuresRow> filterArray(ArrayList<BrandSizeGuideMeasuresRow> arrayToFilter){
        Iterator<BrandSizeGuideMeasuresRow> iterator = arrayToFilter.iterator();
        ArrayList<String> containedSize = new ArrayList<>();
        String sizeCountry = findSizeType();
        while(iterator.hasNext()){
            HashMap<String,String> rowMeasures = iterator.next().getCorrespondingSizes();
            String size = rowMeasures.get(sizeCountry);
            if(containedSize.contains(size)){
                iterator.remove();
            }
            else {
                containedSize.add(size);
            }
        }
        return arrayToFilter;
    }

    private Bitmap rotate(Bitmap src, float degree) {
        // create new matrix
        Matrix matrix = new Matrix();
        // setup rotation degree
        matrix.postRotate(degree);
        // return new bitmap rotated using matrix
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    private void showImage(Uri fileUri) {
        userWishlist.setPicture(fileUri.getPath());
        // Get Image Width and Height
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileUri.getPath(), options);
        // Get the bitmap
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath());
        //rotate if needed
        if(bitmap.getWidth() > bitmap.getHeight()){
            bitmap = rotate(bitmap,90);
        }
        imgExtended.setImageBitmap(bitmap);
        // show bitmap
        imgExtended.setVisibility(View.VISIBLE);
        if(validation) {
            ((AddWishListActivity) getActivity()).setValidation(validation);
        }
    }
}
