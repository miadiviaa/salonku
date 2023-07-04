package com.mia.salonku.Adapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mia.salonku.API.APIRequestData;
import com.mia.salonku.API.RetroServer;
import com.mia.salonku.Activity.DetailActivity;
import com.mia.salonku.Activity.EditActivity;
import com.mia.salonku.Activity.MainActivity;
import com.mia.salonku.Model.ModelResponse;
import com.mia.salonku.Model.ModelSalon;
import com.mia.salonku.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterSalon extends RecyclerView.Adapter<AdapterSalon.VHSalon>{
    private Context ctx;
    private List<ModelSalon> listSalon;

    public AdapterSalon(Context ctx, List<ModelSalon> listSalon) {
        this.ctx = ctx;
        this.listSalon = listSalon;
    }

    @NonNull
    @Override
    public VHSalon onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(ctx).inflate(R.layout.item_card, parent, false);
        return new VHSalon(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHSalon holder, int position) {
        ModelSalon MS = listSalon.get(position);
        holder.tvId.setText(MS.getId());
        holder.tvNama.setText(MS.getNama());
        holder.tvDeskripsi.setText(MS.getDeskripsi());
        holder.tvAlamat.setText(MS.getAlamat());
        holder.tvKoordinat.setText(MS.getKoordinat());
        holder.tvFoto.setText(MS.getFoto());
        Glide
                .with(ctx)
                .load(MS.getFoto())
                .into(holder.ivFoto);
    }

    @Override
    public int getItemCount() {
        return listSalon.size();
    }

    public class VHSalon extends RecyclerView.ViewHolder{
        private TextView tvId, tvNama, tvDeskripsi, tvAlamat, tvKoordinat, tvFoto;
        private Button btnHapus, btnUbah, btnDetail;
        private ImageView ivFoto;
        public VHSalon(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvDeskripsi = itemView.findViewById(R.id.tv_deskripsi);
            tvAlamat = itemView.findViewById(R.id.tv_alamat);
            tvKoordinat = itemView.findViewById(R.id.tv_koordinat);
            tvFoto = itemView.findViewById(R.id.tv_foto);
            ivFoto = itemView.findViewById(R.id.iv_foto);
            btnHapus = itemView.findViewById(R.id.btn_hapus);
            btnUbah = itemView.findViewById(R.id.btn_ubah);
            btnDetail = itemView.findViewById(R.id.btn_detail);


            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteSalon(tvId.getText().toString());

                }
            });

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, EditActivity.class);
                    kirim.putExtra("xId", tvId.getText().toString());
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    kirim.putExtra("xAlamat", tvAlamat.getText().toString());
                    kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    ctx.startActivity(kirim);
                }
            });

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent kirim = new Intent(ctx, DetailActivity.class);
                    kirim.putExtra("xNama", tvNama.getText().toString());
                    kirim.putExtra("xDeskripsi", tvDeskripsi.getText().toString());
                    kirim.putExtra("xAlamat", tvAlamat.getText().toString());
                    kirim.putExtra("xKoordinat", tvKoordinat.getText().toString());
                    kirim.putExtra("xFoto", tvFoto.getText().toString());
                    ctx.startActivity(kirim);
                }
            });
        }


        void deleteSalon(String id){
            APIRequestData API = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ModelResponse> proses = API.ardDelete(id);
            proses.enqueue(new Callback<ModelResponse>() {
                @Override
                public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                    String kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode:"+kode+"Pesan : "+ pesan, Toast.LENGTH_SHORT).show();
                    ((MainActivity) ctx).retriveSalon();
                }

                @Override
                public void onFailure(Call<ModelResponse> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal menghubungi server", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
