package com.example.samuel.recyclermvp.data;

import com.example.samuel.recyclermvp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Samuel on 23/12/2017.
 */

public class FakeDataSource implements DataSourceInterface {

    private static final int sizeOfCollection = 12;
    private Random random;

    public FakeDataSource() {
    }

    private final String[] datesAndTimes = {
            "6:30AM 06/01/2017",
            "9:26PM 04/22/2013",
            "2:01PM 12/02/2015",
            "2:43AM 09/7/2018",
    };

    private final String[] messages = {
            "Check out content like Fragmented Podcast to expose yourself to the knowledge, ideas, " +
                    "and opinions of experts in your field",
            "Look at Open Source Projects like Android Architecture Blueprints to see how experts" +
                    " design and build Apps",
            "Write lots of Code and Example Apps. Writing good Quality Code in an efficient manner "
                    + "is a Skill to be practiced like any other.",
            "If at first something doesn't make any sense, find another explanation. We all " +
                    "learn/teach different from each other. Find an explanation that speaks to you."
    };

    private final int[] drawables = {
            R.color.GREEN,
            R.color.RED,
            R.color.YELLOW,
            R.color.BLUE
    };
    @Override
    public List<ListItem> getListItems() {
        ArrayList<ListItem> ListItems = new ArrayList<>();
        for(int i = 0;i< 12 ; i++){

            ListItem item = new ListItem(messages[random.nextInt(4)],datesAndTimes[random.nextInt(4)]
                    ,drawables[random.nextInt(4)]);

            ListItems.add(item);
        }


        return ListItems;
    }
}
