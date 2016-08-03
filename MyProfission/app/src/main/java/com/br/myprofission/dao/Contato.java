package com.br.myprofission.dao;

/**
 * Created by LENOVO on 09/07/2016.
 */
public class Contato {

    private Long id;
    private String nome;
    private String numero;
    private String foto;
    public Usuario profissao= new Usuario();

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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
