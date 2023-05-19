package com.kauegontijo.who;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kauegontijo.who.databinding.TelaPerfilBinding;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class TelaPerfil extends AppCompatActivity {

    private TextView editNome;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual;


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
}