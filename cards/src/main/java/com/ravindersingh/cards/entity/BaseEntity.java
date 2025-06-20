package com.ravindersingh.cards.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public class BaseEntity {
    private LocalDate createdAt;
    private LocalDate createdBy;
    private LocalDate updatedAt;
    private LocalDate updatedBy;
}
