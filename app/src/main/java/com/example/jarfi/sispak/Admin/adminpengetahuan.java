package com.example.jarfi.sispak.Admin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jarfi.sispak.Adapter.Admin.PengetahuanAdapter;
import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.Database.Model.ModelPengetahuan;

import com.example.jarfi.sispak.Database.Model.motormodel;
import com.example.jarfi.sispak.Klik.MyDividerItemDecoration;
import com.example.jarfi.sispak.Klik.RecylerTouchListener;
import com.example.jarfi.sispak.R;

import java.util.ArrayList;
import java.util.List;

public class adminpengetahuan extends AppCompatActivity {
    public Context mContext;
    private PengetahuanAdapter pengetahuanAdapter;
    private List<ModelPengetahuan> modelPengetahuanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView noPengetahuanView;
    private Database db;
    Button btnupload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpengetahuan);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.cordinatoradminpengetahuan);
        recyclerView = findViewById(R.id.rvadminpengetahuan);
        noPengetahuanView = findViewById(R.id.tidakada);
        btnupload = findViewById(R.id.button8);

        db = new Database();
        modelPengetahuanList.addAll(db.getAllPengetahuan());
        Database datac = new Database();
        datac.getReadableDatabase();
        FloatingActionButton fab = findViewById(R.id.fbadminpengetahuan);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  openbrose();
                showPengetahuanDialog(false, null, -1);
            }
        });
        pengetahuanAdapter = new PengetahuanAdapter(this, modelPengetahuanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(pengetahuanAdapter);

        toggleEmptyPengetahuan();

        recyclerView.addOnItemTouchListener(new RecylerTouchListener(this,
                recyclerView, new RecylerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {

            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));


    }


    private void showPengetahuanDialog(final boolean shouldUpdate, final ModelPengetahuan modelPengetahuan, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        View view = layoutInflater.inflate(R.layout.dialog_pengetahuan, null);

        AlertDialog.Builder alertDialogPengetahuanInput = new AlertDialog.Builder(adminpengetahuan.this);
        alertDialogPengetahuanInput.setView(view);

        final Spinner spinner = view.findViewById(R.id.idmo);
        final Spinner spinner2 = view.findViewById(R.id.idker);
        final Spinner spinner3 = view.findViewById(R.id.idge);
        final EditText bb =  view.findViewById(R.id.etbobot);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Cursor cus = (Cursor) spinner.getSelectedItem();
//                String nm = cus.getString(cus.getColumnIndex
//                        (data.getNamamotor()));
//             //   namaMotor.setText(nm);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getAllMotorSpiner()));
        ((ArrayAdapter<?>) spinner.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner2.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getAllKerusakanSpiner()));
        ((ArrayAdapter<?>) spinner2.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setAdapter(new ArrayAdapter(this, android.R.layout.simple_spinner_item, db.getAllGejalaSpiner()));
        ((ArrayAdapter<?>) spinner3.getAdapter()).setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_pengetahuan_title) : getString(R.string.lbl_edit_pengetahuan_title));

        if (shouldUpdate && modelPengetahuan != null) {


            bb.setText(modelPengetahuan.getBobot());



        }
        alertDialogPengetahuanInput.setCancelable(false).setPositiveButton(shouldUpdate ? "update" : "simpan", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }

        }).setNegativeButton("batal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog1 = alertDialogPengetahuanInput.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(bb.getText())) {
                    Toast.makeText(adminpengetahuan.this, " Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog1.dismiss();
                }
                if (shouldUpdate && modelPengetahuan != null) {



                    int spinnera1 = spinner.getSelectedItemPosition();
                    String spinnerb1 = spinner2.getSelectedItem().toString().trim();
                    String spinnerc1 = spinner3.getSelectedItem().toString().trim();
                    String bobot1 = bb.getText().toString();

                    updatePengetahuan(spinnera1,spinnerb1,spinnerc1,bobot1,position);
                    Toast.makeText(adminpengetahuan.this, " Update Data Berhasil !", Toast.LENGTH_SHORT).show();
                } else {

                    int spinneraa = spinner.getSelectedItemPosition();
                    String spinnerb = spinner2.getSelectedItem().toString().trim();
                    String spinnerc = spinner3.getSelectedItem().toString().trim();
                    String bobot = bb.getText().toString().trim();

                     createPengetahuan( spinneraa, spinnerb, spinnerc,bobot);
                    Toast.makeText(adminpengetahuan.this, " Simpan Data Berhasil !", Toast.LENGTH_SHORT).show();
                }
            }

        });


    }

    private void createPengetahuan( int motor, String kerusakan, String gejala, String bobot) {
        long id = db.insertPengetahuan(motor, kerusakan, gejala ,bobot);

        ModelPengetahuan n = db.getPengetahuan(id);
        if (n != null) {

            modelPengetahuanList.add(1, n);

            pengetahuanAdapter.notifyDataSetChanged();

            toggleEmptyPengetahuan();
        }
    }

    private void toggleEmptyPengetahuan() {
        if (db.getPengetahuanCount() > 0) {
            noPengetahuanView.setVisibility(View.GONE);

        } else {
            noPengetahuanView.setVisibility(View.VISIBLE);
        }
    }

    private void updatePengetahuan(int idm, String idk, String idg, String bobot, int position) {
        ModelPengetahuan modelPengetahuan = modelPengetahuanList.get(position);
        modelPengetahuan.setMotor(idm);
        modelPengetahuan.setKerusakan(idk);
        modelPengetahuan.setGejala(idg);
        modelPengetahuan.setBobot(bobot);

        db.updatePengetahuan(modelPengetahuan);
        modelPengetahuanList.set(position, modelPengetahuan);
        pengetahuanAdapter.notifyItemChanged(position);

        toggleEmptyPengetahuan();

    }

    private void deletePengetahuan(int position) {
        db.deletePengetahuan(modelPengetahuanList.get(position));
        modelPengetahuanList.remove(position);
        pengetahuanAdapter.notifyItemRemoved(position);
        toggleEmptyPengetahuan();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Ubah", "Hapus"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu Pilihan");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showPengetahuanDialog(true, modelPengetahuanList.get(position), position);
                } else {
                    deletePengetahuan(position);
                }
            }
        });
        builder.show();
    }



}
