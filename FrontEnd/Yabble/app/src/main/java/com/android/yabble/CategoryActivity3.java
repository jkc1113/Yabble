package com.android.yabble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CategoryActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category3);

        ImageView imgView = (ImageView)findViewById(R.id.imageView3);
        TextView txtView = (TextView)findViewById(R.id.textView3);

        txtView.setText(getIntent().getStringExtra("Category Name"));
        imgView.setImageResource(getIntent().getIntExtra("Flag", R.drawable.app_icon));
    }
}
