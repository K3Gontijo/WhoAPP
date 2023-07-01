package com.kauegontijo.who;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BuscaViewHolder extends RecyclerView.ViewHolder {

    ImageView fotoUsuario;
    TextView nome, descricao, trabalho, avaliacao;

    public BuscaViewHolder(@NonNull View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.txtNome);
        avaliacao = itemView.findViewById(R.id.txtAvaliacao);
        descricao = itemView.findViewById(R.id.txtDescricao);
        trabalho = itemView.findViewById(R.id.txtTrabalho);
        fotoUsuario = itemView.findViewById(R.id.fotoUsuario);
    }
}
