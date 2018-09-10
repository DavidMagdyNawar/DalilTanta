package com.daliltanta.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daliltanta.R;
import com.daliltanta.data.SubItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by David on 11/24/2017.
 */

public class MyItemsAdapter extends RecyclerView.Adapter<MyItemsAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SubItem> subItems  = new ArrayList<>();
    private DatabaseReference databaseReference;

    String userNameString="";

    public MyItemsAdapter(Context context, ArrayList<SubItem> subItems) {
        this.context = context;
        this.subItems = subItems;
    }

    @Override
    public MyItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_item_design, parent, false);
        return new MyItemsAdapter.ViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SubItem subItem= subItems.get(position);
        getUserName(subItem,holder);
        holder.SubItem_description.setText(subItem.getSubItem_description());
        Glide.with(context).load(subItem.getSubItemImage()).into(holder.SubItemImage);
        if(subItem.getSubItem_privacy().equals("private"))
        {
            Glide.with(context).load(R.drawable.ic_lock).into(holder.ivPrivacy);
        }
        if(subItem.getSubItem_approved().equals("false")) {
            holder.approved.setText("approval pending..");
        }
        else holder.approved.setText("approved ✓✓");


    }


    @Override
    public int getItemCount() {
        return subItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView SubItem_description ;
        TextView subItemUserName;
        ImageView SubItemImage;
        ImageView ivPrivacy;
        TextView approved;
        public ViewHolder(View itemView) {
            super(itemView);
            SubItem_description = (TextView) itemView.findViewById(R.id.SubItem_description);
            subItemUserName = itemView.findViewById(R.id.tvUserName);
            SubItemImage = (ImageView) itemView.findViewById(R.id.SubItemImage);
            ivPrivacy = (ImageView) itemView.findViewById(R.id.ivPrivacy);
            approved = itemView.findViewById(R.id.tvApproved);
        }
    }

    public String getUserName(SubItem subItem, final ViewHolder holder) {
        databaseReference = FirebaseDatabase.getInstance().getReference();

         databaseReference.child("Users").child(subItem.getSubItem_user_id()).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 userNameString = (String) dataSnapshot.child("userName").getValue();
                 holder.subItemUserName.setText(userNameString);
             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
    return userNameString;
    }
}
