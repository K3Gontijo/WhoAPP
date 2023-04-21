package com.kauegontijo.who;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

    }

    //METODO PARA MANDAR PARA TELA DE LOGIN
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