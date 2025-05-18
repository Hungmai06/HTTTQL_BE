package com.example.library.models;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DateBase {
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Instant createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Instant updatedAt;

}
