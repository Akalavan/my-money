create schema if not exists budget;
create table budget.type_operation
(
    id          serial primary key,
    name        varchar(45) not null check ( length(trim(name)) >= 3),
    description varchar(255)
);

create table budget.categories
(
    id          serial primary key,
    name        varchar(45) not null check ( length(trim(name)) >= 2),
    description varchar(255)
);

create table budget.monetary_transactions
(
    id                serial primary key,
    name              varchar(255)   not null check ( length(trim(name)) >= 2),
    description       varchar(255),
    amount            numeric(12, 2) not null,
    categories_id     int            not null references budget.categories (id),
    type_operation_id int            not null references budget.type_operation (id),
    date_operation    timestamp      not null
)