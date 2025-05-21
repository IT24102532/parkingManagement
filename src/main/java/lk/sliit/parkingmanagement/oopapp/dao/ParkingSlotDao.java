package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 ** ParkingSlot operations via the DAO interface
 **/

public interface ParkingSlotDao extends DAO<ParkingSlot> {
    // Get all parking slots at a specific location
    List<ParkingSlot> getAllSlotsByLocation(String location);
    // Get available slots between dates at a specific location
    List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate, String location);
    // Get available slots between dates
    List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate);
    // Get available slots between hours at specific location
    List getSlotDetails(Map query);
    void updateDates(String id, LocalDate startDate, LocalDate endDate);
    void updateHours(String id, LocalDateTime startTime, LocalDateTime endTime);
    List<ParkingSlot> getAvailableSlotsByHours(LocalDateTime startTime, LocalDateTime endTime);
    List<ParkingSlot> getAvailableSlotsByHours(LocalDateTime startTime, LocalDateTime endTime, String location);
}
