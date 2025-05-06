package lk.sliit.parkingmanagement.oopapp.dao;

import lk.sliit.parkingmanagement.oopapp.config.FileConfig;
import lk.sliit.parkingmanagement.oopapp.model.LongTermSlot;
import lk.sliit.parkingmanagement.oopapp.model.ParkingSlot;
import lk.sliit.parkingmanagement.oopapp.utils.JsonHelper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ParkingSlotDaoImpl implements ParkingSlotDao {
    private final Logger LOGGER = Logger.getLogger(ParkingSlotDaoImpl.class.getName());
    private final String filePath = FileConfig.INSTANCE.getSlotPath();
    JsonHelper<ParkingSlot> slotJsonHelper = new JsonHelper<>(filePath, ParkingSlot.class);

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

    @Override
    public List<ParkingSlot> getAvailableSlotsByDates(LocalDate startDate, LocalDate endDate, String location) {
        try {
            return findAll().stream()
                    .filter(slot -> slot instanceof LongTermSlot)
                    .map(slot -> (LongTermSlot) slot)
                    .filter(LongTermSlot::isAvailable)
                    .filter(slot -> isSlotAvailable(slot, startDate, endDate))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Error getting available long-term slots by dates", ex);
            return Collections.emptyList();
        }
    }

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

    }

    @Override
    public void update(ParkingSlot object) throws Exception {

    }

    @Override
    public void delete(int id) throws Exception {

    }

    private boolean isSlotAvailable(LongTermSlot slot, LocalDate startDate, LocalDate endDate) {
        for (String booked : slot.getBookedDates()) {
            LocalDate bookedDate = LocalDate.parse(booked.substring(0, 10));
            if (!startDate.isAfter(bookedDate) && !endDate.isBefore(bookedDate)) {
                return false;
            }
        }
        return true;
    }
}
