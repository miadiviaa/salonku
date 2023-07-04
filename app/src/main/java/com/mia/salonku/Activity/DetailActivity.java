package com.mia.salonku.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mia.salonku.R;

public class DetailActivity extends AppCompatActivity {
    private TextView tvNama, tvDeskripsi, tvAlamat;
    private ImageView ivFoto;
    private String yNama, yDeskripsi, yAlamat, yKoordinat, yFoto;
    private Button btn_koordinat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvNama = findViewById(R.id.tv_nama);
        tvDeskripsi = findViewById(R.id.tv_deskripsi);
        tvAlamat = findViewById(R.id.tv_alamat);
        btn_koordinat = findViewById(R.id.btn_koordinat);
        ivFoto = findViewById(R.id.iv_foto);

        Intent terima = getIntent();
        yNama = terima.getStringExtra("xNama");
        yDeskripsi = terima.getStringExtra("xDeskripsi");
        yAlamat = terima.getStringExtra("xAlamat");
        yKoordinat = terima.getStringExtra("xKoordinat");
        yFoto = terima.getStringExtra("xFoto");

        tvNama.setText(yNama);;
        tvDeskripsi.setText(yDeskripsi);
        tvAlamat.setText(yAlamat);
        Glide
                .with(DetailActivity.this)
                .load(yFoto)
                .into(ivFoto);

        btn_koordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri lokasi = Uri.parse("geo:0,0?q=" + yKoordinat);
                Intent bukalokasi = new Intent(Intent.ACTION_VIEW, lokasi);
                startActivity(bukalokasi);

            }
        });


    }
}