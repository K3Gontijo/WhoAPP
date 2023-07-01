package com.kauegontijo.who;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TelaPerfil extends AppCompatActivity {

    private ImageButton editarDescricao;
    private ImageView fotoPerfil;
    private TextView editNome, trabalho;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Uri mSelectedUri;
    private TextView txtDescricao;
    private TextView txtAvaliacao;

    @Override
    public void onStart() {
        super.onStart();

        ChamaDados();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent inicio = new Intent(this, TelaInicial.class);
        startActivity(inicio);
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        editNome = findViewById(R.id.editNome);
        trabalho = findViewById(R.id.txtTrabalho);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        txtAvaliacao = findViewById(R.id.txtAvaliacao);
        txtDescricao = findViewById(R.id.txtDescricao);
        editarDescricao = findViewById(R.id.editarDescricao);


        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //função para escolher a foto na galeria
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), 1);
            }
        });
    }



    //METODOS PARA REDIRECIONAR TELA
    public void IrInicio (View v){
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }

    public void IrConfig (View v){
        Intent irConfig = new Intent(this, TelaConfig.class);
        startActivity(irConfig);
    }

    public void IrAlteraRegistro(View v){
        Intent irAltera = new Intent(this, TelaAlteraRegistro.class);
        startActivity(irAltera);
    }






    //METODO PARA PUXAR OS DADOS DO USUARIO DA BANCO DE DADOS
    public void ChamaDados(){

        db.collection("users")
                .whereEqualTo("uid", usuarioAtual)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (!task.getResult().isEmpty()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                editNome.setText(document.get("nome").toString());

                                //definindo o serviço
                                if(document.get("trabalho")!= ""){
                                    trabalho.setClickable(false);
                                    trabalho.setText(document.get("trabalho").toString());;
                                }

                                if(document.get("descricao")!= ""){
                                    //mudando a visibilidade do botão
                                    editarDescricao.setVisibility(View.INVISIBLE);
                                    txtDescricao.setText(document.get("descricao").toString());
                                }

                                if(document.get("avaliacao")!= ""){
                                    txtAvaliacao.setText(document.get("avaliacao").toString());
                                }

                                //verficando se o usuário ja possui foto ou não
                                if(document.get("url") != ""){
                                    String url = document.get("url").toString();

                                    Picasso.get().load(url).into(fotoPerfil);
                                }
                            }
                        } else {
                            Toast.makeText(TelaPerfil.this, "Não foi possível carregar os dados", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }






    //metodo para tratar a resposta da chamada de dados da galeria
    protected void onActivityResult(int RequestCode, int ResultCode, Intent dados) {
        super.onActivityResult(RequestCode, ResultCode, dados);
        if (ResultCode == Activity.RESULT_OK) {
            if (RequestCode == 1) {
                //nessa variavel estamos armazenando os dados da foto
                mSelectedUri = dados.getData();

                //aqui estamos definindo a imagem no layout
                Picasso.get().load(mSelectedUri).into(fotoPerfil);
                //aqui estamos mandando a foto para o firestorage
                UploadFoto();
            }
        }
    }




    //METODO PARA SALVAR FOTO NO STORAGE
    private void UploadFoto(){
        String filename = UUID.randomUUID().toString();
        StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(mSelectedUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                db.collection("users")
                                                .document(usuarioAtual)
                                                        .update("url",uri);

                                Log.i("Teste", uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Log.e("Teste", e.getMessage(), e);
                    }
                });
    }
}