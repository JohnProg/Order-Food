package com.tectime.johnpaul.orderfood;

import android.content.DialogInterface;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tectime.johnpaul.orderfood.adapter.CartAdapter;
import com.tectime.johnpaul.orderfood.common.Common;
import com.tectime.johnpaul.orderfood.common.CustomDialogMessage;
import com.tectime.johnpaul.orderfood.database.Database;
import com.tectime.johnpaul.orderfood.model.Food;
import com.tectime.johnpaul.orderfood.model.Order;
import com.tectime.johnpaul.orderfood.model.Request;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    TextView txtTotal;
    LinearLayout linear_layout_empty_list;
    RecyclerView recycler_cart;
    RecyclerView.LayoutManager layoutManager;
    FButton btnRequestOrder;
    private CustomDialogMessage customDialogMessage;
    private FirebaseDatabase database;
    private DatabaseReference table_requests;
    List<Order> carts = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initFirebase();
        initViews();
        loadOrders();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        table_requests = database.getReference("Requests");
    }

    private void initViews() {
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        linear_layout_empty_list = (LinearLayout) findViewById(R.id.linear_layout_empty_list);
        btnRequestOrder = (FButton) findViewById(R.id.btnRequestOrder);
        recycler_cart = (RecyclerView) findViewById(R.id.recycler_cart);

        layoutManager = new LinearLayoutManager(this);
        recycler_cart.setHasFixedSize(true);
        recycler_cart.setLayoutManager(layoutManager);

        btnRequestOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View layout_dialog_message = View.inflate(getBaseContext(), R.layout.custom_dialog_message, null);
                final EditText editText = (EditText) layout_dialog_message.findViewById(R.id.editText);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(CartActivity.this);
                alertDialog.setTitle(getString(R.string.cart_title_dialog));
                alertDialog.setMessage(getString(R.string.card_message_dialog));
                alertDialog.setView(layout_dialog_message);
                alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
                alertDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Request request = new Request(
                                Common.currentUser.getPhone(),
                                Common.currentUser.getName(),
                                editText.getText().toString(),
                                txtTotal.getText().toString(),
                                carts
                        );
                        // Save to firebase
                        table_requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);
                        new Database(getBaseContext()).cleanAllOrders();
                        Toast.makeText(getBaseContext(), "Thank you, order place", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                alertDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    private void loadOrders() {
        carts = new Database(this).getAllOrders();
        adapter = new CartAdapter(this, carts);
        recycler_cart.setAdapter(adapter);
        calculateTotalPrice(carts);

        if (carts.isEmpty()) {
            linear_layout_empty_list.setVisibility(View.VISIBLE);
            btnRequestOrder.setEnabled(false);
        } else {
            linear_layout_empty_list.setVisibility(View.GONE);
            btnRequestOrder.setEnabled(true);
        }
    }

    private void calculateTotalPrice(List<Order> carts) {
        int total = 0;
        for (Order order : carts) {
            total += (Integer.parseInt(order.getPrice()) * Integer.parseInt(order.getQuantity()));
        }

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance().getCurrencyInstance(locale);
        txtTotal.setText(fmt.format(total));
    }
}
