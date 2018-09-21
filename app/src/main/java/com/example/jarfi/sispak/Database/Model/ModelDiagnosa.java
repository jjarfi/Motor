package com.example.jarfi.sispak.Database.Model;

public class ModelDiagnosa {

    int id;
    String nama ;
    String bobot;

    public ModelDiagnosa(int id, String nama, String bobot) {
        this.id = id;
        this.nama = nama;
        this.bobot = bobot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }
}
