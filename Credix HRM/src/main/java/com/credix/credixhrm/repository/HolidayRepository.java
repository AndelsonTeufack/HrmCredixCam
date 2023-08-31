package com.credix.credixhrm.repository;

import com.credix.credixhrm.enumm.Status;
import com.credix.credixhrm.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Integer> {

    List<Holiday> findByStatus(Status status);
}
