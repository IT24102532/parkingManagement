package lk.sliit.parkingmanagement.oopapp.model;

public class LongTermSlot extends ParkingSlot {
    private double pricePerDay;
    private double overstayCharge;

    public LongTermSlot() {}

    public LongTermSlot(double pricePerDay, double overstayCharge) {
        this.pricePerDay = pricePerDay;
        this.overstayCharge = overstayCharge;
    }

    public double getPricePerDay() {return pricePerDay;}
    public double getOverstayCharge() {return overstayCharge;}

    public void setPricePerDay(double pricePerDay) {this.pricePerDay = pricePerDay;}
    public void setOverstayCharge(double overstayCharge) {this.overstayCharge = overstayCharge;}

    @Override
    public String toString() {
        return super.toString() + " LongTermSlot{" +
                "pricePerDay=" + pricePerDay +
                ", overstayCharge=" + overstayCharge +
                '}';
    }
}
