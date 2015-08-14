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
import android.graphics.Point;
import android.graphics.Rect;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.Brand;
import com.aerolitec.SMXL.model.BrandSizeGuideMeasuresRow;
import com.aerolitec.SMXL.model.GarmentType;
import com.aerolitec.SMXL.model.User;
import com.aerolitec.SMXL.tools.Constants;
import com.aerolitec.SMXL.tools.UtilityMethodsv2;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.activity.AddWishListActivity;
import com.aerolitec.SMXL.ui.activity.ImageViewerActivity;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
    private static final String ARG_UPDATE = "update";
    private static final String ARG_IMAGE_URI = "image_URI";

    protected static final int CHOOSE_CAMERA_GALLERY = 5;

    private User user;
    private GarmentType selectedGarmentType;
    private Brand selectedBrand;
    private BrandSizeGuideMeasuresRow selectedRow;

    private ImageView imgWishList,addImage;
    private TextView tvCategoryGarment,tvBrand,tvSize;

    private Uri outputFileUri;
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

        View view = inflater.inflate(R.layout.fragment_add_wishlist, container, false);

        imgWishList = (ImageView) view.findViewById(R.id.imgWishList);
        addImage = (ImageView) view.findViewById(R.id.addImage);
        tvCategoryGarment = (TextView) view.findViewById(R.id.tv_category_garment);
        tvBrand = (TextView) view.findViewById(R.id.tv_brand);
        tvSize = (TextView) view.findViewById(R.id.tv_size);


        user = UserManager.get().getUser();
        if(user == null) {
            getActivity().finish();
        }


        Bundle extras =  getArguments();
        if(extras!=null){
            selectedGarmentType = (GarmentType) extras.get(ARG_GARMENT);
            selectedBrand = (Brand) extras.get(ARG_BRAND);
            selectedRow = (BrandSizeGuideMeasuresRow) extras.get(ARG_SIZE);

            tvCategoryGarment.setText(selectedGarmentType.getType());
            tvBrand.setText(selectedBrand.getBrand_name());


            if(extras.containsKey(ARG_UPDATE)){
                existWishListItem();
            }
            else
            {
                ((AddWishListActivity)getActivity()).setValueValidation(true);
            }

            /*(view.findViewById(R.id.layoutImageProfil)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openImageIntent();
                }
            });
*/
            imgWishList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (outputFileUri != null) {
                        Intent intent = new Intent(getActivity(), ImageViewerActivity.class);
                        intent.putExtra(ARG_IMAGE_URI, outputFileUri);
                        startActivity(intent);
                    }
                }
            });
            addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openImageIntent();
                }
            });


            Fragment fragment = SelectSizeFragment.newInstance(selectedGarmentType,selectedBrand,selectedRow);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.containerSize, fragment)
                    .commit();
        }
        return view;
    }

    public void saveToWishList() {

    }

    public void updateWishList() {

    }

    private void existWishListItem(){
        ((AddWishListActivity)getActivity()).setValueUpdate(true);
    }

    public void setSize(BrandSizeGuideMeasuresRow measuresRow,String selectedColumn) {
        selectedRow = measuresRow;
        HashMap<String,String > map = selectedRow.getCorrespondingSizes();
        tvSize.setText(map.get(selectedColumn));
    }

    protected void openImageIntent() {

        // Determine Uri of camera image to save.
        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root, "SMXL");
        myDir.mkdirs();
       // try {
            //File tmpFile = File.createTempFile("smxl_wishlist_item",".png");
            File tmpFile = new File(myDir,"test.png");
           // File tmpFile = new File(getActivity().getFilesDir(), "wishlist_tmp.png");

            outputFileUri = Uri.fromFile(tmpFile);
        //} catch (IOException e) {
        //    Log.d(Constants.TAG,"Couldn't create temporary file");
        //}


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

    private void showImage(Uri fileUri) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = UtilityMethodsv2.calculateInSampleSize(fileUri.getPath(), imgWishList.getWidth(), imgWishList.getHeight());
        Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),options);
        imgWishList.setImageBitmap(bitmap);
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
}
