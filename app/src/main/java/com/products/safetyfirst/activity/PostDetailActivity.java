package com.products.safetyfirst.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.CommentsAdapter;
import com.products.safetyfirst.adapters.Home_Slider_Adapter;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.impementations.presenter.PostDetailPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.PostDetailPresenter;
import com.products.safetyfirst.interfaces.view.PostDetailView;
import com.products.safetyfirst.models.Comment;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;
import com.products.safetyfirst.utils.JustifiedWebView;
import com.products.safetyfirst.utils.PrefManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import jp.wasabeef.richeditor.RichEditor;


@SuppressWarnings({"ALL", "EmptyMethod"})
public class PostDetailActivity extends BaseActivity implements View.OnClickListener, PostDetailView {


    public static final String EXTRA_POST_KEY = "post_key";
    private static final String TAG = "PostDetailActivity";

    private static final String URL_REGEX2 = "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)";

    private JustifiedWebView mBodyView;
    private TextView mTitleView;
    private TextView mAuthorName;
    private TextView mAuthorEmail;
    private TextView mDate;
    private ImageView mAuthorImage;
    private ImageView mOverflow;

    private ImageButton mShare;
    private String mPostKey;
    private FloatingActionButton fab;
    private Button mView_details;

    private PostDetailPresenter presenter;

    private CommentsAdapter commentsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView commentsRecycler;
    private LinearLayoutManager commentsLayoutManager;
    private List<String> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_post_detail);

        mBodyView = findViewById(R.id.post_body);
        mTitleView = findViewById(R.id.post_title);
        mDate = findViewById(R.id.dateTime);
        mAuthorImage = findViewById(R.id.post_author_photo);
        mAuthorName = findViewById(R.id.post_author);
        mAuthorEmail = findViewById(R.id.post_author_email);

        mView_details = findViewById(R.id.view_details);
        mView_details.setVisibility(View.GONE);

        mOverflow = findViewById(R.id.overflow);
        mOverflow.setVisibility(View.GONE);
     //   mShare =(ImageButton) findViewById(R.id.share);
        recyclerView = findViewById(R.id.recycler_view);

        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        fab = findViewById(R.id.fab);

//        mShare.setOnClickListener(this);
        fab.setOnClickListener(this);

        presenter = new PostDetailPresenterImpl(this, mPostKey);
        presenter.requestPost();

        PrefManager prefManager = new PrefManager(this);
        if (prefManager.isFirstPostLaunch()) {
            showTutorial();
        }

        prefManager.setFirstPostLaunch(false);

        initCommentsView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                if(getCurrentUserId() == null ) {
                    Toast.makeText(this, "Please Sign In to post a comment", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Dialog fullscreenDialog = new Dialog(PostDetailActivity.this, R.style.DialogFullscreen);
                fullscreenDialog.setContentView(R.layout.dialog_fullscreen);

                final RichEditor editor = fullscreenDialog.findViewById(R.id.new_ans_edit);

                initEditor(editor);

                TextView mPostTitle = fullscreenDialog.findViewById(R.id.post_title);
                mPostTitle.setVisibility(View.GONE);

                ImageView img_dialog_fullscreen_close = fullscreenDialog.findViewById(R.id.img_dialog_fullscreen_close);
                img_dialog_fullscreen_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullscreenDialog.dismiss();
                    }
                });

                Button mPostButton = fullscreenDialog.findViewById(R.id.post_btn);
                mPostButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if(!editor.getHtml().trim().equals("")){
                            presenter.setAns(mPostKey, editor.getHtml());
                            fullscreenDialog.dismiss();
                        }
                        else {
                            Toast.makeText(PostDetailActivity.this, "Comment cannot be left blank", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                fullscreenDialog.show();
                break;
        }
    }

    private void initEditor(RichEditor editor) {
        editor.setEditorHeight(400);
        editor.setPadding(10, 10, 50, 10);
        editor.setPlaceholder("Write an Answer...");
    }

    @Override
    public void setBookMark() {

    }

    @Override
    public void share() {

    }

    @Override
    public void addComment() {

    }

    @Override
    public void setPost(PostModel post) {
        if(post != null){
            mTitleView.setText(post.getTitle());
            mBodyView.setText(post.getBody());

            if(post.getImageList() != null){
                List<String> imageList = post.getImageList();
               // Toast.makeText(this, "Images", Toast.LENGTH_SHORT).show();
                initImageRecycler(post.getImageList());


            }else{
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void setAuthor(UserModel user) {
        if(user != null){
            mAuthorName.setText(user.getUsername());
            mAuthorEmail.setText(user.getEmail());
            Glide.with(getBaseContext()).load(user.getPhotoUrl())
                    .error(R.drawable.ic_person_black_24dp)
                    .transform(new CircleTransform(getBaseContext())).into(mAuthorImage);

        }
    }

    @Override
    public void setComments(List<Comment> comments, List<String> keys) {

    }

    private void showTutorial(){

    }

    private void initImageRecycler(List<String> imageList) {


        Home_Slider_Adapter imageAdapter = new Home_Slider_Adapter(PostDetailActivity.this, imageList);
        recyclerView.setAdapter(imageAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    }

    private void initCommentsView(){
        commentsRecycler = findViewById(R.id.recycler_comments);
        commentsLayoutManager = new LinearLayoutManager(PostDetailActivity.this);
        commentsRecycler.setLayoutManager(commentsLayoutManager);
        commentsRecycler.setHasFixedSize(true);
        commentsRecycler.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(commentsRecycler.getContext(),
                commentsLayoutManager.getOrientation());
        commentsRecycler.addItemDecoration(dividerItemDecoration);

        commentsAdapter = new CommentsAdapter(PostDetailActivity.this, mPostKey);


        commentsAdapter.request();

        commentsRecycler.setAdapter(commentsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }




    private static boolean checkHyperlinkText(String input) {
        Log.d("TAGhyper7", "string: " + input);
        Spanned output;
        String preText, postText;

        Pattern p = Pattern.compile(URL_REGEX2, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        String link = "";
        String[] parts = input.split("\\s");
        preText = "";
        postText = "";
        int flag = 0;
        for (String item : parts) {
            if (!p.matcher(item).matches() && flag == 0) {
                preText += item;
                preText += " ";
            }
            if (p.matcher(item).matches()) {
                link = item;
                //Log.d("Here",item);
                flag = 1;
            }
            if (!p.matcher(item).matches() && flag == 1) {
                postText += " ";
                postText += item;
            }
        }
        return flag == 1;
    }



}
