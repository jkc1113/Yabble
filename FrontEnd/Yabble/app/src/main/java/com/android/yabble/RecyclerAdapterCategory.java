package com.android.yabble;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class RecyclerAdapterCategory extends RecyclerView.Adapter {
    @NonNull
    @Override


    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_layout, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((RecyclerViewHolder) viewHolder).bindView(i);
    }

    @Override
    public int getItemCount() {
        return OurData.categoryTypes.length;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView CategoryIcon;
        private TextView categoryDescription;


        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            CategoryIcon = (ImageView) itemView.findViewById(R.id.imageCategory);
            categoryDescription = (TextView) itemView.findViewById(R.id.textCategory);
            itemView.setOnClickListener(this);
        }

        public void bindView(int position)
        {
            CategoryIcon.setImageResource(OurData.picturePath[position]);
            categoryDescription.setText(OurData.categoryTypes[position]);
        }

        public void onClick(View v)
        {

        }
    }
}
