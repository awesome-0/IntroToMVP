package com.example.samuel.recyclermvp.view;

import android.app.ActivityOptions;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.transition.Fade;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samuel.recyclermvp.R;
import com.example.samuel.recyclermvp.data.FakeDataSource;
import com.example.samuel.recyclermvp.data.ListItem;
import com.example.samuel.recyclermvp.logic.Controller;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements ViewInterface {

    private List<ListItem> listItems;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private Controller controller;
    boolean isScrolling;

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rec_main_activity);

        controller = new Controller(this, new FakeDataSource());
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.createNewItem();
            }
        });
    }

    public View findView(View view, int Id) {
        return view.findViewById(Id);
    }


    @Override
    public void startDetailsActivity(ListItem item, View viewRoot) {
        Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
        detailsIntent.putExtra("item", item);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setEnterTransition(new Fade(Fade.IN));
            getWindow().setEnterTransition(new Fade(Fade.OUT));
            ActivityOptions options = ActivityOptions
                    .makeSceneTransitionAnimation(
                            this,
                            new Pair<View, String>(findView(viewRoot, R.id.data_image), getString(R.string.drawable)),
                            new Pair<View, String>(findView(viewRoot, R.id.data_caption), getString(R.string.message)),
                            new Pair<View, String>(findView(viewRoot, R.id.data_date_time), getString(R.string.date_time))
                    );
            startActivity(detailsIntent, options.toBundle());
        } else {
            startActivity(detailsIntent);
        }


    }

    public void setIsScrolling (boolean isScrolling){
        this.isScrolling = isScrolling;
    }
    public boolean isScrolling(){
        return isScrolling;
    }

    @Override
    public void setUpAdapterAndView(final List<ListItem> listItems) {
        LinearLayoutManager manager = new LinearLayoutManager(this);

        this.listItems = listItems;
        recyclerView.setLayoutManager(manager);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {


            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int vert = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int hori = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(vert,hori);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               controller.onDragging(viewHolder.getAdapterPosition(),target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                Log.d(TAG, "onSwiped: item swiped");
                try {

                    int position = viewHolder.getAdapterPosition();
                    controller.OnListItemSwiped(
                            position,
                            listItems.get(position)
                    );
                } catch (IndexOutOfBoundsException e) {
                    Log.e(TAG, "onSwiped: " + e.getMessage());
                }

            }

        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter = new CustomAdapter(this);
        adapter.setItemTouchHelper(itemTouchHelper);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                setIsScrolling(true);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setIsScrolling(true);
            }
        });
        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), manager.getOrientation());
        decoration.setDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.divider_white));
        recyclerView.addItemDecoration(decoration);

    }

    @Override
    public void addItemToAdapter(ListItem item) {
        listItems.add(item);
        int endOfList = listItems.size() - 1;
        adapter.notifyItemInserted(endOfList);
        recyclerView.smoothScrollToPosition(endOfList);
    }

    @Override
    public void deleteItemAt(int position) {
        listItems.remove(position);
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void showUndoSnackBar() {
        Snackbar.make(
                findViewById(R.id.root_layout),
                "Item Deleted",
                Snackbar.LENGTH_LONG
        ).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.OnUndoConfirmed();
            }
        }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                controller.OnSnackBarDismissed();
            }
        }).show();
    }

    @Override
    public void insertItemAt(int tempItemPosition, ListItem tempItem) {
        listItems.add(tempItemPosition, tempItem);
        adapter.notifyItemInserted(tempItemPosition);
    }

    @Override
    public void doneSwitching(int fromPosition, int toPosition) {
        adapter.notifyItemMoved(fromPosition,toPosition);
    }


    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> implements GestureDetector.OnGestureListener {

        GestureDetector mGestureDetector;
        ItemTouchHelper helper;
        RecyclerView.ViewHolder viewHolder;

        void setItemTouchHelper(ItemTouchHelper helper) {
            this.helper = helper;
        }

        public CustomAdapter(Context mContext) {
            mGestureDetector = new GestureDetector(mContext,this);
        }

        @Override
        public CustomAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = getLayoutInflater().inflate(R.layout.item_data, parent, false);
            return new CustomViewHolder(view);


        }

        @Override
        public void onBindViewHolder(CustomAdapter.CustomViewHolder holder, int position) {

            viewHolder = holder;
            ListItem item = listItems.get(position);
            holder.color.setImageResource(item.getColorResource());
            holder.msg.setText(item.getMessage());
            holder.date.setText(item.getDateTime());
        }

        @Override
        public int getItemCount() {
            return listItems.size();
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
            if(!isScrolling){
                helper.startDrag(viewHolder);
            }



        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }


        public class CustomViewHolder extends RecyclerView.ViewHolder {
            private TextView msg, date;
            private CircleImageView color;

            public CustomViewHolder(View itemView) {
                super(itemView);
                msg = itemView.findViewById(R.id.data_caption);
                date = itemView.findViewById(R.id.data_date_time);
                color = itemView.findViewById(R.id.data_image);

                itemView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        ListItem item = listItems.get(getAdapterPosition());
                        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                            setIsScrolling(false);
                            mGestureDetector.onTouchEvent(motionEvent);
                            }
                        return false;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ListItem item = listItems.get(getAdapterPosition());
                        controller.OnListItemClicked(item, view);
                    }
                });
            }
        }
    }
}
