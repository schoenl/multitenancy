create table car
(
    id            bigint generated always as identity primary key,
    version       bigint                              not null,
    inserted      timestamp default CURRENT_TIMESTAMP not null,
    last_modified timestamp default CURRENT_TIMESTAMP not null,
    brand         varchar(255)                        not null,
    model         varchar(255)                        not null,
    color         varchar(255)                        not null
);