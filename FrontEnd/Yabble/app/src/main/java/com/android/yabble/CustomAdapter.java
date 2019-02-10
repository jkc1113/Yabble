package com.android.yabble;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Supriya Raul on 1/27/2019
*/
public class CustomAdapter extends ArrayAdapter<ListViewDataItems> {

    Context context;
    int layoutResourceId;
    List<ListViewDataItems> data;

    public CustomAdapter(Context context, int resource, List<ListViewDataItems> data) {
        super(context, resource, data);

        this.layoutResourceId = resource;
        this.context = context;
        this.data = data;
    }

    static class DataHolder{
        ImageView ivFlag;
        TextView tvCategoryName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataHolder holder =null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(context); //((Activity)context).getLayoutInflater();

            convertView = inflater.inflate(layoutResourceId, parent, false);

            holder = new DataHolder();
            holder.ivFlag = (ImageView)convertView.findViewById(R.id.imageCategory);
            holder.tvCategoryName = (TextView)convertView.findViewById(R.id.textCategory);
            convertView.setTag(holder);
        }
        else{
            holder = (DataHolder)convertView.getTag();
        }

        ListViewDataItems dataItem = data.get(position);
        holder.tvCategoryName.setText(dataItem.categoryName);
        holder.ivFlag.setImageResource(dataItem.resourceIdThumbnail);
        return convertView;

    }
}
