package com.example.samuel.recyclermvp.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samuel.recyclermvp.R;
import com.example.samuel.recyclermvp.data.FakeDataSource;
import com.example.samuel.recyclermvp.data.ListItem;
import com.example.samuel.recyclermvp.logic.Controller;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewInterface{

    private List<ListItem> listItems;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Controller controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rec_main_activity);

        controller = new Controller(this,new FakeDataSource());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.createNewItem();
            }
        });
    }

    @Override
    public void startDetailsActivity(ListItem item) {
        Intent detailsIntent = new Intent(MainActivity.this,DetailsActivity.class);
        detailsIntent.putExtra("item",item);
        startActivity(detailsIntent);

    }

    @Override
    public void setUpAdapterAndView(List<ListItem> listItems) {
        LinearLayoutManager manager = new LinearLayoutManager(this);

        this.listItems = listItems;
        recyclerView.setLayoutManager(manager);
        adapter = new CustomAdapter();
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(),manager.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(MainActivity.this,R.drawable.divider_white));
        recyclerView.addItemDecoration(decoration);
    }

    @Override
    public void addItemToAdapter(ListItem item) {
        listItems.add(item);
        int endOfList = listItems.size() - 1;
        adapter.notifyItemInserted(endOfList);
        recyclerView.smoothScrollToPosition(endOfList);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder>{



        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = getLayoutInflater().inflate(R.layout.item_data,parent,false);
            return new CustomViewHolder(view);


        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {

            ListItem item = listItems.get(position);
            holder.color.setImageResource(item.getColorResource());
            holder.msg.setText(item.getMessage());
            holder.date.setText(item.getDateTime());
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView msg,date;
            private ImageView color;
            public CustomViewHolder(View itemView) {
                super(itemView);
                msg = itemView.findViewById(R.id.data_caption);
                date = itemView.findViewById(R.id.data_date_time);
                color = itemView.findViewById(R.id.data_image);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListItem item = listItems.get(getAdapterPosition());
                        controller.OnListItemClicked(item);
                    }
                });
            }
        }
    }
}
