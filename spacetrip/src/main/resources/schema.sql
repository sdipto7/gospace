CREATE TABLE IF NOT EXISTS space_trip (
    id                     INT            NOT NULL AUTO_INCREMENT,
    version                INT            NOT NULL,
    created                DATETIME       NOT NULL,
    updated                DATETIME       NULL,
    destination_id         INT            NOT NULL,
    destination_name       VARCHAR(256)   NOT NULL,
    space_craft_id         INT            NOT NULL,
    space_craft_name       VARCHAR(256)   NOT NULL,
    departure_time         DATETIME       NOT NULL,
    estimated_arrival_time DATETIME       NOT NULL,
    ticket_price           DECIMAL(10, 2) NOT NULL,
    total_seats            INT            NOT NULL,
    available_seats        INT            NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_space_trip_space_craft_id UNIQUE (space_craft_id)
);
