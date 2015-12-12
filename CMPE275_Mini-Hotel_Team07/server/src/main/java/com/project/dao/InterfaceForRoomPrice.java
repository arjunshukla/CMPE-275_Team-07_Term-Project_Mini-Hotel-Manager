package com.project.dao;

import com.project.ENUMS.RoomType;

import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for RoomPrice
 */

public interface InterfaceForRoomPrice {

    InterfaceForRoomPrice save(InterfaceForRoomPrice room);
    public void update(InterfaceForRoomPrice roomPrice);
    public void delete(InterfaceForRoomPrice roomPrice);
    public InterfaceForRoomPrice getRoomPriceByType(RoomType roomType);
    public List<RoomType> getAllRoomTypes();

    Double getRoomPrice(Enum<RoomType> roomType);
}
