package com.example.waves013;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PicosAdapter extends RecyclerView.Adapter<PicosAdapter.MyViewHolder> {

    ArrayList<Pico> picosList;
    Context context;
    DatabaseHelper databaseHelper;

    public PicosAdapter(ArrayList<Pico> picosList, Context context) {
        this.picosList = picosList;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PicosAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pico_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicosAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_nome.setText(picosList.get(position).getNome());
        holder.tv_cidade.setText(picosList.get(position).getCity());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("id", picosList.get(position).getId());
                context.startActivity(intent);
            }
        });
        if (picosList.get(position).isFavorite()){
            holder.btn_star.setImageResource(R.drawable.ic_star_on);
        } else {
            holder.btn_star.setImageResource(R.drawable.ic_star_off);
        }
        holder.btn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picosList.get(position).isFavorite()){
                    picosList.get(position).setFavorite(false);
                    holder.btn_star.setImageResource(R.drawable.ic_star_off);
                    databaseHelper.saveData(picosList);

                } else {
                    picosList.get(position).setFavorite(true);
                    holder.btn_star.setImageResource(R.drawable.ic_star_on);
                    databaseHelper.saveData(picosList);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return picosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nome;
        TextView tv_cidade;
        ImageButton btn_star;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nome = itemView.findViewById(R.id.tv_nome_pico);
            tv_cidade = itemView.findViewById(R.id.tv_nome_cidade);
            btn_star = itemView.findViewById(R.id.btn_star);
            layout = itemView.findViewById(R.id.pico_item);

        }
    }
}
