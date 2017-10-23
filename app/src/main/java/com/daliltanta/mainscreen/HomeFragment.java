package com.daliltanta.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.daliltanta.R;
import com.daliltanta.addingitem.AddActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.daliltanta.R.string.name;

/**
 * Created by ahmedelshaer on 10/16/17.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    SliderLayout sliderShow;
    FloatingActionButton floatingActionButton = null;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;


    public HomeFragment() {
        // Requires empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        sliderShow = (SliderLayout) root.findViewById(R.id.slider);
        sliderShow.setPresetTransformer(SliderLayout.Transformer.Fade);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setDuration(4000);

        floatingActionButton = (FloatingActionButton) root.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddActivity addActivity = new AddActivity();

                FragmentTransaction manger = getActivity().getSupportFragmentManager().beginTransaction();
                addActivity.show(manger, null);
            }
        });


        recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getSliderItems();

        return root;
    }

    private void getSliderItems() {
        databaseReference.child("slider_items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = "";
                List<String> imageList = new ArrayList<String> ();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                     image = (String) postSnapshot.child("image").getValue();
                     imageList.add(image);
                    // initialize a SliderLayout

                }

                if(!imageList.equals(null))
                {
                for(int i = 0 ; i<imageList.size();i++){
                    DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());

                    defaultSliderView
                            .image(imageList.get(i))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                    sliderShow.addSlider(defaultSliderView);



                }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStop() {
        sliderShow.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        sliderShow.startAutoCycle();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
