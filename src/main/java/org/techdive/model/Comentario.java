package org.techdive.model;

import java.time.LocalDateTime;

public class Comentario {

    private Long id;

    private String  texto;

    private LocalDateTime dataInclusao;

    private LocalDateTime dataAtualizacao;


    public Comentario() { }

    public Comentario(Long id, String texto, LocalDateTime dataInclusao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.texto = texto;
        this.dataInclusao = dataInclusao;
        this.dataAtualizacao = dataAtualizacao;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDateTime dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

}
