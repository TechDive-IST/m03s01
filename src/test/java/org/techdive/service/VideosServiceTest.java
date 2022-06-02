package org.techdive.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techdive.dao.ComentariosDao;
import org.techdive.dao.VideosDao;
import org.techdive.exception.RegistroExistenteException;
import org.techdive.exception.RegistroNaoEncontradoException;
import org.techdive.model.Video;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.techdive.TestHelper.obterVideo;

@ExtendWith(MockitoExtension.class)
class VideosServiceTest {

    @Mock
    private VideosDao videosDao;
    @Mock
    private ComentariosDao comentariosDao;

    @InjectMocks
    private VideosService service;


    @Test
    @DisplayName("Quando video nao existente, Deve lançar exceção")
    void obterVideoPorId_falha() {
        Mockito.when(videosDao.obterPorId(anyString())).thenReturn(Optional.empty());
        assertThrows(RegistroNaoEncontradoException.class, () -> service.obterVideoPorId("id"));
    }

    @Test
    @DisplayName("Quando id existente existente, Deve retornar Video instanciado")
    void obterVideoPorId_sucesso() {
        // given
        Video video = obterVideo();
        Mockito.when(videosDao.obterPorId(anyString())).thenReturn(Optional.of(video));
        // when
        Video result = service.obterVideoPorId("id");
        assertNotNull(result);
        assertInstanceOf(Video.class, result);
    }

    @Test
    @DisplayName("Quando URL jah existe, Deve lançar exceção")
    void inserirVideo_falhaURL() {
        Video video = obterVideo();
        Mockito.when(videosDao.obterPorURL(anyString())).thenReturn(Optional.of(video));
        assertThrows(RegistroExistenteException.class, () -> service.inserirVideo(video));
    }

    @Test
    @DisplayName("Quando dados válidos de video, Deve gravar e retornar Video com id e data de inclusão")
    void inserirVideo_sucesso() {
        // given
        Video video = obterVideo();
        video.setId(null);            // pre-condicao que o id esteja nulo
        video.setDataInclusao(null);  // pre-condicao que data inclusao esteja nula
        Mockito.when(videosDao.obterPorURL(anyString())).thenReturn(Optional.empty());
        // when
        Video result = service.inserirVideo(video);
        // then
        assertNotNull(result);
        assertInstanceOf(Video.class, result);
        assertNotNull(result.getId());
        assertNotNull(result.getDataInclusao());
    }

    @Test
    @DisplayName("Quando id existente, Deve apagar o video")
    void removerVideo_sucesso() {
        Mockito.when(videosDao.obterPorId(Mockito.anyString())).thenReturn(Optional.of(new Video()));
        assertDoesNotThrow(() -> service.remover("id"));
    }

    @Test
    @DisplayName("Quando dados de visualizacao validos, Deve adicionar visualizacao no video")
    void adicionarVisualizacao() {
        // given
        Video video = obterVideo();
        int nroDeVisualizacoesOriginal = 10;
        video.setVisualizacoes(nroDeVisualizacoesOriginal);
        video.setDataUltimaVisualizacao(LocalDateTime.now());
        Mockito.when(videosDao.obterPorId(anyString())).thenReturn(Optional.of(video));
        // when
        Integer result = service.adicionarVisualizacao("id");
        // then
        assertNotNull(result);
        assertEquals(nroDeVisualizacoesOriginal + 1, result);
    }

}
