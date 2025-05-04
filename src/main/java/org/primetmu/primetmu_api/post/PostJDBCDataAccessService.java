package org.primetmu.primetmu_api.post;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("postJdbc")
public class PostJDBCDataAccessService implements PostDao {
    private final JdbcTemplate jdbcTemplate;
    private final PostRowMapper pRM;

    public PostJDBCDataAccessService(JdbcTemplate temp, PostRowMapper postRowMapper) {
        this.jdbcTemplate = temp;
        this.pRM = postRowMapper;
    }

    @Override
    public List<Post> selectAllPosts() {
        var sql = """
                SELECT *
                from POST
                LIMIT 1000
                """;

        return jdbcTemplate.query(sql, pRM);
    }

    @Override
    public Optional<Post> selectPostById(UUID id) {
        var sql = """
                SELECT *
                from post
                Where id = ?
                """;

        return jdbcTemplate.query(sql, pRM, id).stream().findFirst();
    }

    @Override
    public List<Post> selectAllPostsByCategory(String category) {
        var sql = """
                Select *
                from post
                where category = ?
                LIMIT 1000
                """;

        return jdbcTemplate.query(sql, pRM, category);
    }

    @Override
    public boolean checkIfPostExists(UUID id) {
        var sql = """
                Select count(id)
                from post
                where id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    @Override
    public void addPost(Post post) {
        System.out.println("post: " + post.toString());
        var sql = """
                INSERT INTO POST(id, username, image_id, category, item_type, title, description, location, price)
                VALUES (?,?,?,?,?,?,?,?,?)
                """;

        System.out.println(post.toString());
        int result = jdbcTemplate.update(
                sql,
                post.getId(),
                post.getUsername(),
                post.getImageId(),
                post.getCategory(),
                post.getItemType(),
                post.getTitle(),
                post.getDescription(),
                post.getLocation(),
                post.getPrice());

        System.out.println("customer added result: " + result);
    }

    @Override
    public void deletePost(UUID id) {
        var sql = """
                DELETE FROM POST
                where ID=?
                """;

        int result = jdbcTemplate.update(sql, id);
        System.out.println("deletePost result = " + result);
    }

    @Override
    public void editPost(Post post) {
        if (post.getCategory() != null) {
            String sql = "UPDATE post set category = ? where id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    post.getCategory(),
                    post.getId());

            System.out.println("update post category result " + result);
        }

        if (post.getItemType() != null) {
            String sql = "UPDATE post set item_type = ? where id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    post.getItemType(),
                    post.getId());

            System.out.println("update post item type result " + result);
        }

        if (post.getImageId() != null) {
            String sql = "Update post set image_url = ? where id = ?";

            int result = jdbcTemplate.update(sql, post.getImageId(), post.getId());

            System.out.println("update post image url result " + result);
        }

        if (post.getTitle() != null) {
            String sql = "UPDATE post set title = ? where id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    post.getTitle(),
                    post.getId());

            System.out.println("update post title result " + result);
        }

        if (post.getDescription() != null) {
            String sql = "UPDATE post set description = ? where id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    post.getDescription(),
                    post.getId());

            System.out.println("update post description result " + result);
        }

        if (post.getLocation() != null) {
            String sql = "UPDATE post set location = ? where id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    post.getLocation(),
                    post.getId());

            System.out.println("update post location result " + result);
        }

        if (post.getPrice() != null) {
            String sql = "UPDATE post set price = ? where id = ?";

            int result = jdbcTemplate.update(
                    sql,
                    post.getPrice(),
                    post.getId());

            System.out.println("update post price result " + result);
        }
    }

    @Override
    public List<Post> selectAllPostsByUsername(String username) {
        var sql = """
                SELECT * FROM POST
                Where username = ?
                """;

        return jdbcTemplate.query(sql, pRM, username);

    }

    @Override
    public void updatePostImageId(UUID id, UUID postId) {
        var sql = """
                Update post
                Set image_id = ?
                where id = ?
                """;
        jdbcTemplate.update(sql, id, postId);
    }

}
