package project.moodipie.music.playlist.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import project.moodipie.Emotion;

import java.util.Date;

@Getter
@Setter
public class UpdatePlaylistRequest {
    @Schema(description = "플레이리스트 제목", example = "우울할때 듣는 음악")
    private String title;
    @Schema(description = "플레이리스트 이미지 URL", example = "http://dfdfjiweoif.com")
    private String playlistImage;
    @Schema(description = "플레이리스트 생성 시간", example = "2024-12-12T12:12:12")
    private Date timestamp;
    @Schema(description = "감정", example = "SAD")
    private Emotion emotion;
}
