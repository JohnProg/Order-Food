package com.tectime.johnpaul.orderfood;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.tectime.johnpaul.orderfood.base.ItemClickListener;
import com.tectime.johnpaul.orderfood.common.Common;
import com.tectime.johnpaul.orderfood.model.Food;
import com.tectime.johnpaul.orderfood.model.Request;
import com.tectime.johnpaul.orderfood.viewHolder.FoodViewHolder;
import com.tectime.johnpaul.orderfood.viewHolder.RequestViewHolder;

public class RequestListActivity extends AppCompatActivity {

    private RecyclerView recycler_request;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseDatabase database;
    private DatabaseReference table_requests;
    FirebaseRecyclerAdapter<Request, RequestViewHolder> adapter;
    FirebaseRecyclerOptions<Request> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        initFirebase();
        initViews();
        loadRequests();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        table_requests = database.getReference("Requests");
    }

    private void initViews() {
        recycler_request = (RecyclerView) findViewById(R.id.recycler_request);
        layoutManager = new LinearLayoutManager(this);
        recycler_request.setHasFixedSize(true);
        recycler_request.setLayoutManager(layoutManager);
    }

    private void loadRequests() {
        String currentUserPhone = Common.currentUser.getPhone();
        Query queryRequestsByPhone = table_requests.orderByChild("phone").equalTo(currentUserPhone);
        options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(queryRequestsByPhone, Request.class).build();

        Log.d("john", "123");
        adapter = new FirebaseRecyclerAdapter<Request, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RequestViewHolder holder, int position, @NonNull Request model) {
                Log.d("john", adapter.getRef(position).getKey());
                model.setId(adapter.getRef(position).getKey());
                holder.bindItemData(model);
                holder.setDelegate(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(getBaseContext(), "Request" + position, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item, parent, false);
                return new RequestViewHolder(view);
            }
        };
        recycler_request.setAdapter(adapter);
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
