package com.aerolitec.SMXL.ui.listener;

import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

/**
 * Created by Jerome on 17/04/2015.
 */
public class MultiChoiceModeListener implements GridView.MultiChoiceModeListener {

    GridView mGrid;

    public MultiChoiceModeListener(GridView gv){
        super();
        mGrid=gv;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle("Select Items");
        mode.setSubtitle("One item selected");
        return true;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
    }

    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

        int selectCount = mGrid.getCheckedItemCount();
        switch (selectCount) {
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + selectCount + " items selected");
                break;
        }
    }

}

