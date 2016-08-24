package com.br.myprofission.dao;

/**
 * Created by LENOVO on 21/08/2016.
 */
public class Experiencia {
    private Long id;
    private String empresa;
    private String inicio;
    private String terminio;
    private String obs;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTerminio() {
        return terminio;
    }

    public void setTerminio(String terminio) {
        this.terminio = terminio;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
}
