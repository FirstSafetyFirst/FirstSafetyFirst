package com.products.safetyfirst.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.products.safetyfirst.R;

import jp.wasabeef.richeditor.RichEditor;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    private RichEditor editor;
    private ImageButton boldBtn;
    private ImageButton italicBtn;
    private ImageButton underlineBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        editor = (RichEditor) findViewById(R.id.new_post_edit);
        boldBtn = (ImageButton) findViewById(R.id.bold_btn);
        italicBtn = (ImageButton) findViewById(R.id.italic_btn);
        underlineBtn = (ImageButton) findViewById(R.id.underline_btn);

        initEditor();
    }

    void initEditor() {
        editor.setEditorHeight(200);
        editor.setPadding(10, 10, 50, 10);
        editor.setPlaceholder("Type question...");

        boldBtn.setOnClickListener(this);
        italicBtn.setOnClickListener(this);
        underlineBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.bold_btn: editor.setBold(); break;
            case R.id.italic_btn: editor.setItalic(); break;
            case R.id.underline_btn: editor.setUnderline(); break;
        }
    }
}
