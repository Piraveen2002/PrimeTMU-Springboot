package org.primetmu.primetmu_api.post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Optional<Post> findPostById(UUID id);

    List<Post> findAllByUserId(UUID id);

    List<Post> findAllByCategory(String category);

    boolean existsById(UUID id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Post p SET p.imageId = ?1 WHERE p.id = ?2")
    int updateImageId(UUID id, UUID post_id);
}
