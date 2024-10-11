CREATE TABLE IF NOT EXISTS space_craft
(
    id                 INT          NOT NULL AUTO_INCREMENT,
    version            INT          NOT NULL,
    created            DATETIME     NOT NULL,
    updated            DATETIME     NULL,
    name               VARCHAR(256) NOT NULL,
    manufacturer       VARCHAR(256) NOT NULL,
    manufacture_date   DATETIME     NOT NULL,
    crew_capacity      INT          NOT NULL,
    passenger_capacity INT          NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_space_craft_name UNIQUE (name)
);
