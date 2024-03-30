package com.gospace.payment.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 3/30/24
 */
@Getter
@Setter
@ToString
@MappedSuperclass
public class Persistent implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    @PrePersist
    @PreUpdate
    private void onCreate() {
        created = LocalDateTime.now();
    }
}
