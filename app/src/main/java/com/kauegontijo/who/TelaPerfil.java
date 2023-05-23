package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

public class TelaPerfil extends AppCompatActivity {

    private ImageView fotoPerfil;
    private TextView editNome;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual;
    private Uri fotoUri;


    @Override
    public void onStart() {
        super.onStart();

        ChamaDados();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        editNome = findViewById(R.id.editNome);
        fotoPerfil = findViewById(R.id.fotoPerfil);


        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //função para escolher a foto na galeria
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua imagem"), 1);
            }
        });
    }

    //METODO PARA MANDA REDIRECIONAR PARA TELA INICIAL
    public void IrInicio (View v){
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }

    //METODO PARA MANDA REDIRECIONAR PARA TELA DE CONFIGURAÇÃO
    public void IrConfig (View v){
        Intent irConfig = new Intent(this, TelaConfig.class);
        startActivity(irConfig);
    }


    //METODO PARA PUXAR OS DADOS DO USUARIO DA BANCO DE DADOS
    public void ChamaDados(){

        usuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("users")
                .whereEqualTo("uid", usuarioAtual)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                editNome.setText(document.get("nome").toString());
                            }
                        } else {
                            Toast.makeText(TelaPerfil.this, "Não deu bom", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    //metodo para tratar a resposta da chamada de dados da galeria
    protected void onActivityResult(int RequestCode, int ResultCode, Intent dados) {
        super.onActivityResult(RequestCode, ResultCode, dados);
        if (ResultCode == Activity.RESULT_OK) {
            if (RequestCode == 1) {
                fotoPerfil.setImageURI(dados.getData());
            }
        }
    }
}