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
}
