package com.aerolitec.SMXL.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aerolitec.SMXL.R;
import com.aerolitec.SMXL.model.UserWishList;
import com.aerolitec.SMXL.tools.manager.UserManager;
import com.aerolitec.SMXL.ui.SMXL;
import com.aerolitec.SMXL.ui.activity.AddWishListActivity;
import com.aerolitec.SMXL.ui.adapter.UserWishListAdapter;
import com.aerolitec.SMXL.ui.fragment.dialog.ConfirmDialogFragment;

import java.io.File;
import java.util.ArrayList;


public class UserWishListFragment extends ListFragment {

    private ArrayList<UserWishList> arrayList;
    private UserWishListAdapter adapter;
    public UserWishListFragment() {
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrayList = SMXL.getUserWishListDBManager().getAllUserWishList(UserManager.get().getUser());
        adapter = new UserWishListAdapter(getActivity(), R.layout.item_wishlist, arrayList);
        setListAdapter(adapter);
        registerForContextMenu(getListView());
        if(arrayList.size() == 0){
            setEmptyText(getResources().getString(R.string.empty_wishlist));
        }


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        UserWishList userWishList = (UserWishList)l.getAdapter().getItem(position);
        Intent wishListIntent = new Intent(getActivity(), AddWishListActivity.class);
        wishListIntent.putExtra("wishlist", userWishList);
        startActivity(wishListIntent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Supprimer");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        if (item.getTitle().equals("Supprimer")) {
            SMXL.getUserWishListDBManager().deleteUserWishList(arrayList.get(position));
            arrayList.remove(position);
            adapter.notifyDataSetChanged();
        }
        return super.onContextItemSelected(item);
    }
}
