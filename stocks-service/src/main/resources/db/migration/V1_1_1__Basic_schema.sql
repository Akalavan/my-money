create schema if not exists assets;
create table assets.stocks
(
    id         serial primary key,
    name       varchar(100) not null check ( length(trim(name)) >= 3),
    short_name varchar(20)  not null check ( length(trim(name)) >= 1),
    amount     numeric(12, 2)
);