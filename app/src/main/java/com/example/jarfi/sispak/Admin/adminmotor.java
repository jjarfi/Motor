package com.example.jarfi.sispak.Admin;

import android.content.DialogInterface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jarfi.sispak.Adapter.Admin.MotorAdpater;
import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.Database.Model.motormodel;
import com.example.jarfi.sispak.Klik.MyDividerItemDecoration;
import com.example.jarfi.sispak.Klik.RecylerTouchListener;
import com.example.jarfi.sispak.R;

import java.util.ArrayList;
import java.util.List;

public class adminmotor extends AppCompatActivity {
    private MotorAdpater motorAdpater;
    private List<motormodel> motormodelList = new ArrayList<>();
    private TextView noMotorView;

    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminmotor);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.cordinatoradminmotor);
        RecyclerView recyclerView = findViewById(R.id.rvadminmotor);
        noMotorView = findViewById(R.id.tidakada);

        db = new Database();

        motormodelList.addAll(db.getAllMotor());

        FloatingActionButton fab = findViewById(R.id.fbadminmotor);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMotorDialog(false, null, -1);
            }
        });
        motorAdpater = new MotorAdpater(this, motormodelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(motorAdpater);

        toggleEmptyMotor();

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

    private void showMotorDialog(final boolean shouldUpdate, final motormodel motormodel, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        View view = layoutInflater.inflate(R.layout.dialog_motor, null);

        AlertDialog.Builder alertDialogMotorInput = new AlertDialog.Builder(adminmotor.this);
        alertDialogMotorInput.setView(view);
        final EditText inputNama = view.findViewById(R.id.etmotor);
        final EditText inputTipe = view.findViewById(R.id.ettipemotor);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_motor_title) : getString(R.string.lbl_edit_motor_title));

        if (shouldUpdate && motormodel != null) {
            inputNama.setText(motormodel.getNamamotor());
            inputTipe.setText(motormodel.getTipemotor());
        }
        alertDialogMotorInput.setCancelable(false).setPositiveButton(shouldUpdate ? "update" : "simpan", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }

        }).setNegativeButton("batal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog1 = alertDialogMotorInput.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputNama.getText().toString() + inputTipe.getText().toString())) {
                    Toast.makeText(adminmotor.this, " Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog1.dismiss();
                }
                if (shouldUpdate && motormodel != null) {


                    String nama = inputNama.getText().toString().trim();
                    String tipe = inputTipe.getText().toString().trim();

                    updateMotor(nama, tipe, position);
                    Toast.makeText(adminmotor.this, " Update Data Berhasil !", Toast.LENGTH_SHORT).show();
                } else {
                    String namai = inputNama.getText().toString().trim();
                    String tipei = inputTipe.getText().toString().trim();

                    createMotor(namai, tipei);
                    Toast.makeText(adminmotor.this, " Simpan Data Berhasil !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createMotor(String nama, String tipe) {
        long id = db.insertMotor(nama, tipe);

        motormodel n = db.getMotor(id);
        if (n != null) {

            motormodelList.add(1, n);

            motorAdpater.notifyDataSetChanged();

            toggleEmptyMotor();
        }
    }

    private void toggleEmptyMotor() {
        if (db.getMotorCount() > 0) {
            noMotorView.setVisibility(View.GONE);

        } else {
            noMotorView.setVisibility(View.VISIBLE);
        }
    }

    private void updateMotor(String nama, String tipe, int position) {
        motormodel motor = motormodelList.get(position);
        motor.setNamamotor(nama);
        motor.setTipemotor(tipe);

        db.updateMotor(motor);
        motormodelList.set(position, motor);
        motorAdpater.notifyItemChanged(position);

        toggleEmptyMotor();

    }

    private void deleteMotor(int position) {
        db.deleteMotor(motormodelList.get(position));
        motormodelList.remove(position);
        motorAdpater.notifyItemRemoved(position);
        toggleEmptyMotor();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Ubah", "Hapus"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu Pilihan");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showMotorDialog(true, motormodelList.get(position), position);
                } else {
                    deleteMotor(position);
                }
            }
        });
        builder.show();
    }
}
