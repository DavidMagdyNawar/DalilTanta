package com.daliltanta.addingitem;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
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
import com.daliltanta.data.SubItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


public class AddActivity extends android.support.v4.app.DialogFragment implements AdapterView.OnItemSelectedListener
{
    View view;
    EditText descriptionET;
    ImageButton imageToIpload;
    Switch switchkey;
    ImageView IsUploadedIV;
    RelativeLayout imageRelativeLO;
    Button buttonclose;
    Button buttonYalla;
    Spinner spinnerSearch;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    FirebaseUser firebaseUser;
    private static final int PICK_IMAGE = 100;
    private DatabaseReference databaseReference;
    private ArrayList<String> ArrayValuesItemCat;
    private ArrayList<String> ArrayValuesItemID;

    private ArrayAdapter<String> adapter;
    Uri DownloadURL=null;
    Uri imageUri=null;
    SubItem subItem;
    String SubItemKey ="";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                IsUploadedIV.setImageBitmap(bitmap);
                imageRelativeLO.setVisibility(view.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        descriptionET = (EditText) view.findViewById(R.id.descriptionET);
        imageToIpload = (ImageButton) view.findViewById(R.id.itemImageToUpload);
        switchkey = (Switch) view.findViewById(R.id.switch1);
        IsUploadedIV = (ImageView) view.findViewById(R.id.IsUploadedIV);
        imageRelativeLO = (RelativeLayout) view.findViewById(R.id.imageRelativeLO);
        buttonclose = (Button) view.findViewById(R.id.buttonclose);
        buttonYalla = (Button) view.findViewById(R.id.yallaButton);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        spinnerSearch = (Spinner) view.findViewById(R.id.SpinnerCategorieSearch);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference().child("images_uploaded");
         subItem = new SubItem();

        ArrayValuesItemCat = new ArrayList<>();
        ArrayValuesItemID = new ArrayList<>();
        getItemCategories();


    }


    @Override
    public void onStart() {
        super.onStart();
        final Dialog dialog = getDialog();
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
                imageUri=null;
                imageRelativeLO.setVisibility(View.GONE);
            }
        });
        buttonYalla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerSearch.getSelectedItemPosition()==0) {
                    onNothingSelected();
                }
                else {
                    Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();

                    if (imageUri!=null ) {
                        Toast.makeText(getActivity(), "uri not null", Toast.LENGTH_SHORT).show();

                        StorageReference reference = storageReference.child("images_uploaded");
                        reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                DownloadURL = taskSnapshot.getDownloadUrl();
                                imageUri = DownloadURL;
                                Log.e("DOWNLOAD_URL",DownloadURL.toString());
                                subItem.setSubItemImage(DownloadURL.toString());
                                databaseReference.child("SubItem").child(subItem.getSubItem_request_id()).child("SubItemImage").setValue(DownloadURL.toString());
//                    Toast.makeText(getActivity(), "Success ", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Failed " + e, Toast.LENGTH_LONG).show();
                                Log.e("OnFailure", e.toString());
                            }
                        });

                    }

//                    HashMap<String,String> shopsHaveID = new HashMap<>() ;
//                    shopsHaveID.put("","");

                    int position = spinnerSearch.getSelectedItemPosition();
                    String myID = ArrayValuesItemID.get(position-1);


                    if(subItem.getSubItemImage()==null || subItem.getSubItemImage().equals("") ) {
                        subItem.setSubItemImage("https://firebasestorage.googleapis.com/v0/b/daliltanta.appspot.com/o/file.png?alt=media&token=504fce18-ea09-4636-9c63-e2b364091d44");
                    }


                    subItem.setSubItem_user_id(firebaseUser.getUid());
                    subItem.setSubItem_request_id("");
                    subItem.setSubItem_approved("false");
                    subItem.setSubItem_description(descriptionET.getText().toString());
                    subItem.setSubItem_id(myID);
                    if(switchkey.isChecked())
                    {
                        subItem.setSubItem_privacy("private");
                    }
                    else if(!switchkey.isChecked())
                    {
                        subItem.setSubItem_privacy("public");
                    }
                    SubItemKey = databaseReference.child("SubItem").push().getKey();
                    subItem.setSubItem_request_id(SubItemKey);
                    databaseReference.child("SubItem").child(subItem.getSubItem_request_id()).setValue(subItem);
                    Toast.makeText(getContext(), "Your photo is being uploaded .... ", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();

                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_add_item, container, false);

        return view;

    }

    //Spinner Categories starts
    private void getItemCategories() {

        try {
            databaseReference.child("categories").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (ArrayValuesItemCat.size() != 0) {
                        ArrayValuesItemCat.clear();
                    }

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        HashMap<String,String> map = (HashMap<String, String>) postSnapshot.getValue();
                        assert map != null;
                        ArrayValuesItemCat = new ArrayList<String>(map.values());

                        String categorieID = postSnapshot.child("_id").getValue(String.class);
                        ArrayValuesItemID.add(categorieID);

                    }


                    if (ArrayValuesItemCat.size() != 0) {
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Make Sure you choose from spinner", Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected() {
        Toast.makeText(getContext(), "Make Sure you choose from spinner", Toast.LENGTH_SHORT).show();
    }



    //Spinner Categories ends
}

