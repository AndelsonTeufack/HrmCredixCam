package com.credix.credixhrm.controller;

import com.credix.credixhrm.model.Announcement;
import com.credix.credixhrm.model.Response;
import com.credix.credixhrm.service.AnnouncementService;
import com.credix.credixhrm.utils.EmployeeUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static com.credix.credixhrm.constents.EmployeeConstants.SOMETHING_WENT_WRONG;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/announcements/manage")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @GetMapping("/getAllAnnouncements")
    public ResponseEntity<Response> getAllAnnouncements() {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Announcements: ", announcementService.getAllAnnouncements()))
                            .message("List of all Announcements")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        return EmployeeUtils.getResponseEntityG(SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/getAnnouncementById/{id}")
    public ResponseEntity<Response> getAnnouncementById(@PathVariable Integer id) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Announcement: ", announcementService.getAnnouncementById(id)))
                            .message("Announcement whith id: "+id)
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Announcement not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{employeeId}/createAnnouncement")
    public ResponseEntity<Response> createAnnouncement(@RequestBody Announcement announcement, @PathVariable Integer employeeId) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Announcement: ", announcementService.createAnnouncement(announcement, employeeId)))
                            .message("Announcement successfully created.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Employee not found with ID: " + employeeId , HttpStatus.NOT_FOUND);
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("you cannot create an announcement without a title or content", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{idEmployee}/updateAnnouncementById/{id}")
    public ResponseEntity<Response> updateAnnouncement(@PathVariable Integer id, @RequestBody Announcement updatedAnnouncement,
                                           @PathVariable Integer idEmployee) {

        try {
            return ResponseEntity.ok(
                    Response.builder()
                            .timeStamp(now())
                            .data(of("Announcement: ", announcementService.updateAnnouncement(id, updatedAnnouncement, idEmployee)))
                            .message("Announcement successfully updated.")
                            .status(OK)
                            .statusCode(OK.value())
                            .build()
            );
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("nothing was found with the id provided " , HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAnnouncementById/{id}")
    public ResponseEntity<Response> deleteAnnouncement(@PathVariable Integer id) {

        try {
            announcementService.deleteAnnouncement(id);
            return EmployeeUtils.getResponseEntityG("Announcement successfully deleted.", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            e.printStackTrace();
            return EmployeeUtils.getResponseEntityG("Announcement not found with ID: " + id , HttpStatus.NOT_FOUND);
        }
    }
}
