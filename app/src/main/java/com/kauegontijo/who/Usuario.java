package com.kauegontijo.who;

public class Usuario {

    private String nome;
    private String uid;
    private String url;

    public Usuario(){

    }

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

    public String getUrl() {
        return url;
    }
}
