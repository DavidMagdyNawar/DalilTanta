package com.daliltanta;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.daliltanta.mRecyclerView.MyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PopActivity extends android.support.v4.app.DialogFragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    View view;
    EditText betdawatEditText;
    ImageButton imageToIpload;
    Switch switchkey;
    Uri imageUri;
    ImageView IsUploadedIV;
    RelativeLayout imageRelativeLO;
    Button buttonclose;
    Button buttonYalla;
    Spinner spinnerSearch;
    private static final int PICK_IMAGE = 100;
    private DatabaseReference databaseReference;
    private Map<String, String> mapData = new HashMap<>();
    private ArrayList<String> ArrayValuesItemCat;
    private ArrayAdapter<String> adapter;
    private String actualPositionOfMySpinner = "";


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            imageRelativeLO.setVisibility(View.VISIBLE);
            IsUploadedIV.setImageURI(imageUri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        betdawatEditText = (EditText) view.findViewById(R.id.betdawarEditText);
        imageToIpload = (ImageButton) view.findViewById(R.id.itemImageToUpload);
        switchkey = (Switch) view.findViewById(R.id.switch1);
        IsUploadedIV = (ImageView) view.findViewById(R.id.IsUploadedIV);
        imageRelativeLO = (RelativeLayout) view.findViewById(R.id.imageRelativeLO);
        buttonclose = (Button) view.findViewById(R.id.buttonclose);
        buttonYalla = (Button) view.findViewById(R.id.yallaButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerSearch = (Spinner) view.findViewById(R.id.SpinnerCategorieSearch);
        spinnerSearch.setOnItemSelectedListener(this);
        getItemCategories();


        buttonYalla.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {

                                               String privacy = "public";
                                               String description = "";
                                               String imageUri;

                                                if(!actualPositionOfMySpinner.equals(""))
                                                {
                                                    if (switchkey.isChecked()) {
                                                        privacy = "private";
                                                    }
                                                    description = betdawatEditText.getText().toString();
                                                    Toast.makeText(getContext(), "is Selected", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(getContext(), "chosse from spinnnnerr yalaa", Toast.LENGTH_SHORT).show();
                                                }

                                           }

                                       }
        )
        ;

    }



    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        imageToIpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        buttonclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IsUploadedIV.setImageURI(null);
                imageRelativeLO.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_add_item, container, false);

        return view;

    }


    @Override
    public void onClick(View v) {
        Button button = (Button) view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
         actualPositionOfMySpinner = String.valueOf(spinnerSearch.getSelectedItemPosition());

    }




    @Override
    public void onNothingSelected(AdapterView<?> parent) {

        Toast.makeText(getContext(), "Please choose from spinner", Toast.LENGTH_SHORT).show();

    }

    private void getItemCategories() {

        try {
            databaseReference.child("clothes").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        mapData = (HashMap<String, String>) dataSnapshot.getValue();
                        ArrayValuesItemCat = new ArrayList<>(mapData.keySet());

                        adapter = new ArrayAdapter<String>(getContext(),
                                android.R.layout.simple_spinner_item, ArrayValuesItemCat);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSearch.setAdapter(adapter);

                        spinnerSearch.setAdapter(
                                new NothingSelectedSpinnerAdapter(
                                        adapter,
                                        R.layout.for_spinner_search,
                                        getContext()));
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
}

