package org.primetmu.primetmu_api.post;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService ps;

    public PostController(PostService postService) {
        this.ps = postService;
    }

    @GetMapping
    public List<Post> getPosts() {
        return ps.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return ps.getPost(id);
    }

    @GetMapping("/category/{name}")
    public List<Post> getPostsByCategory(@PathVariable String name) {
        return ps.getAllPostsByCategory(name);
    }

    @PostMapping
    public ResponseEntity<?> postPost(@RequestBody PostAdditionRequest request) {
        ps.addPost(request);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable UUID id, @RequestBody PostUpdateRequest request) {
        ps.updatePost(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        ps.deletePost(id);
    }

    @PostMapping(value = "{postId}/image")
    public void uploadImage(
            @PathVariable UUID postId,
            @RequestParam("file") MultipartFile file) {
        
        
        ps.uploadPhotoToS3(postId, file);
    }

    @GetMapping(value = "{postId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPostImage(
            @PathVariable UUID postId) {
        return ps.getPostImage(postId);
    }

}
