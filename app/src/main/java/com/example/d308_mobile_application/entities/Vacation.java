package com.example.d308_mobile_application.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;
import java.util.Date;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;

    private String vacationName;

    private String hotelName;

    private String startDate;

    private String endDate;

    public Vacation(int vacationID, String vacationName, String hotelName, String startDate, String endDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.hotelName = hotelName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getVacationID() {
        return vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
