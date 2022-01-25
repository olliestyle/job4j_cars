create table drivers
(
    id serial primary key,
    name varchar(30) not null,
    surname varchar(30) not null
);

create table engines
(
    id serial primary key,
    name varchar(30) not null
);

create table cars
(
    id serial primary key,
    brand varchar(30) not null ,
    model varchar(30) not null ,
    engine_id int references engines(id)
);

create table history_owner
(
    id serial primary key,
    driver_id int references drivers(id),
    car_id int references cars(id)
);

