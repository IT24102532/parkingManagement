package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.io.IOException;
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
    public Booking getById(int id) throws Exception {
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
        return List.of();
    }

    @Override
    public void create(Booking object) throws Exception {

    }

    @Override
    public void update(Booking object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
