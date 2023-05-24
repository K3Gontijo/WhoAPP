package com.kauegontijo.who;

public class Usuario {

    private String nome;
    private String uid;
    private String url;

    public Usuario(String nome, String uid, String url) {
        this.nome = nome;
        this.uid = uid;
        this.url = url;
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
