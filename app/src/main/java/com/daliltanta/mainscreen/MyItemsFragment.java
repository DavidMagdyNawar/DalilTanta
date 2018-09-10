package com.daliltanta.mainscreen;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daliltanta.R;
import com.daliltanta.adapters.MyItemsAdapter;
import com.daliltanta.data.SubItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by David on 11/24/2017.
 */
// Items the users approved and the details ... related to the profile icon

public class MyItemsFragment extends Fragment {

//    private static String CATEGPRY_KEY="Category";
//    private String mCategoryString;
    private DatabaseReference databaseReference;
    ArrayList<SubItem> subItems = new ArrayList<>();
    RecyclerView rvItem;
    MyItemsAdapter myItemsAdapter;
    FirebaseUser firebaseUser ;


    public static MyItemsFragment newInstance() {
        Bundle args = new Bundle();
        MyItemsFragment fragment = new MyItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mCategoryString = getArguments().getString(CATEGPRY_KEY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        rvItem= (RecyclerView) view.findViewById(R.id.rvItems);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        getMainItemFromFirebaseToMyITems();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity(),LinearLayoutManager.VERTICAL,false);
        rvItem.setLayoutManager(linearLayoutManager);

        myItemsAdapter = new MyItemsAdapter(getActivity(),subItems);
        rvItem.setAdapter(myItemsAdapter);
        return view;
    }
    //get main item from database
    public void getMainItemFromFirebaseToMyITems() {
        DatabaseReference categRef = databaseReference.child("SubItem");
        categRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                subItems.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SubItem subItem= postSnapshot.getValue(SubItem.class);
                    if(subItem.getSubItem_user_id().equals(firebaseUser.getUid())) {
                        subItems.add(subItem);
                    }

                }
            myItemsAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    //end of retrieving
}
