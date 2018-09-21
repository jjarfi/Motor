package com.example.jarfi.sispak.Adapter.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.jarfi.sispak.Database.Database;
import com.example.jarfi.sispak.R;
import com.example.jarfi.sispak.User.Data.Userkerusakanadapter;
import com.example.jarfi.sispak.User.Data.Userkerusakanmodel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.jarfi.sispak.util.Konstan.ID_KERUSAKAN;
import static com.example.jarfi.sispak.util.Konstan.INFORMASI;
import static com.example.jarfi.sispak.util.Konstan.NAMA_KERUSAKAN;
import static com.example.jarfi.sispak.util.Konstan.PENYEBAB;
import static com.example.jarfi.sispak.util.Konstan.SOLUSI;

public class kerusakanmotor extends AppCompatActivity {
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static ArrayList<Userkerusakanmodel> data;
    ArrayList <String> idkerusakanlist;
    ArrayList<String> namakerusakanlist;
    ArrayList<String> deskripsilist;
    ArrayList<String> informasilist;
    ArrayList<String> penyebablist;
    ArrayList<String> solusilist;
    LinkedHashMap namelist;
    LinkedHashMap desklist;
    LinkedHashMap penyelist;
    LinkedHashMap soluslist;
    LinkedHashMap title;

    private SQLiteDatabase db;
    private Database datakerusakan;
    private Cursor kerusakanCursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kerusakanmotor);
        recyclerView = findViewById(R.id.rcuserkerusakan);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<Userkerusakanmodel>();
        datakerusakan = new Database();
        db = datakerusakan.getReadableDatabase();
        datakerusakan.getKerusakanCount();

        fetchData();
    }
    public void fetchData() {
        desklist=new LinkedHashMap<>();
        penyelist=new LinkedHashMap<>();
        soluslist=new LinkedHashMap<>();
        namelist=new LinkedHashMap<>();
        title=new LinkedHashMap<>();
        int a,b,c,d,e;

        kerusakanCursor = db.query("kerusakan" ,null, null, null, null, null, null);
        e=kerusakanCursor.getColumnIndex(ID_KERUSAKAN);
        a=kerusakanCursor.getColumnIndex(NAMA_KERUSAKAN);
        b=kerusakanCursor.getColumnIndex(INFORMASI);
        c=kerusakanCursor.getColumnIndex(PENYEBAB);
        d=kerusakanCursor.getColumnIndex(SOLUSI);

        idkerusakanlist=new ArrayList<String>();
        namakerusakanlist= new ArrayList<String>();
        deskripsilist= new ArrayList<String>();
        informasilist= new ArrayList<String>();
        penyebablist= new ArrayList<String>();
        solusilist= new ArrayList<String>();
        while (kerusakanCursor.moveToNext()){
            title.put(kerusakanCursor.getString(a),"");
            namelist.put(kerusakanCursor.getString(e),kerusakanCursor.getString(b));
            desklist.put(kerusakanCursor.getString(b),kerusakanCursor.getString(d));
            penyelist.put(kerusakanCursor.getString(c),kerusakanCursor.getString(d));
            soluslist.put(kerusakanCursor.getString(d),kerusakanCursor.getString(d));
        }
        Iterator entry = title.entrySet().iterator();
        Iterator entries = namelist.entrySet().iterator();
        Iterator entriess = desklist.entrySet().iterator();
        Iterator entriesss = penyelist.entrySet().iterator();
        Iterator entriessss = soluslist.entrySet().iterator();

        while (entries.hasNext()&& entry.hasNext() && entriess.hasNext() && entriesss.hasNext() && entriessss.hasNext() ) {
            Map.Entry satu = (Map.Entry) entries.next();
            Map.Entry dua = (Map.Entry) entriess.next();
            Map.Entry tiga = (Map.Entry) entry.next();
            Map.Entry empat = (Map.Entry) entriesss.next();
            Map.Entry lima = (Map.Entry) entriessss.next();

            idkerusakanlist.add(String.valueOf(tiga.getKey()));
            namakerusakanlist.add(String.valueOf(satu.getKey()));
            deskripsilist.add(String.valueOf(satu.getKey()));
            informasilist.add(String.valueOf(dua.getKey()));
            penyebablist.add(String.valueOf(empat.getKey()));
            solusilist.add(String.valueOf(lima.getKey()));
        }

        for (int i = 0; i < namakerusakanlist.size(); i++) {
            data.add(new Userkerusakanmodel(idkerusakanlist.get(i), namakerusakanlist.get(i), informasilist.get(i)
            , penyebablist.get(i), solusilist.get(i)));
        }
        adapter = new Userkerusakanadapter(data);
        recyclerView.setAdapter(adapter);
    }


}
