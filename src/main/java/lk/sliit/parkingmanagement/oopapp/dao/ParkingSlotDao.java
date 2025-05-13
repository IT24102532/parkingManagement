package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface ParkingSlotDao extends DAO<ParkingSlot> {
    List<ParkingSlot> getAllSlotsByLocation(String location);
    List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate, String location);
    List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate);
    List getSlotDetails(Map query);
    void updateDates(String id, LocalDate startDate, LocalDate endDate);
    void updateHours(String id, LocalDateTime startTime, LocalDateTime endTime);
    List<ParkingSlot> getAvailableSlotsByHours(LocalDateTime startTime, LocalDateTime endTime);
    List<ParkingSlot> getAvailableSlotsByHours(LocalDateTime startTime, LocalDateTime endTime, String location);
}
