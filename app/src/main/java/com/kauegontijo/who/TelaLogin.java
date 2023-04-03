package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class TelaLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

    }

    public void IrTelaCadastro (View v){
        Intent telaCadstro = new Intent(this,TelaCadastro.class);
        startActivity(telaCadstro);
    }
}