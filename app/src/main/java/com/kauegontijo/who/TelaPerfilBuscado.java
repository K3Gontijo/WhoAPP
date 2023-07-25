package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class TelaPerfilBuscado extends AppCompatActivity {

    private ImageView fotoPerfil;
    private TextView txtNome, txtTrabalho, txtAvaliacao, txtDescricao;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inicio = new Intent(this, TelaInicial.class);
        startActivity(inicio);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil_buscado);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        txtNome = findViewById(R.id.editNome);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        txtTrabalho = findViewById(R.id.txtTrabalho);
        txtAvaliacao = findViewById(R.id.txtAvaliacao);
        txtDescricao = findViewById(R.id.txtDescricao);

        String[] dados = getIntent().getStringArrayExtra("dados");

        txtNome.setText(dados[0]);
        txtTrabalho.setText(dados[2]);
        txtDescricao.setText(dados[3]);
        txtAvaliacao.setText(dados[4]);
        String url = dados[1];

        try {
            Picasso.get().load(url).into(fotoPerfil);
        }catch (Exception e){
            url = "https://firebasestorage.googleapis.com/v0/b/whoapp-36515.appspot.com/o/images%2Fperfil-vazio.png?alt=media&token=e3558881-a384-4498-9c8f-035629e71c8f";
            Picasso.get().load(url).into(fotoPerfil);
        }
    }

    //METODOS PARA REDIRECIONAR TELA
    public void IrInicio (View v){
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }
}