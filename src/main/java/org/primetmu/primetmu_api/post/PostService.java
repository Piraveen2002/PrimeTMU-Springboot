package org.primetmu.primetmu_api.post;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.primetmu.primetmu_api.client.ClientDao;
import org.primetmu.primetmu_api.s3.S3Buckets;
import org.primetmu.primetmu_api.s3.S3Service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PostService {
    private final PostDao postDao;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final Logger log = LoggerFactory.getLogger(PostService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientDao clientDao;

    public PostService(@Qualifier("postJdbc") PostDao postDao,
            S3Service s3Service,
            S3Buckets s3Buckets,
            @Qualifier("clientJdbc") ClientDao clientDao) {
        this.postDao = postDao;
        this.s3Buckets = s3Buckets;
        this.s3Service = s3Service;
        this.clientDao = clientDao;
    }

    public List<Post> getAllPosts() {
        return postDao.selectAllPosts();
    }

    public Post getPost(UUID id) {
        return postDao.selectPostById(id)
                .orElseThrow(() -> new ResourceAccessException("No Post with that ID found!"));
    }

    public List<Post> getAllPostsByCategory(String cat) {
        return postDao.selectAllPostsByCategory(cat);
    }

    public void uploadPostToS3(Post post) {
        if (postDao.checkIfPostExists(post.getId()))
            throw new ResourceAccessException("Post already exists!");

        try {
            String key = "posts/" + post.getId().toString() + ".json";
            String postAsJson = objectMapper.writeValueAsString(post);

            s3Service.putObject(s3Buckets.getPost(), key, postAsJson.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload post to S3", e);
        }
    }

    public void uploadPhotoToS3(UUID post_id, MultipartFile file) {
        if (!postDao.checkIfPostExists(post_id))
            throw new ResourceAccessException("Post doesn\'t exist!");

        UUID image_id = UUID.randomUUID();
        try {
            String key = "images/%s/%s".formatted(post_id.toString(), image_id);

            s3Service.putObject(s3Buckets.getPost(), key, file.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload photo to S3", e);
        }

        postDao.updatePostImageId(image_id, post_id);
    }

    public byte[] getPostImage(UUID id) {
        var post = postDao.selectPostById(id)
                .orElseThrow(() -> new ResourceAccessException(
                        "Photo with id: [%s] not found".formatted(id)));

        if (StringUtils.isBlank(post.getImageId().toString())) {
            throw new ResourceAccessException(
                    "customer with id [%s] not found".formatted(id));
        }

        byte[] postImage = s3Service.getObject(
                s3Buckets.getPost(),
                "images/%s/%s".formatted(id, post.getImageId().toString()));

        return postImage;
    }

    public void addPost(PostAdditionRequest pAR) {
        if (pAR.category() == null || pAR.description() == null
                || pAR.location() == null || pAR.itemType() == null
                || pAR.price() == null || pAR.title() == null) {
            throw new IllegalArgumentException("A field is null!");
        }

        if (clientDao.existsClientByUsername(pAR.username()) == false) {
            throw new IllegalArgumentException("Client does not exist!");
        }

        Post newPost = new Post(
                UUID.randomUUID(),
                pAR.username(),
                pAR.imageId(),
                pAR.category(),
                pAR.itemType(),
                pAR.title(),
                pAR.description(),
                pAR.location(),
                pAR.price());

        uploadPostToS3(newPost);
        postDao.addPost(newPost);

    }

    public void deletePost(UUID id) {
        String key = "posts/" + id + ".json";
        System.out.println("S3 Key: " + key);

        try {
            System.out.println("s3 Post: " + s3Buckets.getPost());
            s3Service.deleteObject(s3Buckets.getPost(), key);
            postDao.deletePost(id);
        } catch (Exception e) {
            log.error("Failed to delete Post from s3. Post ID: {}", id, e);
        }
    }

    public void updatePost(UUID id, PostUpdateRequest pAD) {
        Post post = postDao.selectPostById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot update ID that doesn\'t exist"));

        if (pAD.category() != null && !(pAD.category().equals(post.getCategory()))) {
            post.setCategory(pAD.category());
        }

        if (pAD.title() != null && !(pAD.title().equals(post.getTitle()))) {
            post.setTitle(pAD.title());
        }

        if (pAD.description() != null && !(pAD.description().equals(post.getDescription()))) {
            post.setDescription(pAD.description());
        }

        if (pAD.itemType() != null && !(pAD.itemType().equals(post.getItemType()))) {
            post.setItemType(pAD.itemType());
        }

        if (pAD.location() != null && !(pAD.location().equals(post.getLocation()))) {
            post.setLocation(pAD.location());
        }

        if (pAD.price() != null && pAD.price() != post.getPrice()) {
            post.setPrice(pAD.price());
        }

        postDao.editPost(post);

        uploadPostToS3(post);
    }
}
