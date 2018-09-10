package com.daliltanta.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.daliltanta.R;
import com.daliltanta.addingitem.AddActivity;
import com.daliltanta.userauthentication.LoginWithFacebook;
import com.daliltanta.utils.ActivityUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chechUserAuth();
        initalizeBottomNavigation(savedInstanceState);
        initializeUI();




    }

    private void chechUserAuth() {
        //check if user is logged in before or not
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null)
        {
            Intent intent = new Intent(MainActivity.this, LoginWithFacebook.class);
            startActivity(intent);
        }

        }

    private void initializeUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        HomeFragment homeFragment =
                (HomeFragment) getSupportFragmentManager().findFragmentById(R.id.mainContainer);
        if (homeFragment == null) {
            // Create the fragment
            homeFragment = HomeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), homeFragment, R.id.mainContainer);
        }

    }

    public void initalizeBottomNavigation(Bundle savedInstanceState){
        SpaceNavigationView spaceNavigationView = (SpaceNavigationView) findViewById(R.id.space);
        spaceNavigationView.showIconOnly();
        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.home), R.drawable.ic_home_black_24dp));
        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.my_items), R.drawable.ic_account_circle_black_24dp));
//        spaceNavigationView.addSpaceItem(new SpaceItem(getResources().getString(R.string.notifications), R.drawable.ic_notifications_black_24dp));
        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                    AddActivity addActivity = new AddActivity();
                    FragmentTransaction manger = getSupportFragmentManager().beginTransaction();
                    addActivity.show(manger, null);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch(itemIndex) {
                    case 0:

                            // Create the fragment
                           HomeFragment homeFragment = HomeFragment.newInstance();
                            ActivityUtils.addFragmentToActivity(
                                    getSupportFragmentManager(), homeFragment, R.id.mainContainer);


                        break;
                    case 1:
//
//                            // Create the fragment
                            MyItemsFragment myItemsFragment = MyItemsFragment.newInstance();
                            ActivityUtils.addFragmentToActivity(
                                    getSupportFragmentManager(), myItemsFragment, R.id.mainContainer);
//

                        break;
                    default:
                        break;


                }
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                Toast.makeText(MainActivity.this, itemIndex + " " + itemName, Toast.LENGTH_SHORT).show();
            }
        });


    }




}
