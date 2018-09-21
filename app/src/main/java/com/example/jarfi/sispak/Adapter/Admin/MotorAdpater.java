package com.example.jarfi.sispak.Adapter.Admin;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jarfi.sispak.Database.Model.motormodel;
import com.example.jarfi.sispak.R;

import java.util.List;

public class MotorAdpater extends RecyclerView.Adapter<MotorAdpater.MyViewHolder> {

    private Context context;
    private List<motormodel>motormodelList;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView textnamamotor;
        public TextView texttipemotor;

        public MyViewHolder(View view){
            super(view);
            textnamamotor = view.findViewById(R.id.txtmotor);
            texttipemotor = view.findViewById(R.id.txttipe);
        }
    }
    public MotorAdpater(Context context, List<motormodel>motormodelList1){
        this.context = context;
        this.motormodelList = motormodelList1;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_row_motor_admin, parent, false);
          return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder (MyViewHolder holder, int position){
        motormodel motormodel = motormodelList.get(position);
        holder.textnamamotor.setText(motormodel.getNamamotor());
        holder.texttipemotor.setText(motormodel.getTipemotor());
    }
    @Override
    public int getItemCount (){
        return motormodelList.size();
    }
}
