package com.newscentral.base;

import android.app.AlertDialog;
import android.app.Notification;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.florent37.materialviewpager.MaterialViewPager;
import com.github.florent37.materialviewpager.header.HeaderDesign;
import com.newscentral.model.Category;
import com.newscentral.utils.Utils;

import dmax.dialog.SpotsDialog;
import newscentral.com.newscentral.R;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Category[] categories = new Category[0];
    private MaterialViewPager mViewPager;
    private Toolbar toolbar;
    private boolean doubleBackToExitPressedOnce = false;
    private AlertDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity);
        progressDialog = new SpotsDialog(BaseActivity.this, R.style.Custom);
        if (categories.length >= 1) {
            setTitle("");
            mViewPager = (MaterialViewPager) findViewById(R.id.materialViewPager);
            toolbar = mViewPager.getToolbar();
            mViewPager.getPagerTitleStrip().setTextColor(Color.WHITE);
            mViewPager.getPagerTitleStrip().setIndicatorHeight(2);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }

            new SetFragmentAdapter().execute();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.base, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    void showHideProgressBar(boolean value) {
         if (value) {
                progressDialog.show();
             progressDialog.setTitle("");
             progressDialog.setMessage("");
            } else {
                progressDialog.dismiss();
            }
//        }
    }

    class SetFragmentAdapter extends AsyncTask<Object, Object, String> {
        private MaterialViewPager.Listener q;
        private FragmentStatePagerAdapter p;

        @Override
        protected void onPostExecute(String s) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mViewPager.getViewPager().setAdapter(p);
                    mViewPager.getViewPager().getAdapter().notifyDataSetChanged();
                    mViewPager.setMaterialViewPagerListener(q);
                    mViewPager.setToolbar(null);
                    mViewPager.getViewPager().setOffscreenPageLimit(mViewPager.getViewPager().getAdapter().getCount());
                    mViewPager.getPagerTitleStrip().setViewPager(mViewPager.getViewPager());
                    ImageView logo = (ImageView) findViewById(R.id.logo_white);
                    if (logo != null) {
                        logo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mViewPager.notifyHeaderChanged();
                                int currentItem = mViewPager.getViewPager().getCurrentItem();
                                Toast.makeText(getApplicationContext(), "Yes, the title is clickable  " + categories[currentItem].getName(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    showHideProgressBar(false);
                }
            }, 2000);


        }

        @Override
        protected void onPreExecute() {
            showHideProgressBar(true);
        }

        @Override
        protected String doInBackground(Object... params) {
            p = new FragmentStatePagerAdapter(getSupportFragmentManager()) {

                @Override
                public Fragment getItem(int position) {
                    Fragment fragment = new RecyclerViewFragment(getApplicationContext());
                    Bundle bundle = new Bundle();
                    bundle.putLong("categoryId", categories[position].getId());
                    fragment.setArguments(bundle);
                    return fragment;
                }

                @Override
                public int getCount() {
                    return categories.length;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return categories[position].getName();
                }
            };



            q = new MaterialViewPager.Listener() {

                @Override
                public HeaderDesign getHeaderDesign(int page) {
                    /*FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Fragment fragment = new RecyclerViewFragment(BaseActivity.this);
                    Bundle bundle = new Bundle();
                    bundle.putLong("categoryId", categories[page].getId());
                    fragment.setArguments(bundle);
                    fragmentTransaction.add(fragment, "frag1");
                    fragmentTransaction.commit();*/
                    Bitmap bitmap = Utils.decodeBase64(categories[page].getLogoUrl());
                    int darkVibrantColor = Palette.generate(bitmap).getVibrantColor(999999);
                    ImageView logo = (ImageView) findViewById(R.id.logo_white);
                    if (logo != null) {
                        int currentItem = mViewPager.getViewPager().getCurrentItem();
                        String categoryName = categories[currentItem].getName();
                        int logoResource = R.drawable.ic_menu_camera;
                        switch (categoryName.toLowerCase()) {
                            case "sports":
                                logoResource = R.drawable.sports_logo_small;
                                break;
                            case "entertainment":
                                logoResource = R.drawable.entertainment_logo_small;
                                break;
                            default:
                                logoResource = R.drawable.ic_menu_gallery;
                        }
                        logo.setImageResource(logoResource);
                    }
                    return HeaderDesign.fromColorAndDrawable(
                            darkVibrantColor, new BitmapDrawable(getResources(), bitmap));
                }

            };

//            mViewPager.getToolbar().setLogo(R.drawable.logo_xxx_small_colorful);



            return "";
        }
    }

}
