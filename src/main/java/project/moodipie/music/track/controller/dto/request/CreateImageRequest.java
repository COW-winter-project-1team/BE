package project.moodipie.music.track.controller.dto.request;

import lombok.Getter;
import project.moodipie.music.track.entity.Album;
import project.moodipie.music.track.entity.Image;

@Getter
public class CreateImageRequest {
    private String url;
    private Album album;

    public Image toEntity(){
        return Image.builder()
                .url(url)
                .album(album)
                .build();
    }
}
