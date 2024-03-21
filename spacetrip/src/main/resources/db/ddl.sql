CREATE TABLE space_craft
(
    id                 INT          NOT NULL AUTO_INCREMENT,
    version            INT          NOT NULL,
    created            DATETIME     NOT NULL,
    updated            DATETIME NULL,
    name               VARCHAR(256) NOT NULL,
    manufacturer       VARCHAR(256) NOT NULL,
    manufacture_date   DATETIME     NOT NULL,
    crew_capacity      INT          NOT NULL,
    passenger_capacity INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_space_craft_name UNIQUE (name)
);

CREATE TABLE space_trip
(
    id                     INT            NOT NULL AUTO_INCREMENT,
    version                INT            NOT NULL,
    created                DATETIME       NOT NULL,
    updated                DATETIME NULL,
    space_craft_id         INT            NOT NULL,
    departure_time         DATETIME       NOT NULL,
    estimated_arrival_time DATETIME       NOT NULL,
    ticket_price           DECIMAL(10, 2) NOT NULL,
    total_seats            INT            NOT NULL,
    available_seats        INT            NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_space_trip_space_craft_id UNIQUE (space_craft_id),
    CONSTRAINT fk_space_trip_space_craft_id FOREIGN KEY (space_craft_id) REFERENCES space_craft (id)
);