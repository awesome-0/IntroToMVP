package com.example.samuel.recyclermvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samuel.recyclermvp.R;
import com.example.samuel.recyclermvp.data.ListItem;

public class DetailsActivity extends AppCompatActivity {
    private TextView msg,date;
    private ImageView color;



    ListItem items ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        msg = findViewById(R.id.item_caption);
        date = findViewById(R.id.item_description);
        color = findViewById(R.id.item_image);

        if(getIntent() != null){
            items = (ListItem) getIntent().getSerializableExtra("item");
            addToScreen(items);
        }

    }

    private void addToScreen(ListItem items) {
        msg.setText(items.getMessage());
        date.setText(items.getDateTime());
        color.setImageResource(items.getColorResource());
    }
}
