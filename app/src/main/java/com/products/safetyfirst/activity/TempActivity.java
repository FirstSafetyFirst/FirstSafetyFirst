package com.products.safetyfirst.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.Dash_Fragment;
import com.products.safetyfirst.fragment.Discussion_Fragment;
import com.products.safetyfirst.fragment.KnowIt_Fragment;
import com.products.safetyfirst.fragment.Laws_Fragment;
import com.products.safetyfirst.fragment.News_Events_Fragment;
import com.products.safetyfirst.fragment.UpdateProfileFragment;

import java.util.ArrayList;
import java.util.List;

//import static com.products.safetyfirst.activity.SignInActivity.PREF_KEY_FIRST_START;
//import static com.products.safetyfirst.activity.SignInActivity.REQUEST_CODE_INTRO;

public class  TempActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG_FRAGMENT_DASH = "tag_frag_dash";
    private static final String TAG_FRAGMENT_HOME = "tag_frag_home";
    private static final String TAG_FRAGMENT_NEWS = "tag_frag_news";
    private static final String TAG_FRAGMENT_DISCUSSION = "tag_frag_discussion";
    private static final String TAG_FRAGMENT_LAWS = "tag_frag_laws";
    private static final String TAG_FRAGMENT_KNOWIT = "tag_frag_knowit";
    private FirebaseUser mFirebaseUser;

    List<Fragment> fragments = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

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
            // enable signup when user is not signed in
          //  menu.findItem(R.id.nav_signup).setEnabled(true);


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

        fragments.add(dashFragment);
        fragments.add(newsFragment);
        fragments.add(discussionFragment);
        fragments.add(lawsFragment);
        fragments.add(knowFragment);
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

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_tnc) {

        } else if (id == R.id.nav_invite) {

        } else if (id == R.id.nav_logout) {
            mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (mFirebaseUser == null) {
                startActivity(new Intent(TempActivity.this,SignInActivity.class));


            }
            else
                logout();

        } else if (id == R.id.nav_update_profile){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
