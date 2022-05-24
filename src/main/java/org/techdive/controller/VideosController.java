package org.techdive.controller;

import org.techdive.dto.VideoResponse;
import org.techdive.mapper.VideoMapper;
import org.techdive.model.Video;
import org.techdive.service.VideosService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Path("/videos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VideosController {

    @Inject
    private VideosService service;

    @GET
    public Response obter() {
        List<Video> videos = service.obterVideos();
        List<VideoResponse> resp = videos.stream().map(VideoMapper.INSTANCE::toResponse).collect(toList());
        return Response.ok(resp).build();
    }

}
