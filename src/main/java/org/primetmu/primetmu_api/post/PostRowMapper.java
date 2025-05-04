package org.primetmu.primetmu_api.post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class PostRowMapper implements RowMapper<Post> {
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
        UUID image_id = null;
        String temp = rs.getString("image_id");

        if (temp != null) {
            image_id = UUID.fromString(temp);
        }

        return new Post(
                UUID.fromString(rs.getString("id")),
                rs.getString("username"),
                image_id,
                rs.getString("category"),
                rs.getString("item_type"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("location"),
                rs.getBigDecimal("price"));
    }
}
