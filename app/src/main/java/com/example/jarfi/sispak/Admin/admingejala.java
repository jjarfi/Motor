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

import com.example.jarfi.sispak.Adapter.Admin.GejalaAdapter;
import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.Database.Model.ModelGejala;
import com.example.jarfi.sispak.Klik.MyDividerItemDecoration;
import com.example.jarfi.sispak.Klik.RecylerTouchListener;
import com.example.jarfi.sispak.R;

import java.util.ArrayList;
import java.util.List;

public class admingejala extends AppCompatActivity {
    private GejalaAdapter gejalaAdapter;
    private List<ModelGejala> modelGejalaList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noGejalaView;

    private Database db3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admingejala);

        coordinatorLayout = findViewById(R.id.cordinatoradmingejala);
        recyclerView = findViewById(R.id.rvadmingejala);
        noGejalaView = findViewById(R.id.tidakada);

        db3 = new Database();

        db3.getGejalaCount();

        modelGejalaList.addAll(db3.getAllGejala());

        FloatingActionButton fab = findViewById(R.id.fbadmingejala);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGejalaDialog(false, null, -1);
            }
        });
        gejalaAdapter = new GejalaAdapter(this, modelGejalaList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(gejalaAdapter);

        toggleEmptyGejala();

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

    private void showGejalaDialog(final boolean shouldUpdate, final ModelGejala modelGejala , final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View view = layoutInflater.inflate(R.layout.dialog_gejala, null);
        AlertDialog.Builder alertDialogGejalaInput = new AlertDialog.Builder(admingejala.this);
        alertDialogGejalaInput.setView(view);

        final EditText inputId = view.findViewById(R.id.etidgejala);
        final EditText inputNama = view.findViewById(R.id.etnamagejala);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_gejala_title) : getString(R.string.lbl_edit_gejala_title));

        if(shouldUpdate && modelGejala != null){
            inputId.setText(modelGejala.getId());
            inputNama.setText(modelGejala.getNama());
        }
        alertDialogGejalaInput.setCancelable(false).setPositiveButton(shouldUpdate ? "Update" : "Simpan", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }

        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog1 = alertDialogGejalaInput.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputNama.getText().toString())){
                    Toast.makeText(admingejala.this, " Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    alertDialog1.dismiss();
                }
                if(shouldUpdate && modelGejala != null){
                    // updateMotor();

                    String nama = inputNama.getText().toString().trim();

                    updateGejala(nama,position);
                    Toast.makeText(admingejala.this, " Update Data Berhasil!", Toast.LENGTH_SHORT).show();
                }else{
                    String id1 = inputId.getText().toString().trim();
                    String nama1 = inputNama.getText().toString().trim();

                    createGejala(id1,nama1);
                    Toast.makeText(admingejala.this, " Simpan Data Berhasil !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createGejala(String idg, String nama) {
        long id = db3.insertGejala(idg,nama);

        ModelGejala n = db3.getGejala(id);
        if (n != null) {

            modelGejalaList.add(0, n);

            gejalaAdapter.notifyDataSetChanged();

            toggleEmptyGejala();
        }
    }

    private void toggleEmptyGejala() {
        if(db3.getGejalaCount() > 0 ){
            noGejalaView.setVisibility(View.GONE);

        }else{
            noGejalaView.setVisibility(View.VISIBLE);
        }
    }

    private void updateGejala(String nama, int position) {
        ModelGejala modelGejala = modelGejalaList.get(position);
        modelGejala.setNama(nama);

        db3.updateGejala(modelGejala);
        modelGejalaList.set(position, modelGejala);
        gejalaAdapter.notifyItemChanged(position);

        toggleEmptyGejala();

    }
    private void deleteGejala(int position){
        db3.deleteGejala(modelGejalaList.get(position));
        modelGejalaList.remove(position);
        gejalaAdapter.notifyItemRemoved(position);
        toggleEmptyGejala();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Detail Data","Ubah Data", "Hapus"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu Pilihan");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showGejalaDetailDialog(true,modelGejalaList.get(position),position);
                } if (which == 1){
                    showGejalaDialog(true, modelGejalaList.get(position), position);
                }if (which == 2){
                    deleteGejala(position);
                }
            }
        });
        builder.show();
    }



    private void showGejalaDetailDialog(final boolean shouldUpdate, final ModelGejala modelGejala , final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        View view = layoutInflater.inflate(R.layout.dialog_gejala, null);

        AlertDialog.Builder alertDialogGejalaInput = new AlertDialog.Builder(admingejala.this);
        alertDialogGejalaInput.setView(view);

        final EditText inputId = view.findViewById(R.id.etidgejala);
        final EditText inputNama = view.findViewById(R.id.etnamagejala);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_detail_gejala_title) : getString(R.string.lbl_detail_gejala_title));

        if(shouldUpdate && modelGejala != null){
            inputId.setText(modelGejala.getId());
            inputNama.setText(modelGejala.getNama());
        }
        alertDialogGejalaInput.setCancelable(false).setPositiveButton(shouldUpdate ? "" : "", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }

        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog1 = alertDialogGejalaInput.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(inputNama.getText().toString())){
                    Toast.makeText(admingejala.this, " Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    alertDialog1.dismiss();
                }
                if(shouldUpdate && modelGejala != null){
                    // updateMotor();

                    String nama = inputNama.getText().toString().trim();


                    updateGejala(nama,position);
                }else{
                    String id1 = inputId.getText().toString().trim();
                    String nama1 = inputNama.getText().toString().trim();

                    createGejala(id1,nama1);
                }
            }
        });

    }


}
