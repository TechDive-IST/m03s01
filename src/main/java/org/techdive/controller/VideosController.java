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
        try {
            Video inserido = inserido = service.inserirVideo(video);
            VideoResponse resp = VideoMapper.INSTANCE.toResponse(inserido);
            return Response.created(URI.create(inserido.getId())).entity(resp).build();
        } catch (RegistroExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @GET
    public Response obter() {
        List<Video> videos = service.obterVideos();
        List<VideoResponse> resp = videos.stream().map(VideoMapper.INSTANCE::toResponse).collect(toList());
        return Response.ok(resp).build();
    }

    @GET
    @Path("/{id}")
    public Response obterPorId(@PathParam(("id")) String id) {
        try {
            Video video = service.obterVideo(id);
            VideoResponse resp = VideoMapper.INSTANCE.toResponse(video);
            return Response.ok(resp).build();
        } catch (RegistroNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response alterar(@PathParam(("id")) String id, @Valid VideoRequest request) {
        Video video = VideoMapper.INSTANCE.toModel(request);
        video.setId(id);
        try {
            Video alterado = service.alterar(video);
            VideoResponse resp = VideoMapper.INSTANCE.toResponse(alterado);
            return Response.ok(resp).build();
        } catch (RegistroNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (RegistroExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") String id) {
        try {
            service.remover(id);
            return Response.noContent().build();
        } catch (RegistroNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}/like")
    public Response adicionarLike(@PathParam("id") String id) {
        try {
            Integer qtd = service.adicionarLike(id);
            VideoResponse resp = new VideoResponse();
            resp.setId(id);
            resp.setLikes(qtd);
            return Response.ok(resp).build();
        } catch (RegistroNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}/like")
    public Response retirarLike(@PathParam("id") String id) {
        try{
            Integer qtd = service.retirarLike(id);
            VideoResponse resp = new VideoResponse();
            resp.setId(id);
            resp.setLikes(qtd);
            return Response.ok(resp).build();
        } catch (RegistroNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}/visualizacao")
    public Response visualizar(@PathParam("id") String id) {
        try{
            Integer qtd = service.adicionarVisualizacao(id);
            VideoResponse resp = new VideoResponse();
            resp.setId(id);
            resp.setVisualizacoes(qtd);
            return Response.ok(resp).build();
        } catch (RegistroNaoEncontradoException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
