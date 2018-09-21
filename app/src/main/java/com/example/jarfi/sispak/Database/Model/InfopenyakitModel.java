package com.example.jarfi.sispak.Database.Model;

public class InfopenyakitModel {

    String namapenyakit, deskripsi, solution;

        public InfopenyakitModel(String namapenyakit, String deskripsi, String solution){
            this.namapenyakit = namapenyakit;
            this.deskripsi = deskripsi;
            this.solution = solution;
    }

    public String getNamapenyakit()
        {
        return namapenyakit;
        }
    public String getDeskripsi()
    {
        return deskripsi;
    }
    public String getSolution() {
        return solution;
    }

}
