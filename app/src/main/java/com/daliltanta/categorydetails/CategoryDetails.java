package com.daliltanta.categorydetails;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daliltanta.R;
import com.daliltanta.data.MainItem;
import com.daliltanta.data.SubItem;
import com.daliltanta.mainscreen.CategoriesAdapter;
import com.daliltanta.utils.ActivityUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategoryDetails extends AppCompatActivity {

    MainItem mainItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        mainItem = (MainItem) getIntent().getSerializableExtra(ActivityUtils.MAIN_ITEM_KEY);





    }



}
