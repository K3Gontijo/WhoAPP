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

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TelaPerfil extends AppCompatActivity {

    private ImageView fotoPerfil;
    private TextView editNome;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Uri mSelectedUri;
    private TextView txtDescricao;

    @Override
    public void onStart() {
        super.onStart();

        ChamaDados();
    }

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        editNome = findViewById(R.id.editNome);
        fotoPerfil = findViewById(R.id.fotoPerfil);
        txtDescricao = findViewById(R.id.txtDescricao);


        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //função para escolher a foto na galeria
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(intent, "Escolha sua imagem"), 1);
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

                                //definindo na tela o nome do usuário
                                editNome.setText(document.get("nome").toString());
                                txtDescricao.setText(document.get("descricao").toString());


                                //verficando se o usuário ja possui foto ou não
                                if(document.get("url") != null){
                                    //variavel para armazenar a url do usuario
                                    String url = document.get("url").toString();

                                    //puxando a foto, caso o usuario ja tenha foto
                                    StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + url);

                                    try {
                                        File arquivoLocal = File.createTempFile("tempFile", ".jpg");
                                        ref.getFile(arquivoLocal)
                                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                        //defindo a foto de perfil dele
                                                        Bitmap bitmap = BitmapFactory.decodeFile(arquivoLocal.getAbsolutePath());
                                                        fotoPerfil.setImageBitmap(bitmap);

                                                    }
                                                });
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                //caso o usuário não tenha foto ainda
                                }else{
                                    Toast.makeText(TelaPerfil.this, "Usuário não possui foto", Toast.LENGTH_SHORT).show();
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
                fotoPerfil.setImageURI(mSelectedUri);
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

        db.collection("users")
                .document(usuarioAtual)
                .update("url", filename);
    }
}