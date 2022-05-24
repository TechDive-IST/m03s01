package org.techdive.service;

import org.techdive.model.Comentario;
import org.techdive.model.Video;

import javax.enterprise.context.RequestScoped;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequestScoped
public class VideosService {

    public List<Video> obterVideos() {
        Video video1 = new Video("ID001", "http://www.fdkjfaksda", "assunto", "usuario", 20, 2, 2,
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null);
        Video video2 = new Video("ID002", "http://www.fdkjfaksda", "assunto", "usuario", 20, 2, 2,
                LocalDateTime.now(), LocalDateTime.now(), LocalDateTime.now(), null);
        return Arrays.asList(video1, video2);
    }

}
