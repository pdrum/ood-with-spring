create table parcels
(
    id                          bigint primary key auto_increment,
    name                        varchar(200) not null,
    location_lat                float        not null,
    location_lng                float        not null,
    destination_lat             float        not null,
    destination_lng             float        not null,
    distance_to_destination     float        not null,
    distance_calculation_method varchar(200) not null
)