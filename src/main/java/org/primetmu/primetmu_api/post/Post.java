package org.primetmu.primetmu_api.post;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "post", uniqueConstraints = {
        @UniqueConstraint(name = "post_id_unique", columnNames = "id")
})
public class Post {
    @Id
    @SequenceGenerator(name = "post_id_seq", sequenceName = "post_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_id_seq")
    private UUID id;
    @Column(nullable = false)
    private String username;
    @Column(name = "image_id")
    private UUID imageId;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String itemType;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private BigDecimal price;

    public Post(UUID id, String username, UUID imageId, String category, String itemType, String title,
            String description, String location, BigDecimal price) {
        this.id = id;
        this.username = username;
        this.imageId = imageId;
        this.category = category;
        this.itemType = itemType;
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
    }

    public Post(String username, UUID imageId, String category, String itemType, String title, String description,
            String location,
            BigDecimal price) {
        this.username = username;
        this.imageId = imageId;
        this.category = category;
        this.itemType = itemType;
        this.title = title;
        this.description = description;
        this.location = location;
        this.price = price;
    }

    public Post() {
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID new_id) {
        this.id = new_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUserId(String name) {
        this.username = name;
    }

    public UUID getImageId() {
        return this.imageId;
    }

    public void setImageId(UUID id) {
        this.imageId = id;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String newCategory) {
        if (newCategory.length() > 10) {
            throw new OutOfMemoryError("Too long!");
        }

        this.category = newCategory;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        if (itemType.length() > 10) {
            throw new OutOfMemoryError("Too long!");
        }

        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (itemType.length() > 50) {
            throw new OutOfMemoryError("Too long!");
        }

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.substring(0, 50);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location.substring(0, 256);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
