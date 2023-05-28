package com.kauegontijo.who;

public class Usuario {

    private String nome;
    private String uid;
    private String url;
    private String descricao;

    public Usuario(String nome, String uid, String url, String descricao) {
        this.nome = nome;
        this.uid = uid;
        this.url = url;
        this.descricao = descricao;
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

    public String getDescricao(){
        return descricao;
    }
}
