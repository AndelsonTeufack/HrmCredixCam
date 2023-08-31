package com.credix.credixhrm.service.impl;

import com.credix.credixhrm.enumm.Status;
import com.credix.credixhrm.model.Employee;
import com.credix.credixhrm.model.Holiday;
import com.credix.credixhrm.repository.EmployeeRepository;
import com.credix.credixhrm.repository.HolidayRepository;
import com.credix.credixhrm.service.EmployeeService;
import com.credix.credixhrm.service.HolidayService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Holiday> getAllHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public List<Holiday> getAllPendingHolidays() {
        return holidayRepository.findByStatus(Status.PENDING);
    }

    @Override
    public List<Holiday> getAllAcceptedHolidays() {
        return holidayRepository.findByStatus(Status.ACCEPTED);
    }

    @Override
    public List<Holiday> getAllRefusedHolidays() {
        return holidayRepository.findByStatus(Status.REFUSED);
    }

    @Override
    public Holiday getHolidayById(Integer id) {
        return holidayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday not found with ID: " + id));
    }

    @Override
    public Holiday holidayRequest(Holiday holiday, Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + employeeId));

        if (holiday.getDescription() == null || holiday.getStartDate() ==null || holiday.getEndDate() == null) {
            throw new DataIntegrityViolationException("You cannot create an employee with null fields");
        }
        holiday.setEmployee(employee);
        return holidayRepository.save(holiday);
    }

    @Override
    public Holiday updateHoliday(Integer id, Holiday updatedHoliday) {
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday not found with ID: " + id));
        holiday.setDescription(updatedHoliday.getDescription());
        holiday.setStatus(updatedHoliday.getStatus());
        holiday.setStartDate(updatedHoliday.getStartDate());
        holiday.setEndDate(updatedHoliday.getEndDate());

        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteHoliday(Integer id) {
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday not found with ID: " + id));
        holidayRepository.delete(holiday);
    }

    @Override
    public Holiday acceptHoliday(Integer id) {
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday not found with ID: " + id));

        if (holiday.getStatus() == Status.PENDING){
            holiday.setStatus(Status.ACCEPTED);
            return holidayRepository.save(holiday);
        } throw new IllegalArgumentException("You can only accept pending requests.");
    }

    @Override
    public Holiday refuseHoliday(Integer id) {
        Holiday holiday = holidayRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Holiday not found with ID: " + id));

        if (holiday.getStatus() == Status.PENDING){
            holiday.setStatus(Status.REFUSED);
            return holidayRepository.save(holiday);
        } throw new IllegalArgumentException("You can only accept pending requests.");
    }

}
