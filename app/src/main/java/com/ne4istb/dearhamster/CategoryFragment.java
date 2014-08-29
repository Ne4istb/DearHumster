package com.ne4istb.dearhamster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class CategoryFragment extends Fragment {

    public static final String ARG_CATEGORY_NAME = "category_name";
    public static final String ARG_IMAGE_POSITION = "image_position";

    public static CategoryFragment newInstance(String type) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_NAME, type);
        fragment.setArguments(args);
        return fragment;
    }

    public CategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        final String categoryName = getArguments().getString(ARG_CATEGORY_NAME);

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity(), categoryName));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra(ARG_CATEGORY_NAME, categoryName);
                intent.putExtra(ARG_IMAGE_POSITION, position);
                startActivity(intent);
            }
        });

        return rootView;
    }
}