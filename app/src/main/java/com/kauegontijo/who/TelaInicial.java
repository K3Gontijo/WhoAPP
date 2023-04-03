package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO
    }
}