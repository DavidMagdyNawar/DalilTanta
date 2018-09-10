package com.daliltanta.mainscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daliltanta.R;

/**
 * Created by DavidNawar on 23.11.17.
 */

public class HomePageFragment extends Fragment {

    public static HomePageFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString(CATEGPRY_KEY, page);
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public final static String CATEGPRY_KEY="Category";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        String mainItemString = getArguments().getString(CATEGPRY_KEY);
        Toast.makeText(getContext(), mainItemString, Toast.LENGTH_SHORT).show();
        return view;
    }
}
