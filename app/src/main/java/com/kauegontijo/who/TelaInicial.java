package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
}