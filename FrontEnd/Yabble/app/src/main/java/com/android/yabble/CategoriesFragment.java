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

public class CategoriesFragment extends Fragment {
    private RecyclerView categoriesPageList;

    View v1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v1 = inflater.inflate(R.layout.fragment_categories_layout, container, false);
        categoriesPageList = (RecyclerView) v1.findViewById(R.id.categories_page_list);
        RecyclerAdapterCategory recyclerAdapterCategories = new RecyclerAdapterCategory();
        categoriesPageList.setAdapter(recyclerAdapterCategories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        categoriesPageList.setLayoutManager(layoutManager);
        return v1;
    }
}
