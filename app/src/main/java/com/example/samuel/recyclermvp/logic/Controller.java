package com.example.samuel.recyclermvp.logic;

import android.util.Log;

import com.example.samuel.recyclermvp.data.DataSourceInterface;
import com.example.samuel.recyclermvp.data.FakeDataSource;
import com.example.samuel.recyclermvp.data.ListItem;
import com.example.samuel.recyclermvp.view.ViewInterface;

/**
 * Created by Samuel on 23/12/2017.
 */

public class Controller {
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

    public void OnListItemClicked(ListItem item) {
        view.startDetailsActivity(item);
    }

    public void createNewItem() {
        ListItem item = data.createNewListItem();
        view.addItemToAdapter(item);
    }
}

