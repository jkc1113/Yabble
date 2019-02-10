package com.android.yabble;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

public class HomeFragment extends Fragment {

    private RecyclerView homePagePostList;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home_layout, container, false);
        homePagePostList = (RecyclerView) v.findViewById(R.id.home_page_posts_list);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter();
        homePagePostList.setAdapter(recyclerAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        homePagePostList.setLayoutManager(layoutManager);
        return v;



    }
}
