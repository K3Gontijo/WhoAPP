package com.kauegontijo.who;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class TelaAlteraRegistro extends AppCompatActivity {

    private EditText editNome, editDescr;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private RadioButton trabEletricista, trabMecanico, trabMarceneiro, trabMotoboy;
    private String novoTrabalho;

    @Override
    protected void onStart() {
        super.onStart();
        ChamaDados();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_altera_registro);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        editNome = findViewById(R.id.editNome);
        editDescr = findViewById(R.id.editDescr);
        trabEletricista = findViewById(R.id.trabEletricista);
        trabMecanico = findViewById(R.id.trabMecanico);
        trabMarceneiro = findViewById(R.id.trabMarceneiro);
        trabMotoboy = findViewById(R.id.trabMotoboy);

    }

    //VER QUAL TRABALHO FOI SELECIONADO
    public void VerificaTrabalho(){
        if(trabEletricista.isChecked() == true){
            novoTrabalho = "Eletricista";
            db.collection("users")
                    .document(usuarioAtual)
                    .update("trabalho", novoTrabalho);

        } else if (trabMecanico.isChecked() == true) {
            novoTrabalho = "Mecânico";
            db.collection("users")
                    .document(usuarioAtual)
                    .update("trabalho", novoTrabalho);

        } else if (trabMarceneiro.isChecked() == true) {
            novoTrabalho = "Marceneiro";
            db.collection("users")
                    .document(usuarioAtual)
                    .update("trabalho", novoTrabalho);

        } else if (trabMotoboy.isChecked()) {
            novoTrabalho = "Motoboy";
            db.collection("users")
                    .document(usuarioAtual)
                    .update("trabalho", novoTrabalho);
        }
    }


    //METODO PARA COLOCAR OS DADOS NOS ELEMENTOS
    public void ChamaDados() {

        db.collection("users")
                .whereEqualTo("uid", usuarioAtual)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //definindo na tela o nome do usuário
                                editNome.setText(document.get("nome").toString());
                                editDescr.setText(document.get("descricao").toString());
                            }
                        }
                    }
                });
    }


    //METODO PARA ALTERAR OS DADOS DO USUARIO
    public void AlterarDados(View v) {

        String novoNome = editNome.getText().toString();
        String novaDescricao = editDescr.getText().toString();

        db.collection("users")
                .document(usuarioAtual)
                .update("nome", novoNome);

        db.collection("users")
                .document(usuarioAtual)
                .update("descricao", novaDescricao);

        VerificaTrabalho();

        IrPerfil();
    }

    public void IrPerfil(){
        Intent irPerfil = new Intent(this, TelaPerfil.class);
        startActivity(irPerfil);
    }

    public void IrInicio(View v){
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }


    //se o usuário clicar no botão para voltar
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(TelaAlteraRegistro.this);
        alertDialog.setTitle("Salvar?");
        alertDialog.setMessage("Quer mesmo sair sem salvar?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                IrPerfil();
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
}