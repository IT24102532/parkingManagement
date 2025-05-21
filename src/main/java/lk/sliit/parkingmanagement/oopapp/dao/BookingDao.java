package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.model.Booking;

import java.util.List;

/**
 * /**
 * * Retrieves all admin logs from the storage.
 * *
 ** return List of all AdminLogs objects, empty list if none found or error occurs
 * * throws Exception If any error occurs during the operation
 **/
 /** * Booking entities' Data Access Object (DAO) interface uses Booking as the type parameter to extend the generic DAO interface.
  *
  *  * and specifies extra operations unique to bookings. * p>Along with specific booking queries, this interface offers CRUD (Create, Read, Update, Delete) operations
  *  * for the system's booking entities.
  *  */


public interface BookingDao extends DAO<Booking> {
     /**
      * Retrieves all booking records from the data store.
      *
      * return A list containing all Booking entities in the system.
      *         Returns an empty list if no bookings exist.
      *
      * implNote This method differs from the standard findAll() in that it may
      *           implement booking-specific filtering or sorting logic.
      */
    List<Booking> getAllBookings();
}
