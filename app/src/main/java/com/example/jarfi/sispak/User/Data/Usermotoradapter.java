package com.example.jarfi.sispak.User.Data;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jarfi.sispak.Diagnosa.Diagnosa;
import com.example.jarfi.sispak.R;

import java.util.ArrayList;



public class Usermotoradapter extends RecyclerView.Adapter<Usermotoradapter.MyViewHolder>{
    private ArrayList<Usermotormodel> dataSet;
    Boolean check=false;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namamotor,deskripsi,solution;
        Button btnupload;
        RelativeLayout expandable;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.expandable= itemView.findViewById(R.id.expandableLayout);
            this.namamotor= itemView.findViewById(R.id.namamotor);
            this.deskripsi =  itemView.findViewById(R.id.deskripsiText);
            this.solution =  itemView.findViewById(R.id.solutiontext);
            this.btnupload = itemView.findViewById(R.id.button8);
        }
    }

    public Usermotoradapter(ArrayList<Usermotormodel> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_infomotor, parent, false);
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

        TextView namamotor1= holder.namamotor;
        TextView deskripsi1 = holder.deskripsi;
        TextView solution1 = holder.solution;

        namamotor1.setText(dataSet.get(listPosition).getId());
        deskripsi1.setText(dataSet.get(listPosition).getNama());
        solution1.setText(dataSet.get(listPosition).getTipe());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
