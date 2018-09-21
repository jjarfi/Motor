package com.example.jarfi.sispak.Admin;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.SimpleDateFormat;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jarfi.sispak.Adapter.Admin.KerusakanAdapter;
import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.Database.Model.ModelKerusakan;
import com.example.jarfi.sispak.Klik.MyDividerItemDecoration;
import com.example.jarfi.sispak.Klik.RecylerTouchListener;
import com.example.jarfi.sispak.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class adminkerusakan extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/DCIM";
    private int GALLERY = 1, CAMERA = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    private KerusakanAdapter kerusakanAdapter;
    private List<ModelKerusakan> modelKerusakanList = new ArrayList<>();
    private TextView noKerusakanView;
    private Database db2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminkerusakan);


        CoordinatorLayout coordinatorLayout = findViewById(R.id.cordinatoradminmotor);
        RecyclerView recyclerView = findViewById(R.id.rvadminmotor);
        noKerusakanView = findViewById(R.id.tidakada);
        verifyStoragePermissions(this);

        db2 = new Database();
        db2.getKerusakanCount();

        modelKerusakanList.addAll(db2.getAllKerusakan());

        FloatingActionButton fab = findViewById(R.id.fbadminkerusakan);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKerusakanDialog(false, null, -1);
            }
        });
        kerusakanAdapter = new KerusakanAdapter(this, modelKerusakanList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(kerusakanAdapter);

        toggleEmptyKerusakan();

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
    public static void verifyStoragePermissions(Activity activity) {
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void showKerusakanDialog(final boolean shouldUpdate, final ModelKerusakan modelKerusakan, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        View view = layoutInflater.inflate(R.layout.dialog_kerusakan, null);

        AlertDialog.Builder alertDialogKerusakanInput = new AlertDialog.Builder(adminkerusakan.this);
        alertDialogKerusakanInput.setView(view);

        final EditText inputId = view.findViewById(R.id.etidkerusakan);
        final EditText inputNama = view.findViewById(R.id.etnamakerusakan);
        final EditText inputInfprmasi = view.findViewById(R.id.etinformasi);
        final EditText inputPenyebab = view.findViewById(R.id.etpenyebab);
        final EditText inputSolusi = view.findViewById(R.id.etsolusi);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        final Button btnbro = view.findViewById(R.id.button9);
        final ImageView imageView = view.findViewById(R.id.gambar);

        btnbro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showActionsDialogOpen();


            }


        });

        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_kerusakan_title) : getString(R.string.lbl_edit_kerusakan_title));

        if (shouldUpdate && modelKerusakan != null) {
            inputId.setText(modelKerusakan.getIdrusak());
            inputNama.setText(modelKerusakan.getNamarusak());
            inputInfprmasi.setText(modelKerusakan.getInformasi());
            inputPenyebab.setText(modelKerusakan.getPenyebab());
            inputSolusi.setText(modelKerusakan.getSolusi());
        }
        alertDialogKerusakanInput.setCancelable(false).setPositiveButton(shouldUpdate ? "Update" : "Simpan", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {

            }

        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }

        });
        final AlertDialog alertDialog1 = alertDialogKerusakanInput.create();
        alertDialog1.show();

        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputNama.getText().toString())) {
                    Toast.makeText(adminkerusakan.this, " Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog1.dismiss();
                }
                if (shouldUpdate && modelKerusakan != null) {


                    String nama = inputNama.getText().toString().trim();
                    String informasi = inputInfprmasi.getText().toString().trim();
                    String penyebab = inputPenyebab.getText().toString().trim();
                    String solusi = inputSolusi.getText().toString().trim();

                    updateKerusakan(nama, informasi, penyebab, solusi, position);
                    Toast.makeText(adminkerusakan.this, " Update Data Berhasil !", Toast.LENGTH_SHORT).show();
                } else {
                    String id1 = inputId.getText().toString().trim();
                    String nama1 = inputNama.getText().toString().trim();
                    String informasi1 = inputInfprmasi.getText().toString().trim();
                    String penyebab1 = inputPenyebab.getText().toString().trim();
                    String solusi1 = inputSolusi.getText().toString().trim();

                    createKerusakan(id1, nama1, informasi1, penyebab1, solusi1);
                    Toast.makeText(adminkerusakan.this, " Simpan Data Berhasil !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createKerusakan(String idk, String nama, String informasi, String penyebab, String solusi) {
        long id = db2.insertKerusakan(idk, nama, informasi, penyebab, solusi);

        ModelKerusakan n = db2.getKerusakan(id);
        if (n != null) {

            modelKerusakanList.add(0, n);

            kerusakanAdapter.notifyDataSetChanged();

            toggleEmptyKerusakan();
        }
    }

    private void toggleEmptyKerusakan() {
        if (db2.getKerusakanCount() > 0) {
            noKerusakanView.setVisibility(View.GONE);

        } else {
            noKerusakanView.setVisibility(View.VISIBLE);
        }
    }

    private void updateKerusakan(String nama, String informasi, String penyebab, String solusi, int position) {
        ModelKerusakan modelKerusakan = modelKerusakanList.get(position);
        modelKerusakan.setNamarusak(nama);
        modelKerusakan.setInformasi(informasi);
        modelKerusakan.setPenyebab(penyebab);
        modelKerusakan.setSolusi(solusi);
        db2.updateKerusakan(modelKerusakan);
        modelKerusakanList.set(position, modelKerusakan);
        kerusakanAdapter.notifyItemChanged(position);

        toggleEmptyKerusakan();

    }

    private void deleteKerusakan(int position) {
        db2.deleteKerusakan(modelKerusakanList.get(position));
        modelKerusakanList.remove(position);
        kerusakanAdapter.notifyItemRemoved(position);
        toggleEmptyKerusakan();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Detail Data", "Ubah Data", "Hapus"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Menu Pilihan");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showKerusakanDetailDialog(true, modelKerusakanList.get(position), position);
                }
                if (which == 1) {
                    showKerusakanDialog(true, modelKerusakanList.get(position), position);
                }
                if (which == 2) {
                    deleteKerusakan(position);
                }
            }
        });
        builder.show();
    }


    private void showKerusakanDetailDialog(final boolean shouldUpdate, final ModelKerusakan modelKerusakan, final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());

        final View view = layoutInflater.inflate(R.layout.dialog_kerusakan, null);

        AlertDialog.Builder alertDialogKerusakanInput = new AlertDialog.Builder(adminkerusakan.this);
        alertDialogKerusakanInput.setView(view);

        final EditText inputId = view.findViewById(R.id.etidkerusakan);
        final EditText inputNama = view.findViewById(R.id.etnamakerusakan);
        final EditText inputInformasi = view.findViewById(R.id.etinformasi);
        final EditText inputPenyebab = view.findViewById(R.id.etpenyebab);
        final EditText inputSolusi = view.findViewById(R.id.etsolusi);
        final ImageView imageView = view.findViewById(R.id.gambar);
        final Button btn = view.findViewById(R.id.button9);
        btn.setVisibility(view.INVISIBLE);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_detail_kerusakan_title) : getString(R.string.lbl_detail_kerusakan_title));
        if (shouldUpdate && modelKerusakan != null) {
            inputId.setText(modelKerusakan.getIdrusak());
            inputNama.setText(modelKerusakan.getNamarusak());
            inputInformasi.setText(modelKerusakan.getInformasi());
            inputPenyebab.setText(modelKerusakan.getPenyebab());
            inputSolusi.setText(modelKerusakan.getSolusi());
        }
        alertDialogKerusakanInput.setCancelable(false).setPositiveButton(shouldUpdate ? "" : "", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog1 = alertDialogKerusakanInput.create();
        alertDialog1.show();
        alertDialog1.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputNama.getText().toString())) {
                    Toast.makeText(adminkerusakan.this, " Data Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog1.dismiss();
                }
                if (shouldUpdate && modelKerusakan != null) {
                    // updateMotor();
                    String nama = inputNama.getText().toString().trim();
                    String informasi = inputInformasi.getText().toString().trim();
                    String penyebab = inputPenyebab.getText().toString().trim();
                    String solusi = inputSolusi.getText().toString().trim();

                    updateKerusakan(nama, informasi, penyebab, solusi, position);
                } else {
                    String id1 = inputId.getText().toString().trim();
                    String nama1 = inputNama.getText().toString().trim();
                    String informasi1 = inputInformasi.getText().toString().trim();
                    String penyebab1 = inputPenyebab.getText().toString().trim();
                    String solusi1 = inputSolusi.getText().toString().trim();
                    createKerusakan(id1, nama1, informasi1, penyebab1, solusi1);
                }
            }
        });
        alertDialog1.show();
    }

    private void showActionsDialogOpen() {
        CharSequence colors[] = new CharSequence[]{"Take Picture", "Load From Gallery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Menu Pilihan");

        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    callCamera();

                } else if (which == 1) {
                    callGallery();
                }
            }

        });
         builder.show();
    }
    public void callCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        final View view = layoutInflater.inflate(R.layout.dialog_kerusakan, null);
        final ImageView imageView = view.findViewById(R.id.gambar);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(adminkerusakan.this, "Load Gambar Gagal!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            Toast.makeText(adminkerusakan.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }






}
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults){
        switch(permsRequestCode){
            case 200:
                boolean writeAccepted = grantResults[0]== PackageManager.PERMISSION_GRANTED;
                break;
        }
    }


    public void callGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);

    }
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new  File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

}
