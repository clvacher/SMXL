package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;

public class AddMeasureActivity extends Activity {
    //FIXME FIXME FIXME
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_measure_fragment);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        getActionBar().setTitle(getResources().getString(R.string.add_measure));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    /*
    public static class PlaceholderFragment extends Fragment {

        private static final int ID_HEAD = 1;
        private static final int ID_SIZE = 2;
        private static final int ID_COLLAR = 3;
        private static final int ID_SHOULDERS = 4;
        private static final int ID_CHEST = 5;
        private static final int ID_ARM = 6;
        private static final int ID_WAIST = 7;
        private static final int ID_HIPS = 8;
        private static final int ID_THIGH = 9; //CUISSE
        private static final int ID_LEGS = 10;
        private static final int ID_FOOT = 11;
        private static final int ID_WEIGHT = 12;
        private static final int ID_SHOE_SIZE = 13;

        private TextView man;
        private TextView woman;
        private ImageView silhouette;
        private User user;
        private static ArrayList<converter> convertSizeShoeMen;
        private static ArrayList<converter> convertSizeShoeWomen;
        private double coeff;
        private ArrayList<MeasureItem> measureItems;
        private ArrayList<MeasureItem> local;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_silouhette, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            user = UserManager.get().getUser();

            if(user == null) {
                getActivity().finish();
                return;
            }

            measureItems = new ArrayList<>();

            man = (TextView) view.findViewById(R.id.man);
            woman = (TextView) view.findViewById(R.id.woman);
            silhouette = (ImageView) view.findViewById(R.id.fond);

            if(user.getSexe().equals("H")) {
                silhouette.setImageResource(R.drawable.homme_pastilles);
                woman.setTextColor(getResources().getColor(R.color.EditTextBorder));
                man.setTextColor(getResources().getColor(R.color.SectionTitle));
            }

            man.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    silhouette.setImageResource(R.drawable.homme_pastilles);
                    woman.setTextColor(getResources().getColor(R.color.EditTextBorder));
                    man.setTextColor(getResources().getColor(R.color.SectionTitle));
                }
            });

            woman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    silhouette.setImageResource(R.drawable.femme_pastilles);
                    man.setTextColor(getResources().getColor(R.color.EditTextBorder));
                    woman.setTextColor(getResources().getColor(R.color.SectionTitle));
                }
            });

            setConvertSizes();

            local = null;
            if(savedInstanceState != null) {
                local = (ArrayList<MeasureItem>) savedInstanceState.getSerializable("measures");

            }

            if(local != null && local.size() > 0) {
                measureItems.clear();
                measureItems.addAll(local);
            } else {
                loadMeasures();
            }

            handleClicks();

        }

        private void handleClicks() {

            View view = getView();

            if(view != null) {
                view.findViewById(R.id.textHauteur).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_SIZE);
                    }
                });
                view.findViewById(R.id.textBras).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_ARM);
                    }
                });

                view.findViewById(R.id.textCou).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_COLLAR);
                    }
                });

                view.findViewById(R.id.textHanches).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_HIPS);
                    }
                });

                view.findViewById(R.id.textTaille).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_WAIST);
                    }
                });

                view.findViewById(R.id.textJambes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_LEGS);
                    }
                });

                view.findViewById(R.id.textPieds).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_SHOE_SIZE);
                    }
                });

                view.findViewById(R.id.textPoitrine).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askForUpdate(ID_CHEST);
                    }
                });

            }


        }

        private void loadMeasures(){
            // load all the profiles from the database

            User user = SMXL.getUserDBManager().getUser(this.user.getId_user());
            // If unit is inch, then convert from cm to inch
            coeff = 1D;
            double coeffW = 1D;
            String unit[] = {"Cm", "Kg"};
            if (user.getUnitLength() == Constants.INCH){
                coeff = Constants.inch;
                unit[0] = "Inch";
            }
            if (user.getUnitWeight() == Constants.POUNDS){
                coeffW = Constants.pounds;
                unit[1] = "Lbs";
            }
            measureItems.add(new MeasureItem(ID_HEAD, getResources().getString(R.string.libSize) + " (" + unit[0]+" )", 0));

            measureItems.add(new MeasureItem(ID_SIZE, getResources().getString(R.string.libSize) + " (" + unit[0]+" )", user.getSize()/coeff));
            measureItems.add(new MeasureItem(ID_WEIGHT, getResources().getString(R.string.libWeight) + " (" + unit[1]+")", user.getWeight()/coeffW));
            measureItems.add(new MeasureItem(ID_COLLAR, getResources().getString(R.string.libCollar) + " (" + unit[0]+" )", user.getCollar()/coeff));
            if (user.getSexe().startsWith("F")) {
                measureItems.add(new MeasureItem(ID_CHEST, getResources().getString(R.string.libBust) + " (" + unit[0]+" )", user.getBust() / coeff));
            }
            if (user.getSexe().startsWith("H")) {
                measureItems.add(new MeasureItem(ID_CHEST, getResources().getString(R.string.libBust) + " (" + unit[0]+" )", user.getChest() / coeff));
            }
            measureItems.add(new MeasureItem(ID_WAIST, getResources().getString(R.string.libWaist) + " (" + unit[0]+" )", user.getWaist()/coeff));
            measureItems.add(new MeasureItem(ID_HIPS, getResources().getString(R.string.libHips) + " (" + unit[0]+" )", user.getHips()/coeff));
            measureItems.add(new MeasureItem(ID_LEGS, getResources().getString(R.string.libInseam) + " (" + unit[0]+" )", user.getInseam()/coeff));
            measureItems.add(new MeasureItem(ID_ARM, getResources().getString(R.string.libSleeve) + " (" + unit[0]+" )", user.getSleeve()/coeff));
            measureItems.add(new MeasureItem(ID_FOOT, getResources().getString(R.string.libFoot) + " (" + unit[0]+" )", user.getFeet()/coeff));
            measureItems.add(new MeasureItem(ID_SHOE_SIZE, getResources().getString(R.string.ShoeSize), computePointure(user.getFeet())));
        }

        public double computePointure(double sizeCm){
            double pointure = 0D;

            if(sizeCm != 0) {
                sizeCm += 1;
                pointure = sizeCm * 1.5;
                pointure = Math.round(pointure);
            }

            return pointure;
        }

        public double computeSizeCm(double pointure){
            double sizeCm = 0D;

            if(pointure != 0) {
                sizeCm = pointure / 1.5;
                sizeCm -= 1;
                sizeCm *= 100;
                sizeCm = Math.round(sizeCm);
                sizeCm /= 100;
            }
            return sizeCm/coeff;
        }

        private void setConvertSizes() {
            convertSizeShoeMen = new ArrayList<>();
            convertSizeShoeMen.add(new converter(23,37));
            convertSizeShoeMen.add(new converter(23.5,37.5));
            convertSizeShoeMen.add(new converter(24,38));
            convertSizeShoeMen.add(new converter(24.5,38.5));
            convertSizeShoeMen.add(new converter(25,39));
            convertSizeShoeMen.add(new converter(25.5,40));
            convertSizeShoeMen.add(new converter(26,40.5));
            convertSizeShoeMen.add(new converter(26.5,41));
            convertSizeShoeMen.add(new converter(27,42));
            convertSizeShoeMen.add(new converter(27.5,42.5));
            convertSizeShoeMen.add(new converter(28,43));
            convertSizeShoeMen.add(new converter(28.5,44));
            convertSizeShoeMen.add(new converter(29,44.5));
            convertSizeShoeMen.add(new converter(29.5,45));
            convertSizeShoeMen.add(new converter(30,46));
            convertSizeShoeMen.add(new converter(30.5,46.5));
            convertSizeShoeMen.add(new converter(31,47));
            convertSizeShoeMen.add(new converter(31.5,48));
            convertSizeShoeMen.add(new converter(32,48.5));
            convertSizeShoeMen.add(new converter(32.5,49));
            convertSizeShoeMen.add(new converter(33,50));
            convertSizeShoeMen.add(new converter(33.5,51));
            convertSizeShoeMen.add(new converter(34,51.5));
            convertSizeShoeMen.add(new converter(35,52));

            convertSizeShoeWomen = new ArrayList<>();
            convertSizeShoeWomen.add(new converter(21,35));
            convertSizeShoeWomen.add(new converter(21.5,35.5));
            convertSizeShoeWomen.add(new converter(22.5,36));
            convertSizeShoeWomen.add(new converter(23,37));
            convertSizeShoeWomen.add(new converter(23.5, 37.5));
            convertSizeShoeWomen.add(new converter(24, 38));
            convertSizeShoeWomen.add(new converter(24.5, 38.5));
            convertSizeShoeWomen.add(new converter(25, 39));
            convertSizeShoeWomen.add(new converter(25.5, 40));
            convertSizeShoeWomen.add(new converter(26, 40.5));
            convertSizeShoeWomen.add(new converter(26.5, 41));
            convertSizeShoeWomen.add(new converter(27, 42));
            convertSizeShoeWomen.add(new converter(27.5, 42.5));
            convertSizeShoeWomen.add(new converter(28, 43));
            convertSizeShoeWomen.add(new converter(28.5, 44));
            convertSizeShoeWomen.add(new converter(29, 44.5));
            convertSizeShoeWomen.add(new converter(29.5, 45));
        }

        private void askForUpdate(final int id){

            final MeasureItem item = getItemFromId(id);

            if(item != null) {
                LayoutInflater li = LayoutInflater.from(getActivity());
                View measureDialog = li.inflate(R.layout.measure_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(measureDialog);
                ((TextView) measureDialog.findViewById(R.id.textView1)).setText(item.getTypeMeasure());
                final EditText userInput = (EditText) measureDialog.findViewById(R.id.etDialogUserInput);
                if (item.getValueMeasure() == 0){
                    userInput.setText("");
                }
                else {
                    userInput.setText(String.valueOf(item.getValueMeasure()));
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double value = 0;
                        if (userInput.getText().toString().length() > 0) {
                            value = Double.valueOf(userInput.getText().toString());
                        }
                        item.setValueMeasure(value);
                        if (id == ID_FOOT) {
                            for(int j = 0; j < measureItems.size(); j++) {
                                if(measureItems.get(j).getId() == ID_SHOE_SIZE) {
                                    measureItems.get(j).setValueMeasure(computePointure(value));
                                    break;
                                }
                            }
                        } else if (id == ID_SHOE_SIZE) {
                            for(int j = 0; j < measureItems.size(); j++) {
                                if(measureItems.get(j).getId() == ID_FOOT) {
                                    measureItems.get(j).setValueMeasure(computeSizeCm(value));
                                    break;
                                }
                            }
                        }

                        local = measureItems;
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

        private MeasureItem getItemFromId(int id) {
            for(int i = 0; i < measureItems.size(); i++) {
                if(measureItems.get(i).getId() == id) {
                    return measureItems.get(i);
                }
            }

            return null;
        }


        public void saveModifications(){
            // Store all the values validated by the user
            // if user unit = INCH then store value in Cm
            double coeff = 1D;
            double coeffW = 1D;
            if (user.getUnitLength()==Constants.INCH){
                coeff = Constants.inch;
            }
            if (user.getUnitWeight()==Constants.POUNDS){
                coeffW = Constants.pounds;
            }
            user.setSize(getItemFromId(ID_SIZE).getValueMeasure() * coeff);
            user.setWeight(getItemFromId(ID_WEIGHT).getValueMeasure() * coeffW);
            user.setCollar(getItemFromId(ID_COLLAR).getValueMeasure() * coeff);
            user.setBust(getItemFromId(ID_CHEST).getValueMeasure() * coeff);
            user.setChest(getItemFromId(ID_CHEST).getValueMeasure() * coeff);
            user.setWaist(getItemFromId(ID_WAIST).getValueMeasure() * coeff);
            user.setHips(getItemFromId(ID_HIPS).getValueMeasure() * coeff);
            user.setInseam(getItemFromId(ID_LEGS).getValueMeasure() * coeff);
            user.setSleeve(getItemFromId(ID_ARM).getValueMeasure() * coeff);
            user.setFeet(getItemFromId(ID_FOOT).getValueMeasure() * coeff);
            user.setPointure(getItemFromId(ID_SHOE_SIZE).getValueMeasure() * coeff);
            SMXL.getUserDBManager().updateUser(user);
            UserManager.get().setUser(user);
        }

        private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight){
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;

            if (height > reqHeight || width > reqWidth){
                final int heightRatio = Math.round((float)height / (float)reqHeight);
                final int widthRatio = Math.round((float)width / (float)reqWidth);
                inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
            }
            return inSampleSize;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Do something that differs the Activity's menu here
            inflater.inflate(R.menu.validate, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.validate :
                    saveModifications();
                    getActivity().finish();
                    break;
                default:
                    break;
            }

            return super.onOptionsItemSelected(item);
        }
    }

    static class converter {

        double sizeCm;
        double pointure;

        converter() {
        }

        converter(double sizeCm, double pointure) {
            this.sizeCm = sizeCm;
            this.pointure = pointure;
        }

        public double getSizeCm() {
            return sizeCm;
        }

        public void setSizeCm(double sizeCm) {
            this.sizeCm = sizeCm;
        }

        public double getPointure() {
            return pointure;
        }

        public void setPointure(double pointure) {
            this.pointure = pointure;
        }
    }
    */
}
