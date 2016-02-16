package com.snilius.foodlife.data;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.snilius.foodlife.R;
import com.snilius.foodlife.model.Food;
import com.snilius.foodlife.model.FoodNotFound;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Victor Häggqvist
 * @since 2/15/16
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    private final List<Food> list;
    private final OnClickListener listener;

    private SparseBooleanArray selected;
    private Drawable defaultBackground;

    public FoodAdapter(List<Food> list, OnClickListener listener) {
        this.list = list;
        this.listener = listener;
        selected = new SparseBooleanArray();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_item, parent, false);
        defaultBackground = v.getBackground();
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(FoodAdapter.ViewHolder holder, int position) {
        holder.bind(list.get(position), selected.get(position, false));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearSelection() {
        selected.clear();
    }

    public void toggleSelect(int position) {
        selected.put(position, !selected.get(position, false));
        this.notifyDataSetChanged();
    }

    public SparseIntArray getSelected() {
        SparseIntArray items = new SparseIntArray();
        for(int i = 0; i < selected.size(); i++) {
            int key = selected.keyAt(i);
            if (selected.get(key)) items.append(items.size(), key);
        }
        return items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @Bind(R.id.container)
        LinearLayout container;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.category)
        TextView category;
        @Bind(R.id.verified)
        ImageView verified;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.itemClick(getAdapterPosition());
        }

        public void bind(Food food, boolean selected) {
            title.setText(food.getTitle());

            //noinspection PointlessBooleanExpression
            if (food instanceof FoodNotFound == false) {
                category.setText(food.getCategory());
                verified.setVisibility(food.isVerified() ? View.VISIBLE : View.GONE);
            } else {
                category.setText(null);
                verified.setVisibility(View.GONE);
            }

            if (selected) {
                container.setBackgroundResource(R.color.accent);
            } else {
                container.setBackgroundDrawable(defaultBackground);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getAdapterPosition();
            toggleSelect(position);
            listener.itemLongClick(getAdapterPosition(), selected.get(position, false));
            return true;
        }
    }

    public interface OnClickListener {
        void itemClick(int position);
        void itemLongClick(int position, boolean selected);
    }
}
