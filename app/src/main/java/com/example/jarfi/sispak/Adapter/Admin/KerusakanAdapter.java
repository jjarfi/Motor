package com.example.jarfi.sispak.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jarfi.sispak.Database.Model.ModelKerusakan;
import com.example.jarfi.sispak.R;

import java.util.List;

public class KerusakanAdapter extends RecyclerView.Adapter<KerusakanAdapter.MyViewHolder> {
    private List<ModelKerusakan> modelKerusakanList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textid;
        public TextView textnama;


        public MyViewHolder(View view){
            super(view);
            textid = view.findViewById(R.id.txtid);
            textnama = view.findViewById(R.id.txtnama);
        }
    }
    public KerusakanAdapter(Context context, List<ModelKerusakan>modelKerusakanList1){
        Context context1 = context;
        this.modelKerusakanList = modelKerusakanList1;
    }
    @Override
    public KerusakanAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_row_kerusakan_admin, parent, false);
        return new KerusakanAdapter.MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder (KerusakanAdapter.MyViewHolder holder, int position){
        ModelKerusakan modelKerusakan = modelKerusakanList.get(position);
        holder.textid.setText(modelKerusakan.getIdrusak());
        holder.textnama.setText(modelKerusakan.getNamarusak());
    }
    @Override
    public int getItemCount (){
        return modelKerusakanList.size();
    }
}
