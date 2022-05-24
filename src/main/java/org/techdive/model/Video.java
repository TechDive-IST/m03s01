package org.techdive.model;

import java.time.LocalDateTime;
import java.util.List;

public class Video {

    private String id;   // UUID

    private String url;

    private String assunto;

    private String usuario;

    private Integer duracao; // em minutos

    private Integer visualizacoes;

    private Integer likes;

    private LocalDateTime dataUltimaVisualizacao;

    private LocalDateTime dataInclusao;

    private LocalDateTime dataAtualizacao;

    private List<Comentario> comentarios;


    public Video() { }

    public Video(String id, String url, String assunto, String usuario, Integer duracao, Integer visualizacoes, Integer likes, LocalDateTime dataUltimaVisualizacao, LocalDateTime dataInclusao, LocalDateTime dataAtualizacao, List<Comentario> comentarios) {
        this.id = id;
        this.url = url;
        this.assunto = assunto;
        this.usuario = usuario;
        this.duracao = duracao;
        this.visualizacoes = visualizacoes;
        this.likes = likes;
        this.dataUltimaVisualizacao = dataUltimaVisualizacao;
        this.dataInclusao = dataInclusao;
        this.dataAtualizacao = dataAtualizacao;
        this.comentarios = comentarios;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getVisualizacoes() {
        return visualizacoes;
    }

    public void setVisualizacoes(Integer visualizacoes) {
        this.visualizacoes = visualizacoes;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public LocalDateTime getDataUltimaVisualizacao() {
        return dataUltimaVisualizacao;
    }

    public void setDataUltimaVisualizacao(LocalDateTime dataUltimaVisualizacao) {
        this.dataUltimaVisualizacao = dataUltimaVisualizacao;
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

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
