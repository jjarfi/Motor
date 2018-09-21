package com.example.jarfi.sispak.User.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jarfi.sispak.Adapter.User.kerusakanmotor;
import com.example.jarfi.sispak.Adapter.User.motoruser;
import com.example.jarfi.sispak.R;

public class usermenu extends AppCompatActivity {
    Button btnmoton, btnkerusakan, btnkeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usermenu);

        btnmoton= findViewById(R.id.button3);
        btnkerusakan= findViewById(R.id.button4);
        btnkeluar=findViewById(R.id.button7);

        btnmoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                motorbuton();
            }
        });
        btnkerusakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rusakbuton();
            }
        });
        btnkeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keluar();
            }
        });
    }
    private void motorbuton(){
        Intent i = new Intent(getApplicationContext(),motoruser.class);
        startActivity(i);
    }
    private void rusakbuton(){
        Intent i = new Intent(getApplicationContext(),kerusakanmotor.class);
        startActivity(i);
    }

    private void keluar(){
       onBackPressed();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Apakah anda yakin ingin keluar?").setCancelable(false).setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                usermenu.this.finish();
            }
        }).setNegativeButton("Tidak", null).show();
    }
}

