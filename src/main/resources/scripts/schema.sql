DROP TABLE IF EXISTS book;
DROP TABLE  IF EXISTS hibernate_sequence;
  create table book (
       id bigint not null,
        isbn varchar(255),
        publisher varchar(255),
        title varchar(255),
        primary key (id)
    ) engine=InnoDB;

      create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;

    insert into hibernate_sequence values ( 1 );