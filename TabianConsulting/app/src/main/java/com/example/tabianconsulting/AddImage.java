package com.example.tabianconsulting;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class AddImage extends Fragment {
ImageView image;
TextView showImages;
Button chooseImage;
Button uploadImage;
Uri imageUri;
EditText mEditText;
    private static final int PICK_IMAGE=1;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;
    private String catName;
    private ProgressBar mProgressBar;



    public AddImage(String name){
        catName = name;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_image_from_storage, container, false);
        chooseImage = view.findViewById(R.id.button_choose_image_file);
        image=view.findViewById(R.id.display_image_for_view);
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImageStorage();
            }
        });
        mStorageRef= FirebaseStorage.getInstance().getReference(catName);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(catName);
        uploadImage=view.findViewById(R.id.upload_image_button);
        mProgressBar = view.findViewById(R.id.progress_bar);
        mEditText = view.findViewById(R.id.image_name);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(mUploadTask!=null&&mUploadTask.isInProgress()){
//                    Toast.makeText(getContext(),"Upload in Progress",Toast.LENGTH_LONG).show();
//                }
//                else{
                    uploadfile();
//                }
            }
        });
        return view;

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadfile() {
        if(imageUri!=null){
            StorageReference FileReference = mStorageRef.child(System.currentTimeMillis()+"."
            +getFileExtension(imageUri));
            mUploadTask=FileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(0);
                        }
                    },500);
                    UploadImageInfo upload = new UploadImageInfo(mEditText.getText().toString().trim(),
                            Objects.requireNonNull(taskSnapshot.getUploadSessionUri()).toString(),
                            ServerValue.TIMESTAMP.toString());
                    String upLoadId = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(upLoadId).setValue(upload);

                    Toast.makeText(getContext(),"Upload Successful",Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
               Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                mProgressBar.setProgress((int)progress);
                }
            });
        }
    }

    private void chooseImageStorage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==PICK_IMAGE && resultCode== Activity.RESULT_OK && data!=null && data.getData()!=null){
            imageUri=data.getData();
            Glide.with(this).load(imageUri).into(image);
        }
    }

}
