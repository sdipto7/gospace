CREATE TABLE IF NOT EXISTS destination (
    id                  INT          NOT NULL AUTO_INCREMENT,
    version             INT          NOT NULL,
    created             DATETIME     NOT NULL,
    updated             DATETIME,
    name                VARCHAR(256) NOT NULL,
    celestial_body_type VARCHAR(256) NOT NULL,
    description         VARCHAR(2048),
    surface_features    VARCHAR(2048),
    atmosphere          VARCHAR(2048),
    distance_from_earth DECIMAL(60, 30),
    diameter            DECIMAL(60, 10),
    mass                DECIMAL(60, 10),
    gravity             DECIMAL(19, 10),
    minimum_temperature DECIMAL(10, 2),
    maximum_temperature DECIMAL(10, 2),
    PRIMARY KEY (id),
    CONSTRAINT uk_destination_name UNIQUE (name)
);
