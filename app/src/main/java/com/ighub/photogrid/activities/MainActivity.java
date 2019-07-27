package com.ighub.photogrid.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ighub.photogrid.R;
import com.ighub.photogrid.adapter.PhotoAdapter;
import com.ighub.photogrid.model.PhotoModel;
import com.ighub.photogrid.utils.GetDataService;
import com.ighub.photogrid.utils.RetrofitClientInstance;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PhotoAdapter adapter;
    private RecyclerView recyclerView;
    ProgressDialog progressDoalog;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        progressDoalog = new ProgressDialog(MainActivity.this);
        progressDoalog.setMessage("Loading....");
        progressDoalog.show();

//        hud.create(MainActivity.this)
//                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                .setLabel("Please wait")
//                .setDetailsLabel("Loading data...")
//                .setCancellable(true)
//                .setAnimationSpeed(2)
//                .setDimAmount(0.5f)
//                .show();



        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);

        Call<List<PhotoModel>> call = service.getAllPhotos();
        call.enqueue(new Callback<List<PhotoModel>>() {

            @Override
            public void onResponse(Call<List<PhotoModel>> call, Response<List<PhotoModel>> response) {
                progressDoalog.dismiss();
//                hud.dismiss();
                generateDataList(response.body());
                Log.d("detail1", ""+response.toString());

            }

            @Override
            public void onFailure(Call<List<PhotoModel>> call, Throwable t) {
                progressDoalog.dismiss();
//                hud.dismiss();
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }

        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<PhotoModel> photoList) {
        recyclerView = findViewById(R.id.customRecyclerView);
        adapter = new PhotoAdapter(this,photoList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

}


