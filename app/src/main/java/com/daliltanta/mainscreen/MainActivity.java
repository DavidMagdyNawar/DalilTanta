package com.daliltanta.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.daliltanta.R;
import com.daliltanta.addingitem.AddActivity;
import com.daliltanta.utils.ActivityUtils;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeBottomNavigation(savedInstanceState);
        initializeUI();



    }

    private void initializeUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        HomeFragment homeFragment =
                (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (homeFragment == null) {
            // Create the fragment
            homeFragment = homeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.mainContainer);
        }
    }

    public void initalizeBottomNavigation(Bundle savedInstanceState){
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.showIconOnly();
        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.home), R.drawable.ic_home_black_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.my_items), R.drawable.ic_account_circle_black_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.notifications), R.drawable.ic_notifications_black_24dp));
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                AddActivity addActivity = new AddActivity();
                FragmentTransaction manger = getSupportFragmentManager().beginTransaction();
                addActivity.show(manger, null);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });


    }




}
