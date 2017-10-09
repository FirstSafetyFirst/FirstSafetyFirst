package com.products.safetyfirst.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.products.safetyfirst.R;

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

    /* Constants */
    private static final int PICK_IMAGE = 233;
    private static final int REQUEST_READ = 12;

    /* Variables used */
    private List<Bitmap> images = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        editor = (RichEditor) findViewById(R.id.new_post_edit);
        boldBtn = (ImageButton) findViewById(R.id.bold_btn);
        italicBtn = (ImageButton) findViewById(R.id.italic_btn);
        underlineBtn = (ImageButton) findViewById(R.id.underline_btn);
        pickImgBtn = (ImageButton) findViewById(R.id.pick_img_btn);
        createPostBtn = (Button) findViewById(R.id.post_btn);

        initEditor();
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

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.bold_btn: editor.setBold(); break;
            case R.id.italic_btn: editor.setItalic(); break;
            case R.id.underline_btn: editor.setUnderline(); break;
            case R.id.pick_img_btn: pickImage(); break;
            //case R.id.post_btn: createPost(); break;
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
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        startActivityForResult(Intent.createChooser(getIntent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.e("Image", "Received");
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                images.add(imageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
