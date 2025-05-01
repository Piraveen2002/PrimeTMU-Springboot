CREATE SEQUENCE post_id_seq;

CREATE TABLE post (
    id UUID PRIMARY KEY,
    user_id UUID,
    image_id UUID,
    category VARCHAR(10),
    item_type VARCHAR(10),
    title VARCHAR(50),
    description VARCHAR(256),
    location VARCHAR(256),
    price DECIMAL
);

CREATE SEQUENCE user_id_seq;
CREATE TABLE client (
    id UUID PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    username VARCHAR(100),
    password VARCHAR(100)
);