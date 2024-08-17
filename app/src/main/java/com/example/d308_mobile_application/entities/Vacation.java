package com.example.d308_mobile_application.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;

    private String vacationName;

    private double price;

    public Vacation(int vacationID, String vacationName, double price) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.price = price;
    }

    public int getVacationID() {
        return vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public double getPrice() {
        return price;
    }
}
