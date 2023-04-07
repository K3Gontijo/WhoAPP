package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TelaLogin extends AppCompatActivity {

    //AQUI ESTAMOS DEFININDO OS ELEMENTOS QUE TEMOS NA NOSSA TELA DE LOGIN
    private EditText editEmail;
    private EditText editSenha;
    private Button btnEntrar;
    private TextView txtCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO


        //REFERENCIANDO COM OS ELEMENTOS DA INTERFACE
        editEmail = findViewById(R.id.editemail);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        txtCadastrar = findViewById(R.id.txtCadastrar);

        //
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                Log.i("Teste", email);
                Log.i("Teste", senha);
            }
        });

    }
    //METODO ADICIONADO NO txtCastrar PARA MANDAR PARA TELA DE CADASTRO
    public void IrTelaCadastro (View v){
        Intent telaCadastro = new Intent(this,TelaCadastro.class);
        startActivity(telaCadastro);
    }
}