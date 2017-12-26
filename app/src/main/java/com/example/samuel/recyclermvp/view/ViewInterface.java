package com.example.samuel.recyclermvp.view;

import android.view.View;

import com.example.samuel.recyclermvp.data.ListItem;

import java.util.List;

/**
 * Created by Samuel on 23/12/2017.
 */

public interface ViewInterface {
    void startDetailsActivity(ListItem item,View ViewRoot);

    void  setUpAdapterAndView(List<ListItem> listItems);

    void addItemToAdapter(ListItem item);

    void deleteItemAt(int position);

    void showUndoSnackBar();

    void insertItemAt(int tempItemPosition, ListItem tempItem);
}
