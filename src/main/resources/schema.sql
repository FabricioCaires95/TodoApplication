DROP TABLE IF EXISTS TODO;
CREATE TABLE TODO (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(255),
    deadline date,
    is_finished boolean
);