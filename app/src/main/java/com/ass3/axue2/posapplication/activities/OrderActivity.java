package com.ass3.axue2.posapplication.activities;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.ass3.axue2.posapplication.R;
import com.ass3.axue2.posapplication.fragments.MainOrdersFragment;
import com.ass3.axue2.posapplication.fragments.MainTableFragment;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {


    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);

        assert mViewPager != null;
        mSectionsPagerAdapter.addFragment(new MainOrdersFragment(), "Current Order");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");
        mSectionsPagerAdapter.addFragment(new MainTableFragment(), "Table");

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();
        private  List<String> mFragmentTitles = new ArrayList<>();

        SectionsPagerAdapter(FragmentManager fm) {super(fm);}

        void addFragment(Fragment fragment, String title){
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {return mFragments.get(position);}

        @Override
        public int getCount() {return mFragments.size();}

        @Override
        public CharSequence getPageTitle(int position) {return mFragmentTitles.get(position);}
    }
}
