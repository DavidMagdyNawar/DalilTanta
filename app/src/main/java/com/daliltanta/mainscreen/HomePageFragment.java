package com.daliltanta.mainscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daliltanta.R;
import com.daliltanta.categorydetails.CategoryDetails;
import com.daliltanta.categorydetails.CategoryDetailsAdapter;
import com.daliltanta.data.SubItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ahmedelshaer on 23.11.17.
 */

public class HomePageFragment extends Fragment {
    private static String CATEGPRY_KEY="Category";
    private String mCategoryString;
    private DatabaseReference databaseReference;
    ArrayList<SubItem> subItems = new ArrayList<>();
    RecyclerView rvItem;
    CategoryDetailsAdapter categoryDetailsAdapter;

    public static HomePageFragment newInstance(String page) {
        Bundle args = new Bundle();
        args.putString(CATEGPRY_KEY, page);
        HomePageFragment fragment = new HomePageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryString = getArguments().getString(CATEGPRY_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        rvItem= (RecyclerView) view.findViewById(R.id.rvItems);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity(),LinearLayoutManager.VERTICAL,false);
        rvItem.setLayoutManager(linearLayoutManager);

        categoryDetailsAdapter = new CategoryDetailsAdapter(getActivity(),subItems);
        rvItem.setAdapter(categoryDetailsAdapter);
        getSubItemFromFirebase();
        return view;
    }
    private void getSubItemFromFirebase() {
        databaseReference.child("SubItem").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subItems.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(mCategoryString.equals(postSnapshot.child("SubItem_id").getValue(String.class))) {
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
