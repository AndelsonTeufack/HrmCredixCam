package com.credix.credixhrm.model;


import com.credix.credixhrm.enumm.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Holiday")
public class Holiday {
    @Id
    @GeneratedValue
    private Integer idHoliday;

    private Date startDate;

    private Date endDate;

    private Status status = Status.PENDING;

    private String Description;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
