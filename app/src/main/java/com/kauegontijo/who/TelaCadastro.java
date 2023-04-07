package com.kauegontijo.who;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseAppLifecycleListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaCadastro extends AppCompatActivity {

    //AQUI ESTAMOS DEFININDO OS ELEMENTOS QUE TEMOS NA NOSSA TELA DE LOGIN
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirma;
    private Button btnCadastrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        //REFERENCIANDO COM OS ELEMENTOS DA INTERFACE
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editemail);
        editSenha = findViewById(R.id.editSenha);
        editConfirma = findViewById(R.id.editConfirma);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        //DANDO A FUNCÇÃO DE CRIAR UM USUARIO USANDO O METODO "CREATEUSER"
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    //CRIANDO O METODO PARA CRIAR UM NOVO USUARIO
    private void createUser() {
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String nome = editNome.getText().toString();
        String senha2 = editConfirma.getText().toString();

        //verificando se os campos foram preenchidos
        if (nome == null || nome.isEmpty()) {
            Toast.makeText(this, "NOME deve ser preenchido", Toast.LENGTH_SHORT).show();
            return;
        }if (email == null || email.isEmpty()){
            Toast.makeText(this, "EMAIL deve ser preenchido", Toast.LENGTH_SHORT).show();
            return;
        }if (senha == null || senha.isEmpty()){
            Toast.makeText(this, "SENHA deve ser preenchida", Toast.LENGTH_SHORT).show();
            return;
        }if (senha2 == null || senha2.isEmpty()){
            Toast.makeText(this, "Confirme a senha", Toast.LENGTH_SHORT).show();
            return;
        }
        //quando os campos forem preenchidos, deve criar um usuário no firebase
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(nome, email, senha)

                //implementação que escuta o nosso objeto do firebase
                    //os mais imporstantes são esses dois:
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
}