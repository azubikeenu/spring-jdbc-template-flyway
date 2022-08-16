drop table if exists book;
drop table if exists author;

create table book
(
    id  BIGINT not null AUTO_INCREMENT,
    isbn      varchar(255),
    publisher varchar(255),
    title     varchar(255),
    author_id BIGINT,
    primary key (id)
);

create table author
(
    id  BIGINT not null AUTO_INCREMENT,
    first_name varchar(255),
    last_name  varchar(255),
    primary key (id)
);