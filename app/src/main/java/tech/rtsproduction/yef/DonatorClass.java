package tech.rtsproduction.yef;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;

public class DonatorClass {

    private String donatorName;
    private String releation;
    private String phoneNo, location, reason;
    private String requestDate;
    private String bloodType, bloodRequired;

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodRequired() {
        return bloodRequired;
    }

    public void setBloodRequired(String bloodRequired) {
        this.bloodRequired = bloodRequired;
    }

    public void setDonatorName(String donatorName) {
        this.donatorName = donatorName;
    }

    public void setReleation(String releation) {
        this.releation = releation;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getDonatorName() {
        return donatorName;
    }

    public String getReleation() {
        return releation;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getLocation() {
        return location;
    }

    public String getReason() {
        return reason;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public DonatorClass(String donatorName, String releation, String phoneNo, String location, String reason, String requestDate, String bloodType, String bloodRequired) {
        this.donatorName = donatorName;
        this.releation = releation;
        this.phoneNo = phoneNo;
        this.location = location;
        this.reason = reason;
        this.requestDate = requestDate;
        this.bloodType = bloodType;
        this.bloodRequired = bloodRequired;
    }

    public DonatorClass() {
        //Empty Constructor For Firebase DataSnapshot
    }
}
