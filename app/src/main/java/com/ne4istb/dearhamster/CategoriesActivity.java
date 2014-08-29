package com.ne4istb.dearhamster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.Locale;


public class CategoriesActivity extends ActionBarActivity implements ActionBar.TabListener {

    public static final String FIRST_RUN_PREFERENCE = "dearHamster.firstRun";

    public static final String WEDDING_CATEGORY = "wedding";
    public static final String BEASTS_CATEGORY = "beasts";
    public static final String MINIONS_CATEGORY = "minions";

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        onFirstRun();

        setContentView(R.layout.activity_categories);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setTitle(getString(R.string.app_title));
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#ffaa66cc"));
        actionBar.setBackgroundDrawable(colorDrawable);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    private void onFirstRun() {

        SharedPreferences preference = getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        boolean firstRun = preference.getBoolean(FIRST_RUN_PREFERENCE, true);

//        if (firstRun || UtilsHelper.DEBUG) {
        if (firstRun) {

            new AlarmNotificationRegistrationService(this).Register();

            preference.edit().putBoolean(FIRST_RUN_PREFERENCE, false).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.categories, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return CategoryFragment.newInstance(WEDDING_CATEGORY);
                case 1:
                    return CategoryFragment.newInstance(BEASTS_CATEGORY);
                case 2:
                    return CategoryFragment.newInstance(MINIONS_CATEGORY);
                default:
                    throw new IllegalArgumentException();

            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }



}
