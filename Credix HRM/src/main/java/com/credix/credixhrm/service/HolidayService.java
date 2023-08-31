package com.credix.credixhrm.service;


import com.credix.credixhrm.model.Holiday;

import java.util.List;

public interface HolidayService {


    List<Holiday> getAllHolidays();

    List<Holiday> getAllPendingHolidays();

    List<Holiday> getAllAcceptedHolidays();

    List<Holiday> getAllRefusedHolidays();

    Holiday getHolidayById(Integer id);

    Holiday holidayRequest(Holiday holiday, Integer employeeId);

    Holiday updateHoliday(Integer id, Holiday updatedHoliday);

    void deleteHoliday(Integer id);

    Holiday acceptHoliday(Integer id);

    Holiday refuseHoliday(Integer id);
}
