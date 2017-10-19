package com.products.safetyfirst.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.products.safetyfirst.R;
import com.products.safetyfirst.fragment.Discussion_Fragment;
import com.products.safetyfirst.fragment.KnowIt_Fragment;
import com.products.safetyfirst.fragment.Laws_Fragment;
import com.products.safetyfirst.fragment.News_Events_Fragment;
import com.products.safetyfirst.fragment.ProfileFragment.ProjectsFragment;
import com.products.safetyfirst.fragment.UpdateProfileFragment;
import com.products.safetyfirst.utils.Constants;
import com.products.safetyfirst.utils.PrefManager;

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

    private static final String DEEP_LINK_URL = Constants.DEEP_LINK_URL;

    List<Fragment> fragments = new ArrayList<>(5);
    Toolbar toolbar;
    public static NavigationView navigationView;
    PrefManager prefManager;
    private FirebaseUser mFirebaseUser;
    private FirebaseAnalytics mFirebaseAnalytics;
    public static BottomNavigationView bottomNavigationView;
    @Override
    protected void onResume() {
        super.onResume();
        if (!isLoggedIn()) {
            Menu menuNav = navigationView.getMenu();

            MenuItem nav_item1 = menuNav.findItem(R.id.nav_logout);
            nav_item1.setTitle("Sign In");

            MenuItem nav_item2 = menuNav.findItem(R.id.nav_update_profile);
            nav_item2.setEnabled(false);
        } else {
            Menu menuNav = navigationView.getMenu();

            MenuItem nav_item1 = menuNav.findItem(R.id.nav_logout);
            nav_item1.setTitle("Logout");

            MenuItem nav_item2 = menuNav.findItem(R.id.nav_update_profile);
            nav_item2.setEnabled(true);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        prefManager = new PrefManager(this);
        if (prefManager.isFirstHomeLaunch()) {
            showTutorial();
        }

        prefManager.setFirstHomeLaunch(false);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
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

        switchFragment(1, TAG_FRAGMENT_DISCUSSION);
        navigationView.getMenu().getItem(1).setChecked(true);

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }

                        // Display deep link in the UI
                        if (deepLink != null) {
                        //    Snackbar.make(findViewById(android.R.id.content),
                          //          "Found deep link!", Snackbar.LENGTH_LONG).show();

                            String[] data = deepLink.toString().split("/");


                            String item = data[data.length-2];
                            String key = data[data.length-1];

                            switch (item){
                                case "news":
                                    Intent intent = new Intent(HomeActivity.this, NewsDetailActivity.class);
                                    intent.putExtra(NewsDetailActivity.EXTRA_NEWS_KEY, key);
                                    startActivity(intent);
                                    break;
                                case "event":
                                    Intent eventIntent = new Intent(HomeActivity.this, NewsDetailActivity.class);
                                    eventIntent.putExtra(EventsDetailActivity.EXTRA_EVENT_KEY, key);
                                    startActivity(eventIntent);
                                    break;
                                case "post":
                                    break;
                                default:
                                    break;
                            }

                        } else {
                            Log.d("HomeActivity", "getDynamicLink: no link found");
                        }

                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("HomeActivity", "getDynamicLink:onFailure", e);
                    }
                });


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
            rateMe();
        } else if (id == R.id.nav_tnc) {
            showTncDialog(getString(R.string.tnc), getString(R.string.lorem_ipsum));
        } else if (id == R.id.nav_invite) {
                sendInvite();
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

    private void sendInvite(){
        validateAppCode();

        final Uri deepLink = buildDeepLink(Uri.parse(DEEP_LINK_URL), 0);


        // Share button click listener
        shareDeepLink(deepLink.toString());
    }

    private void rateMe() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + "com.vikas.dtu.safetyfirst2")));
        } catch (android.content.ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + "com.vikas.dtu.safetyfirst2")));
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @VisibleForTesting
    public Uri buildDeepLink(@NonNull Uri deepLink, int minVersion) {
        String domain = getString(R.string.app_code) + ".app.goo.gl";

        // Set dynamic link parameters:
        //  * Domain (required)
        //  * Android Parameters (required)
        //  * Deep link
        // [START build_dynamic_link]
        DynamicLink.Builder builder = FirebaseDynamicLinks.getInstance()
                .createDynamicLink()
                .setDynamicLinkDomain(domain)
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder()
                        .setMinimumVersion(minVersion)
                        .build())
                .setLink(deepLink);

        // Build the dynamic link
        DynamicLink link = builder.buildDynamicLink();
        // [END build_dynamic_link]

        // Return the dynamic link as a URI
        return link.getUri();
    }

    private void shareDeepLink(String deepLink) {


        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLongLink(Uri.parse(deepLink))
                .buildShortDynamicLink()
                .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = task.getResult().getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();

                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.setType("text/html");
                            intent.putExtra(Intent.EXTRA_SUBJECT, "Firebase Deep Link");
                            intent.putExtra(Intent.EXTRA_TEXT,shortLink.toString());
                            startActivity(intent);

                        } else {
                            // Error
                            // ...
                        }
                    }
                });





    }

    private void validateAppCode() {
        String appCode = getString(R.string.app_code);
        if (appCode.contains("YOUR_APP_CODE")) {
            new AlertDialog.Builder(this)
                    .setTitle("Invalid Configuration")
                    .setMessage("Please set your app code in app/build.gradle")
                    .setPositiveButton(android.R.string.ok, null)
                    .create().show();
        }
    }

    void showTutorial(){
        // We load a drawable and create a location to show a tap target here
        // We need the display to get the width and height at this point in time
        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        final Drawable droid = ContextCompat.getDrawable(this, R.drawable.ic_camera_alt_black_24dp);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, droid.getIntrinsicWidth() * 2, droid.getIntrinsicHeight() * 2);
        // Using deprecated methods makes you look way cool
        droidTarget.offset(display.getWidth() / 2, display.getHeight() / 2);

        final SpannableString sassyDesc = new SpannableString("It shows some additional information");
        sassyDesc.setSpan(new StyleSpan(Typeface.ITALIC), sassyDesc.length() - "information".length(), sassyDesc.length(), 0);

        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forToolbarNavigationIcon(toolbar, "This is the menu button", sassyDesc).id(1),
                        // Likewise, this tap target will target the search button
                        TapTarget.forToolbarMenuItem(toolbar, R.id.search, "This is a search icon", "As you can see, it has gotten pretty dark around here...")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2),
                        // You can also target the overflow button in your toolbar
                        TapTarget.forToolbarOverflow(toolbar, "This will show more options", "But they're not useful :(").id(3),
                        // This tap target will target our droid buddy at the given target rect
                        TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                .cancelable(false)
                               // .icon(droid)
                                .id(4)
                )
                .listener(new TapTargetSequence.Listener() {
                    // This listener will tell us when interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(HomeActivity.this, "You are educated now", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

        // You don't always need a sequence, and for that there's a single time tap target
        final SpannableString spannedDesc = new SpannableString("Hello There!! Welcome to Safety First");
        spannedDesc.setSpan(new UnderlineSpan(), spannedDesc.length() - "Safety First".length(), spannedDesc.length(), 0);
        TapTargetView.showFor(this, TapTarget.forView(findViewById(R.id.bottom_navigation), "Hello There!!", spannedDesc)
                .cancelable(false)
                .drawShadow(true)
                .titleTextDimen(R.dimen.title_text_size)
                .tintTarget(false), new TapTargetView.Listener() {
            @Override
            public void onTargetClick(TapTargetView view) {
                super.onTargetClick(view);
                // .. which evidently starts the sequence we defined earlier
                sequence.start();
            }

            @Override
            public void onOuterCircleClick(TapTargetView view) {
                super.onOuterCircleClick(view);
                Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                Log.d("TapTargetViewSample", "You dismissed me :(");
            }
        });


    }
}
