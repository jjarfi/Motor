package com.example.jarfi.sispak.Diagnosa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.Database.Model.gejalapenyakit;
import com.example.jarfi.sispak.R;

import java.util.ArrayList;
import java.util.List;

public class Diagnosa extends AppCompatActivity {
    private gejalapenyakit data;
    private Database datagejala;
    private SQLiteDatabase db;
    private TextView gejala;
    private Cursor gejalaCursor;
    private String kode_main;
    private String kode_tidak;
    private String kode_ya;
    private String mulai;
    private String selesai;
    private Button lanjut;

    List<Question> QuestList;
    Question currentQ;

    private String result;
    private RadioButton rba,rbb,rbc,rbd,rbe,rbf;
//    private RadioButton rbb;
//    private RadioButton rbc;
//    private RadioButton rbd;
//    private RadioButton rbe;
//    private RadioButton rbf;
    private List<String> arrDiagnosa = new ArrayList<String>();
    String strDiagnosa = "";
    double nilai = 0;
    int qid = 0;

    double cfuser = 0;
    double cfpakar = 0;
    double cf, cfcombine;

    public Diagnosa() {
        db = null;
        gejalaCursor = null;
        datagejala = null;
        data = new gejalapenyakit();
        result = "";
        kode_main = "";
        kode_ya = "";
        kode_tidak = "";
        mulai = "";
        selesai = "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagnosa);
        gejala = findViewById(R.id.gejala1);
        lanjut = findViewById(R.id.btnlanjut);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        TextView pilihgejala = findViewById(R.id.pilihgejala);
        datagejala = new Database();
        db = datagejala.getWritableDatabase();

        Database db=new Database();
       //datagejala.getReadableDatabase();
        QuestList=db.getAllQuestions();
        currentQ=QuestList.get(qid);




        rba = findViewById(R.id.rbtidaktau);
        rbb = findViewById(R.id.rbtidakyakin);
        rbc = findViewById(R.id.rbsedikityakin);
        rbd = findViewById(R.id.rbcukupyakin);
        rbe = findViewById(R.id.rbyakin);
        rbf = findViewById(R.id.rbsangatyakin);
        setQuestionView();
        showText();
        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(myAnim);
                RadioGroup grp=findViewById(R.id.radiogroup);
                RadioButton answer=findViewById(grp.getCheckedRadioButtonId());
                cfpakar = currentQ.getANSWER();
                if(answer == rba){
                    cfuser = 0;
                    getTIDAK();

                } else if(answer == rbb) {
                    cfuser = 0.2;
                    getTIDAKYAKIN();

                } else if (answer == rbc){
                    cfuser = 0.4;
                    getSEDIKITYAKIN();

                } else if (answer == rbd){
                    cfuser = 0.6;
                    getCUKUPYAKIN();

                } else if (answer == rbe){
                    cfuser = 0.8;
                    getYAKIN();
                } else {
                    cfuser = 1;
                    getSANGATYAKIN();

                }
                cf = cfpakar * cfuser;
                if (qid == 1){
                    cfcombine = cfpakar * cfuser;
                }else {
                    cfcombine = cfcombine + (cf  * (1-cfcombine));
                   // nilai = cfcombine * 100;
                }
                if(qid < QuestList.size()){
//                    if(qid < QuestList.size()){
                    cf = cfpakar * cfuser;
                   // currentQ=QuestList.get(qid);
                 //   setQuestionView();
                }else{
                    Intent intent = new Intent(Diagnosa.this, Hasil_Diagnosa.class);
                    Bundle b = new Bundle();
                    b.putDouble("nilai", nilai); //Your score
                    intent.putExtras(b); //Put your score to your next Intent
                    intent.putExtra("nama", kode_main);
                    intent.putExtra("namagejala", strDiagnosa);
                    startActivity(intent);
                    finish();
                }

            }
        });
    }
    public void showText() {
        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = 'G1'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }
        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setClickable(false);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");

    }
    public void getYAKIN() {
        arrDiagnosa.add(data.getYa());
        strDiagnosa += arrDiagnosa.size() + ". " + data.getDiagnosa() + "\n";

        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getYa() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }

        if (kode_main.equals("K1")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K2")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K3")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K4")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K5")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K6")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K7")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K8")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K9")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K10")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K11")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K12")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K13")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }


        if (selesai.equals("Y")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }

        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");
       // rg.clearCheck();
        rba.invalidate();
        rbb.invalidate();
