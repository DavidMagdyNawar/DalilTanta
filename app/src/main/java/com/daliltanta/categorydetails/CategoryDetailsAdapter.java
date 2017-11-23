package com.daliltanta.categorydetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.daliltanta.R;
import com.daliltanta.UI.FontTextView;
import com.daliltanta.data.MainItem;
import com.daliltanta.data.SubItem;
import com.daliltanta.mainscreen.CategoriesAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 11/21/2017.
 */

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<SubItem> subItems= new ArrayList<>();

    public CategoryDetailsAdapter(Context context, ArrayList<SubItem> subItems) {
        this.context = context;
        this.subItems = subItems;
    }

    @Override
    public CategoryDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorie_details_item, parent, false);
        return new ViewHolder(itemView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

//        String SubItem_id;
//        String SubItem_user_id;
//        String SubItem_request_id;
//        List<String> SubItem_shop_IDs;

        FontTextView SubItem_description,SubItemUserName ;
        ImageView SubItem_approved,SubItemImage,SubItem_privacy;
        public ViewHolder(View itemView) {
            super(itemView);

            SubItem_approved = (ImageView) itemView.findViewById(R.id.approved);
            SubItemImage = (ImageView) itemView.findViewById(R.id.SubItemImage);
            SubItem_privacy = (ImageView) itemView.findViewById(R.id.SubItem_privacy);
            SubItem_description = (FontTextView) itemView.findViewById(R.id.SubItem_description);
            SubItemUserName = (FontTextView) itemView.findViewById(R.id.SubItemUserName);
        }
    }



    @Override
    public void onBindViewHolder(CategoryDetailsAdapter.ViewHolder holder, int position) {
        final SubItem subItem= subItems.get(position);

        holder.SubItem_description.setText(subItem.getSubItem_description());

        Glide.with(context).load(subItem.getSubItemImage()).into(holder.SubItemImage);
    //I need to initialize approved, privacy and get User Name from user ID

    }

    @Override
    public int getItemCount() {
        return subItems.size();
    }


}
