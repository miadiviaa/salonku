package com.mia.salonku.Activity;

import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mia.salonku.API.APIRequestData;
import com.mia.salonku.API.RetroServer;
import com.mia.salonku.Adapter.AdapterSalon;
import com.mia.salonku.Model.ModelResponse;
import com.mia.salonku.Model.ModelSalon;
import com.mia.salonku.R;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvSalon;
    private RecyclerView.Adapter adSalon;

    private FloatingActionButton fabTambah;
    private RecyclerView.LayoutManager lmSalon;

    private ProgressBar pbSalon;

    private List<ModelSalon> listSalon =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvSalon = findViewById(R.id.rv_salon);
        pbSalon = findViewById(R.id.pb_salon);
        fabTambah = findViewById(R.id.fab_tambah);

        lmSalon = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSalon.setLayoutManager(lmSalon);
    }

    @Override
    protected void onResume() {
        super.onResume();
        retriveSalon();
    }

    public void retriveSalon(){
        pbSalon.setVisibility(View.VISIBLE);

        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardRetrive();
        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listSalon = response.body().getData();
                adSalon = new AdapterSalon(MainActivity.this, listSalon);
                rvSalon.setAdapter(adSalon);
                adSalon.notifyDataSetChanged();
                pbSalon.setVisibility(View.GONE);

                fabTambah.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, AddActivity.class));
                    }
                });
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                pbSalon.setVisibility(View.GONE);
            }
        });
    }
}