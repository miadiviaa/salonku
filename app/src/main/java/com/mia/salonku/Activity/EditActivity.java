package com.mia.salonku.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mia.salonku.API.APIRequestData;
import com.mia.salonku.API.RetroServer;
import com.mia.salonku.Model.ModelResponse;
import com.mia.salonku.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity {
    private String yId, yNama, yDeskripsi, yAlamat, yKoordinat, yFoto;
    private EditText etNama, etDeskripsi, etAlamat, etKoordinat, etFoto;
    private Button btnUbah;
    private String Nama, Deskripsi, Alamat, Koordinat, Foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etNama = findViewById(R.id.et_nama);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etAlamat = findViewById(R.id.et_alamat);
        etKoordinat = findViewById(R.id.et_koordinat);
        etFoto = findViewById(R.id.et_foto);
        btnUbah = findViewById(R.id.btn_ubah);

        Intent terima = getIntent();
        yId = terima.getStringExtra("xId");
        yNama = terima.getStringExtra("xNama");
        yDeskripsi = terima.getStringExtra("xDeskripsi");
        yAlamat = terima.getStringExtra("xAlamat");
        yKoordinat = terima.getStringExtra("xKoordinat");
        yFoto = terima.getStringExtra("xFoto");

        etNama.setText(yNama);
        etDeskripsi.setText(yDeskripsi);
        etAlamat.setText(yAlamat);
        etKoordinat.setText(yKoordinat);
        etFoto.setText(yFoto);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                Alamat = etAlamat.getText().toString();
                Koordinat = etKoordinat.getText().toString();
                Foto = etFoto.getText().toString();

                if (Nama.trim().isEmpty()){
                    etNama.setError("Nama harus di isi!");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi harus di isi!");
                } else if (Alamat.trim().isEmpty()) {
                    etAlamat.setError("Alamat harus di isi!");
                } else if (Koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Koordinat harus di isi!");
                } else if (Foto.trim().isEmpty()) {
                    etFoto.setError("Foto harus di isi!");
                }else {
                    ubahSalon();
                }
            }
        });
    }

    private void ubahSalon(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardUpdate(yId, Nama, Deskripsi, Alamat, Koordinat, Foto);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();

                Toast.makeText(EditActivity.this, "kode : " + kode +" Pesan : " + pesan, Toast.LENGTH_SHORT).show();
                finish();

            }
            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(EditActivity.this, "Error: Gagal ubah data! ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}