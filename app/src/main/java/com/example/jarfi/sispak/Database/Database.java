package com.example.jarfi.sispak.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jarfi.sispak.Database.Model.ModelGejala;
import com.example.jarfi.sispak.Database.Model.ModelKerusakan;
import com.example.jarfi.sispak.Database.Model.ModelPengetahuan;
import com.example.jarfi.sispak.Database.Model.motormodel;
import com.example.jarfi.sispak.Diagnosa.Question;
import com.example.jarfi.sispak.util.Jarfi;

import java.util.ArrayList;
import java.util.List;

import static com.example.jarfi.sispak.util.Konstan.BOBOT2;
import static com.example.jarfi.sispak.util.Konstan.HASIL;
import static com.example.jarfi.sispak.util.Konstan.ID_ATURAN;
import static com.example.jarfi.sispak.util.Konstan.ID_GEJALA;
import static com.example.jarfi.sispak.util.Konstan.ID_GEJALA1;
import static com.example.jarfi.sispak.util.Konstan.ID_GEJALAATURAN;
import static com.example.jarfi.sispak.util.Konstan.ID_KERUSAKAN;
import static com.example.jarfi.sispak.util.Konstan.ID_KERUSAKAN1;
import static com.example.jarfi.sispak.util.Konstan.ID_KERUSAKANATURAN;
import static com.example.jarfi.sispak.util.Konstan.ID_MOTOR;
import static com.example.jarfi.sispak.util.Konstan.ID_MOTOR1;
import static com.example.jarfi.sispak.util.Konstan.ID_MOTORATURAN;
import static com.example.jarfi.sispak.util.Konstan.INFORMASI;
import static com.example.jarfi.sispak.util.Konstan.KEY_ANSWER;
import static com.example.jarfi.sispak.util.Konstan.KEY_ID;
import static com.example.jarfi.sispak.util.Konstan.KEY_OPTA;
import static com.example.jarfi.sispak.util.Konstan.KEY_OPTB;
import static com.example.jarfi.sispak.util.Konstan.KEY_OPTC;
import static com.example.jarfi.sispak.util.Konstan.KEY_OPTD;
import static com.example.jarfi.sispak.util.Konstan.KEY_OPTE;
import static com.example.jarfi.sispak.util.Konstan.KEY_OPTF;
import static com.example.jarfi.sispak.util.Konstan.KEY_QUES;
import static com.example.jarfi.sispak.util.Konstan.NAMA_GEJALA;
import static com.example.jarfi.sispak.util.Konstan.NAMA_KERUSAKAN;
import static com.example.jarfi.sispak.util.Konstan.NAMA_MOTOR;
import static com.example.jarfi.sispak.util.Konstan.PENYEBAB;
import static com.example.jarfi.sispak.util.Konstan.SOLUSI;
import static com.example.jarfi.sispak.util.Konstan.TABEL_ATURAN;
import static com.example.jarfi.sispak.util.Konstan.TABEL_GEJALA;
import static com.example.jarfi.sispak.util.Konstan.TABEL_HASIL;
import static com.example.jarfi.sispak.util.Konstan.TABEL_KERUSAKAN;
import static com.example.jarfi.sispak.util.Konstan.TABEL_MOTOR;
import static com.example.jarfi.sispak.util.Konstan.TABLE_QUEST;
import static com.example.jarfi.sispak.util.Konstan.TGL;
import static com.example.jarfi.sispak.util.Konstan.TIPE_MOTOR;


