package com.example.jarfi.sispak.User.Data;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jarfi.sispak.R;

import java.util.ArrayList;

public class Userkerusakanadapter extends RecyclerView.Adapter<Userkerusakanadapter.MyViewHolder>{
    private ArrayList<Userkerusakanmodel> dataSet;
    Boolean check=false;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namakerusakan,deskripsi,informasi,penyebab,solusi;
        ImageView gambar;
        RelativeLayout expandable;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.expandable= itemView.findViewById(R.id.expandableLayout1);
            this.namakerusakan= itemView.findViewById(R.id.namakerusakan);
            this.deskripsi =  itemView.findViewById(R.id.deskripsiText1);
            this.informasi = itemView.findViewById(R.id.deskripsiText2);
            this.penyebab = itemView.findViewById(R.id.deskripsiText3);
            this.solusi = itemView.findViewById(R.id.deskripsiText4);
        }
    }

    public Userkerusakanadapter(ArrayList<Userkerusakanmodel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_infokerusakan, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check)
                {
                    myViewHolder.expandable.animate()
                            .alpha(0.0f)
                            .setDuration(1000);

                    myViewHolder.expandable.setVisibility(View.GONE);
                    check=true;


                }
                else {
                    myViewHolder.expandable.setVisibility(View.VISIBLE);
                    myViewHolder.expandable.animate()
                            .alpha(1.0f)
                            .setDuration(1000);

                    check=false;

                }
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView namakerusakan1= holder.namakerusakan;
        TextView deskripsi1 = holder.deskripsi;
        TextView informasi = holder.informasi;
        TextView penyebab = holder.penyebab;
        TextView solusi = holder.solusi;
        namakerusakan1.setText(dataSet.get(listPosition).getIdrusak());
        deskripsi1.setText(dataSet.get(listPosition).getNamarusak());
        informasi.setText(dataSet.get(listPosition).getInformasi());
        penyebab.setText(dataSet.get(listPosition).getPenyebab());
        solusi.setText(dataSet.get(listPosition).getSolusi());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}