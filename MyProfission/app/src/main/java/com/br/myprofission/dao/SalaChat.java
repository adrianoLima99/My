package com.br.myprofission.dao;

/**
 * Created by LENOVO on 31/07/2016.
 */
public class SalaChat {
    private Long id;
    private String nome;
    private String nomeExibicao;
    private String emailDestino;
    private String emailorigem;
    private String caminhoImg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeExibicao() {
        return nomeExibicao;
    }

    public void setNomeExibicao(String nomeExibicao) {
        this.nomeExibicao = nomeExibicao;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getEmailorigem() {
        return emailorigem;
    }

    public void setEmailorigem(String emailorigem) {
        this.emailorigem = emailorigem;
    }

    public String getCaminhoImg() {
        return caminhoImg;
    }

    public void setCaminhoImg(String caminhoImg) {
        this.caminhoImg = caminhoImg;
    }
}
