package com.kauegontijo.who;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
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
    private ImageView hideEye;
    private ImageView hideEye2;


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
        hideEye = findViewById(R.id.hideEye);
        hideEye2 = findViewById(R.id.hideEye2);


        //DANDO A FUNÇÃO DE CRIAR UM USUARIO USANDO O METODO "CREATEUSER"
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }

    //METODO PARA CRIAR UM NOVO USUARIO
    private void createUser() {
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String senha2 = editConfirma.getText().toString();


        //verificando se os campos foram preenchidos
        if (nome == null || nome.isEmpty()) {
            Toast.makeText(this, "NOME deve ser preenchido", Toast.LENGTH_SHORT).show();
            return;
        }else if (email == null || email.isEmpty()){
            Toast.makeText(this, "EMAIL deve ser preenchido", Toast.LENGTH_SHORT).show();
            return;
        }else if (senha == null || senha.isEmpty()){
            Toast.makeText(this, "SENHA deve ser preenchida", Toast.LENGTH_SHORT).show();
            return;
        }else if (senha2 == null || senha2.isEmpty()){
            Toast.makeText(this, "Confirme a senha", Toast.LENGTH_SHORT).show();
            return;
        }


        //verificando se as duas senhas conferem
        if(senha2.equals(senha)){

            //guardando o usuario cadastrado no firebase
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Log.i("Teste", task.getResult().getUser().getUid());
                                saveUserInFirebase();
                                IrTelaLogin();
                            }
                        }})
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            txtErro.setText("Email ou senha inválido");
                            Log.i("Teste", e.getMessage());
                        }
                    });
        } else if (senha != senha2) {
            txtErro.setText("Senhas não conferem");
            return;
        }
    }




    //METODO PARA SALVAR DADOS DE UM USUARIO EM UM ARQUIVO NO FIREBASE
    private void saveUserInFirebase() {

        //definindo as variaves para os dados
        String nome= editNome.getText().toString();
        String uid = FirebaseAuth.getInstance().getUid();
        String url = "";
        String descricao = "";
        String trabalho ="";

        Usuario user = new Usuario(nome, uid, url, descricao, trabalho);

        FirebaseFirestore.getInstance().collection("users")
                .document(uid)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("Teste", e.getMessage());
                    }
                });
    }



//METODO PARA MANDAR PRA OUTRA TELA
    public void IrTelaLogin(){
        Intent telaLogin = new Intent(this, TelaLogin.class);
        startActivity(telaLogin);
    }


//SOBREESCREVENDO O BOTAO DE VOLTAR DO CELULAR PARA MANDAR PARA TELA PRINCIPAL
    @Override
    public void onBackPressed() {
        IrTelaLogin();
    }



//METODO PRA MOSTRAR E ESCONDER SENHA
    public void ChangeType (View v){

        if(editSenha.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            hideEye.setImageResource(R.drawable.baseline_remove_red_eye_24);

        }else {
            hideEye.setImageResource(R.drawable.baseline_visibility_off_24);
            editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editSenha.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }


//METODO PRA MOSTRAR E ESCONDER SENHA (confirma senha)
    public void ChangeType2 (View v){

        if(editConfirma.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
            editConfirma.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            hideEye2.setImageResource(R.drawable.baseline_remove_red_eye_24);

        }else {
            hideEye2.setImageResource(R.drawable.baseline_visibility_off_24);
            editConfirma.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            editConfirma.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }
}


