package com.android.yabble;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_layout, viewGroup, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((RecyclerViewHolder) viewHolder).bindView(i);

    }

    @Override
    public int getItemCount() {
        return OurData.category.length;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView postProfileImage;
        private TextView postUserName;
        private TextView postCategory;
        private TextView postTopic;
        private TextView postDescription;

        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            postProfileImage = (ImageView) itemView.findViewById(R.id.post_profile_image);
            postUserName = (TextView) itemView.findViewById(R.id.post_user_name);
            postCategory = (TextView) itemView.findViewById(R.id.post_category);
            postTopic = (TextView) itemView.findViewById(R.id.post_topic);
            postDescription = (TextView) itemView.findViewById(R.id.post_description);

            itemView.setOnClickListener(this);
        }

        public void bindView(int position)
        {
            postProfileImage.setImageResource(OurData.picturePath[position]);
            postUserName.setText(OurData.username[position]);
            postCategory.setText(OurData.category[position]);
            postTopic.setText(OurData.topic[position]);
            postDescription.setText(OurData.desc[position]);
        }

        public void onClick(View v)
        {

        }
    }
}
