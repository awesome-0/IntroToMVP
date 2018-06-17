package com.example.samuel.recyclermvp.logic;

import android.util.Log;
import android.view.View;

import com.example.samuel.recyclermvp.data.DataSourceInterface;
import com.example.samuel.recyclermvp.data.FakeDataSource;
import com.example.samuel.recyclermvp.data.ListItem;
import com.example.samuel.recyclermvp.view.ViewInterface;

/**
 * Created by Samuel on 23/12/2017.
 */

public class Controller {
    private ListItem tempItem;
    private int tempItemPosition;
    private static final String TAG = "Controller";
    private ViewInterface view;
    private DataSourceInterface data;
  //  private FakeDataSource source;
    public Controller(ViewInterface view, DataSourceInterface data) {
        this.view = view;
        this.data = data;
       // source = new FakeDataSource();
        getListItemsFromDataSource();
    }

    public void getListItemsFromDataSource() {
        view.setUpAdapterAndView(data.getListItems());
      //  Log.d(TAG, "getListItemsFromDataSource: " + data.getListItems());
    }

    public void OnListItemClicked(ListItem item,View viewRoot) {
        view.startDetailsActivity(item,viewRoot);
    }

    public void createNewItem() {
        ListItem item = data.createNewListItem();
        view.addItemToAdapter(item);
    }

    public void OnListItemSwiped(int position, ListItem listItem) {
        data.deleteListItem(listItem);
        view.deleteItemAt(position);
        tempItem = listItem;
        tempItemPosition = position;
        view.showUndoSnackBar();
    }

    public void OnUndoConfirmed() {
        if(tempItem != null ){
            data.insertItem(tempItemPosition,tempItem);
            view.insertItemAt(tempItemPosition,tempItem);
            tempItemPosition = 0;
            tempItem = null;
        }
    }

    public void OnSnackBarDismissed() {
        tempItemPosition = 0;
        tempItem = null;
    }

    public void onDragging(int fromPosition, int toPosition) {
        data.swapItems(fromPosition,toPosition);
        view.doneSwitching(fromPosition,toPosition);
    }
}

