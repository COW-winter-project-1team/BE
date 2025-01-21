package project.moodipie.music.track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.moodipie.music.track.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
