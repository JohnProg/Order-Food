package com.tectime.johnpaul.orderfood;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tectime.johnpaul.orderfood.base.BaseActivity;
import com.tectime.johnpaul.orderfood.database.Database;
import com.tectime.johnpaul.orderfood.model.Food;
import com.tectime.johnpaul.orderfood.model.Order;


public class FoodDetailActivity extends BaseActivity {

    private TextView food_name, food_price, food_description;
    private ImageView food_image;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private ElegantNumberButton numberButton;
    private FirebaseDatabase database;
    private DatabaseReference table_foods;
    private String foodId = "";
    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        initFirebase();
        initViews();

        if (getIntent() != null) {
            foodId = getIntent().getStringExtra("foodId");
            if (!foodId.isEmpty() && foodId != null) {
                loadFoodDetail(foodId);
            }
        }
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        table_foods = database.getReference("Foods");
    }

    private void loadFoodDetail(final String foodId) {
        table_foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Glide.with(getBaseContext())
                        .load(currentFood.getImage())
                        .apply(new RequestOptions().error(R.drawable.background))
                        .into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_name.setText(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = (TextView) findViewById(R.id.food_price);
        food_description = (TextView) findViewById(R.id.food_description);
        food_image = (ImageView) findViewById(R.id.food_image);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandeddAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        btnCart = (FloatingActionButton) findViewById(R.id.fab);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).saveOrder(new Order(
                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Snackbar.make(view, "The food was added to your cart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