//        rbc.invalidate();
//        rbd.invalidate();
//        rbe.invalidate();
//        rbf.invalidate();


    }
    public void getSANGATYAKIN() {
        arrDiagnosa.add(data.getYa());
        strDiagnosa += arrDiagnosa.size() + ". " + data.getDiagnosa() + "\n";

        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getYa() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }

        if (kode_main.equals("K1")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K2")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K3")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K4")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K5")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K6")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K7")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K8")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K9")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K10")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K11")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K12")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K13")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }


        if (selesai.equals("Y")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }

        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");
        // rg.clearCheck();
        rba.invalidate();
        rbb.invalidate();

    }
    public void getTIDAKYAKIN() {
        arrDiagnosa.add(data.getYa());
        strDiagnosa += arrDiagnosa.size() + ". " + data.getDiagnosa() + "\n";

        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getYa() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }

        if (kode_main.equals("K1")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K2")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K3")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K4")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K5")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K6")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K7")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K8")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K9")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K10")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K11")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K12")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K13")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }


        if (selesai.equals("Y")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }

        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");
        // rg.clearCheck();
        rba.invalidate();
        rbb.invalidate();


    }
    public void getCUKUPYAKIN() {
        arrDiagnosa.add(data.getYa());
        strDiagnosa += arrDiagnosa.size() + ". " + data.getDiagnosa() + "\n";

        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getYa() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }

        if (kode_main.equals("K1")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K2")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K3")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K4")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K5")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K6")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K7")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K8")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K9")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K10")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K11")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K12")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K13")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }


        if (selesai.equals("Y")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }

        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");
        // rg.clearCheck();
        rba.invalidate();
        rbb.invalidate();
    }
    public void getSEDIKITYAKIN() {
        arrDiagnosa.add(data.getYa());
        strDiagnosa += arrDiagnosa.size() + ". " + data.getDiagnosa() + "\n";

        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getYa() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }

        arrDiagnosa.add(data.getYa());
        strDiagnosa += arrDiagnosa.size() + ". " + data.getDiagnosa() + "\n";

        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getYa() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }

        if (kode_main.equals("K1")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K2")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K3")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K4")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K5")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K6")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K7")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K8")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K9")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K10")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K11")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K12")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K13")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }


        if (selesai.equals("Y")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }

        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");
        // rg.clearCheck();
        rba.invalidate();
        rbb.invalidate();
    }
    public void getTIDAK() {
        gejalaCursor = db.rawQuery("SELECT * FROM gejalal where id = '" + data.getTidak() + "'", null);
        if (gejalaCursor.moveToFirst()) {
            result = gejalaCursor.getString(1);
            kode_ya = gejalaCursor.getString(2);
            kode_tidak = gejalaCursor.getString(3);
            mulai = gejalaCursor.getString(4);
            selesai = gejalaCursor.getString(5);
            kode_main = gejalaCursor.getString(0);
            while (!gejalaCursor.isAfterLast()) {
                result = gejalaCursor.getString(1);
                kode_ya = gejalaCursor.getString(2);
                kode_tidak = gejalaCursor.getString(3);
                mulai = gejalaCursor.getString(4);
                selesai = gejalaCursor.getString(5);
                kode_main = gejalaCursor.getString(0);
                gejalaCursor.moveToNext();
            }
        }
        if (kode_tidak.contentEquals("B")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("namanol", " Kerusakan pada motor anda mungkin tidak ada");
            intent.putExtra("namagejala", " ");
            startActivity(intent);
            return;

        }
        if (kode_main.equals("K1")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K2")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K3")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K4")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K5")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K6")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K7")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K8")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K9")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K10")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K11")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K12")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai", String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }
        if (kode_main.equals("K13")) {

            nilai = cfcombine * 100;
            System.out.println(String.valueOf(nilai));
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nilai",String.format("%.2f", nilai)+"%");
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }


        if (selesai.equals("Y")) {
            Intent intent = new Intent(getApplicationContext(), Hasil_Diagnosa.class);
            intent.putExtra("nama", kode_main);
            intent.putExtra("namagejala", strDiagnosa);
            startActivity(intent);
            return;
        }

        data.setDiagnosa(result);
        data.setYa(kode_ya);
        data.setTidak(kode_tidak);
        gejala.setText("Apakah motor anda mengalami\n" + data.getDiagnosa() + "?");
        // rg.clearCheck();
        rba.invalidate();
        rbb.invalidate();
    }

    private void setQuestionView()
    {
       // gejala.setText(currentQ.getQUESTION());
        rba.setText(currentQ.getOPTA());
        rbb.setText(currentQ.getOPTB());
        rbc.setText(currentQ.getOPTC());
        rbd.setText(currentQ.getOPTD());
        rbe.setText(currentQ.getOPTE());
        rbf.setText(currentQ.getOPTF());
       // gejala.setText("Apakah motor anda mengalami\n" + currentQ.getQUESTION() + "?");
       // qid++;
    }

}