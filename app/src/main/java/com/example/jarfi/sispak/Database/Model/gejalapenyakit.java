package com.example.jarfi.sispak.Database.Model;

public class gejalapenyakit {
    String ID;
    String diagnosa;
    String kembali;
    String tidak;
    String ya;

    public gejalapenyakit(String ID, String diagnosa, String kembali, String tidak, String ya) {
        this.ID = ID;
        this.diagnosa = diagnosa;
        this.kembali = kembali;
        this.tidak = tidak;
        this.ya = ya;
    }

    public gejalapenyakit() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getKembali() {
        return kembali;
    }

    public void setKembali(String kembali) {
        this.kembali = kembali;
    }

    public String getTidak() {
        return tidak;
    }

    public void setTidak(String tidak) {
        this.tidak = tidak;
    }

    public String getYa() {
        return ya;
    }

    public void setYa(String ya) {
        this.ya = ya;
    }
}
