create table if not exists operating_systems
(
    id      serial4 primary key,
    name    varchar(255) not null,
    version varchar(10)  not null
);

create table if not exists cloud_instances
(
    id                serial4 primary key,
    public_ip_address varchar(15)  not null unique,
    instance_name     varchar(255) not null,
    os_id             integer references operating_systems (id)
);

