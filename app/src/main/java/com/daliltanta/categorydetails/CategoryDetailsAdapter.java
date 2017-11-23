package com.daliltanta.categorydetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daliltanta.R;
import com.daliltanta.UI.FontTextView;
import com.daliltanta.data.MainItem;
import com.daliltanta.data.SubItem;
import com.daliltanta.mainscreen.CategoriesAdapter;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

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
                .inflate(R.layout.item_design, parent, false);
        return new ViewHolder(itemView);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView SubItem_description ;
        ImageView SubItemImage;
        public ViewHolder(View itemView) {
            super(itemView);

            SubItemImage = (ImageView) itemView.findViewById(R.id.SubItemImage);
            SubItem_description = (TextView) itemView.findViewById(R.id.SubItem_description);
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
