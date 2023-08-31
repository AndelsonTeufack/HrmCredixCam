package com.credix.credixhrm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@EntityListeners(AuditingEntityListener.class)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Announcement")
public class Announcement {
    @Id
    @GeneratedValue
    private Integer idAnnouncement;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
