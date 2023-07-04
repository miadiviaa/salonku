package com.mia.salonku.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelSalon> data;

    public String getKode(){return kode;}
    public String getPesan(){return pesan;}

    public List<ModelSalon> getData(){
        return data;
    }
}
