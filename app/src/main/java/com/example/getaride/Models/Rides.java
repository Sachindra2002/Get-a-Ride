package com.example.getaride.Models;

public class Rides {

    String customerName, customerEmail, pickup, dropoff, drivername, vehicletype, time, status, date, customernumber, driverNumber, vehicleNumber, logStart, logEnd, price;

    public Rides() {
    }

    public Rides(String customerName, String customerEmail, String pickup, String dropoff, String drivername, String vehicletype, String time, String status, String date, String customernumber, String driverNumber, String vehicleNumber, String logStart, String logEnd, String price) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.drivername = drivername;
        this.vehicletype = vehicletype;
        this.time = time;
        this.status = status;
        this.date = date;
        this.customernumber = customernumber;
        this.driverNumber = driverNumber;
        this.vehicleNumber = vehicleNumber;
        this.logStart = logStart;
        this.logEnd = logEnd;
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getPickup() {
        return pickup;
    }

    public void setPickup(String pickup) {
        this.pickup = pickup;
    }

    public String getDropoff() {
        return dropoff;
    }

    public void setDropoff(String dropoff) {
        this.dropoff = dropoff;
    }

    public String getDrivername() {
        return drivername;
    }

    public void setDrivername(String drivername) {
        this.drivername = drivername;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(String customernumber) {
        this.customernumber = customernumber;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getLogStart() {
        return logStart;
    }

    public void setLogStart(String logStart) {
        this.logStart = logStart;
    }

    public String getLogEnd() {
        return logEnd;
    }

    public void setLogEnd(String logEnd) {
        this.logEnd = logEnd;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
