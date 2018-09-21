package com.example.jarfi.sispak.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jarfi.sispak.R;

public class adminmenu extends AppCompatActivity {
    Button btnmotor, btnrusakn, btngejala, btnkeluar, btnpengetahuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmenu);

        btnmotor=findViewById(R.id.button3);
        btnrusakn=findViewById(R.id.button4);
        btngejala=findViewById(R.id.button5);
        btnkeluar=findViewById(R.id.button7);
        btnpengetahuan=findViewById(R.id.button6);

        btnmotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motor();
            }
        });
        btnrusakn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rusak();
            }
        });
        btngejala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gejala();
            }
        });
        btnkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });
        btnpengetahuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                penge();
            }
        });
    }
    private void motor(){
        Intent i = new Intent(getApplicationContext(),adminmotor.class);
        startActivity(i);
    }
    private void rusak(){
        Intent i = new Intent(getApplicationContext(),adminkerusakan.class);
        startActivity(i);
    }
    private void gejala(){
        Intent i = new Intent(getApplicationContext(),admingejala.class);
        startActivity(i);
    }

    private void penge(){
        Intent i = new Intent(getApplicationContext(),adminpengetahuan.class);
        startActivity(i);
    }
    private void keluar(){
        onBackPressed();
    }



    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Apakah anda yakin ingin keluar?").setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                adminmenu.this.finish();
            }
        }).setNegativeButton("Tidak", null).show();
    }


}
