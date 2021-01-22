package com.example.getaride;

public class Rides {

    String customerName, customerEmail, pickup, dropoff, drivername, vehicletype, time, status;

    public Rides() {
    }

    public Rides(String customerName, String customerEmail, String pickup, String dropoff, String drivername, String vehicletype, String time, String status) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.drivername = drivername;
        this.vehicletype = vehicletype;
        this.time = time;
        this.status = status;
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
}
