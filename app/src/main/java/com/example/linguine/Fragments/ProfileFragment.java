package com.example.linguine.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.linguine.Activities.EditProfile2Activity;
import com.example.linguine.Activities.MainActivity;
import com.example.linguine.Common.SessionMangment;
import com.example.linguine.Models.UserModel;
import com.example.linguine.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    final static private int RESULT_LOAD_CAM = 1;
    final static private int RESULT_LOAD_IMG = 0;

    SessionMangment mSessionMangment;

    CircleImageView circleImageView;


    View view;

    RelativeLayout inviteFriendsRelative, customerSupportRelative, makeSuggestionRelative;

    Button editprofileBtn , mLogOut;

    static Bitmap bitmap;

    TextView mUserName, mUserMail;





    DatabaseReference mDatabaseReference;

    static int kind = 0;

    Intent mIntent;

    LinearLayout mMailAndName;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view=inflater.inflate(R.layout.fragment_profile, container, false);
        mIntent = getActivity().getIntent();
        mSessionMangment = new SessionMangment(getActivity());
        try {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(mSessionMangment.getUserDetails().get(mSessionMangment.KEY_ID));

        } catch (Exception e) {
            mDatabaseReference = FirebaseDatabase.getInstance().getReference("User Data").child(FirebaseAuth.getInstance().getUid());
        }

        initViews();

        return view;
    }


    private void initViews() {


        kind = mIntent.getIntExtra("KindOfEdit", 1);


        circleImageView = view.findViewById(R.id.user_photo);
        circleImageView.setOnClickListener(this);

        editprofileBtn = view.findViewById(R.id.meditprofilebtn);
        editprofileBtn.setOnClickListener(this);


        inviteFriendsRelative = view.findViewById(R.id.minvitefriendsRelative);
        customerSupportRelative = view.findViewById(R.id.mcustomerSupportRelative);
        makeSuggestionRelative = view.findViewById(R.id.mmakesuggestionRelative);




        mLogOut = view.findViewById(R.id.mLogOut);



        mLogOut.setOnClickListener(this);
        inviteFriendsRelative.setOnClickListener(this);
        customerSupportRelative.setOnClickListener(this);

        makeSuggestionRelative.setOnClickListener(this);


        mUserName = view.findViewById(R.id.user_name);
        mUserMail = view.findViewById(R.id.UserMail);

        mMailAndName = view.findViewById(R.id.NameAndMail);
        mMailAndName.setOnClickListener(this);

        //------------------------------------------------------------------------------------------

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                UserModel model = dataSnapshot.getValue(UserModel.class);

                mUserMail.setText(model.getmMail());
                mUserName.setText(model.getmName());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {




            case R.id.minvitefriendsRelative:
                Invite();

                break;

            case R.id.mcustomerSupportRelative:


            case R.id.mmakesuggestionRelative:
                String url = "https://api.whatsapp.com/send?phone=" + "+201066555162";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                break;

            case R.id.user_photo:
                chosseAlertDialoge();

                break;


            case R.id.meditprofilebtn:

            case R.id.NameAndMail:

                Intent mIntent = new Intent(getActivity(), EditProfile2Activity.class);
                mIntent.putExtra("User Id", this.mIntent.getStringExtra("User Id"));
                startActivity(mIntent);
                break;
            case R.id.mLogOut:
                mSessionMangment.logoutUser();
                getActivity().finish();
                break;

        }

    }

    //---------------------------------------------------------------------------------------------------------------------------------
    private void Invite() {

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "My app name");
            String strShareMessage = "\nLet me recommend you this application\n\n";
            strShareMessage = strShareMessage + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
            Uri screenshotUri = Uri.parse("android.resource://packagename/drawable/image_name");
            i.setType("image/png");
            i.putExtra(Intent.EXTRA_STREAM, screenshotUri);
            i.putExtra(Intent.EXTRA_TEXT, strShareMessage);
            startActivity(Intent.createChooser(i, "Share via"));
        } catch (Exception e) {
            //e.toString();

        }
    }

    //-----------------------------------------------------------------------------------------------------------------------------------
    @AfterPermissionGranted(101)
    public void choosePhotoFromGallary() {
        String[] galleryPermissions = new String[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            galleryPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        }
        if (EasyPermissions.hasPermissions(getActivity(), galleryPermissions)) {
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
        if (EasyPermissions.hasPermissions(getActivity(), galleryPermissions)) {
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
            Snackbar.make(getActivity().findViewById(android.R.id.content), "You Do Not Picked Any Photo", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (requestCode == RESULT_LOAD_IMG) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);

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
        AlertDialog.Builder selectionDialog = new AlertDialog.Builder(getActivity());
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
        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
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


    @Override
    public void onResume() {

        super.onResume();


        try {
            loadImageFromStorage(mSessionMangment.getUserDetails().get(mSessionMangment.KEY_IMAGE));
        } catch (Exception e) {
        }
    }
}
