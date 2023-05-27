package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BuscaMotoboy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca_motoboy);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

    }
}