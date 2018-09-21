package com.example.jarfi.sispak.User.Data;

public class Userkerusakanmodel {
    String idrusak;
    String namarusak;
    String informasi;
    String penyebab;
    String solusi;


    public Userkerusakanmodel(String idrusak, String namarusak, String informasi, String penyebab, String solusi) {
        this.idrusak = idrusak;
        this.namarusak = namarusak;
        this.informasi = informasi;
        this.penyebab = penyebab;
        this.solusi = solusi;
    }

    public String getIdrusak() {
        return idrusak;
    }

    public void setIdrusak(String idrusak) {
        this.idrusak = idrusak;
    }

    public String getNamarusak() {
        return namarusak;
    }

    public void setNamarusak(String namarusak) {
        this.namarusak = namarusak;
    }

    public String getInformasi() {
        return informasi;
    }

    public void setInformasi(String informasi) {
        this.informasi = informasi;
    }

    public String getPenyebab() {
        return penyebab;
    }

    public void setPenyebab(String penyebab) {
        this.penyebab = penyebab;
    }

    public String getSolusi() {
        return solusi;
    }

    public void setSolusi(String solusi) {
        this.solusi = solusi;
    }
}
