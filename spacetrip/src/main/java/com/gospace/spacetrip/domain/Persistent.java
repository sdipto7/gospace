package com.gospace.spacetrip.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author rumidipto
 * @since 3/18/24
 */
@Getter
@Setter
@MappedSuperclass
public class Persistent implements Serializable {

    @Version
    private int version;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updated;

    @PrePersist
    private void onCreate() {
        created = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updated = LocalDateTime.now();
    }
}