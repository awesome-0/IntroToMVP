package com.example.samuel.recyclermvp.view;

import com.example.samuel.recyclermvp.data.ListItem;

import java.util.List;

/**
 * Created by Samuel on 23/12/2017.
 */

public interface ViewInterface {
    void startDetailsActivity(ListItem item);

    void  setUpAdapterAndView(List<ListItem> listItems);

    void addItemToAdapter(ListItem item);
}
