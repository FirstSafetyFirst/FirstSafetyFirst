package com.products.safetyfirst.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.Discussion_Fragment;
import com.products.safetyfirst.fragment.KnowIt_Fragment;
import com.products.safetyfirst.fragment.Laws_Fragment;
import com.products.safetyfirst.fragment.News_Events_Fragment;
import com.products.safetyfirst.fragment.ProfileFragment.ProjectsFragment;
import com.products.safetyfirst.fragment.UpdateProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProjectsFragment.OnFragmentInteractionListener {

    //private static final String TAG_FRAGMENT_HOME = "tag_frag_home";
    private static final String TAG_FRAGMENT_NEWS = "tag_frag_news";
    private static final String TAG_FRAGMENT_DISCUSSION = "tag_frag_discussion";
    private static final String TAG_FRAGMENT_LAWS = "tag_frag_laws";
    private static final String TAG_FRAGMENT_KNOWIT = "tag_frag_knowit";
    private static final String TAG_FRAGMENT_UPDATE_PROFILE = "tag_fragment_update_profile";
    List<Fragment> fragments = new ArrayList<>(5);
    private FirebaseUser mFirebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            //case R.id.home:
                            //    switchFragment(0, TAG_FRAGMENT_HOME);
                            //    return true;
                            case R.id.news:
                                switchFragment(0, TAG_FRAGMENT_NEWS);
                                return true;
                            case R.id.discussion:
                                switchFragment(1, TAG_FRAGMENT_DISCUSSION);
                                return true;
                            case R.id.laws:
                                switchFragment(2, TAG_FRAGMENT_LAWS);
                                return true;
                            case R.id.know_it:
                                switchFragment(3, TAG_FRAGMENT_KNOWIT);
                                return true;
                        }
                        return false;
                    }
                });

        buildFragmentsList();

        // Set the 1st Fragment to be displayed by default.
        switchFragment(1, TAG_FRAGMENT_DISCUSSION);
        navigationView.getMenu().getItem(1).setChecked(true);
    }


    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragments.get(pos), tag)
                .commit();
    }


    private void buildFragmentsList() {
        // Home_Fragment homeFragment = new Home_Fragment();
        News_Events_Fragment newsFragment = new News_Events_Fragment();
        Discussion_Fragment discussionFragment = new Discussion_Fragment();
        Laws_Fragment lawsFragment = new Laws_Fragment();
        KnowIt_Fragment knowFragment = new KnowIt_Fragment();
        UpdateProfileFragment updateProfileFragment = new UpdateProfileFragment();

        // fragments.add(homeFragment);
        fragments.add(newsFragment);
        fragments.add(discussionFragment);
        fragments.add(lawsFragment);
        fragments.add(knowFragment);
        fragments.add(updateProfileFragment);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
        int id = item.getItemId();

        if (id == R.id.nav_faq) {

        } else if (id == R.id.nav_help) {
            showHelpDialog();
        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_tnc) {
            showTncDialog(getString(R.string.tnc), getString(R.string.lorem_ipsum));
        } else if (id == R.id.nav_invite) {

        } else if (id == R.id.nav_logout) {
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mFirebaseUser == null) {
                startActivity(new Intent(HomeActivity.this, SignInActivity.class));
            } else
                showLogoutDialog();

        } else if (id == R.id.nav_update_profile) {
            switchFragment(4, TAG_FRAGMENT_UPDATE_PROFILE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void showTncDialog(String title, String body) {

        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_tnc);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        TextView dialogContent = (TextView) dialog.findViewById(R.id.content);

        dialogTitle.setText(title);
        dialogContent.setText(body);

        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showHelpDialog() {

        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_help);
        TextView dialogTitle = (TextView) dialog.findViewById(R.id.dialogTitle);
        TextView dialogContent = (TextView) dialog.findViewById(R.id.content);

        Button btnDone = (Button) dialog.findViewById(R.id.btn_done);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLogoutDialog() {

        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.setContentView(R.layout.dialog_logout);


        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
