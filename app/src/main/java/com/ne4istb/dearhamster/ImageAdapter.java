package com.ne4istb.dearhamster;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class ImageAdapter extends BaseAdapter {

    private final String mCategoryName;
    private final LayoutInflater mInflater;
    private Context mContext;
    private AssetManager mAssetManager;
    private String[] mList = {};

    public ImageAdapter(Context context, String categoryName) {

        mInflater = LayoutInflater.from(context);

        mContext = context;
        mAssetManager = mContext.getAssets();
        mCategoryName = categoryName;

        try {
            mList = mAssetManager.list(mCategoryName);
        } catch (IOException e) {
            UtilsHelper.printException(e);
        }
    }

    @Override
    public int getCount() {
        return mList.length;
    }

    @Override
    public Object getItem(int position) {
        return mCategoryName + "/" + mList[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ImageView picture;
        TextView name;

        ImageView imageView;

        if (view == null) {  // if it's not recycled, initialize some attributes
            view = mInflater.inflate(R.layout.image_grid_item, parent, false);
            view.setTag(R.id.picture, view.findViewById(R.id.picture));
//            view.setTag(R.id.text, view.findViewById(R.id.text));
        }

        picture = (ImageView)view.getTag(R.id.picture);
//        name = (TextView)view.getTag(R.id.text);


        Bitmap bitmap = getBitmapFromAsset(position);
        picture.setImageBitmap(bitmap);
//        name.setText(mList[position]);

        return view;
    }

    private Bitmap getBitmapFromAsset(int index)
    {
        InputStream inputStream = null;
        try {
            inputStream = mAssetManager.open((String) getItem(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeStream(inputStream);
    }

}