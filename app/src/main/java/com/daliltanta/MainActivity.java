package com.daliltanta;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {
//    SliderLayout sliderShow;
    SliderLayout sliderShow;
    FloatingActionButton floatingActionButton = null;
    private RecyclerView recyclerView;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStorageRef = FirebaseStorage.getInstance().getReference();


        sliderShow = (SliderLayout) findViewById(R.id.slider);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

//        DefaultSliderView DefaultSliderView = new DefaultSliderView(this);
//        DefaultSliderView
//                .description("Game of Thrones")
//
//                .image(R.drawable.promo322954306);
//        sliderShow.addSlider(DefaultSliderView);
//        sliderShow.setPresetTransformer(SliderLayout.Transformer.Fade);
//        sliderShow.setCustomAnimation(new DescriptionAnimation());
//        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopActivity popActivity = new PopActivity();

                FragmentTransaction manger = getSupportFragmentManager().beginTransaction();
                popActivity.show(manger, null);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());



    }




}
