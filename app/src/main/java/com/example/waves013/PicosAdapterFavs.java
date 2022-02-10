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

public class PicosAdapterFavs extends RecyclerView.Adapter<PicosAdapterFavs.MyViewHolder> {

    ArrayList<Pico> picosList;
    Context context;
    DatabaseHelper databaseHelper;

    public PicosAdapterFavs(ArrayList<Pico> picosList, Context context) {
        this.picosList = picosList;
        this.context = context;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PicosAdapterFavs.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pico_item_favorite, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PicosAdapterFavs.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_nome.setText(picosList.get(position).getNome());
        holder.tv_cidade.setText(picosList.get(position).getCity());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MainActivity.rolarUp(picosList.get(position).getId());
                ((MainActivity) context).rolarUp();
                ((MainActivity) context).atualizarDados(picosList.get(position));
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
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nome = itemView.findViewById(R.id.tv_nome_pico);
            tv_cidade = itemView.findViewById(R.id.tv_nome_cidade);
            layout = itemView.findViewById(R.id.pico_item_favorite);

        }
    }
}
