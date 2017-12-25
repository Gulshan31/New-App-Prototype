package com.kinitoapps.moneymanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TodayFragment.OnFragmentInteractionListener,YesterdayFragment.OnFragmentInteractionListener {
    boolean mDrawerItemClicked = false;
    short clicked = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = TodayFragment.class;
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        SharedPreferences sharedPreferences = getSharedPreferences("SWITCH", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("SWITCH",true)) {
            Log.v("INSIDE","home.java");
            startService(new Intent(this, LockScreenService.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                if(mDrawerItemClicked) {
                    android.support.v4.app.Fragment fragment = null;
                    Class fragmentClass = null;
                    if (clicked == 8)
                        startActivity(new Intent(home.this, Settings.class));

                    else if (clicked == 1) {
                        fragmentClass = TodayFragment.class;
                        try {
                            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();

                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.flContent, fragment).commit();
                    }

                    else if (clicked == 2){
                        fragmentClass = YesterdayFragment.class;
                        try {
                            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).replace(R.id.flContent, fragment).commit();
                    }

                    clicked = 0;
                    mDrawerItemClicked = false;


                }

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().findItem(R.id.nav_camera).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
//workaround because opening settings does some weird stuff to today tab
//    @Override
//    protected void onResume() {
//        super.onResume();
//        NavigationView mNavigationView = (NavigationView) findViewById(R.id.nav_view);
//        boolean isNoneChecked = true;
//        int size = mNavigationView.getMenu().size();
//        for (int i = 0; i < size; i++) {
//            if(mNavigationView.getMenu().getItem(i).isChecked()){
//                isNoneChecked = false;
//                break;
//            }
//
//        }
//
//        if(isNoneChecked)
//            mNavigationView.getMenu().findItem(R.id.nav_camera).setChecked(true);
//
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //workaround for bug with selecting item 1 at default in navigationview
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        if(navigationView.getMenu().findItem(R.id.nav_camera).isChecked() && clicked!=1){
//            navigationView.getMenu().findItem(R.id.nav_camera).setChecked(false);
//
//        }
//        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        if (id == R.id.nav_camera) {
            clicked = 1;
            //part of the work around
//            navigationView.getMenu().findItem(R.id.nav_camera).setChecked(true);
        } else if (id == R.id.nav_gallery) {
            clicked = 2;


        } else if (id == R.id.nav_slideshow) {


        } else if (id == R.id.nav_edit_history) {


        } else if (id == R.id.nav_settings) {
            clicked = 8;
        }
        mDrawerItemClicked = true;
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}