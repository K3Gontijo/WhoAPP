package com.kauegontijo.who;

public class Usuario {

    private String nome;
    private String uid;
    private String url;
    private String descricao;
    private String trabalho;
    private Double avaliacao;

    public Usuario(String nome, String uid, String url, String descricao, String trabalho, Double avaliacao) {
        this.nome = nome;
        this.uid = uid;
        this.url = url;
        this.descricao = descricao;
        this.trabalho = trabalho;
        this.avaliacao = avaliacao;
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

    public String getTrabalho(){
        return trabalho;
    }

    public Double getAvaliacao(){
        return avaliacao;
    }
}
