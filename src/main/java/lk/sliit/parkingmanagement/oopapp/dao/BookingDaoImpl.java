package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookingDaoImpl implements BookingDao {
    private final Logger LOGGER = Logger.getLogger(BookingDaoImpl.class.getName());
    private final String filePath = FileConfig.INSTANCE.getBookingsPath();
    JsonHelper<Booking> bookingJsonHelper = new JsonHelper<>(filePath, Booking.class);

    @Override
    public List<Booking> getAllBookings() {
        try {
            return bookingJsonHelper.readAll();
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error reading the booking data", ex);
            return null;
        }
    }


    @Override
    public Booking getById(String id) throws Exception {
        try {
            return bookingJsonHelper.findOne(
                    b -> b.getBookingId().equalsIgnoreCase(String.valueOf(id))
            );
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding booking", e);
            return null;
        }
    }

    @Override
    public List<Booking> findAll() throws Exception {
        try {
            return bookingJsonHelper.readAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading transactions", e);
            return Collections.emptyList();
        }
    }

    @Override
    public void create(Booking object) throws Exception {
        try {
            bookingJsonHelper.create(object);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating booking", e);
        }
    }

    @Override
    public void update(Booking object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
