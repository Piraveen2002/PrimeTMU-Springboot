package org.primetmu.primetmu_api.post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostDao {
    public List<Post> selectAllPosts();

    public Optional<Post> selectPostById(UUID id);

    public List<Post> selectAllPostsByUserId(UUID userId);

    public List<Post> selectAllPostsByCategory(String category);

    public void editPost(Post p);

    public void deletePost(UUID id);

    public void addPost(Post post);

    public boolean checkIfPostExists(UUID id);

    public void updatePostImageId(UUID id, UUID post_id);
}
