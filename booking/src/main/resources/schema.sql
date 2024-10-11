CREATE TABLE IF NOT EXISTS booking (
    id               INT          NOT NULL AUTO_INCREMENT,
    version          INT          NOT NULL,
    created          DATETIME     NOT NULL,
    updated          DATETIME,
    trip_id          INT          NOT NULL,
    reference_number VARCHAR(256) NOT NULL,
    passenger_name   VARCHAR(256) NOT NULL,
    passenger_email  VARCHAR(256) NOT NULL,
    passenger_phone  VARCHAR(256) NOT NULL,
    total_seats      INT          NOT NULL,
    total_price      DECIMAL(19, 10),
    booking_status   VARCHAR(256) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_booking_reference_number UNIQUE (reference_number)
);
