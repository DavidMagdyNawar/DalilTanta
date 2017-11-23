package com.daliltanta.mainscreen;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daliltanta.R;
import com.daliltanta.UI.FontTextView;
import com.daliltanta.categorydetails.CategoryDetails;
import com.daliltanta.data.MainItem;
import com.daliltanta.utils.ActivityUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<MainItem> mainItems = new ArrayList<>();

    public CategoriesAdapter(Context context , ArrayList<MainItem> mainItems) {
        this.mainItems = mainItems;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MainItem mainItem = mainItems.get(position);
        holder.fontTextView.setText(mainItem.getMainItemType());
        Glide.with(context).load(mainItem.getMainItemImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, CategoryDetails.class).putExtra(ActivityUtils.MAIN_ITEM_KEY,mainItem));
            }
        });
        }


    public class ViewHolder extends RecyclerView.ViewHolder {
        FontTextView fontTextView ;
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.mainItemImage);
            fontTextView = (FontTextView) itemView.findViewById(R.id.mainItemType);
        }

    }
    @Override
    public int getItemCount() {
        return mainItems.size();
    }

}
