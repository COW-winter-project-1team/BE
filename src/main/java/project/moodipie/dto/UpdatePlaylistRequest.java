package project.moodipie.dto;

import lombok.Getter;
import project.moodipie.entity.Emotion;

import java.util.Date;

@Getter
public class UpdatePlaylistRequest {
    private Long id;
    private String Title;
    private String PlaylistImage;
    private Date getTimestamp;
    private Emotion emotion;
}
