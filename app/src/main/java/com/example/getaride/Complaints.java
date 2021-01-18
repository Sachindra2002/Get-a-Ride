package com.example.getaride;

public class Complaints {
    String inquiry, complaint, id, complainee, status, date;

    public Complaints() {
    }

    public Complaints(String inquiry, String complaint, String id, String complainee, String status, String date) {

        this.inquiry = inquiry;
        this.complaint = complaint;
        this.id = id;
        this.complainee = complainee;
        this.status = status;
        this.date = date;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplainee() {
        return complainee;
    }

    public void setComplainee(String complainee) {
        this.complainee = complainee;
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
}
