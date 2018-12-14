package com.tectime.johnpaul.orderfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tectime.johnpaul.orderfood.base.BaseActivity;
import com.tectime.johnpaul.orderfood.base.ItemClickListener;
import com.tectime.johnpaul.orderfood.model.Category;
import com.tectime.johnpaul.orderfood.model.Food;
import com.tectime.johnpaul.orderfood.viewHolder.FoodViewHolder;

public class FoodListActivity extends BaseActivity {

    private RecyclerView recycler_food;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference table_foods;
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    FirebaseRecyclerOptions<Food> options;
    String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        initFirebase();
        initViews();
        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("categoryId");
            if (!categoryId.isEmpty() && categoryId != null) {
                loadFoodList(categoryId);
            }
        }
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        table_foods = database.getReference("Foods");
    }

    private void initViews() {
        recycler_food = (RecyclerView) findViewById(R.id.recycler_food);
        layoutManager = new LinearLayoutManager(this);
        recycler_food.setHasFixedSize(true);
        recycler_food.setLayoutManager(layoutManager);
    }

    private void loadFoodList(String categoryId) {
        Query queryFoodById = table_foods.orderByChild("menuId").equalTo(categoryId);
        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(queryFoodById, Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                holder.bindItemData(model);
                holder.setDelegate(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodDetail = new Intent(FoodListActivity.this, FoodDetailActivity.class);
                        foodDetail.putExtra("foodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
                return new FoodViewHolder(view);
            }
        };
        recycler_food.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
