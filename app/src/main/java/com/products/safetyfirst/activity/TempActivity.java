package com.products.safetyfirst.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.products.safetyfirst.fragment.Dash_Fragment;
import com.products.safetyfirst.fragment.Discussion_Fragment;
import com.products.safetyfirst.fragment.KnowIt_Fragment;
import com.products.safetyfirst.fragment.Laws_Fragment;
import com.products.safetyfirst.fragment.News_Events_Fragment;
import com.products.safetyfirst.fragment.ProfileFragment.AnswersFragment;
import com.products.safetyfirst.fragment.ProfileFragment.ProjectsFragment;
import com.products.safetyfirst.fragment.UpdateProfileFragment;

import java.util.ArrayList;
import java.util.List;

public class  TempActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ProjectsFragment.OnFragmentInteractionListener {
    private static final String TAG_FRAGMENT_DASH = "tag_frag_dash";
    private static final String TAG_FRAGMENT_HOME = "tag_frag_home";
    private static final String TAG_FRAGMENT_NEWS = "tag_frag_news";
    private static final String TAG_FRAGMENT_DISCUSSION = "tag_frag_discussion";
    private static final String TAG_FRAGMENT_LAWS = "tag_frag_laws";
    private static final String TAG_FRAGMENT_KNOWIT = "tag_frag_knowit";
    private static final String TAG_FRAGMENT_UPDATE_PROFILE = "tag_fragment_update_profile";
    List<Fragment> fragments = new ArrayList<>(5);
    private FirebaseUser mFirebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        buildFragmentsList();

        // Set the 0th Fragment to be displayed by default.
        switchFragment(0, TAG_FRAGMENT_DASH);
        navigationView.getMenu().getItem(0).setChecked(true);
         mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (mFirebaseUser == null) {
           // startActivity(new Intent(this, SignInActivity.class));
          //  finish();


            // get menu from navigationView
            Menu menu = navigationView.getMenu();

            // find MenuItem you want to change
            MenuItem nav_logout = menu.findItem(R.id.nav_logout);

            // set new title to the MenuItem
            nav_logout.setTitle("Sign In");

            //Disable Update Profile if not signed in
            menu.findItem(R.id.nav_update_profile).setEnabled(false);
        }

    }

    public void open_news(View v){
        switchFragment(1, TAG_FRAGMENT_NEWS);
    }
    public void open_discussion(View v){
        switchFragment(2, TAG_FRAGMENT_DISCUSSION);
    }
    public void open_knowit(View v){
        switchFragment(4, TAG_FRAGMENT_KNOWIT);
    }
    public void open_laws(View v){
        switchFragment(3, TAG_FRAGMENT_LAWS);
    }

    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragments.get(pos), tag)
                .addToBackStack("Frag1")
                .commit();
    }


    private void buildFragmentsList() {
        Dash_Fragment dashFragment = new Dash_Fragment();
        News_Events_Fragment newsFragment = new News_Events_Fragment();
        Discussion_Fragment discussionFragment = new Discussion_Fragment();
        Laws_Fragment lawsFragment = new Laws_Fragment();
        KnowIt_Fragment knowFragment = new KnowIt_Fragment();
        UpdateProfileFragment updateProfileFragment = new UpdateProfileFragment();

        fragments.add(dashFragment);
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
        }
        else if(getSupportFragmentManager().findFragmentByTag("Frag1") != null){
            getSupportFragmentManager().popBackStackImmediate("Frag1",0);
        }
        else {
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
        // Handle navigation view item clicks here.
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
                startActivity(new Intent(TempActivity.this,SignInActivity.class));
            }
            else
                showLogoutDialog();

        } else if (id == R.id.nav_update_profile){
            switchFragment(5, TAG_FRAGMENT_UPDATE_PROFILE);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    
    private void showTncDialog(String title, String body) {

        final Dialog dialog = new Dialog(TempActivity.this);
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

        final Dialog dialog = new Dialog(TempActivity.this);
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

        final Dialog dialog = new Dialog(TempActivity.this);
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