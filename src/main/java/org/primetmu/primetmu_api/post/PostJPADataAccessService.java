package org.primetmu.primetmu_api.post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository("postJpa")
public class PostJPADataAccessService implements PostDao {
    private final PostRepository postRepo;

    public PostJPADataAccessService(PostRepository postRepository) {
        this.postRepo = postRepository;
    }

    @Override
    public List<Post> selectAllPosts() {
        Page<Post> page = postRepo.findAll(Pageable.ofSize(1000));
        return page.getContent();
    }

    @Override
    public Optional<Post> selectPostById(UUID id) {
        return postRepo.findById(id);
    }

    @Override
    public List<Post> selectAllPostsByUsername(String username) {
        return postRepo.findByUsername(username);
    }

    @Override
    public List<Post> selectAllPostsByCategory(String category) {
        return postRepo.findAllByCategory(category);
    }

    @Override
    public boolean checkIfPostExists(UUID post_id) {
        return postRepo.existsById(post_id);
    }

    @Override
    public void addPost(Post post) {
        postRepo.save(post);
    }

    @Override
    public void deletePost(UUID id) {
        postRepo.deleteById(id);
    }

    @Override
    public void editPost(Post p) {
        postRepo.save(p);
    }

    @Override
    public void updatePostImageId(UUID id, UUID postId) {
        postRepo.updateImageId(id, postId);
    }

}
