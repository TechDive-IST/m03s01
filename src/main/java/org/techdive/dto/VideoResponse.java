package org.techdive.dto;

import org.techdive.model.Comentario;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class VideoResponse implements Serializable {

    private String id;

    private String url;

    private String assunto;

    private String usuario;

    private Integer duracao;

    private Integer visualizacoes;

    private Integer likes;

    private LocalDateTime dataUltimaVisualizacao;


    public VideoResponse() { }

    public VideoResponse(String id, String url, String assunto, String usuario, Integer duracao, Integer visualizacoes, Integer likes, LocalDateTime dataUltimaVisualizacao) {
        this.id = id;
        this.url = url;
        this.assunto = assunto;
        this.usuario = usuario;
        this.duracao = duracao;
        this.visualizacoes = visualizacoes;
        this.likes = likes;
        this.dataUltimaVisualizacao = dataUltimaVisualizacao;
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
}
