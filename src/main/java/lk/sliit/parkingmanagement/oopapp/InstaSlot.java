package lk.sliit.parkingmanagement.oopapp;

public class InstaSlot extends ParkingSlot{
    private double pricePerHour;
    private int maxDurationHours = 1;
    private double overStayCharge;

    public InstaSlot() {}

    public InstaSlot(double pricePerHour, int maxDurationHours, double overStayCharge) {
        this.pricePerHour = pricePerHour;
        this.maxDurationHours = maxDurationHours;
        this.overStayCharge = overStayCharge;
    }

    public double getPricePerHour() {return pricePerHour;}
    public void setPricePerHour(double pricePerHour) {this.pricePerHour = pricePerHour;}

    public int getMaxDurationHours() {return maxDurationHours;}
    public void setMaxDurationHours(int maxDurationHours) {this.maxDurationHours = maxDurationHours;}

    public double getOverStayCharge() {return overStayCharge;}
    public void setOverStayCharge(double overStayCharge) {this.overStayCharge = overStayCharge;}

    @Override
    public String toString() {
        return "InstaSlot{" +
                "pricePerHour=" + pricePerHour +
                ", maxDurationHours=" + maxDurationHours +
                ", overStayCharge=" + overStayCharge +
                '}';
    }
}
