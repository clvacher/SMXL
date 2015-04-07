package com.aerolitec.SMXL.ui.activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.TabSizes;
import com.aerolitec.SMXL.model.UserClothes;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.fragment.dialog.ConfirmDialogFragment;

import java.util.ArrayList;

public class DisplayGarmentActivity extends Activity {

    private static UserClothes clothe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_garment);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            clothe = (UserClothes) extras.getSerializable("clothe");
        }

        if(clothe == null) {
            finish();
            return;
        }

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SummaryGarmentFragment())
                    .commit();
        }

        getActionBar().setTitle(clothe.getType() + " " + clothe.getBrand());
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

    public static class SummaryGarmentFragment extends Fragment implements ConfirmDialogFragment.ConfirmDialogListener {

        private ImageView imageSummary;
        private TextView textGarmentSummary;
        private TextView textBrandSummary;
        private TextView textSizes;
        private EditText editComments;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_add_garment_summary, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            editComments = (EditText) view.findViewById(R.id.editComments);
            imageSummary = (ImageView) view.findViewById(R.id.imageSummary);
            textGarmentSummary = (TextView) view.findViewById(R.id.textGarmentSummary);
            textBrandSummary = (TextView) view.findViewById(R.id.textBrandSummary);

            textSizes = (TextView) view.findViewById(R.id.textSizes);

            imageSummary.setImageResource(clothe.getCategory().getIcon());
            textGarmentSummary.setText(clothe.getType());
            textBrandSummary.setText(clothe.getBrand());

            textSizes.setText(formatedStingSizes(clothe.getSizes()));
            editComments.setText(clothe.getComment());


            getActivity().invalidateOptionsMenu();
        }

        private StringBuilder formatedStingSizes(ArrayList<TabSizes> tabsizes){
            StringBuilder sb = new StringBuilder("");
            if (tabsizes.size()==0)
                return sb;
            for (TabSizes ts : tabsizes){
                sb.append(ts.getPays()).append(" : ").append(String.valueOf(ts.getValeur())).append("\n");
            }
            return sb;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.edit_garment, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.delete:
                    ConfirmDialogFragment dialog = new ConfirmDialogFragment();
                    Bundle args = new Bundle();
                    args.putString("confirm", getResources().getString(R.string.remove));
                    args.putString("message", getResources().getString(R.string.remove_garment));
                    dialog.setArguments(args);
                    dialog.setTargetFragment(this, 42);
                    dialog.show(getFragmentManager(), "delete");
                    break;
                case R.id.validate:
                    clothe.setComment(editComments.getText().toString());
                    SMXL.get().getDataBase().updateUserGarment(clothe);
                    getActivity().finish();
                    break;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onConfirm(boolean confirmed, int id) {
            if(confirmed && getActivity() != null) {
                SMXL.get().getDataBase().removeUserGarment(clothe);
                getActivity().finish();
            }
        }
    }
}
