package com.kauegontijo.who;


import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBusca extends RecyclerView.Adapter<BuscaViewHolder> {

    private Context context;
    private ArrayList<Usuario> itens;


    public AdapterBusca(Context context, ArrayList<Usuario> itens) {
        this.context = context;
        this.itens = itens;

    }

    @NonNull
    @Override
    public BuscaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_busca_usuario, parent, false);
        BuscaViewHolder buscaViewHolder = new BuscaViewHolder(view);
        return buscaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BuscaViewHolder buscaViewHolder, int position) {
        Usuario usuario = itens.get(position);
        buscaViewHolder.nome.setText(usuario.getNome());
        buscaViewHolder.trabalho.setText(usuario.getTrabalho());
        buscaViewHolder.descricao.setText(usuario.getDescricao());
        buscaViewHolder.avaliacao.setText(usuario.getAvaliacao().toString());

        if (usuario.getUrl() != ""){
            Picasso.get().load(usuario.getUrl()).into(buscaViewHolder.fotoUsuario);

        }else if (usuario.getUrl() == ""){


        }

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
