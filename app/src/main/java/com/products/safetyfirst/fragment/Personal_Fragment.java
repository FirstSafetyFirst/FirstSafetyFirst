package com.products.safetyfirst.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.products.safetyfirst.R;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.impementations.presenter.UpdateProfilePresenterImpl;
import com.products.safetyfirst.interfaces.presenter.UpdateProfilePresenter;
import com.products.safetyfirst.interfaces.view.UpdateProfileView;
import com.products.safetyfirst.Pojos.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

@SuppressWarnings({"ALL", "EmptyMethod"})
public class Personal_Fragment extends Fragment implements UpdateProfileView, View.OnClickListener {
    private static final int RESULT_GALLERY_IMAGE = 100;
    private static final int RESULT_CAMERA_IMAGE = 101;
    private static final int REQUEST_EXTERNAL_STORAGE = 101;
    FirebaseUser user;
    private Uri Imagepath = null;
    private EditText mName;
    private TextView mPhone;
    private EditText mCompany;
    private EditText mDesignation;
    private ImageView mPhoto;
    //private EditText mCertificate;
    private EditText mCity;
    private ProgressBar mProgressBar;

    private boolean check = false;
    private View mainView;
    private UpdateProfilePresenter presenter;
    private File Imagefile;
    private Button mSubmit;


    public Personal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_personal, container, false);


        mPhoto = mainView.findViewById(R.id.camera);
        mName = mainView.findViewById(R.id.username);
        mPhone = (EditText) mainView.findViewById(R.id.phone);
        mCompany = mainView.findViewById(R.id.company);
        mDesignation = mainView.findViewById(R.id.designation);
        mCity = mainView.findViewById(R.id.city);
        mProgressBar = mainView.findViewById(R.id.progressBar);
        mSubmit = mainView.findViewById(R.id.submit);

        presenter = new UpdateProfilePresenterImpl(this);
        presenter.requestCurrentDetails();
        mSubmit.setOnClickListener(this);
        mPhoto.setOnClickListener(this);
        return mainView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUsernameError() {
        mName.setError("Required");
    }

    @Override
    public void setPhoneError() {
        mPhone.setError("Required");
    }

    @Override
    public void setCompanyError() {
        mCompany.setError("Required");
    }

    @Override
    public void setDesignationdError() {
        mDesignation.setError("Required");
    }

    @Override
    public void setCertificateError() {
       // mCertificate.setError("Required");
    }

    @Override
    public void setCityError() {
        mCity.setError("Required");
    }

    @Override
    public void navigateToHome() {
        presenter.requestCurrentDetails();
    }

    @Override
    public void setUser(UserModel user) {
        mName.setText(user.getUsername());
        mPhone.setText(String.valueOf(user.getPhone()));
        mCompany.setText(user.getCompany());
        mDesignation.setText(user.getDesignation());
        mCity.setText(user.getCity());
        Glide.with(getContext()).load(user.getPhotoUrl())
                .error(R.drawable.ic_person_black_24dp)
                .transform(new CircleTransform(getContext()))
                .into(mPhoto);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError() {
        Toast.makeText(getContext(), "Update Failed", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onStop() {
        super.onStop();
    }

    private void ChangeProfilePic() {

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
            TakePhoto();
        } else {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                TakePhoto();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(getContext(), "External Storage and Camera permission is required to read images from device", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        REQUEST_EXTERNAL_STORAGE);
            }
        }

    }

    private void TakePhoto() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setItems(R.array.options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent galleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryintent, RESULT_GALLERY_IMAGE);
                } else if (which == 1) {
                    Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (cameraintent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivityForResult(cameraintent, RESULT_CAMERA_IMAGE);
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        check = true;
        if (requestCode == RESULT_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Imagepath = data.getData();
            InputStream inputstream;
            try {
                inputstream = getActivity().getContentResolver().openInputStream(Imagepath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputstream);
                bitmap = Bitmap.createScaledBitmap(bitmap, 710, 710, true);
                mPhoto.setImageBitmap(bitmap);
                UploadPhoto(Imagepath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        } else if (requestCode == RESULT_CAMERA_IMAGE && resultCode == RESULT_OK) {

            Bitmap bitmap;
            bitmap = (Bitmap) data.getExtras().get("data");
            bitmap = Bitmap.createScaledBitmap(bitmap, 710, 710, true);
            mPhoto.setImageBitmap(bitmap);
            Imagepath = getImageUri(getContext(), bitmap);
            UploadPhoto(Imagepath);
        }

    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void UploadPhoto(Uri Imagepathq) {

        presenter.updatePhoto(Imagepathq);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submit) {
            presenter.validateCredentials(mName.getText().toString(), mPhone.getText().toString(), mCompany.getText().toString(),
                    mDesignation.getText().toString(), "", mCity.getText().toString());


        }
        if (i == R.id.camera) {
            ChangeProfilePic();
        }
    }
}
