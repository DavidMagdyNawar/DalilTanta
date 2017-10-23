package com.daliltanta.addingitem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
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

import com.daliltanta.R;
import com.daliltanta.data.Item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AddActivity extends android.support.v4.app.DialogFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    View view;
    EditText description_editText;
    ImageButton imageToIpload;
    Switch switchkey;
    Uri imageUri;
    ImageView IsUploadedIV;
    RelativeLayout imageRelativeLO;
    Button buttonclose;
    Button Done;
    Spinner spinnerSearch;
    private static final int PICK_IMAGE = 100;
    private DatabaseReference databaseReference;
    private Map<String, String> mapData = new HashMap<>();
    private ArrayList<String> ArrayValuesItemCat;
    private ArrayAdapter<String> adapter;
    //Strings for validation
    String spinnerItemSelected="";
    String descriptionString,privacyString;
    Item item = new Item ();
    private boolean firestTrigger=false;


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

        description_editText = (EditText) view.findViewById(R.id.description_editText);
        imageToIpload = (ImageButton) view.findViewById(R.id.itemImageToUpload);
        switchkey = (Switch) view.findViewById(R.id.switch1);
        IsUploadedIV = (ImageView) view.findViewById(R.id.IsUploadedIV);
        imageRelativeLO = (RelativeLayout) view.findViewById(R.id.imageRelativeLO);
        buttonclose = (Button) view.findViewById(R.id.buttonclose);
        Done = (Button) view.findViewById(R.id.Done);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerSearch = (Spinner) view.findViewById(R.id.SpinnerCategorieSearch);
        spinnerSearch.setOnItemSelectedListener(this);
        getItemCategories();

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            item.setItem_description(description_editText.getText().toString());
                if(switchkey.isChecked()){
                    item.setItem_privacy("private");
                }
                else if (!switchkey.isChecked()){
                    item.setItem_privacy("public");
                }
//                item.setImage(image);

                if(item.getItem_categorie_id()==null)
                {
                    Toast.makeText(getContext(), "choose from spinner", Toast.LENGTH_SHORT).show();
                }

            }
        });
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
                imageRelativeLO.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_add_item, container, false);

        return view;

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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (!firestTrigger)
        firestTrigger=true;
        else
        item.setItem_categorie_id(ArrayValuesItemCat.get(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }


}

