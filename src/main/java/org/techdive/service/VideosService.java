package org.techdive.service;

import org.techdive.dao.VideosDao;
import org.techdive.exception.RegistroExistenteException;
import org.techdive.exception.RegistroNaoEncontradoException;
import org.techdive.model.Video;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class VideosService {

    @Inject
    private VideosDao videosDao;

    public List<Video> obterVideos() {
        return videosDao.obter();
    }

    public Video inserirVideo(Video video) {
        verificarSeExisteVideoComURL(video);
        video.setId(UUID.randomUUID().toString());
        video.setDataInclusao(LocalDateTime.now());
        videosDao.salvar(video);
        return video;
    }

    public Video alterar(Video video) {
        obterVideoPorId(video.getId());
        verificarSeExisteVideoComURL(video);
        videosDao.alterar(video);
        return video;
    }

    public void remover(String id) {
        obterVideoPorId(id);
        videosDao.remover(id);
    }

    public Video obterVideoPorId(String id) {
        Optional<Video> videoOpt = videosDao.obterPorId(id);
        return videoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Video", id));
    }

    public Integer adicionarLike(String id) {
        Video video = obterVideoPorId(id);
        video.incrementarLikes();
        videosDao.alterar(video);
        return video.getLikes();
    }

    public Integer retirarLike(String id) {
        Video video = obterVideoPorId(id);
        video.decrementarLikes();
        videosDao.alterar(video);
        return video.getLikes();
    }

    public Integer adicionarVisualizacao(String id) {
        Video video = obterVideoPorId(id);
        video.incrementarVisualizacao();
        videosDao.alterar(video);
        return video.getLikes();
    }


    private void verificarSeExisteVideoComURL(Video video) {
        Optional<Video> videoOpt = videosDao.obterPorURL(video.getUrl());
        if (videoOpt.isPresent())
            throw new RegistroExistenteException("Video", video.getUrl());
    }

}
