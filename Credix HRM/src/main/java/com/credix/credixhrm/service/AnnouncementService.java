package com.credix.credixhrm.service;

import com.credix.credixhrm.model.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> getAllAnnouncements();

    Announcement getAnnouncementById(Integer id);

    Announcement createAnnouncement(Announcement announcement, Integer employeeId);

    Announcement updateAnnouncement(Integer id, Announcement updatedAnnouncement, Integer employeeId);

    void deleteAnnouncement(Integer id);

}
