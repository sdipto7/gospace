CREATE TABLE payment
(
    id               INT          NOT NULL AUTO_INCREMENT,
    created          DATETIME     NOT NULL,
    reference_number VARCHAR(256) NOT NULL,
    amount           DECIMAL(19, 10),
    payment_method   VARCHAR(256) NOT NULL,
    payment_status   VARCHAR(256) NOT NULL,
    transaction_time DATETIME     NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_payment_reference_number UNIQUE (reference_number)
);
