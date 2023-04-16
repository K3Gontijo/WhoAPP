package com.kauegontijo.who;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class TelaCadastro extends AppCompatActivity {

    //AQUI ESTAMOS DEFININDO OS ELEMENTOS QUE TEMOS NA NOSSA TELA DE LOGIN
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirma;
    private Button btnCadastrar;
    private TextView txtErro;


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
        txtErro = findViewById(R.id.txtErro);

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


        //GUARDANDO O USUARIO CADASTRADO NO FIREBASE
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)

                //implementação que escuta o nosso objeto do firebase
                    //os mais imporstantes são esses dois:
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("Teste", task.getResult().getUser().getUid());
                            saveUserInFirebase();
                            IrTelaLogin();
                    }
                }})
                //nessa atividade, caso dê algum problema na autenticação, ele nos retorne o que aconteceu
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //aqui vou fazer que apareça uma mensagem para o usuario caso não exista cadastro
                        txtErro.setText("Email já está sendo utilizado");
                        Log.i("Teste", e.getMessage());
                    }
                });
    }



    //METODO PARA SALVAR DADOS DE UM USUARIO EM UM ARQUIVO NO FIREBASE
    private void saveUserInFirebase() {

        //definindo as variaves para os dados
        String nome= editNome.getText().toString();
        String uid = FirebaseAuth.getInstance().getUid();

        //classe para criar um novo usuario
        Usuario user = new Usuario(nome, uid);

        //criando uma coleção de usuarios "user"
        FirebaseFirestore.getInstance().collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("Teste", documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
        }

        public void IrTelaLogin(){
            Intent telaLogin = new Intent(this, TelaLogin.class);
            startActivity(telaLogin);
        }
    }