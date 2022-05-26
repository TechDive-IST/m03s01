package org.techdive.controller;

import org.techdive.dto.VideoRequest;
import org.techdive.dto.VideoResponse;
import org.techdive.exception.RegistroExistenteException;
import org.techdive.exception.RegistroNaoEncontradoException;
import org.techdive.mapper.VideoMapper;
import org.techdive.model.Video;
import org.techdive.service.VideosService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Path("/videos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VideosController {

    @Inject
    private VideosService service;

    @POST
    public Response inserir(@Valid VideoRequest request) {
        Video video = VideoMapper.INSTANCE.toModel(request);
        Video inserido = inserido = service.inserirVideo(video);
        VideoResponse resp = VideoMapper.INSTANCE.toResponse(inserido);
        return Response.created(URI.create(inserido.getId())).entity(resp).build();
    }

    @GET
    public Response obter(@QueryParam("assunto") String assunto,
            @QueryParam("sort") String ordenadoPor, @QueryParam("limit") Integer limite, @QueryParam("page") Integer pagina) {
        List<Video> videos = service.obterVideos(assunto, ordenadoPor, limite, pagina);
        List<VideoResponse> resp = videos.stream().map(VideoMapper.INSTANCE::toResponse).collect(toList());
        return Response.ok(resp).build();
    }

    @GET
    @Path("/{id}")
    public Response obterPorId(@PathParam(("id")) String id) {
        Video video = service.obterVideoPorId(id);
        VideoResponse resp = VideoMapper.INSTANCE.toResponse(video);
        resp.adicionarLinks();
        return Response.ok(resp).links(resp.getLinks().toArray(new Link[0])).build();
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam(("id")) String id, @Valid VideoRequest request) {
        Video video = VideoMapper.INSTANCE.toModel(request);
        video.setId(id);
        Video alterado = service.alterar(video);
        VideoResponse resp = VideoMapper.INSTANCE.toResponse(alterado);
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") String id) {
        service.remover(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}/like")
    public Response adicionarLike(@PathParam("id") String id) {
        Integer qtd = service.adicionarLike(id);
        VideoResponse resp = new VideoResponse();
        resp.setId(id);
        resp.setLikes(qtd);
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("/{id}/like")
    public Response retirarLike(@PathParam("id") String id) {
        Integer qtd = service.retirarLike(id);
        VideoResponse resp = new VideoResponse();
        resp.setId(id);
        resp.setLikes(qtd);
        return Response.ok(resp).build();
    }

    @GET
    @Path("/{id}/visualizacao")
    public Response visualizar(@PathParam("id") String id) {
        Integer qtd = service.adicionarVisualizacao(id);
        VideoResponse resp = new VideoResponse();
        resp.setId(id);
        resp.setVisualizacoes(qtd);
        return Response.ok(resp).build();
    }

}
