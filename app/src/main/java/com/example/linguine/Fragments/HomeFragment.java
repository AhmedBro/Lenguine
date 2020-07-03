package com.example.linguine.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.linguine.Adapters.Adapter_Horizontal;
import com.example.linguine.Adapters.Adapter_Vertical;
import com.example.linguine.Adapters.Category_Adapter;
import com.example.linguine.Adapters.SmoothLayoutManager;
import com.example.linguine.Common.SessionMangment;
import com.example.linguine.Models.CategoryModel;
import com.example.linguine.Models.HomeVerticalAdapter;
import com.example.linguine.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener, Category_Adapter.Click {
    View view;

    RecyclerView mRecVert, mReoHori, mCategiry;


    DatabaseReference mDatabaseReference, mDatabaseRef;


    ArrayList<String> mImages = new ArrayList<>();
    ArrayList<HomeVerticalAdapter> mItemVert = new ArrayList<>();
    ArrayList<CategoryModel> mCat = new ArrayList<>();
    SessionMangment mangment;

    //----------------------------------------------------------------------------------------------
    final int speedScroll = 2000;
    final Handler handler = new Handler();

    Adapter_Horizontal mAdapter_horizontal;


    //----------------------------------------------------------------------------------------------
    ImageView mFaceBook, mInstgram, mSnapShat;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mangment = new SessionMangment(getActivity());
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Food Photo");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Menu");
        instView();

        LoadHori();

        LoadCat();

        LoadVert("Sweet Crepe");
        return view;
    }

    //----------------------------------------------------------------------------------------------

    public void onStart() {
        super.onStart();
        LoadVert("Sweet Crepe");

    }
    //----------------------------------------------------------------------------------------------

    private void instView() {


        mReoHori = view.findViewById(R.id.RecHori);

        mCategiry = view.findViewById(R.id.mCategory);
//--------------------------------------------------------------------------------------------------

        mFaceBook = view.findViewById(R.id.Facebook);
        mInstgram = view.findViewById(R.id.Instgram);
        mSnapShat = view.findViewById(R.id.SnapShat);

        mSnapShat.setOnClickListener(this);
        mFaceBook.setOnClickListener(this);
        mInstgram.setOnClickListener(this);

//--------------------------------------------------------------------------------------------------

        mRecVert = view.findViewById(R.id.RecVer);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        mRecVert.setLayoutManager(layoutManager);


    }

    void LoadCat() {
        CategoryModel m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13;
        m1 = new CategoryModel("Sweet Crepe", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FSweetCreab.jpeg?alt=media&token=53440fea-705c-439d-9a3f-a5278520ff3f");
        m2 = new CategoryModel("Crepe", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2Fcrepe4.jpg?alt=media&token=af18ee0d-2181-45cd-b839-9422995b29f1");
        m3 = new CategoryModel("Super Meals", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FSuper4.jpg?alt=media&token=0b540ea6-3f67-4ba4-88c7-73b7900e0eab");
        m4 = new CategoryModel("Mex Meals", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FMex4.jpg?alt=media&token=8d26e2d2-ee0a-44bb-8966-eb5226731eaf");
        m5 = new CategoryModel("White Sauce Noodles", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2Fwhite%20Nooles4.jpg?alt=media&token=aaae972c-15ad-46d3-9547-cf83e0d99012");
        m6 = new CategoryModel("Red Sauce Noodles", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FRed%20Sause4.jpg?alt=media&token=ba589797-8fe8-4400-b340-f00201332158");
        m7 = new CategoryModel("Side Items", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2Fside4.jpg?alt=media&token=59bdbc32-d60f-4163-b328-5648c4026995");
        m8 = new CategoryModel("Chicken Sandwiches", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FChicken4.jpg?alt=media&token=b1d290c6-99ba-4455-831a-c70120cbe257");
        m9 = new CategoryModel("Meet Sandwiches", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FMeet4.png?alt=media&token=8321e3c6-3bfd-4d4f-b2a1-23782867b109");
        m10 = new CategoryModel("Basmati Rice", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FBasmaty4.jpg?alt=media&token=9cd7ea11-b5d2-441b-8681-93cc1fd790e7");
        m11 = new CategoryModel("Syrian Side", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FSyrian4.jpg?alt=media&token=d45f7ad9-f9f6-4b88-9660-05fd1cc7b646");
        m12 = new CategoryModel("Balady Side", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FBalady4.webp?alt=media&token=46a7a711-6704-4a2e-87fc-6a871b93aeee");
        m13 = new CategoryModel("Mex Crepe", "https://firebasestorage.googleapis.com/v0/b/lenguine-f3a74.appspot.com/o/Category%2FMex%20Crepe4.jpg?alt=media&token=a2ccb2b9-0588-4d8f-a3a3-5f578a77e9fe");
        mCat.add(m1);
        mCat.add(m2);
        mCat.add(m3);
        mCat.add(m4);
        mCat.add(m5);
        mCat.add(m6);
        mCat.add(m7);
        mCat.add(m8);
        mCat.add(m9);
        mCat.add(m10);
        mCat.add(m11);
        mCat.add(m12);
        mCat.add(m13);

        Category_Adapter mCategory_adapter = new Category_Adapter(mCat, getActivity(), this);
        mCategiry.setAdapter(mCategory_adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        mCategiry.setLayoutManager(manager);
    }

    void LoadHori() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mImages.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    String imageUploadInfo = postSnapshot.getValue(String.class);

                    mImages.add(imageUploadInfo);
                }

                Adapter_Horizontal adapter_horizontal = new Adapter_Horizontal(mImages, getActivity());

                mReoHori.setAdapter(adapter_horizontal);
                scroll();
                ScrollSmothey(getActivity());

                // Hiding the progress dialog.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    void LoadVert(String Child) {
        mDatabaseRef.child(Child).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mItemVert.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    HomeVerticalAdapter imageUploadInfo = postSnapshot.getValue(HomeVerticalAdapter.class);

                    mItemVert.add(imageUploadInfo);
                }

                Adapter_Vertical adapter_horizontal = new Adapter_Vertical(mItemVert, getActivity());

                mRecVert.setAdapter(adapter_horizontal);


                // Hiding the progress dialog.
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
            }
        });

    }

    //----------------------------------------------------------------------------------------------

    private void ScrollSmothey(Context iC) {
        mAdapter_horizontal = new Adapter_Horizontal(mImages, getActivity());

        mReoHori.setLayoutManager(new SmoothLayoutManager(iC, LinearLayoutManager.HORIZONTAL, false).setSpeedOfSmooth(SmoothLayoutManager.X_200));
        mReoHori.setAdapter(mAdapter_horizontal);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mReoHori);
    }

    //----------------------------------------------------------------------------------------------
    void scroll() {
        final Runnable runnable = new Runnable() {
            int count = 0;
            boolean flag = true;

            @Override
            public void run() {
                if (count < mAdapter_horizontal.getItemCount()) {
                    if (count == mAdapter_horizontal.getItemCount() - 1) {
                        flag = false;
                    } else if (count == 0) {
                        flag = true;
                    }
                    if (flag) count++;
                    else count--;

                    mReoHori.smoothScrollToPosition(count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };

        handler.postDelayed(runnable, speedScroll);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.Facebook:
                String YourPageURL = "https://web.facebook.com/waheed.shaaban";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL));
                startActivity(browserIntent);
                break;

            case R.id.Instgram:
                String YourPageURL2 = "https://www.instagram.com/";
                Intent browserIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL2));
                startActivity(browserIntent2);
                break;

            case R.id.SnapShat:
                String YourPageURL3 = "https://www.snapchat.com/";
                Intent browserIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(YourPageURL3));
                startActivity(browserIntent3);
                break;
        }
    }

    @Override
    public void select(String mName) {
        switch (mName) {
            case "Sweet Crepe":
                LoadVert("Sweet Crepe");
                break;
            case "Mex Crepe":
                LoadVert("Mex Crepe");
                break;
            case "Basmati Rice":
                LoadVert("Basmatic Rice");
                break;
            case "Crepe":
                LoadVert("Crepe");
                break;
            case "Super Meals":
                LoadVert("Super Meals");
                break;
            case "Mex Meals":
                LoadVert("Mex Meals");
                break;
            case "White Sauce Noodles":
                LoadVert("White Sauce Noodles");
                break;
            case "Red Sauce Noodles":
                LoadVert("Red Sauce Noodles");
                break;
            case "Side Items":
                LoadVert("Side Items");
                break;
            case "Chicken Sandwiches":
                LoadVert("Chicken Sandwiches");
                break;
            case "Meet Sandwiches":
                LoadVert("Meet Sandwiches");
                break;
            case "Syrian Side":
                LoadVert("Syrian Side");
                break;
            case "Balady Side":
                LoadVert("Balady Side");
                break;
        }
    }
}
