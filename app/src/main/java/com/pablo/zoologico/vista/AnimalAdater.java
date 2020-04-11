package com.pablo.zoologico.vista;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pablo.zoologico.R;
import com.pablo.zoologico.modelo.Animal;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class AnimalAdater extends RecyclerView.Adapter<AnimalAdater.MyViewHolder>{
    ArrayList<Animal>listAnimal;
    ItemClickListener itemClickListener;

    public AnimalAdater(ArrayList<Animal> listAnimal,ItemClickListener itemClickListener) {
        this.itemClickListener=itemClickListener;
        this.listAnimal = listAnimal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_animal_layout,null,false);

        return new MyViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
                holder.tvName.setText(listAnimal.get(position).getName());
                holder.tvType.setText(listAnimal.get(position).getType());
                holder.tvHabit.setText(listAnimal.get(position).getHabit());
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listAnimal.remove(position);
                        AnimalActivity.adpater.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public int getItemCount() {
        return listAnimal.size();
    }
    //ponemos todo el item view a la eschucha
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView tvName,tvType,tvHabit;
            private Button btnDelete;
            private ItemClickListener itemClickListener;
            private Context context;
        public MyViewHolder(@NonNull View itemView,ItemClickListener itemClickListener) {
            super(itemView);
            context=itemView.getContext();
            tvName=itemView.findViewById(R.id.textViewName);
            tvType=itemView.findViewById(R.id.textViewType);
            tvHabit=itemView.findViewById(R.id.textViewHabit);
            btnDelete=itemView.findViewById(R.id.buttonDelete);
            itemView.setOnClickListener(this);
            this.itemClickListener=itemClickListener;
        }

        @Override
        public void onClick(View v) {
            if(this.itemClickListener!=null)
            this.itemClickListener.onClick(getAdapterPosition());

        }
    }
    public interface ItemClickListener{
        void onClick(int position);
    }
}
