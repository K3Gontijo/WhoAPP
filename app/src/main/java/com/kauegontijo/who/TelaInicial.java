package com.kauegontijo.who;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class TelaInicial extends AppCompatActivity {

    private ImageButton service1;
    private ImageButton service2;
    private ImageButton service3;
    private ImageButton service4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        service1 = findViewById(R.id.service1);
        service2 = findViewById(R.id.service2);
        service3 = findViewById(R.id.service3);
        service4 = findViewById(R.id.service4);

    }

    public void ProfissaoSelecionada(View v){

        service1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicial.this, TelaResultadoProfissao.class);
                String trabSelecionado = "Eletricista"; // O valor que deseja passar
                intent.putExtra("selecionado", trabSelecionado);
                startActivity(intent);
            }
        });

        service2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicial.this, TelaResultadoProfissao.class);
                String trabSelecionado = "Mecanico"; // O valor que deseja passar
                intent.putExtra("selecionado", trabSelecionado);
                startActivity(intent);
            }
        });

        service3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicial.this, TelaResultadoProfissao.class);
                String trabSelecionado = "Marceneiro"; // O valor que deseja passar
                intent.putExtra("selecionado", trabSelecionado);
                startActivity(intent);
            }
        });

        service4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaInicial.this, TelaResultadoProfissao.class);
                String trabSelecionado = "Motoboy"; // O valor que deseja passar
                intent.putExtra("selecionado", trabSelecionado);
                startActivity(intent);
            }
        });
    }


    //METODO PARA MANDAR PARA TELA DE DESTAQUES
    public void IrDestaques(View v){
        Intent irDestaques = new Intent(this, TelaDestaques.class);
        startActivity(irDestaques);
    }

    //METODO PARA MANDAR PARA TELA DE RECENTES
    public void IrRecentes(View v){
        Intent irRecentes = new Intent(this, TelaRescentes.class);
        startActivity(irRecentes);
    }

    //METODO PARA MANDAR PARA TELA DE SERVIÇOS
    public void IrServicos(View v){
        Intent irServicos = new Intent(this, TelaServicos.class);
        startActivity(irServicos);
    }

    //METODO PARA MANDAR PARA TELA DE PERFIL
    public void IrPerfil(View v){
        Intent irPerfil = new Intent(this, TelaPerfil.class);
        startActivity(irPerfil);
    }


    //SOBREESCREVENDO O BOTAO DE VOLTAR DO CELULAR PARA MOSTRAR UMA MENSAGEM SE O USUARIO QUER FECHAR O APLICATIVO
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TelaInicial.this);
        alertDialog.setTitle("Sair");
        alertDialog.setMessage("Quer mesmo sair?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }
}