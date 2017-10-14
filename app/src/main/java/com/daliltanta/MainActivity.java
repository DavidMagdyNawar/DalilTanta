package com.daliltanta;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daliltanta.mRecyclerView.MyAdapter;
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

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    SliderLayout sliderShow;
    Spinner SpinnerCategorieHome;
    FloatingActionButton floatingActionButton = null;
    private List<MyItem> itemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private DatabaseReference databaseReference;
    Map<String, String> mapData = new HashMap<>();
    private ArrayAdapter<String> adapter;
    String actualPositionOfMySpinner = "-1";
    private ArrayList<String> ArrayKeysItemCat;
    ArrayList<String> ArrayValuesItemCat;

    private ArrayList<String> ArrayKeys;
    ArrayList<String> ArrayValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        sliderShow = (SliderLayout) findViewById(R.id.slider);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        DefaultSliderView DefaultSliderView = new DefaultSliderView(this);
        DefaultSliderView
                .description("Game of Thrones")

                .image(R.drawable.promo322954306);
        sliderShow.addSlider(DefaultSliderView);
        sliderShow.setPresetTransformer(SliderLayout.Transformer.Fade);
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopActivity popActivity = new PopActivity();

                FragmentTransaction manger = getSupportFragmentManager().beginTransaction();
                popActivity.show(manger, null);
            }
        });

        getItemCategories();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new MyAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        //spinner and spinnerAdapter
        SpinnerCategorieHome = (Spinner) findViewById(R.id.SpinnerCategorieHome);
        SpinnerCategorieHome.setOnItemSelectedListener(this);
        //spinner ends

        prepareItemData(actualPositionOfMySpinner);

    }

    private void prepareItemData(String actualPositionOfMySpinner) {
//        MyItem myItem = new MyItem("تفاصيل",
//                String.valueOf(R.drawable.ic_public),
//                String.valueOf(R.drawable.promo322954306),
//                "نوع");
//        itemList.add(myItem);
        String itemSpinner = null;
        if (!actualPositionOfMySpinner.equals("-1")) {

            itemSpinner = (String) SpinnerCategorieHome.getItemAtPosition(Integer.parseInt(this.actualPositionOfMySpinner));

            if (itemSpinner != null) {
                try {
                    databaseReference.child("clothes").child(itemSpinner).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {

                                String descriptionString = "";
                                String isPrivate = "false";
                                String type = "";
                                String image = "";
                                mapData = (HashMap<String, String>) dataSnapshot.getValue();
                                ArrayKeysItemCat = new ArrayList<>(mapData.keySet());

                                ArrayKeys = new ArrayList<>();
                                ArrayValues = new ArrayList<>();

                                DataSnapshot ds = null;
                                Map<String, String> map = new HashMap<>();

                                for (int i = 0; i < ArrayKeysItemCat.size(); i++) {
                                    ds = dataSnapshot.child(ArrayKeysItemCat.get(i).toString());
                                    map = (Map<String, String>) ds.getValue();

                                    ArrayKeys = new ArrayList<>(map.keySet());
                                    ArrayValues = new ArrayList<>(map.values());

                                    descriptionString = "";
                                    isPrivate = "false";
                                    type = "";
                                    image = "";

                                    for (int j = 0; j < ArrayKeys.size(); j++) {


                                        if (ArrayKeys.get(j).equals("description")) {
                                            descriptionString = ArrayValues.get(j).toString();
                                        } else if (ArrayKeys.get(j).equals("type"))
                                            type = ArrayValues.get(j).toString();

                                        else if (ArrayKeys.get(j).equals("image"))
                                            image = ArrayValues.get(j).toString();

                                    }
                                    if (!descriptionString.equals("") ||
                                            !image.equals("")
                                            || !type.equals("")) {
                                        MyItem myItem = new MyItem(descriptionString, isPrivate, image, type);

                                        itemList.add(myItem);
                                        mAdapter.notifyDataSetChanged();

                                    }
                                }
                            }

                            Toast.makeText(MainActivity.this, "ssss", Toast.LENGTH_SHORT).show();
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mAdapter.notifyDataSetChanged();

            }
        } else
            Toast.makeText(this, "choose from spiiner", Toast.LENGTH_SHORT).show();
    }


    private void getItemCategories() {

        try {
            databaseReference.child("clothes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        mapData = (HashMap<String, String>) dataSnapshot.getValue();
                        ArrayValuesItemCat = new ArrayList<>(mapData.keySet());

                        adapter = new ArrayAdapter<String>(MainActivity.this,
                                android.R.layout.simple_spinner_item, ArrayValuesItemCat);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        SpinnerCategorieHome.setAdapter(adapter);

                        SpinnerCategorieHome.setAdapter(
                                new NothingSelectedSpinnerAdapter(
                                        adapter,
                                        R.layout.nothing_selected_spinner,
                                        MainActivity.this));
                    }

                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        actualPositionOfMySpinner = String.valueOf(SpinnerCategorieHome.getSelectedItemPosition());
        itemList.clear();
        mAdapter.notifyDataSetChanged();
        prepareItemData(actualPositionOfMySpinner);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
