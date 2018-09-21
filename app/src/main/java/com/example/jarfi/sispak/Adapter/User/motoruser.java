package com.example.jarfi.sispak.Adapter.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.Diagnosa.Diagnosa;
import com.example.jarfi.sispak.Klik.RecylerTouchListener;
import com.example.jarfi.sispak.R;
import com.example.jarfi.sispak.User.Data.Usermotoradapter;
import com.example.jarfi.sispak.User.Data.Usermotormodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.jarfi.sispak.util.Konstan.NAMA_MOTOR;
import static com.example.jarfi.sispak.util.Konstan.TIPE_MOTOR;

public class motoruser extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static ArrayList<Usermotormodel> data;
    ArrayList <String> idmotorlist;
    ArrayList<String> namamotorlist;
    ArrayList<String> tipemotorlist;
    SearchView searchView;
    LinkedHashMap namelist;
    LinkedHashMap title;

    private SQLiteDatabase db;
    private Database datamotor;
    private Cursor motorCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motoruser);
        recyclerView = findViewById(R.id.rcusermotor);
        recyclerView.setHasFixedSize(true);
        //searchView = findViewById(R.id.carimotor);
        //searchView.setQueryHint("Pencarian");
        //searchView.setQueryRefinementEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<Usermotormodel>();
        datamotor = new Database();
        db = datamotor.getReadableDatabase();
        datamotor.getMotorCount();
        fetchData();
        recyclerView.addOnItemTouchListener(new RecylerTouchListener(this,
                recyclerView, new RecylerTouchListener.ClickListener(){
            @Override
            public void onClick(View view, final int position){

            }
            @Override
            public void onLongClick(View view, int position){
                showActionsDialog(position);
            }
        }));


    }
    public void fetchData() {

        namelist=new LinkedHashMap<>();
        title=new LinkedHashMap<>();

        int a,b;

        motorCursor = db.query("motor" ,null, null, null, null, null, null);
        a=motorCursor.getColumnIndex(NAMA_MOTOR);
        b=motorCursor.getColumnIndex(TIPE_MOTOR);

        idmotorlist=new ArrayList<String>();
        namamotorlist= new ArrayList<String>();
        tipemotorlist= new ArrayList<String>();

        while (motorCursor.moveToNext()){
            title.put(motorCursor.getString(a),"");
            namelist.put(motorCursor.getString(a), motorCursor.getString(b));
        }
        Iterator entry = title.entrySet().iterator();
        Iterator entries = namelist.entrySet().iterator();

        while (entries.hasNext()&& entry.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            Map.Entry thatEntry = (Map.Entry) entry.next();
            idmotorlist.add(String.valueOf(thatEntry.getKey()));
            namamotorlist.add(String.valueOf(thisEntry.getKey()));
            tipemotorlist.add(String.valueOf(thisEntry.getValue()));
        }

        for (int i = 0; i < namamotorlist.size(); i++) {
            data.add(new Usermotormodel(idmotorlist.get(i), namamotorlist.get(i), tipemotorlist.get(i)));
        }
        adapter = new Usermotoradapter(data);
        recyclerView.setAdapter(adapter);
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Diagnosa Motor", "Batal"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu Pilihan");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    diagnosaa();
                } else {
                    //deleteMotor(position);
                }
            }
        });
        builder.show();
    }
    private void diagnosaa(){
        Intent i = new Intent(getApplicationContext(),Diagnosa.class);
        startActivity(i);
    }

}
