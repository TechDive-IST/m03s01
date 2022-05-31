package org.techdive;

import org.techdive.dto.VideoRequest;
import org.techdive.model.Video;

public class TestHelper {

    public static Video obterVideo() {
        return new Video("id", "url", "assunto", "usuario");
    }

    public static VideoRequest obterVideoRequest() {
        return new VideoRequest("url", "assunto", "usuario", 30);
    }


}
