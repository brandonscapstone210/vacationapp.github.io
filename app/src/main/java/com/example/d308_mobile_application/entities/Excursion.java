package com.example.d308_mobile_application.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.DateFormat;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey
    private int excursionID;
    private int vacationID;
    private String excursionName;
    private String excursionDate;


    public Excursion(int excursionID, int vacationID, String excursionName, String excursionDate) {
        this.excursionID = excursionID;
        this.vacationID = vacationID;
        this.excursionName = excursionName;
        this.excursionDate = excursionDate;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public int getVacationID() {
        return vacationID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }
}
