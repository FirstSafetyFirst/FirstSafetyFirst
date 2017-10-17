package com.products.safetyfirst.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.androidhelpers.ImageSelectionHelper;
import com.products.safetyfirst.androidhelpers.NotificationHelper;
import com.products.safetyfirst.interfaces.view.SimpleNotification;
import com.products.safetyfirst.modelhelper.PostHelper;
import com.products.safetyfirst.modelhelper.UserHelper;

import java.io.IOException;
import java.util.ArrayList;
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

    /* Recycler view Adapter */
    private ImageAdapter imgAdapter;

    /* Constants */
    private static final int PICK_IMAGE = 233;
    private static final int REQUEST_READ = 12;

    /* Variables used */
    private List<Bitmap> imageList = new ArrayList<>();

    /* Helper class instances */
    private UserHelper user;
    private PostHelper postHelper;
    private SimpleNotification notifHelper;
    private ImageSelectionHelper imageSelectionHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        user = new UserHelper();
        postHelper = new PostHelper();
        notifHelper = NotificationHelper.getInstance();

        /* CHeck for sign in */
        if(!user.isSignedIn()) {
            finish();
        }

        editor = (RichEditor) findViewById(R.id.new_post_edit);
        boldBtn = (ImageButton) findViewById(R.id.bold_btn);
        italicBtn = (ImageButton) findViewById(R.id.italic_btn);
        underlineBtn = (ImageButton) findViewById(R.id.underline_btn);
        pickImgBtn = (ImageButton) findViewById(R.id.pick_img_btn);
        createPostBtn = (Button) findViewById(R.id.post_btn);
        imgView = (RecyclerView) findViewById(R.id.images_list_view);
        titleText = (EditText) findViewById(R.id.new_post_title);



        initEditor();
        initImgRecycler();
    }

    void initEditor() {
        editor.setEditorHeight(400);
        editor.setPadding(10, 10, 50, 10);
        editor.setPlaceholder("Type question...");

        boldBtn.setOnClickListener(this);
        italicBtn.setOnClickListener(this);
        underlineBtn.setOnClickListener(this);
        pickImgBtn.setOnClickListener(this);
        createPostBtn.setOnClickListener(this);
    }

    void initImgRecycler() {
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
        }
    }

    void pickImage(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                pickImageMain();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ);
                pickImageMain();
            }
        } else {
            pickImageMain();
        }
    }

    void pickImageMain() {
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
    }

    public void createNewPost() {
        if(!titleText.getText().toString().trim().equals("") && !editor.getHtml().trim().equals("")) {
            final String postKey = postHelper.createPostKey();
            List<String> imageUrls = new ArrayList<>();
            postHelper.createImageUrls(postKey, imageList, imageUrls, 0, new PostHelper.FinalCallback() {
                @Override
                public void onComplete(List<String> imageList) {
                    postHelper.createNewPost(postKey, titleText.getText().toString(), editor.getHtml(), null, imageList);
                    int notificationId = notifHelper.createNotif(NewPostActivity.this, "Created new post", titleText.getText().toString());
                    finish();
                }
            });
        }
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

        private List<Bitmap> thumbList;

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

            private ImageView thumbImg;
            private ImageViewHolder(View itemView) {
                super(itemView);
                thumbImg = (ImageView) itemView.findViewById(R.id.new_post_img);
            }
        }
    }
}
