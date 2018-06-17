package com.example.samuel.recyclermvp.data;

import java.util.List;

/**
 * Created by Samuel on 23/12/2017.
 */

public interface DataSourceInterface {
    List<ListItem> getListItems();

    ListItem createNewListItem();

    void deleteListItem(ListItem listItem);

    void insertItem(int tempItemPosition, ListItem tempItem);

    void swapItems(int fromPosition, int toPosition);
}
