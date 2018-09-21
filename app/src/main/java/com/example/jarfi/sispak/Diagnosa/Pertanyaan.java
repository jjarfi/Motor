package com.example.jarfi.sispak.Diagnosa;

public class Pertanyaan {
    private String ID;
    private String TANYA;
    private double JAWAB;

    public Pertanyaan(){
        ID="";
        TANYA = "";
        JAWAB=0;

    }

    public Pertanyaan(String ID, String TANYA, double JAWAB) {
        this.ID = ID;
        this.TANYA = TANYA;
        this.JAWAB = JAWAB;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTANYA() {
        return TANYA;
    }

    public void setTANYA(String TANYA) {
        this.TANYA = TANYA;
    }

    public double getJAWAB() {
        return JAWAB;
    }

    public void setJAWAB(double JAWAB) {
        this.JAWAB = JAWAB;
    }
}
