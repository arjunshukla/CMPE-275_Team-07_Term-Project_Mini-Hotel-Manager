package com.project.entities;

import com.project.ENUMS.RoomType;

import javax.persistence.*;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * RoomType ENUM
 */

@Entity
@Table(name = "room_price")
public class RoomPrice {


    @Column(name="room_price", unique=false, nullable = false)
    private Double room_price;

    @Id
    @Enumerated(EnumType.STRING)
    @Column(name="room_type", unique = false, nullable = false)
    private RoomType room_type;

    public Double getRoom_price() {
        return room_price;
    }

    public void setRoom_price(Double room_price) {
        this.room_price = room_price;
    }

    public RoomType getRoom_type() {
        return room_type;
    }

    public void setRoom_type(RoomType room_type) {
        this.room_type = room_type;
    }
}