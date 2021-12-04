package com.example.gproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Product> list;
    //    ArrayList<String> list;
    MyAdapter adapter;
    String username;
    String customer_username;
    Order order;

    public void goBack(View v) {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_page);

        Intent intent = getIntent();
        username = intent.getStringExtra(CustomerMainActivity.EXTRA_MESSAGE);
        customer_username = intent.getStringExtra(CustomerMainActivity.CUSTOMER_EXTRA_MESSAGE);

        recyclerView = findViewById(R.id.list_item);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter(this, list);
        recyclerView.setAdapter(adapter);

        DB.getShopProducts(username, adapter, recyclerView, list, this);

    }

    public void checkout(View view) {
        order = new Order();
        order.setOwner(username);
        order.setCustomer(customer_username);
        order.setCart_products(MyAdapter.order_list);
        double final_price = 0;
        for (Product p: MyAdapter.order_list)
            final_price += p.price * p.order_amount;
        order.final_price = final_price;
        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        intent.putExtra(CartActivity.EXTRA_MESSAGE, order);
        OnToast.showToast("Checkout success!",this);
        startActivity(intent);
    }


}