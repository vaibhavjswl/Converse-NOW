package com.zain.deeplearning.conversenow.conv_now;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;



public class submitdata extends AppCompatActivity {
    Button ch,up ;
    ImageView img;
    StorageReference mStorageRef;

    private StorageTask uploadTask;
    private Uri imguri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        mStorageRef= FirebaseStorage.getInstance().getReference("Images");
        ch=(Button)findViewById(R.id.chse);
        up=(Button)findViewById(R.id.upld);


        img=(ImageView)findViewById(R.id.imgv);
        ch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Filechooser();
            }
        });

        ch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Filechooser();
            }
        });
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadTask != null && uploadTask.isInProgress())
                {
                    Toast.makeText(submitdata.this, "Upload in process", Toast.LENGTH_LONG).show();
                }
                else {
                    Fileuploader();
                }
            }
        });
    }

    private String getExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader()
    {
        StorageReference Ref=mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imguri));

        uploadTask=Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(submitdata.this, "Image Uploaded", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }


    private void Filechooser()
    {
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            img.setImageURI(imguri);
        }
    }

}