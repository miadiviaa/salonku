package com.mia.salonku.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mia.salonku.API.APIRequestData;
import com.mia.salonku.API.RetroServer;
import com.mia.salonku.Model.ModelResponse;
import com.mia.salonku.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddActivity extends AppCompatActivity {
    private Button btnTambah;
    private EditText etNama, etDeskripsi, etAlamat, etKoordinat, etFoto;
    private String Nama, Deskripsi, Alamat, Koordinat, Foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etNama = findViewById(R.id.et_nama);
        etDeskripsi = findViewById(R.id.et_deskripsi);
        etAlamat = findViewById(R.id.et_alamat);
        etKoordinat = findViewById(R.id.et_koordinat);
        etFoto = findViewById(R.id.et_foto);
        btnTambah = findViewById(R.id.btn_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Deskripsi = etDeskripsi.getText().toString();
                Alamat = etAlamat.getText().toString();
                Koordinat = etKoordinat.getText().toString();
                Foto = etFoto.getText().toString();

                if (Nama.trim().isEmpty()){
                    etNama.setError("Nama salon harus di isi");
                } else if (Deskripsi.trim().isEmpty()) {
                    etDeskripsi.setError("Deskripsi salon harus di isi");
                } else if (Alamat.trim().isEmpty()) {
                    etAlamat.setError("Alamat salon harus di isi");
                } else if (Koordinat.trim().isEmpty()) {
                    etKoordinat.setError("Koordinat salon harus di isi");
                }else if (Foto.trim().isEmpty()){
                    etFoto.setError("Link foto salon harus di isi");
                } else {
                    tambahSalon();
                }
        }

    });
}
    private void tambahSalon(){
        APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = API.ardCreate(Nama, Deskripsi, Alamat, Koordinat, Foto);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode, pesan;
                kode = response.body().getKode();
                pesan = response.body().getPesan();
                Toast.makeText(AddActivity.this, "kode : " + kode + "Pesan" + pesan , Toast.LENGTH_SHORT).show();
                finish();
            }
            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal kirim data!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}