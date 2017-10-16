package com.daliltanta.mainscreen;

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

import com.daimajia.slider.library.SliderLayout;
import com.daliltanta.R;
import com.daliltanta.addingitem.AddActivity;

/**
 * Created by ahmedelshaer on 10/16/17.
 */

public class HomeFragment extends Fragment {
    SliderLayout sliderShow;
    FloatingActionButton floatingActionButton = null;
    private RecyclerView recyclerView;

    public HomeFragment() {
        // Requires empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.home_fragment, container, false);
        sliderShow = (SliderLayout) root.findViewById(R.id.slider);

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


        return root;
    }

}
