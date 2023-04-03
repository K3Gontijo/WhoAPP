package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TelaCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

    }
}