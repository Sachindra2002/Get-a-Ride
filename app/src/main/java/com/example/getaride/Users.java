package com.example.getaride;

public class Users {
    private String fullName, email, phone, dob, address, vehicleNumber, vehicleType, role, status, currentLocation, uID;

    public Users() {
    }
    public Users(String fullName, String email, String phone, String dob, String address, String vehicleNumber, String vehicleType, String role, String status, String currentLocation) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.role = role;
        this.status = status;
        this.currentLocation = currentLocation;
    }

    public Users(String fullName, String email, String phone, String dob, String address, String role) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.address = address;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

}