public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "novan";
    private static final int DATABASE_VERSION = 1;


    public Database() {
        super(Jarfi.context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private SQLiteDatabase dbase;

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbase = db;

        //MOTOR
        String CREATE_TABEL_MOTOR = "CREATE TABLE " + TABEL_MOTOR + "("
                + ID_MOTOR + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAMA_MOTOR + " TEXT NOT NULL, "
                + TIPE_MOTOR + " TEXT "
                + ")";


        //KERUSAKAN
        String CREATE_TABEL_KERUSAKAN = "CREATE TABLE " + TABEL_KERUSAKAN + "("
                + ID_KERUSAKAN + " TEXT PRIMARY KEY , "
                + NAMA_KERUSAKAN + " TEXT NOT NULL, "
                + INFORMASI + " TEXT NOT NULL, "
                + PENYEBAB + " TEXT NOT NULL, "
                + SOLUSI + " TEXT NOT NULL "
                + ")";

        //GEJALA
        String CREATE_TABEL_GEJALA = "CREATE TABLE " + TABEL_GEJALA + "("
                + ID_GEJALA + " VARCHAR(3) PRIMARY KEY , "
                + NAMA_GEJALA + " TEXT "
                + ")";


        //HASIL 5
        String CREATE_TABEL_HASIL = "CREATE TABLE " + TABEL_HASIL + "("
                + TGL + "INTEGER PRIMARY KEY,"
                + ID_MOTOR1 + "INTEGER NOT NULL,"
                + ID_KERUSAKAN1 + " TEXT, "
                + ID_GEJALA1 + " TEXT,"
                + HASIL + " TEXT "
                + ")";


        //ATURAN
        String CREATE_TABEL_ATURAN = " CREATE TABLE IF NOT EXISTS " + TABEL_ATURAN + "("
                + ID_ATURAN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_MOTORATURAN + " INTEGER NOT NULL, "
                + ID_KERUSAKANATURAN + " TEXT NOT NULL, "
                + ID_GEJALAATURAN + " TEXT NOT NULL, "
                + BOBOT2 + " TEXT, "
                + "FOREIGN KEY (" + ID_MOTORATURAN + ") REFERENCES " + TABEL_MOTOR + "(" + ID_MOTOR + ") ON UPDATE SET NULL ON DELETE SET NULL, "
                + "FOREIGN KEY (" + ID_KERUSAKANATURAN + ") REFERENCES " + TABEL_KERUSAKAN + "(" + ID_KERUSAKAN + ") ON UPDATE SET NULL ON DELETE SET NULL, "
                + "FOREIGN KEY (" + ID_GEJALAATURAN + ") REFERENCES " + TABEL_GEJALA + "(" + ID_GEJALA + ") ON UPDATE SET NULL ON DELETE SET NULL "
                + ")";


        String CREATE_TABEL_QUEST = "CREATE TABLE IF NOT EXISTS " + TABLE_QUEST + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_QUES
                + " TEXT, " + KEY_ANSWER + " TEXT, " + KEY_OPTA + " TEXT, "
                + KEY_OPTB + " TEXT, " + KEY_OPTC + " TEXT, " + KEY_OPTD + " TEXT, "
                + KEY_OPTE + " TEXT, " + KEY_OPTF + " TEXT)";


        db.execSQL(CREATE_TABEL_MOTOR);
        db.execSQL(CREATE_TABEL_KERUSAKAN);
        db.execSQL(CREATE_TABEL_GEJALA);
        db.execSQL(CREATE_TABEL_ATURAN);
        db.execSQL(CREATE_TABEL_HASIL);
        dbase.execSQL(CREATE_TABEL_QUEST);
        addQuestions();
        createTable2(db);
        generateData2(db);
        createTable(db);
        generateData(db);

    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
           db.execSQL("PRAGMA foreign_keys=OFF;");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABEL_MOTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_KERUSAKAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_GEJALA);
        db.execSQL("DROP TABLE IF EXISTS " + TABEL_ATURAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUEST);
        onCreate(db);


    }

    //MOTOR
    public List<motormodel> getAllMotor() {
        List<motormodel> motormodels = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABEL_MOTOR + " ORDER BY " +
                TIPE_MOTOR + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                motormodel motormodel = new motormodel();
                motormodel.setIdmotor(cursor.getInt(cursor.getColumnIndex(ID_MOTOR)));
                motormodel.setNamamotor(cursor.getString(cursor.getColumnIndex(NAMA_MOTOR)));
                motormodel.setTipemotor(cursor.getString(cursor.getColumnIndex(TIPE_MOTOR)));
                motormodels.add(motormodel);
            } while (cursor.moveToNext());
        }
        db.close();

        return motormodels;
    }

    public long insertMotor(String nama, String tipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMA_MOTOR, nama);
        values.put(TIPE_MOTOR, tipe);
        long id = db.insert(TABEL_MOTOR, null, values);
        db.close();
        return id;
    }

    public motormodel getMotor(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABEL_MOTOR,
                new String[]{NAMA_MOTOR, TIPE_MOTOR},
                NAMA_MOTOR + "= ? ",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(NAMA_MOTOR));

            cursor.close();
        }
        return null;
    }

    public ArrayList<String> getAllMotorSpiner() {
        ArrayList<String> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        db.beginTransaction();
        try {

            String selectQuery = "SELECT * FROM " + TABEL_MOTOR;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0)

            {
                while (cursor.moveToNext()) {

                    String nmmot = cursor.getString(cursor.getColumnIndex("idmotor"));
                    list.add(nmmot);
                }
            }
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;

    }

    public int updateMotor(motormodel motormodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMA_MOTOR, motormodel.getNamamotor());
        values.put(TIPE_MOTOR, motormodel.getTipemotor());

        return db.update(TABEL_MOTOR, values, ID_MOTOR + " = ?",
                new String[]{String.valueOf(motormodel.getIdmotor())});
    }

    public void deleteMotor(motormodel motormodel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_MOTOR, ID_MOTOR + " = ?",
                new String[]{String.valueOf(motormodel.getIdmotor())});
        db.close();
    }

    public int getMotorCount() {
        String countQuery = "SELECT  * FROM " + TABEL_MOTOR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    /*  KERUSAKAN*/

    public List<ModelKerusakan> getAllKerusakan() {
        List<ModelKerusakan> modelKerusakans = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABEL_KERUSAKAN + " ORDER BY " +
                ID_KERUSAKAN + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelKerusakan modelKerusakan = new ModelKerusakan();
                modelKerusakan.setIdrusak(cursor.getString(cursor.getColumnIndex(ID_KERUSAKAN)));
                modelKerusakan.setNamarusak(cursor.getString(cursor.getColumnIndex(NAMA_KERUSAKAN)));
                modelKerusakan.setInformasi(cursor.getString(cursor.getColumnIndex(INFORMASI)));
                modelKerusakan.setPenyebab(cursor.getString(cursor.getColumnIndex(PENYEBAB)));
                modelKerusakan.setSolusi(cursor.getString(cursor.getColumnIndex(SOLUSI)));
                modelKerusakans.add(modelKerusakan);
            } while (cursor.moveToNext());
        }
        db.close();

        return modelKerusakans;
    }

    public long insertKerusakan(String idk, String nama, String informasi, String penyebab, String solusi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_KERUSAKAN, idk);
        values.put(NAMA_KERUSAKAN, nama);
        values.put(INFORMASI, informasi);
        values.put(PENYEBAB, penyebab);
        values.put(SOLUSI, solusi);
        long id = db.insert(TABEL_KERUSAKAN, null, values);
        db.close();
        return id;
    }

    public ModelKerusakan getKerusakan(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABEL_KERUSAKAN,
                new String[]{ID_KERUSAKAN, NAMA_KERUSAKAN, INFORMASI, PENYEBAB, SOLUSI},
                ID_KERUSAKAN + "= ? ",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(NAMA_KERUSAKAN));
            cursor.getString(cursor.getColumnIndex(INFORMASI));

            cursor.close();
        }
        return null;
    }

    public ArrayList<String> getAllKerusakanSpiner() {
        ArrayList<String> list = new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        try {

            String selectQuery = "SELECT * FROM " + TABEL_KERUSAKAN;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String nmmot = cursor.getString(cursor.getColumnIndex("idkerusakan"));
                    list.add(nmmot);
                }
            }
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;

    }

    public int updateKerusakan(ModelKerusakan modelKerusakan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMA_KERUSAKAN, modelKerusakan.getNamarusak());
        values.put(INFORMASI, modelKerusakan.getInformasi());
        values.put(PENYEBAB, modelKerusakan.getPenyebab());
        values.put(SOLUSI, modelKerusakan.getSolusi());
        return db.update(TABEL_KERUSAKAN, values, ID_KERUSAKAN + " = ?",
                new String[]{String.valueOf(modelKerusakan.getIdrusak())});
    }

    public void deleteKerusakan(ModelKerusakan modelKerusakan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_KERUSAKAN, ID_KERUSAKAN + " = ?",
                new String[]{String.valueOf(modelKerusakan.getIdrusak())});
        db.close();
    }

    public int getKerusakanCount() {
        String countQuery = "SELECT  * FROM " + TABEL_KERUSAKAN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    /*GEJALA*/
    public List<ModelGejala> getAllGejala() {
        List<ModelGejala> modelGejalas = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABEL_GEJALA + " ORDER BY " +
                ID_GEJALA + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelGejala modelGejala = new ModelGejala();
                modelGejala.setId(cursor.getString(cursor.getColumnIndex(ID_GEJALA)));
                modelGejala.setNama(cursor.getString(cursor.getColumnIndex(NAMA_GEJALA)));

                modelGejalas.add(modelGejala);
            } while (cursor.moveToNext());
        }
        db.close();

        return modelGejalas;
    }

    public long insertGejala(String idg, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_GEJALA, idg);
        values.put(NAMA_GEJALA, nama);
        long id = db.insert(TABEL_GEJALA, null, values);
        db.close();
        return id;
    }

    public ModelGejala getGejala(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABEL_GEJALA,
                new String[]{ID_GEJALA, NAMA_GEJALA},
                ID_GEJALA + "= ? ",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(NAMA_GEJALA));

            cursor.close();
        }
        return null;
    }

    public ArrayList<String> getAllGejalaSpiner() {
        ArrayList<String> list = new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();
        try {

            String selectQuery = "SELECT * FROM " + TABEL_GEJALA;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String nmmot = cursor.getString(cursor.getColumnIndex("idgejala"));
                    list.add(nmmot);
                }
            }
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {
            e.printStackTrace();

        } finally {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;

    }

    public int updateGejala(ModelGejala modelGejala) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAMA_GEJALA, modelGejala.getNama());
        return db.update(TABEL_GEJALA, values, ID_GEJALA + " = ?",
                new String[]{String.valueOf(modelGejala.getId())});
    }

    public void deleteGejala(ModelGejala modelGejala) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_GEJALA, ID_GEJALA + " = ?",
                new String[]{String.valueOf(modelGejala.getId())});
        db.close();
    }

    public int getGejalaCount() {
        String countQuery = "SELECT  * FROM " + TABEL_GEJALA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /*PENGETAHUN*/
    public List<ModelPengetahuan> getAllPengetahuan() {
        List<ModelPengetahuan> modelPengetahuans = new ArrayList<>();
        String selectQuery = " SELECT * FROM " + TABEL_ATURAN + " ORDER BY " +
                ID_ATURAN + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ModelPengetahuan modelPengetahuan = new ModelPengetahuan();
                modelPengetahuan.setId(cursor.getInt(cursor.getColumnIndex(ID_ATURAN)));
                modelPengetahuan.setMotor(cursor.getInt(cursor.getColumnIndex(ID_MOTORATURAN)));
                modelPengetahuan.setKerusakan(cursor.getString(cursor.getColumnIndex(ID_KERUSAKANATURAN)));
                modelPengetahuan.setGejala(cursor.getString(cursor.getColumnIndex(ID_GEJALAATURAN)));
                modelPengetahuan.setBobot(cursor.getString(cursor.getColumnIndex(BOBOT2)));


                modelPengetahuans.add(modelPengetahuan);
            } while (cursor.moveToNext());
        }
        db.close();

        return modelPengetahuans;
    }

    public long insertPengetahuan(int motor, String kerusakan, String gejala, String bobot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_MOTORATURAN, motor);
        values.put(ID_KERUSAKANATURAN, kerusakan);
        values.put(ID_GEJALAATURAN, gejala);
        values.put(BOBOT2, bobot);
        long id = db.insert(TABEL_ATURAN, null, values);
        db.close();
        return id;
    }

    public ModelPengetahuan getPengetahuan(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABEL_ATURAN,
                new String[]{ID_MOTORATURAN, ID_GEJALAATURAN, BOBOT2},
                ID_ATURAN + "= ? ",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.getString(cursor.getColumnIndex(ID_MOTORATURAN));
//            cursor.getString(cursor.getColumnIndex(ID_KERUSAKANATURAN));

            cursor.close();
        }
        return null;
    }

    public int updatePengetahuan(ModelPengetahuan modelPengetahuan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ID_MOTORATURAN, modelPengetahuan.getMotor());
        values.put(ID_KERUSAKANATURAN, modelPengetahuan.getKerusakan());
        values.put(ID_GEJALAATURAN, modelPengetahuan.getGejala());
        values.put(BOBOT2, modelPengetahuan.getBobot());


        return db.update(TABEL_ATURAN, values, ID_ATURAN + " = ?",
                new String[]{String.valueOf(modelPengetahuan.getId())});
    }

    public void deletePengetahuan(ModelPengetahuan modelPengetahuan) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABEL_ATURAN, ID_ATURAN + " = ?",
                new String[]{String.valueOf(modelPengetahuan.getId())});
        db.close();
    }

    public int getPengetahuanCount() {
        String countQuery = "SELECT  * FROM " + TABEL_ATURAN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public void createTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS gejalal");
        db.execSQL("CREATE TABLE if not exists gejalal (id TEXT PRIMARY KEY, nama TEXT, " +
                "ya VARCHAR(5), tidak VARCHAR(5), mulai VARCHAR(5), selesai VARCHAR(5));");
    }

    private void createTable2(SQLiteDatabase db2) {
        db2.execSQL("DROP TABLE IF EXISTS penyakit");
        db2.execSQL("CREATE TABLE if not exists penyakit (kode_penyakit TEXT PRIMARY KEY , " +
                "nama_penyakit TEXT, deskripsi TEXT, penanganan TEXT);");
    }


    public void generateData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO gejalal VALUES ('G1','Tenaga yang dihasilkan berkurang', 'G2', 'B', 'Y', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G2','Keluar asap putih pada knalpot', 'G3', 'G24', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G3','Suara kasar pada selinder', 'G4', 'G13', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G4','Getaran mesin sangat terasa', 'K1', 'G5', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G5','Suara berisik ketika di gas', 'G6', 'G9', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G6','Mesin brebet ketika dijalankan', 'G7', 'G7', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G7','Mesin tidak stabil ketika brjalan atau tersendat-sendat', 'G8', 'G8', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G8','Suara kasar pada blok sebelah kiri', 'K2', 'K2', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G9','Saat gas ditarik motor tidak mau berjalan', 'G10', 'K1', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G10','Suara mesin tidak lepas', 'G11', 'G11', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G11','Slip ketika melakukan perpindahan persneleng', 'G12', 'G12', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G12','Sulit ketika melakukan perpindahan persneleng', 'K3', 'K3', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G13','Pedal transmisi los', 'G14', 'G17', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G14','Pedal tidak mau berpindah transmisi', 'K4', 'G15', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G15','Gas tidak stasioner', 'G16', 'G16', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G16','Keluar asap hitam pada knalpot', 'K5', 'K5', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G17','Bbm boros', 'G18', 'G21', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G18','Oli mesin cepat berkurang', 'G19', 'G19', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G19','Mesin terasa tersendat saat hidup', 'G20', 'G20', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G20','Tarikan lemah', 'P006', 'P006', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G21','Putaran mesin lemah atau mengembang', 'G22', 'G4', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G22','Kecepatan motor melambat', 'G23', 'G23', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G23','Mesin susah dihidupkan ', 'K7', 'K7', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G24','Tidak ad percikan api pada busi', 'G25', 'G27', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G25','Mesin meledak-meledak ketika hidup', 'G26', 'G26', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G26','Percikan api pada busi berwarna kemerahan', 'K8', 'K8', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G27','Mesin tiba-tiba mati ketika berjalan', 'G28', 'G3', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G28','Mesin brebet ketika di gas tinggi', 'G29', 'G29', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G29','Percikan busi api pendek', 'G30', 'G30', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G30','Busi sering mati', 'K10', 'K10', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G31','Mesin tidak bisa dihidupkan', 'K11', 'K11', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G32','Lampu-lampu (spidometer, lampu depan, lampu belakang, lampu sen, dll) mati', 'K9', 'K9', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G33','Balon lampu sering putus atau mati', 'K12', 'K12', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G34','Aki cepat soak', 'K13', 'K13', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G35','Mesin tiba-tiba mati saat sedang berjalan', 'G10', 'K9', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G36','Lampu indikator pada spidometer tidak menyala', 'K12', 'K12', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G37','Dinamo stater tidak befungsi', 'K12', 'K12', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('G38','Kelakson tidak bunyi', 'G13', 'K13', 'T', 'T');");
        db.execSQL("INSERT INTO gejalal VALUES ('K1','Piston', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K2','Stang Seher', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K3','Rantai Kamar ', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K4','Kopling', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K5','Transmisi', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K6','Klep', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K7','Karburator', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K8','Busi', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K9','Koil', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K10','CDI', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K11','Spul', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K12','Kiprok', '0', '0', 'T', 'Y');");
        db.execSQL("INSERT INTO gejalal VALUES ('K13','Aki', '0', '0', 'T', 'Y');");

    }

    private void generateData2(SQLiteDatabase db2) {
        db2.execSQL("INSERT INTO penyakit VALUES ('K1', 'Piston', 'Gastritis kronik adalah suatu peradangan bagian mukosa lambung yang menahun. Gastritis kronik baru merupakan diagnosa setelah didasarkan pada hasil pemeriksaan hispatologi.', " +
                "'-Makan tepat waktu\n" +
                "-Makan sering dengan porsi kecil\n" +
                "-Makanlah makanan yang cair dan lembek\n" +
                "-Hindari makanan yang meningkatkan asam lambung atau perut kembung seperti kopi, teh, makanan pedas dan kol\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K2', 'Stang Seher', 'Tukak gaster merupakan luka terbuka dengan pinggir edema disertai indurasi dengan dasar tukak ditutupi debris.', " +
                "'-Istirahat dan hindari stress\n" +
                "-Berhenti merokok\n" +
                "-Hindari alkohol terutama dalam lambung kosong\n" +
                "-Diet ketat tidak dianjurkan lagi\n" +
                "-Hindari makanan asam, pedas, panas, banyak lemak\n" +
                "-Makan teratur sebaiknya yang lunak\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K3', 'Rantai Kamar', 'Tukak duodenum merupakan suatu defek mukosa/submukosa yang terbatas tegas dapat menembus muskularis mukosa sampai lapisan serosa sehingga dapat terjadi perforasi.', " +
                "'-Makan dalam jumlah sedikit tapi sering\n" +
                "-Hindari makanan asam, pedas, panas, berlemak\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K4', 'Kopling', 'Gastroenteritis merupakan infeksi pada saluran pencernaan yang biasanya disebabkan oleh infeksi yang menyebar melalui air dan makanan yang sudah tercemar oleh tinja yang terinfeksi.', " +
                "'-Biasakan mencuci tangan sebelum makan dan masaklah makanan dengan baik dan benar.\n" +
                "-Makanlah makanan yang lunak dan mudah dicerna\n" +
                "-Hindari makanan pedas dan berlemak\n" +
                "-Segera berobat ke dokter untuk pengobatan lebih lanjut\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K5', 'Transmisi', 'Ileus merupakan suatu keadaan dimana pergerakan kontraksi normal dinding usus untuk sementara waktu berhenti. Ileus dapat disebabkan oleh suatu infeksi atau bekuan darah di alam perut. Untuk menetapkan diagnosa perlu dilakukan pemeriksaan foto rontgen perut.', " +
                "'-Segera berobat ke dokter untuk mendapatkan infus dan pengobatan medis lainnya\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K6', 'Klep', 'Demam tifoid merupakan salah satu gangguan pencernaan yang cukup bayak diderita di Indonesia. Tipus disebabkan oleh kuman atau bakteri yang disebut Salmonella Typi. Penyakit ini menular melalui makanan atau minuman yang terkontaminasi kuman tipus.', " +
                "'-Makanlah yang lunak dan mudah dicerna secara teratur\n" +
                "-Jagalah kebersihan diri dan lingkungan\n" +
                "-Hindari makanan pedas dan berlemak\n" +
                "-Segera hubungi dokter untuk pengobatan lebih lanjut\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K7', 'Karburator', 'Disentri basilar adalah suatu infeksi akut radang kolon yang disebabkan kuman genus shigella yang terdapat di lingkungan yang buruk.', " +
                "'-Istirahat yang cukup\n" +
                "-Makanlah makanan yang lunak\n" +
                "-Segera berobat ke dokter jika kondisi belum membaik\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K8', 'Busi', 'Diare akut adalah pengeluaran kotoran (tinja) yang lebih sering dari biasanya dan perut terasa sakit dan kembung.', " +
                "'-Makan dalam jumlah sedikit tapi sering\n" +
                "-Hindari makanan asam, pedas, panas, berlemak\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K9', 'Koil', 'Apendisitis akut adalah radang yang timbul secara mendadak pada apendik, merupakan salah satu kasus akut abdomen yang paling sering ditemui dan jika tidak ditangani dapat menyebabkan perforasi.', " +
                "'-Bed rest total posisi fowler (setengah duduk)\n" +
                "-Sebaiknya langsung dirujuk ke rumah sakit terdekat untuk dilakukan operasi cito\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K10', 'Koil', 'Apendisitis akut adalah radang yang timbul secara mendadak pada apendik, merupakan salah satu kasus akut abdomen yang paling sering ditemui dan jika tidak ditangani dapat menyebabkan perforasi.', " +
                "'-Bed rest total posisi fowler (setengah duduk)\n" +
                "-Sebaiknya langsung dirujuk ke rumah sakit terdekat untuk dilakukan operasi cito\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K11', 'Koil', 'Apendisitis akut adalah radang yang timbul secara mendadak pada apendik, merupakan salah satu kasus akut abdomen yang paling sering ditemui dan jika tidak ditangani dapat menyebabkan perforasi.', " +
                "'-Bed rest total posisi fowler (setengah duduk)\n" +
                "-Sebaiknya langsung dirujuk ke rumah sakit terdekat untuk dilakukan operasi cito\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K12', 'Koil', 'Apendisitis akut adalah radang yang timbul secara mendadak pada apendik, merupakan salah satu kasus akut abdomen yang paling sering ditemui dan jika tidak ditangani dapat menyebabkan perforasi.', " +
                "'-Bed rest total posisi fowler (setengah duduk)\n" +
                "-Sebaiknya langsung dirujuk ke rumah sakit terdekat untuk dilakukan operasi cito\n' );");
        db2.execSQL("INSERT INTO penyakit VALUES ('K13', 'Koil', 'Apendisitis akut adalah radang yang timbul secara mendadak pada apendik, merupakan salah satu kasus akut abdomen yang paling sering ditemui dan jika tidak ditangani dapat menyebabkan perforasi.', " +
                "'-Bed rest total posisi fowler (setengah duduk)\n" +
                "-Sebaiknya langsung dirujuk ke rumah sakit terdekat untuk dilakukan operasi cito\n' );");
    }

    public List<Question> getAllQuestions() {
        SQLiteDatabase db;
        List<Question> quesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUEST;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setID(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getDouble(2));
                quest.setOPTA(cursor.getString(3));
                quest.setOPTB(cursor.getString(4));
                quest.setOPTC(cursor.getString(5));
                quest.setOPTD(cursor.getString(6));
                quest.setOPTE(cursor.getString(7));
                quest.setOPTF(cursor.getString(8));
                quesList.add(quest);
            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
    }


    private void addQuestions() {
        Question q1 = new Question("Saat gas ditarik motor motor tidak mau berjalan?", "Tidak Tahu", "Tidak Yakin", "Sedikit Yakin", "Cukup Yakin", "Yakin", "Sangat Yakin", 0.16);
        this.addQuestion(q1);
        Question q2 = new Question("Suara mesin tidak lepas?", "Tidak Tahu", "Tidak Yakin", "Sedikit Yakin", "Cukup Yakin", "Yakin", "Sangat Yakin", 0.16);
        this.addQuestion(q2);
        Question q3 = new Question("Selip ketika melakukan perpindahan persneleng?", "Tidak Tahu", "Tidak Yakin", "Sedikit Yakin", "Cukup Yakin", "Yakin", "Sangat Yakin", 0.16);
        this.addQuestion(q3);
        Question q4 = new Question("Sulit ketika melakukan perpindahan persneleng?", "Tidak Tahu", "Tidak Yakin", "Sedikit Yakin", "Cukup Yakin", "Yakin", "Sangat Yakin", 0.16);
        this.addQuestion(q4);
        Question q5 = new Question("Pedal transmisi los?", "Tidak Tahu", "Tidak Yakin", "Sedikit Yakin", "Cukup Yakin", "Yakin", "Sangat Yakin", 0.16);
        this.addQuestion(q5);
        Question q6 = new Question("Pedal tidak mau berpindah transmisi ?", "Tidak Tahu", "Tidak Yakin", "Sedikit Yakin", "Cukup Yakin", "Yakin", "Sangat Yakin", 0.16);
        this.addQuestion(q6);

    }

    private void addQuestion(Question quest) {
//        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUES, quest.getQUESTION());
        values.put(KEY_ANSWER, quest.getANSWER());
        values.put(KEY_OPTA, quest.getOPTA());
        values.put(KEY_OPTB, quest.getOPTB());
        values.put(KEY_OPTC, quest.getOPTC());
        values.put(KEY_OPTD, quest.getOPTD());
        values.put(KEY_OPTE, quest.getOPTE());
        values.put(KEY_OPTF, quest.getOPTF());
        // Inserting Row
        dbase.insert(TABLE_QUEST, null, values);
    }


}
