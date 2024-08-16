create table cities (
    id bigint not null auto_increment,
    name varchar(255),
    primary key (id)
);

create table sessions (
    id bigint not null auto_increment,
    active bit,
    current_city varchar(255),
    primary key (id)
);

create table sessions_cities (
    game_session_id bigint not null,
    used_cities_id bigint not null,
    primary key (game_session_id, used_cities_id)
);

alter table sessions_cities add constraint fk_cities foreign key (used_cities_id) references cities (id);
alter table sessions_cities add constraint fk_sessions foreign key (game_session_id) references sessions (id);
