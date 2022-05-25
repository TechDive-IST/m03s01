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

    public Video inserirVideo(Video video) throws RegistroExistenteException {
        verificarSeExisteComURL(video);
        video.setId(UUID.randomUUID().toString());
        video.setDataInclusao(LocalDateTime.now());
        videosDao.salvar(video);
        return video;
    }

    public Video alterar(Video video) throws RegistroNaoEncontradoException, RegistroExistenteException {
        verificarSeExisteComID(video);
        String urlOriginal = videosDao.obterPorId(video.getId()).get().getUrl();
        if (!urlOriginal.equals(video.getUrl()))
            verificarSeExisteComURL(video);
        videosDao.alterar(video);
        return video;
    }

    public void remover(String id) throws RegistroNaoEncontradoException {
        Video video = new Video();
        video.setId(id);
        verificarSeExisteComID(video);
        videosDao.remover(id);
    }


    private void verificarSeExisteComID(Video video) throws RegistroNaoEncontradoException {
        Optional<Video> videoOpt = videosDao.obterPorId(video.getId());
        if (!videoOpt.isPresent())
            throw new RegistroNaoEncontradoException("Video", video.getId());
    }

    private void verificarSeExisteComURL(Video video) throws RegistroExistenteException {
        boolean existe = videosDao.obterPorURL(video.getUrl()).isPresent();
        if (existe)
            throw new RegistroExistenteException("Video", video.getUrl());
    }

    public Video obterVideo(String id) throws RegistroNaoEncontradoException {
        Optional<Video> videoOpt = videosDao.obterPorId(id);
        return videoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Video", id));
    }

    public Integer adicionarLike(String id) throws RegistroNaoEncontradoException {
        Optional<Video> videoOpt = videosDao.obterPorId(id);
        Video video = videoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Video", id));
        video.incrementarLikes();
        videosDao.alterar(video);
        return video.getLikes();
    }

    public Integer retirarLike(String id) throws RegistroNaoEncontradoException {
        Optional<Video> videoOpt = videosDao.obterPorId(id);
        Video video = videoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Video", id));
        video.decrementarLikes();
        if (video.getLikes() < 0)
            video.setLikes(0);
        videosDao.alterar(video);
        return video.getLikes();
    }

    public Integer adicionarVisualizacao(String id) throws RegistroNaoEncontradoException {
        Optional<Video> videoOpt = videosDao.obterPorId(id);
        Video video = videoOpt.orElseThrow(() -> new RegistroNaoEncontradoException("Video", id));
        video.incrementarVisualizacao();
        videosDao.alterar(video);
        return video.getLikes();
    }

}
