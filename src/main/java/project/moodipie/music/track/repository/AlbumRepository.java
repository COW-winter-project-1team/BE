package project.moodipie.music.track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moodipie.music.track.entity.Album;

public interface AlbumRepository extends JpaRepository<Album, String> {
}
