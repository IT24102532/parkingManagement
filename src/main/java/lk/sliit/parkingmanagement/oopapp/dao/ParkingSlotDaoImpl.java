package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.InstaSlot;
import lk.sliit.parkingmanagement.oopapp.model.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/** * ParkingSlotDao interface implementation for parking slot management manages both
 *  hourly InstaSlots and daily LongTermSlots parking.
 */

public class ParkingSlotDaoImpl implements ParkingSlotDao {
    // Logger for tracking errors and information
    private final Logger LOGGER = Logger.getLogger(ParkingSlotDaoImpl.class.getName());
    // File path for storing parking slot data
    private final String filePath = FileConfig.INSTANCE.getSlotPath();
    // Helper for JSON operations on ParkingSlot objects
    JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);

    /** * Returns every parking space for a given address.
     * param location The address by which slots should be filtered
     * * @return List of ParkingSlot objects that match the address, or null if an error occurs
     **/

    @Override
    public List<ParkingSlot> getAllSlotsByLocation(String location) {
        try {
            return slotJsonHelper.findAll(

                    s -> s.getLocation().equalsIgnoreCase(location)
            );
        }
        catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error finding locations @getAllSlotsByLocation", ex);
            return null;
        }
    }

    /** * Locates long-term slots at a given location for a range of dates
     * * startDate The desired period's start date
     * endDate The desired period's end date
     * *  location Where to use
     *return to filter slots List of LongTermSlot objects that are available,
     * or an empty list in the event of an error
     * */


    /** Find available long-term slots for date range at location**/
    @Override
    public List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate, String location) {
        try {
            return findAll().stream()
                    .filter(slot -> slot instanceof LongTermSlot)
                    .map(slot -> (LongTermSlot) slot)
                    .filter(LongTermSlot::isAvailable)
                    .filter(slot -> slot.getLocation().equalsIgnoreCase(location))
                    .filter(slot -> isSlotAvailable(slot, startDate, endDate))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error getting available long-term slots by dates", ex);
            return Collections.emptyList();
        }
    }


    /** Find available hourly slots for time range at location**/
    @Override
    public List<ParkingSlot> getAvailableSlotsByHours(LocalDateTime startTime, LocalDateTime endTime, String location) {
        try {
            return findAll().stream()
                    .filter(slot -> slot instanceof InstaSlot && slot.isAvailable())
                    .map(slot -> (InstaSlot) slot)
                    .filter(InstaSlot::isAvailable)
                    .filter(slot -> slot.getLocation().equalsIgnoreCase(location))
                    .filter(slot -> isSlotAvailableByHour(slot, startTime, endTime))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error getting available InstaSlots by hours", ex);
            return Collections.emptyList();
        }
    }

    @Override
    public List<ParkingSlot> getAvailableSlotsByHours(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            return findAll().stream()
                    .filter(slot -> slot instanceof InstaSlot && slot.isAvailable())
                    .map(slot -> (InstaSlot) slot)
                    .filter(slot -> isSlotAvailableByHour(slot, startTime, endTime))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error getting available InstaSlots by hours", ex);
            return Collections.emptyList();
        }
    }

    /** Find available hourly slots (all locations)**/
    @Override
    public List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate) {
        try {
            return findAll().stream()
                    .filter(slot -> slot instanceof LongTermSlot && slot.isAvailable())
                    .map(slot -> (LongTermSlot) slot)
                    .filter(slot -> isSlotAvailable(slot, startDate, endDate))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error getting available long-term slots by dates", ex);
            return Collections.emptyList();
        }
    }
    /**Find slots matching query parameters**/
    @Override
    public List getSlotDetails(Map query) {
       return  slotJsonHelper.findByFields(query);
    }
     /**Update booked dates for a slot**/
    @Override
    public void updateDates(String id, LocalDate startDate, LocalDate endDate) {
        List<String> newDates = new ArrayList<>();
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            newDates.add(current.toString());
            current = current.plusDays(1);
        }

        slotJsonHelper.partialUpdate(
                slot -> slot.getSlotId().equals(id),
                Map.of("bookedDates", newDates)
        );
    }
    /**Update booked hours for a slot**/
    @Override
    public void updateHours(String id, LocalDateTime startTime, LocalDateTime endTime) {
        List<String> newTimes = new ArrayList<>();
        LocalDateTime current = startTime;

        while (!current.isAfter(endTime)) {
            newTimes.add(current.toString());
            current = current.plusHours(1);
        }

        slotJsonHelper.partialUpdate(
                slot -> slot.getSlotId().equals(id),
                Map.of("bookedTimes", newTimes)
        );
    }
    // Get slot by ID
    @Override
    public ParkingSlot getById(String id) throws Exception {
        try {
            return slotJsonHelper.findOne(
                    s -> s.getSlotId().equalsIgnoreCase(id)
            );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error finding id @getById", e);
            return null;
        }
    }
/**Get all slots**/

    @Override
    public List<ParkingSlot> findAll() throws Exception {
        try {
            return slotJsonHelper.readAll();
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading slot file", e);
            return null;
        }
    }

    @Override
    public void create(ParkingSlot object) throws Exception {
        try {
            slotJsonHelper.create(object);
        }
        catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error creating object @create", e);
        }
    }

    @Override
    public void update(ParkingSlot object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }
    /** Check if long-term slot is available for dates**/
    private boolean isSlotAvailable(LongTermSlot slot, LocalDate startDate, LocalDate endDate) {
        for (String booked : slot.getBookedDates()) {
            LocalDate bookedDate = LocalDate.parse(booked.substring(0, 10));
            if (!startDate.isAfter(bookedDate) && !endDate.isBefore(bookedDate)) {
                return false;
            }
        }
        return true;
    }
    /**Check if hourly slot is available for times**/
    private boolean isSlotAvailableByHour(InstaSlot slot, LocalDateTime startTime, LocalDateTime endTime) {
        Set<String> bookedTimes = slot.getBookedTimes() != null ? new HashSet<>(slot.getBookedTimes()) : Set.of();

        LocalDateTime current = startTime;
        while (!current.isAfter(endTime)) {
            String timeStr = current.toString();
            if (bookedTimes.contains(timeStr)) {
                return false;
            }
            current = current.plusHours(1);
        }
        return true;
    }

}
