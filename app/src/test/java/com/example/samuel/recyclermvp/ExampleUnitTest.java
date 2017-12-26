package com.example.samuel.recyclermvp;

import com.example.samuel.recyclermvp.data.DataSourceInterface;
import com.example.samuel.recyclermvp.data.ListItem;
import com.example.samuel.recyclermvp.logic.Controller;
import com.example.samuel.recyclermvp.view.ViewInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

   @Mock
    DataSourceInterface data;

   @Mock
    ViewInterface viewInterface;

   Controller controller;

   private ListItem listItem = new ListItem("hello from mock","3/4/2017",R.color.RED);

   @Before
   public void setUpMocks(){
       MockitoAnnotations.initMocks(this);
       controller = new Controller(viewInterface,data);
   }

    @Test
    public void OnGetDataSuccessful (){

        ArrayList<ListItem> listItems = new ArrayList<>();
        listItems.add(listItem);

        Mockito.when(data.getListItems())
                .thenReturn(listItems);
        controller.getListItemsFromDataSource();

        Mockito.verify(viewInterface).setUpAdapterAndView(listItems);

    }
}