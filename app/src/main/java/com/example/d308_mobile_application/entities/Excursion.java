package com.example.d308_mobile_application.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey
    private int excursionID;
    private String excursionName;
    private double price;
    private int productID;


    public Excursion(int excursionID, String excursionName, double price, int productID) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.price = price;
        this.productID = productID;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public double getPrice() {
        return price;
    }

    public int getProductID() {
        return productID;
    }
}
