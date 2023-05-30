package com.kauegontijo.who;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BuscaMarceneiro extends AppCompatActivity {

    private RecyclerView rv;
    private AdapterBusca adapter;
    private ArrayList<Usuario> itens;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();


    @Override
    protected void onStart() {
        super.onStart();
        BuscaUsuarios();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busca_marceneiro);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        rv = findViewById(R.id.listaMarceneiro);
        itens = new ArrayList<Usuario>();
    }

    public void BuscaUsuarios(){
        db.collection("users")
                .whereEqualTo("trabalho", "Marceneiro")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult() ) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Usuario user = new Usuario(document.get("nome").toString(), document.get("uid").toString(), document.get("url").toString(),
                                        document.get("descricao").toString(), document.get("trabalho").toString());

                                if (user.getUid() != usuarioAtual) {
                                    itens.add(new Usuario(user.getNome(), user.getUid(), user.getUrl(), user.getDescricao(), user.getTrabalho()));
                                    adapter = new AdapterBusca(BuscaMarceneiro.this, itens);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(BuscaMarceneiro.this,
                                            LinearLayoutManager.VERTICAL, false);
                                    rv.setLayoutManager(layoutManager);
                                    rv.setAdapter(adapter);
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void IrIncio(View v){
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }
}