package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.Booking;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A tangible implementation of the BookingDao interface that uses JSON file storage to offer
 * data access operations for Booking entities.
 * All CRUD functions for parking reservations are managed by this class.
 */


public class BookingDaoImpl implements BookingDao {
    private final Logger LOGGER = Logger.getLogger(BookingDaoImpl.class.getName());
    private final String filePath = FileConfig.INSTANCE.getBookingsPath();
    JsonHelper<Booking> bookingJsonHelper = new JsonHelper<>(filePath, Booking.class);
    /** * Gets every booking record out of the JSON storage.

     * @return List of all Booking objects, or null in the event of an error
     * */


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
    /** * Locates a reservation using its special code.

     * @param id The booking ID to look up (case-insensitive)
     *  * @return * @throws Exception The matching Booking object, or null if it cannot be found or an error occurs In the event that a critical error arises during the operation */


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
    /** * Returns an empty list upon error; retrieves all bookings (same as getAllBookings()).

     * @throws Exception * @return List of all Booking objects, empty list if none found or error occurs In the event that a critical error arises during the operation */

    @Override
    public List<Booking> findAll() throws Exception {
        try {
            return bookingJsonHelper.readAll();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading transactions", e);
            return Collections.emptyList();
        }
    }
    /**
     * Creates a new booking record in the JSON storage.
     *
     * param object The Booking object to be created
     * throws Exception If the operation fails
     */

    @Override
    public void create(Booking object) throws Exception {
        try {
            bookingJsonHelper.create(object);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating booking", e);
        }
    }
    /** * Modifies an existing reservation record (not yet implemented).

     * param object The Booking object that needs to be modified * @throwsException If the process is unsuccessful */

    @Override
    public void update(Booking object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
}
