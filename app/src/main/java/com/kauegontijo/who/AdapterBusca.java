package com.kauegontijo.who;

import android.content.Context;
import android.content.Intent;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_resultado_busca_usurario, parent, false);
        BuscaViewHolder buscaViewHolder = new BuscaViewHolder(view);
        return buscaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BuscaViewHolder buscaViewHolder, int position) {

        //criando as variaveis
        Usuario usuario = itens.get(position);
        String nome = usuario.getNome();
        String url = usuario.getUrl();
        String trabalho = usuario.getTrabalho();
        String descricao = usuario.getDescricao();
        String avaliacao = usuario.getAvaliacao().toString();

        //para passar os valores
        String[] dados = {nome, url, trabalho, descricao,avaliacao};

        //definindo na interface
        buscaViewHolder.nome.setText(nome);
        buscaViewHolder.trabalho.setText(trabalho);
        buscaViewHolder.descricao.setText(descricao);
        buscaViewHolder.avaliacao.setText(avaliacao);

        if (url != ""){
            Picasso.get().load(usuario.getUrl()).into(buscaViewHolder.fotoUsuario);
        }else if (url == ""){
            //caso não seja possível carregar a imagem
        }

        //quando um item for clicado na lista
        buscaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent irPerfil = new Intent(context,TelaPerfilBuscado.class);
                irPerfil.putExtra("dados", dados);
                context.startActivity(irPerfil);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
