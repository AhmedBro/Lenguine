package com.example.linguine.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.linguine.Common.SessionMangment;
import com.example.linguine.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class EditProfile2Activity extends AppCompatActivity implements View.OnClickListener {


    final static private int RESULT_LOAD_CAM = 1;
    final static private int RESULT_LOAD_IMG = 0;
    SessionMangment mSessionMangment;
    CircleImageView circleImageView;
    static Bitmap bitmap;

    Intent mIntent;
    ImageView goback;
    TextInputEditText mE_MailEP, mnameEP;
    Button mContenuo;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile2);
        mIntent = getIntent();
        mSessionMangment = new SessionMangment(this);
        initViews();

        try {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(mSessionMangment.getUserDetails().get(mSessionMangment.KEY_ID));

        }
        catch (Exception e){
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(mIntent.getStringExtra("User Id"));

        }

    }

    private void initViews() {


        mE_MailEP = findViewById(R.id.mEmailEP);
        mnameEP = findViewById(R.id.mNameEP);

        goback = findViewById(R.id.mgobackIconEP);
        goback.setOnClickListener(this);


        circleImageView = findViewById(R.id.user_photo);
        circleImageView.setOnClickListener(this);

        mnameEP.setOnClickListener(this);
        mE_MailEP.setOnClickListener(this);

        mContenuo = findViewById(R.id.mContinueRelative);
        mContenuo.setOnClickListener(this);

    }


    private boolean validate() {

        if (mE_MailEP.getText().toString().isEmpty()) {
            Snackbar.make(this.findViewById(android.R.id.content), "Please enter your  E-Mail", Snackbar.LENGTH_LONG).show();
            return false;
        } else if (mnameEP.getText().toString().isEmpty()) {
            Snackbar.make(this.findViewById(android.R.id.content), "Please enter your  User Name", Snackbar.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }
    //---------------------------------------------------------------------------------------------------------------------------------


    @Override
    public void onResume() {

        super.onResume();


        try {
            loadImageFromStorage(mSessionMangment.getUserDetails().get(mSessionMangment.KEY_IMAGE));
        } catch (Exception e) {
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.user_photo:
                chosseAlertDialoge();

                break;
            case R.id.mgobackIconEP:

                finish();
                break;
            case R.id.mContinueRelative:

                if (validate()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.updateEmail(mE_MailEP.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @SuppressLint("LongLogTag")
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        mSessionMangment.updatename(mnameEP.getText().toString());
                                        mSessionMangment.updatEmial(mE_MailEP.getText().toString());
                                        mDatabaseReference.child("mName").setValue(mnameEP.getText().toString());

                                        mDatabaseReference.child("mEmail").setValue(mE_MailEP.getText().toString());
                                        Snackbar.make(findViewById(android.R.id.content), "Your Email is Uploaded", Snackbar.LENGTH_LONG).show();
                                    } else
                                        Snackbar.make(findViewById(android.R.id.content), task.getException().getMessage(), Snackbar.LENGTH_LONG).show();


                                }
                            });
                }
                break;

        }

    }


    //-----------------------------------------------------------------------------------------------------------------------------------
    @AfterPermissionGranted(101)
    public void choosePhotoFromGallary() {
        String[] galleryPermissions = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            galleryPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    101, galleryPermissions);
        }
    }

    @AfterPermissionGranted(123)
    private void takePhotoFromCamera() {
        String[] galleryPermissions = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            galleryPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RESULT_LOAD_CAM);
        } else {
            EasyPermissions.requestPermissions(this, "Access for storage",
                    123, galleryPermissions);
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            Snackbar.make(this.findViewById(android.R.id.content), "You Do Not Picked Any Photo", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (requestCode == RESULT_LOAD_IMG) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    circleImageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == RESULT_LOAD_CAM) {
            bitmap = (Bitmap) data.getExtras().get("data");
            circleImageView.setImageBitmap(bitmap);
        }
        mSessionMangment.SavePhoto(saveToInternalStorage(bitmap));

    }
    //-------------------------------------------------------------------------------------------------------------------------------


    /*to go and picked the photo*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (grantResults.length > 0) {
            if (grantResults.toString().equals(RESULT_LOAD_IMG)) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            } else if (grantResults.toString().equals(RESULT_LOAD_CAM)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, RESULT_LOAD_CAM);
            }
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------------
    /*to choose between the gallery and the camera By show popup*/
    public void chosseAlertDialoge() {
        AlertDialog.Builder selectionDialog = new AlertDialog.Builder(this);
        selectionDialog.setTitle("Select Action");
        String[] SelectDialogAction = {
                "Select Photo From Gallery",
                "Capture Photo From Camera"
        };
        selectionDialog.setItems(SelectDialogAction, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        choosePhotoFromGallary();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;

                }
            }
        });
        selectionDialog.show();
    }

    //---------------------------------------------------------------------------------------------------------------
    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(this.getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    //-------------------------------------------------------------------------------------------------------------------------
    private void loadImageFromStorage(String path) {

        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            circleImageView.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //-------------------------------------------------------------------------------------------------------------------------

}
