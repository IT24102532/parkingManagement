package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;

import java.time.LocalDate;
import java.util.List;

public interface ParkingSlotDao extends DAO<ParkingSlot> {
    List<ParkingSlot> getAllSlotsByLocation(String location);
    List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate, String location);
}
