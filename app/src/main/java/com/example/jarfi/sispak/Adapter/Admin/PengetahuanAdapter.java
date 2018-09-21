package com.example.jarfi.sispak.Adapter.Admin;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.jarfi.sispak.Database.Model.ModelPengetahuan;
import com.example.jarfi.sispak.R;

import java.util.List;

public class PengetahuanAdapter extends RecyclerView.Adapter<PengetahuanAdapter.MyViewHolder> {
    private Context context;
    private List<ModelPengetahuan> modelPengetahuanList;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textidpenge;
        public TextView textnamapenge;
        public TextView textnamakerus;
        public Spinner spin1;
        public Button bntup;
        public EditText bb;

        public MyViewHolder(View view) {
            super(view);
            spin1 = view.findViewById(R.id.idmo);
            bb = view.findViewById(R.id.etbobot);
            textidpenge = view.findViewById(R.id.txtnamapengetahuan);
            textnamapenge = view.findViewById(R.id.txtpengetahuan);
            textnamakerus = view.findViewById(R.id.txtnamarusa);

            bntup = view.findViewById(R.id.button8);

        }
    }

    public PengetahuanAdapter(Context context, List<ModelPengetahuan> modelPengetahuanList1) {
        this.context = context;
        this.modelPengetahuanList = modelPengetahuanList1;
    }

    @Override
    public PengetahuanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_row_pengetahuan, parent, false);
        return new PengetahuanAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PengetahuanAdapter.MyViewHolder holder, int position) {
        ModelPengetahuan modelPengetahuan = modelPengetahuanList.get(position);
        holder.textidpenge.setText(modelPengetahuan.getGejala());
        holder.textnamapenge.setText(modelPengetahuan.getBobot());
        holder.textnamakerus.setText(modelPengetahuan.getKerusakan());
    }

    @Override
    public int getItemCount() {
        return modelPengetahuanList.size();
    }


}

