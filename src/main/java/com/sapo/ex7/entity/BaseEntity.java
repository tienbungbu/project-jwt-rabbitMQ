package com.sapo.ex7.entity;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "DATETIME default current_timestamp")
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:MM:ss")
    private Date createdDate;

    @Column(columnDefinition = "DATETIME on update current_timestamp")
    @DateTimeFormat(pattern = "dd-MM-yyyy hh:MM:ss")
    private Date updatedDate;
}
