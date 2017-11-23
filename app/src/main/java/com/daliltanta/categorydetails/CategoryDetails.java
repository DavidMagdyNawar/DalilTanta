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
    private DatabaseReference databaseReference;
    ArrayList<SubItem> subItems = new ArrayList<>();
    RecyclerView recyclerView;
    CategoryDetailsAdapter categoryDetailsAdapter;
    MainItem mainItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_details);
        mainItem = (MainItem) getIntent().getSerializableExtra(ActivityUtils.MAIN_ITEM_KEY);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.SubItemRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                CategoryDetails.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

         categoryDetailsAdapter = new CategoryDetailsAdapter(this,subItems);
        recyclerView.setAdapter(categoryDetailsAdapter);
        getSubItemFromFirebase();



    }

    private void getSubItemFromFirebase() {
        databaseReference.child("SubItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subItems.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(mainItem.getMainItemID().equals(postSnapshot.child("SubItem_id").getValue(String.class))) {
                        SubItem subItem = postSnapshot.getValue(SubItem.class);
                        subItems.add(subItem);
                    }

                }
                categoryDetailsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
