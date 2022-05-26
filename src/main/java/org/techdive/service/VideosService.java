package org.techdive.service;

import org.techdive.dao.VideosDao;
import org.techdive.exception.RegistroExistenteException;
import org.techdive.exception.RegistroNaoEncontradoException;
import org.techdive.model.Video;
import org.techdive.util.Paginador;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@RequestScoped
public class VideosService {

    @Inject
    private VideosDao videosDao;

    private static int TAMANHO_PAGINA = 2;


    public List<Video> obterVideos(String assunto, String ordenadoPor, Integer limite, Integer pagina) {
        // o ideal é aplicar os filtros e demais operacoes via SQL
        List<Video> videos = videosDao.obter();
        if (assunto != null && !assunto.isEmpty())
            videos = videos.stream().filter(v -> v.getAssunto().equals(assunto)).collect(toList());
        if (ordenadoPor != null)
            ordenarResultadoPor(ordenadoPor, videos);
        if (limite != null)
            videos = videos.subList(0, limite);
        if (pagina != null)
            videos = obterPagina(pagina, videos);
        return videos;
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
        video.setDataUltimaVisualizacao(LocalDateTime.now());
        videosDao.alterar(video);
        return video.getLikes();
    }


    private void verificarSeExisteVideoComURL(Video video) {
        Optional<Video> videoOpt = videosDao.obterPorURL(video.getUrl());
        if (videoOpt.isPresent())
            throw new RegistroExistenteException("Video", video.getUrl());
    }

    private List<Video> obterPagina(Integer pagina, List<Video> videos) {
        Paginador<Video> paginador = new Paginador<>(videos, TAMANHO_PAGINA);
        List<Video> videosPaginados = paginador.obterPagina(pagina);
        return videosPaginados;
    }

    private void ordenarResultadoPor(String ordenadoPor, List<Video> videos) {
        if (ordenadoPor.equals("assunto"))
            Collections.sort(videos, Comparator.comparing(Video::getAssunto));
        else if (ordenadoPor.equals("usuario"))
            Collections.sort(videos, Comparator.comparing(Video::getUsuario));
    }

}
