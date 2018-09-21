package com.example.jarfi.sispak.Database.Model;

public class ModelPengetahuan {
    int id;
    int motor;
    String kerusakan;
    String gejala;
    String bobot;

    public ModelPengetahuan() {
        this.id = id;
        this.motor = motor;
        this.kerusakan = kerusakan;
        this.gejala = gejala;
        this.bobot = bobot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMotor() {
        return motor;
    }

    public void setMotor(int motor) {
        this.motor = motor;
    }

    public String getKerusakan() {
        return kerusakan;
    }

    public void setKerusakan(String kerusakan) {
        this.kerusakan = kerusakan;
    }

    public String getGejala() {
        return gejala;
    }

    public void setGejala(String gejala) {
        this.gejala = gejala;
    }

    public String getBobot() {
        return bobot;
    }

    public void setBobot(String bobot) {
        this.bobot = bobot;
    }
}

