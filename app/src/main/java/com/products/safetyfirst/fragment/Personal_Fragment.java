package com.products.safetyfirst.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.products.safetyfirst.R;
import com.products.safetyfirst.activity.ProfileActivity;
import com.products.safetyfirst.customview.CircleTransform;
import com.products.safetyfirst.models.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.plus.PlusOneDummyView.TAG;
import static com.products.safetyfirst.utils.FirebaseUtils.getCurrentUserId;

/**
 * A simple {@link Fragment} subclass.
 */
public class Personal_Fragment extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {
    FirebaseUser user;
    Uri Imagepath=null;
    EditText mName;
    TextView mPhone;
    EditText mCompany;
    EditText mDesignation;
    ImageView mPhoto;
    Spinner  mJoinAs;
    EditText mCertificate;
    EditText mCity;
    static final int RESULT_GALLERY_IMAGE=100;
    static final int RESULT_CAMERA_IMAGE=101;
    private static final int REQUEST_EXTERNAL_STORAGE=101;
    private ValueEventListener mUserListener;
    private File Imagefile;
    private Button mSubmit;
    private StorageReference mstorageRef;
    StorageReference profilephotoRef;
    String joinAs;
    boolean check=false;
    View mainView;

    private DatabaseReference mProfileReference;
    private ValueEventListener mProfileListener;

    public Personal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_personal, container, false);

        mstorageRef= FirebaseStorage.getInstance().getReference();

        mPhoto = (ImageView) mainView.findViewById(R.id.camera);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeProfilePic();
            }
        });

        mName = (EditText) mainView.findViewById(R.id.username);
        mPhone = (EditText) mainView.findViewById(R.id.phone);
        mCompany = (EditText) mainView.findViewById(R.id.company);
        mDesignation = (EditText) mainView.findViewById(R.id.designation);
        mJoinAs = (Spinner) mainView.findViewById(R.id.type);
        mCertificate = (EditText) mainView.findViewById(R.id.certificate);
        mCity = (EditText) mainView.findViewById(R.id.city);

        mSubmit = (Button) mainView.findViewById(R.id.submit);

        user = FirebaseAuth.getInstance().getCurrentUser();
        mJoinAs.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Contractor");
        categories.add("Safety Officer");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        mJoinAs.setAdapter(dataAdapter);

        String mProfileKey = user.getUid();

        if (mProfileKey == null) {
            throw new IllegalArgumentException("Profile key cannot be empty");
        }


       // Toast.makeText(getContext(), user.getUid(), Toast.LENGTH_SHORT).show();

        mProfileReference = FirebaseDatabase.getInstance().getReference()
                .child("users").child(mProfileKey);

        mSubmit.setOnClickListener(this);
        return mainView;

    }

    @Override
    public void onStart() {
        super.onStart();
        profilephotoRef = mstorageRef.child(user.getUid()+"/ProfilePhoto.jpg");
        ValueEventListener profileListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get News object and use the values to update the UI
                UserModel user  = dataSnapshot.getValue(UserModel.class);
                // [START_EXCLUDE]
                if(user != null) {
                    mName.setText(user.getUsername());
                    if(user.getCompany() != null) mCompany.setText(user.getCompany());
                    if(user.getDesignation() != null) mDesignation.setText(user.getDesignation());
                    if(user.getCertificate() != null) mCertificate.setText(user.getCertificate());
                    if(String.valueOf(user.getPhone()) != null) mPhone.setText(String.valueOf(user.getPhone()));
                    if(user.getCity() != null) mCity.setText(user.getCity());
                }

                if(user.getPhotoUrl()!=null){
                   Glide.with(getContext()).load(user.getPhotoUrl())
                           .error(R.drawable.ic_person_black_24dp)
                           .transform(new CircleTransform(getContext()))
                           .into(mPhoto);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(getContext(), "Failed to load profile.",
                        Toast.LENGTH_SHORT).show();
            }
        };
        mProfileReference.addValueEventListener(profileListener);
        mProfileListener = profileListener;

    }


    @Override
    public void onStop() {
        super.onStop();

        // Remove post value event listener
        if (mProfileListener != null) {
            mProfileReference.removeEventListener(mProfileListener);
        }

    }
    private void ChangeProfilePic() {
        if(android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){
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
                requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        REQUEST_EXTERNAL_STORAGE);
            }
        }

    }

    private void TakePhoto(){
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());

        builder.setItems(R.array.options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which==0){
                    Intent galleryintent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryintent,RESULT_GALLERY_IMAGE);
                }
                else if(which==1){
                    Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if(cameraintent.resolveActivity(getActivity().getPackageManager())!=null){
                        startActivityForResult(cameraintent,RESULT_CAMERA_IMAGE);
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
        check=true;
        if(requestCode==RESULT_GALLERY_IMAGE&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){

            Imagepath=data.getData();
            InputStream inputstream;
            try {
                inputstream=getActivity().getContentResolver().openInputStream(Imagepath);
                Bitmap bitmap= BitmapFactory.decodeStream(inputstream);
                bitmap=Bitmap.createScaledBitmap(bitmap,710,710,true);
                mPhoto.setImageBitmap(bitmap);
                UploadPhoto(Imagepath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        else if(requestCode==RESULT_CAMERA_IMAGE&&resultCode==RESULT_OK){

            Bitmap bitmap;
            bitmap=(Bitmap)data.getExtras().get("data");
            bitmap=Bitmap.createScaledBitmap(bitmap,710,710,true);
            mPhoto.setImageBitmap(bitmap);
            Imagepath=getImageUri(getContext(),bitmap);
            UploadPhoto(Imagepath);
        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void UploadPhoto(Uri Imagepathq) {
        profilephotoRef = mstorageRef.child(user.getUid()+"/ProfilePhoto.jpg");

        profilephotoRef.putFile(Imagepathq)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                        Toast.makeText(getContext(),"Some Error Occured..Please Try Again!",Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.submit){
            if (validateForm()) {
                //TODO upload data
                String name = mName.getText().toString();
                String phone = mPhone.getText().toString();
                String company = mCompany.getText().toString();
                String designation = mDesignation.getText().toString();
                String certificate = mCertificate.getText().toString();
                String city = mCity.getText().toString();


                updateUser(name, phone, company,designation,certificate, city);

                //TODO show my profile after update
            }
        }
    }

    void updateUser(String name, String phone, String company,String designation,String certificate, String city){
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("company", company);
        childUpdates.put("designation", designation);
        childUpdates.put("city", city);
        childUpdates.put("joinAs", joinAs);

        mProfileReference.updateChildren(childUpdates);

        Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();

        Intent userDetailIntent = new Intent(getActivity(), ProfileActivity.class);
        userDetailIntent.putExtra(ProfileActivity.EXTRA_PROFILE_KEY,
                getCurrentUserId());
        startActivity(userDetailIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        joinAs = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validateForm() {
        boolean result = true;

        if (TextUtils.isEmpty(mCompany.getText().toString())) {
            mCompany.setError("Required");
            result = false;
        } else {
            mCompany.setError(null);
        }

        if (TextUtils.isEmpty(mDesignation.getText().toString())) {
            mDesignation.setError("Required");
            result = false;
        } else {
            mDesignation.setError(null);
        }
        if (TextUtils.isEmpty(mCity.getText().toString())) {
            mCity.setError("Required");
            result = false;
        } else {
            mCity.setError(null);
        }

        return result;
    }
}
