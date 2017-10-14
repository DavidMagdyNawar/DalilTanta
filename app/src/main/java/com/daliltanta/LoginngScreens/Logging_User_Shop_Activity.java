package com.daliltanta.LoginngScreens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.daliltanta.R;

public class Logging_User_Shop_Activity extends AppCompatActivity {

    CardView loggingAsShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_or_shop_activity);
        loggingAsShop = (CardView) findViewById(R.id.loggingAsShop);

    }
}
