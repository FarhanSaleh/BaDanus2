package com.farhan.badanus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailSellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_sell);

        Intent intent = getIntent();
        String sellName = intent.getStringExtra("sell_name");
        String sellCount = intent.getStringExtra("sell_count");
        String sellPrice = intent.getStringExtra("sell_price");

        TextView nameTextView = findViewById(R.id.sell_name);
        TextView countTextView = findViewById(R.id.count_data);
        TextView priceTextView = findViewById(R.id.price_data);

        nameTextView.setText(sellName);
        countTextView.setText(sellCount);
        priceTextView.setText(sellPrice);
    }
}