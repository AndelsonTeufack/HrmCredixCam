package com.credix.credixhrm.repository;

import com.credix.credixhrm.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
