package com.daliltanta.mRecyclerView;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daliltanta.MyItem;
import com.daliltanta.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    private List<MyItem> myItemList;

    public MyAdapter(List<MyItem> myItemList) {
        this.myItemList = myItemList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView item_description;
        ImageView itemImage,privacy;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_description = (TextView) itemView.findViewById(R.id.details_show);
            itemImage = (ImageView) itemView.findViewById(R.id.Image_show);
            privacy = (ImageView) itemView.findViewById(R.id.privacy_show);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyItem myItem = myItemList.get(position);
        holder.item_description.setText(myItem.getItem_description());
        Context context = holder.itemView.getContext();
//        Picasso.with(context).load(myItem.getItem_Image()).into(holder.itemImage);
        Glide.with(context).load(myItem.getItem_Image()).into(holder.itemImage);
//        holder.itemImage.setImageURI(Uri.parse(myItem.getItem_Image()));
//        holder.privacy.setImageURI(Uri.parse(myItem.isItem_isPrivate()));

    }

    @Override
    public int getItemCount() {
        return myItemList.size();
    }


}
