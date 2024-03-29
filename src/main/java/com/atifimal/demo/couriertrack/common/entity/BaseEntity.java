package com.atifimal.demo.couriertrack.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Column(name = "creation_date")
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    @Column(name = "update_date")
    private LocalDateTime updateDateTime;

    @Column(name = "is_deleted")
    private Boolean deleted = Boolean.FALSE;
}
