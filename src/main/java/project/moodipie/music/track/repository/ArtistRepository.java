package project.moodipie.music.track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moodipie.music.track.entity.Artist;

public interface ArtistRepository extends JpaRepository<Artist, String> {
}
