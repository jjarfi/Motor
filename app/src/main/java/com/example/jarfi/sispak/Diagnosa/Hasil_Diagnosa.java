package com.example.jarfi.sispak.Diagnosa;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.R;

import static android.R.attr.x;
import static android.R.attr.y;

public class Hasil_Diagnosa extends AppCompatActivity {

    protected Cursor cursor;
    private Database datagejala;
    Database dbHelper;
    TextView final_result;
    TextView nama_penyakit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hasil__diagnosa);
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        dbHelper  = new Database();
       // dn.getReadableDatabase();
        final_result =  findViewById(R.id.final_result);
        TextView persen =  findViewById(R.id.persen);
        TextView judul_keterangan =  findViewById(R.id.textView5);
        nama_penyakit =  findViewById(R.id.namapenyakit);
        TextView penyakitnol =  findViewById(R.id.penyakitnol);
        Button nearhospital = findViewById(R.id.rsterdekat);
        Button btsimpan = findViewById(R.id.button10);
        TextView tv = findViewById(R.id.textView12);
        EditText et = findViewById(R.id.etsimpan);

        Bundle b = getIntent().getExtras();
        double score= b.getDouble("nilai");
        int precentage = (int) score;
        //int scored= b.getInt("nilai");
        persen.setText(String.valueOf(precentage) + " %");

        cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM kerusakan WHERE idkerusakan = '" + getIntent().getStringExtra("nama") + "' ", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            cursor.moveToPosition(0);
            judul_keterangan.setVisibility(View.VISIBLE);
            nearhospital.setVisibility(View.VISIBLE);
            nama_penyakit.setText(cursor.getString(1).toString());
            final_result.setText(getIntent().getStringExtra("namagejala"));

            // int scored= b.getInt("nilai");
           // persen.setText(String.valueOf(precentage) + " %");
            persen.setText(getIntent().getStringExtra("nilai"));

        } else {
            final_result.setText(getIntent().getStringExtra("namagejala"));
            final_result.setText("Tidak ada gejala kerusakan yang dipilih");
            judul_keterangan.setVisibility(View.INVISIBLE);
            nearhospital.setVisibility(View.INVISIBLE);
            et.setVisibility(View.INVISIBLE);
            tv.setVisibility(View.INVISIBLE);
            btsimpan.setVisibility(View.INVISIBLE);

        }
        penyakitnol.setText(getIntent().getStringExtra("namanol"));


        nearhospital.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.startAnimation(myAnim);
                String uri = "geo:" + x + "," + y + "?q=repair shop+near+my+location";
                startActivity(new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });


        Button Mulailagi = (Button) findViewById(R.id.mulailagi);
        Mulailagi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                view.startAnimation(myAnim);
                Intent i = new Intent(getApplicationContext(), Diagnosa.class);
                startActivity(i);
            }
        });
    }

    public void onBackPressed() {


    }
}
