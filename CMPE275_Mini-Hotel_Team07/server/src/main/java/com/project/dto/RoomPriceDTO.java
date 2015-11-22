package com.project.dto;

import java.lang.Enum;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * RoomPrice Table Data Transaction Object
 */


public class RoomPriceDTO{

    private Double room_price;
    private Enum room_type;

    public Double getRoom_price() {
        return room_price;
    }

    public void setRoom_price(Double room_price) {
        this.room_price = room_price;
    }

    public Enum getRoom_type() {
        return room_type;
    }

    public void setRoom_type(Enum room_type) {
        this.room_type = room_type;
    }
}









