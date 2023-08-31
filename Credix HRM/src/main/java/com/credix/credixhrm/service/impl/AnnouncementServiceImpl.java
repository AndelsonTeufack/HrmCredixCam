package com.credix.credixhrm.service.impl;

import com.credix.credixhrm.model.Announcement;
import com.credix.credixhrm.model.Employee;
import com.credix.credixhrm.repository.AnnouncementRepository;
import com.credix.credixhrm.repository.EmployeeRepository;
import com.credix.credixhrm.service.AnnouncementService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    @Override
    public Announcement getAnnouncementById(Integer id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found with ID: " + id));
    }

    @Override
    public Announcement createAnnouncement(Announcement announcement, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));
        announcement.setEmployee(employee);

        if(announcement.getContent() != null || announcement.getTitle()!=null){
            return announcementRepository.save(announcement);
        }throw new DataIntegrityViolationException("you cannot create an announcement without a title or content");
    }

    @Override
    public Announcement updateAnnouncement(Integer id, Announcement updatedAnnouncement, Integer employeeId) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found with ID: " + id));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        announcement.setEmployee(employee);
        announcement.setTitle(updatedAnnouncement.getTitle());
        announcement.setContent(updatedAnnouncement.getContent());

        return announcementRepository.save(announcement);
    }

    @Override
    public void deleteAnnouncement(Integer id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Announcement not found with ID: " + id));

        announcementRepository.delete(announcement);
    }

}
