package com.kauegontijo.who;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TelaLogin extends AppCompatActivity {

    //AQUI ESTAMOS DEFININDO OS ELEMENTOS QUE TEMOS NA NOSSA TELA DE LOGIN
    private EditText editEmail;
    private EditText editSenha;
    private Button btnEntrar;
    private TextView txtCadastrar;
    private TextView txtErro;
    private ImageView hideEye;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(usuarioAtual != null){
            IrTelaInicial();
        }
    }


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
        txtErro = findViewById(R.id.txtErro);
        hideEye = findViewById(R.id.hideEye);


        //DEFININDO A FUNÇÃO DO BOTÃO AO CLICAR
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();

                Log.i("Teste", email);
                Log.i("Teste", senha);

                if (email == null || email.isEmpty()) {
                    Toast.makeText(TelaLogin.this, "EMAIL deve ser preenchido", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (senha == null || senha.isEmpty()) {
                    Toast.makeText(TelaLogin.this, "SENHA deve ser preenchida", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.i("Teste", task.getResult().getUser().getUid());
                                    IrTelaInicial();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                txtErro.setText("Email ou senha inválido");
                                Log.i("Teste", e.getMessage());
                            }
                        });
            }
        });
    }




    //METODO ADICIONADO NO txtCastrar PARA MANDAR PARA TELA DE CADASTRO
    public void IrTelaCadastro(View v) {
        Intent telaCadastro = new Intent(this, TelaCadastro.class);
        startActivity(telaCadastro);
    }

    public void IrTelaInicial() {
        Intent telaInicial = new Intent(this, TelaInicial.class);
        startActivity(telaInicial);
    }


    //SOBREESCREVENDO O BOTAO DE VOLTAR DO CELULAR PARA MOSTRAR UMA MENSAGEM SE O USUARIO QUER FECHAR O APLICATIVO
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TelaLogin.this);
        alertDialog.setTitle("Sair");
        alertDialog.setMessage("Quer mesmo sair?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                finishAffinity();
            }
        });
        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
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
}