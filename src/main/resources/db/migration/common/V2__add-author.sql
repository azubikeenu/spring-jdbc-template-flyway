CREATE TABLE IF NOT EXISTS author
(
    id         BIGINT NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    PRIMARY KEY (id)
) engine = InnoDB;