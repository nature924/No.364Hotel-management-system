package cn.cow.hotel.entity;

public class OccupancyRate {
    private int month;
    private double occupancyRate;

    public OccupancyRate() {
    }

    public OccupancyRate(int month, double occupancyRate) {
        this.month = month;
        this.occupancyRate = occupancyRate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public double getOccupancyRate() {
        return occupancyRate;
    }

    public void setOccupancyRate(double occupancyRate) {
        this.occupancyRate = occupancyRate;
    }
}
