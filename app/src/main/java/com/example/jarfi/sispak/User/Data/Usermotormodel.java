package com.example.jarfi.sispak.User.Data;

public class Usermotormodel {
    String id, nama, tipe;

    public Usermotormodel(String id, String nama, String tipe){
        this.id = id;
        this.nama = nama;
        this.tipe = tipe;
    }

    public  String getId()
    {
        return id;
    }
    public  String getNama()
    {
        return nama;
    }
    public String getTipe() {
        return tipe;
    }

}
