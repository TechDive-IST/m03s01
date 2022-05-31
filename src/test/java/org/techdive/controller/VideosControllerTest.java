package org.techdive.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.techdive.dto.VideoRequest;
import org.techdive.dto.VideoResponse;
import org.techdive.exception.RegistroExistenteException;
import org.techdive.exception.RegistroNaoEncontradoException;
import org.techdive.mapper.VideoMapper;
import org.techdive.model.Video;
import org.techdive.service.VideosService;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.techdive.TestHelper.obterVideo;
import static org.techdive.TestHelper.obterVideoRequest;

@ExtendWith(MockitoExtension.class)
class VideosControllerTest {

    @Mock
    private VideosService service;

    @InjectMocks
    private VideosController controller;


    @Test
    @DisplayName("Quando requisição com id não existente, Deve retornar status NOT FOUND")
    void obterPorId_falhaNaoEncontrado() {
        Mockito.when(service.obterVideoPorId(anyString())).thenThrow(RegistroNaoEncontradoException.class);
        assertThrows(RegistroNaoEncontradoException.class, () -> controller.obterPorId("id"));
    }

    @Test
    @DisplayName("Quando requisição com id válido e existente, Deve retornar status OK e objeto de video")
    void obterPorId_sucesso() {
        // given
        Video video = obterVideo();
        String id = video.getId();
        Mockito.when(service.obterVideoPorId(anyString())).thenReturn(video);
        // when
        Response result = controller.obterPorId(id);
        // then
        assertNotNull(result);
        assertNotNull(result.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertInstanceOf(VideoResponse.class, result.getEntity());
        VideoResponse resp = (VideoResponse) result.getEntity();
        assertFalse(resp.getLinks().isEmpty());
    }

    @Test
    @DisplayName("Quando sem videos cadastrados, Deve retornar status 200 com lista vazia")
    void obter_falha() {
        Mockito.when(service.obterVideos(anyString(), anyString(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        // when
        Response result = controller.obter("assunto", "ordenadoPor", 1, 1);
        // then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity(), "Deveria retornar objeto não nulo no response");
        List<VideoResponse> lista = (List<VideoResponse>) result.getEntity();
        assertTrue(lista.isEmpty(), "Deveria retornar lista vazia");
    }

    @Test
    @DisplayName("Quando existe videos cadastrados, Deve retornar status 200 com lista preenchida")
    void obter_sucesso() {
        List<Video> videos = Arrays.asList(obterVideo(), obterVideo());
        Mockito.when(service.obterVideos(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(videos);
        // when
        Response result = controller.obter("assunto", "ordenadoPor", 1, 1);
        // then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertNotNull(result.getEntity(), "Deveria retornar objeto não nulo no response");
        List<VideoResponse> lista = (List<VideoResponse>) result.getEntity();
        assertFalse(lista.isEmpty(), "Deveria retornar lista não vazia");
        assertEquals(videos.size(), lista.size());
    }

    @Test
    @DisplayName("Quando requisição com URL já cadastrada, Deve retornar status CONFLICT")
    void inserir_jahExistente() {
        Mockito.when(service.inserirVideo(Mockito.any(Video.class))).thenThrow(RegistroExistenteException.class);
        assertThrows(RegistroExistenteException.class, () -> controller.inserir(new VideoRequest()));
    }

    @Test
    @DisplayName("Quando requisição com dados válidos, Deve retornar status OK e objeto de Video com id preenchido")
    void inserir_sucesso() {
        // given
        VideoRequest request = obterVideoRequest();
        Video video = VideoMapper.INSTANCE.toModel(request);
        video.setId("id");
        Mockito.when(service.inserirVideo(Mockito.any(Video.class))).thenReturn(video);
        // when
        Response result = controller.inserir(request);
        // then
        assertNotNull(result);
        assertNotNull(result.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertInstanceOf(VideoResponse.class, result.getEntity());
        VideoResponse resp = (VideoResponse) result.getEntity();
        assertNotNull(resp.getId());
    }

    @Test
    @DisplayName("Quando id não existente, Deve lançar exceção")
    void remover_falha() {
        Mockito.doThrow(new RegistroNaoEncontradoException("Video", "id")).when(service).remover(anyString());
        assertThrows(RegistroNaoEncontradoException.class, () -> controller.remover("id"));
    }

    @Test
    @DisplayName("Quando id existente, Deve retornar status NO CONTENT e remover video")
    void remover_sucesso() {
        Response result = controller.remover("id");
        assertNotNull(result);
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), result.getStatus());
        assertNull(result.getEntity(), "Não deveria conter objeto no response (decisao de projeto)");
    }

}