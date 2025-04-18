package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.Booking;

import java.util.List;

public interface BookingDao extends DAO<Booking> {
    List<Booking> getAllBookings();
}
