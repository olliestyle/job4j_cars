create table users
(
    id serial primary key ,
    name varchar(255) not null ,
    password varchar(255) not null ,
    email varchar(255) not null
);

create table carBrands
(
    id serial primary key ,
    carBrand varchar(255) not null
);

create table bodyTypes
(
    id serial primary key ,
    bodyType varChar(255) not null
);

create table transmissions
(
    id serial primary key ,
    transmission varchar(255) not null
);

create table carModels
(
    id serial primary key ,
    carModel varchar(255) not null ,
    carBrand_id int references carBrands(id) ,
    bodyType_id int references bodyTypes(id)
);

create table carModels_transmissions
(
    id serial primary key ,
    carModel_id int references carModels(id),
    transmission_id int references transmissions(id)
);

create table carAds
(
    id serial primary key ,
    description varchar(255) not null ,
    price numeric not null ,
    photo varchar(255) not null ,
    isSold boolean not null ,
    manufactureYear int not null ,
    mileage int not null ,
    user_id int references users(id) ,
    carBrand_id int references carBrands(id) ,
    carModel_id int references carModels(id) ,
    bodyType_id int references bodyTypes(id) ,
    transmission_id int references transmissions(id)
);

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

