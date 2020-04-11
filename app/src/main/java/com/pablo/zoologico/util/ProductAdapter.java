package com.pablo.zoologico.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.zoologico.R;
import com.pablo.zoologico.modelo.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    ArrayList<Product>listP;
    public ItemClickListener listener;
    public ProductAdapter(ArrayList<Product>listP,ItemClickListener listener){
        this.listP=listP;
        this.listener=listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_layout,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tvN.setText(listP.get(position).getName());
        holder.tvC.setText(String.valueOf(listP.get(position).getCost()));
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");

        holder.tvM.setText(format.format(listP.get(position).getManufacture()));
        holder.tvE.setText(format.format(listP.get(position).getExpire()));

    }

    @Override
    public int getItemCount() {
        return listP.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
            TextView tvN,tvC,tvM,tvE;
        public MyViewHolder(@NonNull View itemView)

        {
            super(itemView);
            //obtenemos referencia
            tvN=itemView.findViewById(R.id.textViewNameP);
            tvC=itemView.findViewById(R.id.textViewCost);
            tvM=itemView.findViewById(R.id.textViewMP);
            tvE=itemView.findViewById(R.id.textViewEP);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onLongClick(getAdapterPosition());
            return false;
        }
    }

    public interface ItemClickListener{
        void onClick(int position);
        void onLongClick(int position);
    }
}
