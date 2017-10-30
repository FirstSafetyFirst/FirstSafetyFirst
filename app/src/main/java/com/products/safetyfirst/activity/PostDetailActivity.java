package com.products.safetyfirst.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.products.safetyfirst.R;
import com.products.safetyfirst.adapters.SliderAdapter;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.impementations.presenter.PostDetailPresenterImpl;
import com.products.safetyfirst.interfaces.presenter.PostDetailPresenter;
import com.products.safetyfirst.interfaces.view.PostDetailView;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.models.UserModel;
import com.products.safetyfirst.utils.JustifiedWebView;
import com.products.safetyfirst.utils.PrefManager;
import com.ramotion.cardslider.CardSliderLayoutManager;
import com.ramotion.cardslider.CardSnapHelper;

import java.util.List;


public class PostDetailActivity extends BaseActivity implements View.OnClickListener, PostDetailView {


    public static final String EXTRA_POST_KEY = "post_key";
    private static final String TAG = "PostDetailActivity";

    private final int[] pics = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, R.drawable.p5};

    private final SliderAdapter sliderAdapter = new SliderAdapter(pics, 5, new OnCardClickListener());

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

    private CardSliderLayoutManager layoutManger;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        initRecyclerView();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Toast.makeText(this, "New Answer", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image_btn:
                //showImage();
                break;
            case R.id.file_btn:
                //showFile();
                break;

            case R.id.link_btn:
                //showLink();
                break;
        }
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
                Toast.makeText(this, "Images", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "No images", Toast.LENGTH_SHORT).show();
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

    private void showTutorial(){

    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(sliderAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new CardSliderLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    onActiveCardChange();
                }
            }
        });

        layoutManger = (CardSliderLayoutManager) recyclerView.getLayoutManager();

        new CardSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void onActiveCardChange() {
        final int pos = layoutManger.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION ) {
            return;
        }
    }


    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm =  (CardSliderLayoutManager) recyclerView.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = recyclerView.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {
                final Intent intent = new Intent(PostDetailActivity.this, DetailsActivity.class);
                intent.putExtra(DetailsActivity.BUNDLE_IMAGE_ID, pics[activeCardPosition % pics.length]);

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent);
                } else {
                    final CardView cardView = (CardView) view;
                    final View sharedView = cardView.getChildAt(cardView.getChildCount() - 1);
                    final ActivityOptions options = ActivityOptions
                            .makeSceneTransitionAnimation(PostDetailActivity.this, sharedView, "shared");
                    startActivity(intent, options.toBundle());
                }
            } else if (clickedPosition > activeCardPosition) {
                recyclerView.smoothScrollToPosition(clickedPosition);
            }
        }
    }


}
