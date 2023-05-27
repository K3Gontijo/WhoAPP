package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BuscaMarceneiro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca_marceneiro);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

    }
}