package com.android.yabble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Supriya Raul on 1/27/2019
 */
public class CategoryActivity extends AppCompatActivity {
    ListView lstView;
    List<ListViewDataItems> categoryViewData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryViewData = new ArrayList<ListViewDataItems>();
        categoryViewData.add(new ListViewDataItems(R.drawable.app_icon, "Politics"));
        categoryViewData.add(new ListViewDataItems(R.drawable.app_icon, "Sports"));
        categoryViewData.add(new ListViewDataItems(R.drawable.app_icon, "The News"));
        categoryViewData.add(new ListViewDataItems(R.drawable.app_icon, "Hypothetical"));
        categoryViewData.add(new ListViewDataItems(R.drawable.app_icon, "The Daily"));

        lstView =  (ListView) findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, R.layout.category_layout, categoryViewData);
         lstView.setAdapter(adapter);
         //adapter.add(new ListViewDataItems(R.drawable.app_icon, "Politics"));

        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("Category Name", categoryViewData.get(position).categoryName);
                intent.putExtra("Flag", categoryViewData.get(position).resourceIdThumbnail);

                intent.setClass(CategoryActivity.this, CategoryActivity3.class ); // update the target activity
                startActivity(intent);
            }
        });
    }
}
