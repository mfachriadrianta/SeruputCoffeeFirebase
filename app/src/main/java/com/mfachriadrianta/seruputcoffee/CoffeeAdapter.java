package com.mfachriadrianta.seruputcoffee;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyCoffees> myCoffees;

    public CoffeeAdapter (Context c,ArrayList<MyCoffees> p){
        context = c;
        myCoffees = p;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mycoffee, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.xnama_coffee.setText(myCoffees.get(i).getNama_coffee());
        myViewHolder.xlokasi.setText(myCoffees.get(i).getLokasi());
        myViewHolder.xjumlah_coffee.setText(myCoffees.get(i).getJumlah_coffee() + "Coffee");

        final String getNamaCoffee = myCoffees.get(i).getNama_coffee();

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotomycoffeedetails = new Intent(context, MyCoffeeDetailAct.class);
                gotomycoffeedetails.putExtra("nama_coffee",getNamaCoffee);
                context.startActivity(gotomycoffeedetails);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCoffees.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView xnama_coffee, xlokasi, xjumlah_coffee;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            xnama_coffee = itemView.findViewById(R.id.xnama_coffee);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_coffee = itemView.findViewById(R.id.xjumlah_coffee);


        }
    }
}
