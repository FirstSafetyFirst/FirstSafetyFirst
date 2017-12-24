package com.products.safetyfirst.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.products.safetyfirst.R;
import com.products.safetyfirst.androidhelpers.ImageHelper;
import com.products.safetyfirst.androidhelpers.ImageSelectionHelper;
import com.products.safetyfirst.androidhelpers.NotificationHelper;
import com.products.safetyfirst.androidhelpers.TagHelper;
import com.products.safetyfirst.interfaces.view.SimpleNotification;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.modelhelper.UserHelper;
import com.products.safetyfirst.utils.Analytics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    /* Views */
    private RichEditor editor;
    private ImageButton boldBtn;
    private ImageButton italicBtn;
    private ImageButton underlineBtn;
    private ImageButton pickImgBtn;
    private Button createPostBtn;
    private RecyclerView imgView;
    private EditText titleText;
    private Button tag_button;
    private HorizontalScrollView horizontalScrollView;

    /* Recycler view Adapter */
    private ImageAdapter imgAdapter;

    /* Constants */
    private static final int REQUEST_READ = 12;

    /* Variables used */
    private final List<Bitmap> imageList = new ArrayList<>();

    /* Helper class instances */
    private UserHelper user;
    private PostHelper postHelper;
    private SimpleNotification notifHelper;
    private ImageSelectionHelper imageSelectionHelper;
    private ImageHelper imageHelper;
    private TagHelper tagHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_new_post);

        user = UserHelper.getInstance();
        postHelper = PostHelper.getInstance();
        notifHelper = NotificationHelper.getInstance();
        imageHelper = ImageHelper.getInstance();
        tagHelper= new TagHelper();

        /* CHeck for sign in */
        if(!user.isSignedIn()) {
            finish();
        }

        editor = findViewById(R.id.new_post_edit);
        boldBtn = findViewById(R.id.bold_btn);
        italicBtn = findViewById(R.id.italic_btn);
        underlineBtn = findViewById(R.id.underline_btn);
        pickImgBtn = findViewById(R.id.pick_img_btn);
        createPostBtn = findViewById(R.id.post_btn);
        imgView = findViewById(R.id.images_list_view);
        titleText = findViewById(R.id.new_post_title);
        tag_button= findViewById(R.id.post_tag_btn);
        horizontalScrollView= findViewById(R.id.horizontal_scroll_view_for_tags);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);

        initEditor();
        initImgRecycler();
    }

    private void initEditor() {
        editor.setEditorHeight(400);
        editor.setPadding(10, 10, 50, 10);
        editor.setPlaceholder("Type question...");
        boldBtn.setOnClickListener(this);
        italicBtn.setOnClickListener(this);
        underlineBtn.setOnClickListener(this);
        pickImgBtn.setOnClickListener(this);
        createPostBtn.setOnClickListener(this);
        tag_button.setOnClickListener(this);
    }

    private void initImgRecycler() {
        imgAdapter = new ImageAdapter(imageList);
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        imgView.setLayoutManager(mLayoutManager);
        imgView.setItemAnimator(new DefaultItemAnimator());
        imgView.setAdapter(imgAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.bold_btn: editor.setBold(); break;
            case R.id.italic_btn: editor.setItalic(); break;
            case R.id.underline_btn: editor.setUnderline(); break;
            case R.id.pick_img_btn: pickImage(); break;
            case R.id.post_btn: createNewPost(); break;
            case R.id.post_tag_btn: handleClickForTagButton(); break;
        }
    }
    private int counter=0;
    private void handleClickForTagButton() {
        if(counter==0){
            showTags();
        }
        else if(counter%2==1){
            horizontalScrollView.setVisibility(View.GONE);
        }
        else {
            horizontalScrollView.removeAllViews();
            post_tags.clear();
            showTags();
        }
        counter++;
    }

    private  ArrayList<String> post_tags= new ArrayList<>();
    private Button button;

    private void showTags() {
        String post_content = (titleText.getText().toString()+" "+editor.getHtml()).toLowerCase();

        ArrayList<String> tags= tagHelper.getTags();
        HashMap<String, Boolean> toBeShownTags= new HashMap<>();
        //firstly make all the buttons greyed out
        // (boolean value in hashmap corresponds to the display behaviour of button)
        for(int i=0; i<tags.size(); i++){
            toBeShownTags.put(tags.get(i),false);

        }
        //now search for the tags in the post_content
        for(int i=0;i<tags.size();i++){
            if(post_content.contains(tags.get(i))){
                toBeShownTags.put(tags.get(i),true);
                post_tags.add(tags.get(i));
            }
        }

        LinearLayout linearLayout= new LinearLayout(NewPostActivity.this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(int i=0; i<tags.size(); i++){
            button= (Button) inflater.inflate(R.layout.tag_item_layout,linearLayout,false);
            button.setText(tags.get(i));
            if(toBeShownTags.get(tags.get(i))) {
                button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                button.setTag("true");
            }
            else
                button.setTag("false");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button b= (Button)view;
                    if(b.getTag().toString().equals("false")) {
                        post_tags.add(b.getText().toString());
                        b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                        b.setTag("true");
                        Toast t= Toast.makeText(NewPostActivity.this,
                                b.getText().toString()+" Tag Added!",Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.BOTTOM,0,0);
                        t.show();
                    }
                    else{
                        post_tags.remove(b.getText().toString());
                        b.setTag("false");
                        b.setBackgroundColor(getResources().getColor(R.color.greyDisabled));
                        Toast t= Toast.makeText(NewPostActivity.this,
                                b.getText().toString()+" Tag Removed!",Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.BOTTOM,0,0);
                        t.show();

                    }

                }
            });
            linearLayout.addView(button);
        }
        horizontalScrollView.addView(linearLayout);

        horizontalScrollView.setVisibility(View.VISIBLE);
    }

    private void pickImage() {
        imageSelectionHelper = new ImageSelectionHelper(this);
        imageSelectionHelper.pickMultipleImages();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if(imageSelectionHelper != null){
            imageList.addAll(imageSelectionHelper.onReceiveResult(requestCode, resultCode, data));
            imgAdapter.notifyDataSetChanged();
        }
        for (int x=0; x < imageList.size(); x++) {
            imageList.set(x, imageHelper.downScale(imageList.get(x)));
        }
    }

    private void createNewPost() {
        if(!titleText.getText().toString().trim().equals("") && !editor.getHtml().trim().equals("")) {
            final DocumentReference postKey = postHelper.createPostKey();
            Toast.makeText(this, postKey.getId(), Toast.LENGTH_SHORT).show();
            Analytics.logEventShare(getApplicationContext(),titleText.getText().toString(),postKey.toString());
            List<String> imageUrls = new ArrayList<>();
            postHelper.createImageUrls(postKey.toString(), imageList, imageUrls, 0, new PostHelper.UploadCallbacks() {
                NotificationHelper.ProgressNotification progressNotification;

                @Override
                public void onComplete(List<String> imageList) {

                    postHelper.createNewPost(postKey, titleText.getText().toString(), editor.getHtml(), null, imageList,post_tags);

                    int notificationId = notifHelper.createNotif(NewPostActivity.this, "Created new post", titleText.getText().toString());
                    if(progressNotification != null) {
                        progressNotification.onCompleteProgress("Image upload complete");
                    } else {
                        finish();
                    }
                }

                @Override
                public void onStartUpload() {
                    progressNotification = notifHelper.createProgressNotif(NewPostActivity.this, "Uploading Images", "Image upload progress");
                    Toast.makeText(NewPostActivity.this, "Images uploading check notification", Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onProgress(int progress, int total) {
                    progressNotification.onProgress(progress, total);
                }

                @Override
                public void onFail(Exception e) {
                    if(progressNotification != null) {
                        progressNotification.onCompleteProgress("Image Upload error");
                    } else {
                        int notificationId = notifHelper.createNotif(NewPostActivity.this, "Post creation failed", titleText.getText().toString());
                        finish();
                    }
                }
            });
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private final List<Bitmap> thumbList;

        public ImageAdapter(List<Bitmap> thumbList) {
            this.thumbList = thumbList;
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_post_image_thumb, parent, false);
            return new ImageViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            Bitmap thumb = thumbList.get(position);
            holder.thumbImg.setImageBitmap(thumb);
        }

        @Override
        public int getItemCount() {
            return thumbList.size();
        }

        public class ImageViewHolder extends RecyclerView.ViewHolder {

            private final ImageView thumbImg;
            private ImageViewHolder(View itemView) {
                super(itemView);
                thumbImg = itemView.findViewById(R.id.new_post_img);
            }
        }
    }
}
