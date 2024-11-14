package com.example.boardproj.entity.base;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {


    @CreatedDate
    @Column(updatable = false)
    private LocalDate regdate;

    @LastModifiedDate
    private LocalDate updatedate;



}
