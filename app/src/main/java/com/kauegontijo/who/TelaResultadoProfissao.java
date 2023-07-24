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

public class TelaResultadoProfissao extends AppCompatActivity {

    private RecyclerView rv;
    private AdapterBusca adapter;
    private ArrayList<Usuario> itens;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String usuarioAtual = FirebaseAuth.getInstance().getCurrentUser().getUid();

    //ONCREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_resultado_profissao);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        rv = findViewById(R.id.lista);
        itens = new ArrayList<Usuario>();
        BuscaUsuarios();
    }

    //PUXANDO PERFIS DO BANCO DE DADOS
    public void BuscaUsuarios(){
        String value = getIntent().getStringExtra("selecionado");

        db.collection("users")
                .whereEqualTo("trabalho", value)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult() ) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                String nome = document.get("nome").toString();
                                String uid = document.get("uid").toString();
                                String url= document.get("url").toString();
                                String descricao = document.get("descricao").toString();
                                String trabalho = document.get("trabalho").toString();
                                String ava = document.get("avaliacao").toString();
                                Double avaliacao = Double.parseDouble(ava);

                                Usuario user = new Usuario(nome, uid, url, descricao, trabalho, avaliacao);

                                if (user.getUid() != usuarioAtual) {
                                    itens.add(new Usuario(user.getNome(), user.getUid(), user.getUrl(), user.getDescricao(), user.getTrabalho(), user.getAvaliacao()));
                                    adapter = new AdapterBusca(TelaResultadoProfissao.this, itens);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(TelaResultadoProfissao.this,
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

    //REDERECIONAR PARA OUTRA INTERFACE
    public void IrIncio(View v){
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }
}