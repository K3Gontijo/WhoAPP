package com.kauegontijo.who;

public class Usuario {

    private final String nome;
    private final String uid;

    public Usuario(String nome, String uid) {
        this.nome = nome;
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public String getUid() {
        return uid;
    }
}
