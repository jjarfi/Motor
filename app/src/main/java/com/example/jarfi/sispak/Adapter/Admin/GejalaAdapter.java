package com.example.jarfi.sispak.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jarfi.sispak.Database.Model.ModelGejala;
import com.example.jarfi.sispak.R;

import java.util.List;

public class GejalaAdapter extends RecyclerView.Adapter<GejalaAdapter.MyViewHolder> {
    private Context context;
    private List<ModelGejala> modelGejalaList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textid;
        public TextView textnama;

        public MyViewHolder(View view){
            super(view);
            textid = view.findViewById(R.id.txtid2);
            textnama = view.findViewById(R.id.txtnama2);
        }
    }
    public GejalaAdapter(Context context, List<ModelGejala>modelGejalaList1){
        this.context = context;
        this.modelGejalaList = modelGejalaList1;
    }
    @Override
    public GejalaAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_row_gejala_admin, parent, false);
        return new GejalaAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder (GejalaAdapter.MyViewHolder holder, int position){
        ModelGejala modelGejala = modelGejalaList.get(position);
        holder.textid.setText( modelGejala.getId());
        holder.textnama.setText(modelGejala.getNama());
    }
    @Override
    public int getItemCount (){
        return modelGejalaList.size();
    }
}

