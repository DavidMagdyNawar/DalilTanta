package com.daliltanta.mainscreen;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.daliltanta.R;
import com.daliltanta.addingitem.AddActivity;
import com.daliltanta.data.MainItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmedelshaer on 10/16/17.
 */

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    SliderLayout sliderShow;
    private DatabaseReference databaseReference;
    ArrayList<MainItem> mainItemArrayList = new ArrayList<>();
    ViewPager viewPager;
    TabLayout tabLayout;
    LottieAnimationView lottieLoading;
    LinearLayout nestedScrollView;


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
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) root.findViewById(R.id.sliding_tabs);
        lottieLoading = root.findViewById(R.id.lottieLoading);
        nestedScrollView=root.findViewById(R.id.myLinearLayout);



        getMainItemFromFirebase();


        getSliderItems();

        return root;
    }

    //get main item from database
    public void getMainItemFromFirebase() {
        DatabaseReference categRef = databaseReference.child("categories");
        categRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mainItemArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    MainItem mainItem = postSnapshot.getValue(MainItem.class);
                    mainItemArrayList.add(mainItem);

                }
                viewPager.setAdapter(new HomeFragmentPagerAdapter(getChildFragmentManager(),
                        getActivity(), mainItemArrayList));
                tabLayout.setupWithViewPager(viewPager);
                lottieLoading.pauseAnimation();
                lottieLoading.setVisibility(View.GONE);
                nestedScrollView.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //end of retrieving


    //slider starts

    private void getSliderItems() {
        databaseReference.child("slider_items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = "";
                List<String> imageList = new ArrayList<String>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    image = (String) postSnapshot.child("image").getValue();
                    imageList.add(image);
                    // initialize a SliderLayout

                }

                if (!imageList.equals(null)) {
                    for (int i = 0; i < imageList.size(); i++) {
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
    //slider ends


    public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {
        private Context context;
        ArrayList<MainItem> mainItemArrayList;

        public HomeFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<MainItem> mainItemArrayList) {
            super(fm);
            this.context = context;
            this.mainItemArrayList = mainItemArrayList;
        }

        @Override
        public int getCount() {
            return mainItemArrayList.size();
        }

        @Override
        public Fragment getItem(int position) {
            MainItem mainItem = mainItemArrayList.get(position);
            return HomePageFragment.newInstance(mainItem.getMainItemID());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            MainItem mainItem = mainItemArrayList.get(position);
            return mainItem.getMainItemType();
        }
    }
}
