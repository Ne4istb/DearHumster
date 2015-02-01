package com.ne4istb.dearhamster;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageActivity extends ActionBarActivity {

    // Gesture Detector
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String category = getIntent().getStringExtra(CategoryFragment.ARG_CATEGORY_NAME);
        Integer position = getIntent().getIntExtra(CategoryFragment.ARG_IMAGE_POSITION, 0);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ImageFragment(category, position))
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class ImageFragment extends Fragment {

        private String category;
        private Integer position;

        AssetManager assetManager;

        ImageView imageView;

        public ImageFragment() {
        }

        public ImageFragment(String category, Integer position) {
            this.category = category;
            this.position = position;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {

            outState.putString(CategoryFragment.ARG_CATEGORY_NAME, category);
            outState.putInt(CategoryFragment.ARG_IMAGE_POSITION, position);

            super.onSaveInstanceState(outState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_image, container, false);
            rootView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
                @Override
                public void onSwipeLeft() {
                    position++;
                    setImage();
//
//                    getFragmentManager()
//                            .beginTransaction()
//
//                                    // Replace the default fragment animations with animator resources representing
//                                    // rotations when switching to the back of the card, as well as animator
//                                    // resources representing rotations when flipping back to the front (e.g. when
//                                    // the system Back button is pressed).
//                            .setCustomAnimations(
//                                    R.animator.card_flip_right_in, R.animator.card_flip_right_out,
//                                    R.animator.card_flip_left_in, R.animator.card_flip_left_out)
//
//                                    // Replace any fragments currently in the container view with a fragment
//                                    // representing the next page (indicated by the just-incremented currentPage
//                                    // variable).
//                            .replace(R.id.container, new ImageFragment(CategoriesActivity.WEDDING_CATEGORY, 1))
//
//                                    // Add this transaction to the back stack, allowing users to press Back
//                                    // to get to the front of the card.
//                            .addToBackStack(null)
//
//                                    // Commit the transaction.
//                            .commit();
                }

                @Override
                public void onSwipeRight() {
                    position--;
                    setImage();
                }
            });

            assetManager = getActivity().getAssets();

            if (savedInstanceState != null) {
                category = savedInstanceState.getString(CategoryFragment.ARG_CATEGORY_NAME);
                position = savedInstanceState.getInt(CategoryFragment.ARG_IMAGE_POSITION);
            }

            imageView = (ImageView) rootView.findViewById(R.id.imageView);

            setImage();

            return rootView;
        }

        private void setImage() {
            Bitmap bitmap = getBitmapFromAsset();
            imageView.setImageBitmap(bitmap);
        }

        @Override
        public void onResume() {
            super.onResume();

            setImage();
        }

        private Bitmap getBitmapFromAsset() {
            InputStream inputStream = null;

            try {
                String[] list = assetManager.list(category);

                if (position < 0)
                    position = list.length - 1;

                if (position >= list.length)
                    position = 0;

                inputStream = assetManager.open(category + "/" + list[position]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BitmapFactory.decodeStream(inputStream);
        }
    }
}
