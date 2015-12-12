package com.project.dao;

import com.project.ENUMS.RoomType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * RoomPrice Data Access Object
 */

@Transactional
public class RoomPriceDAO implements InterfaceForRoomPrice {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public InterfaceForRoomPrice save(InterfaceForRoomPrice room) {
        return null;
    }

    @Override
    public void update(InterfaceForRoomPrice roomPrice) {

    }

    @Override
    public void delete(InterfaceForRoomPrice roomPrice) {

    }

    @Override
    public InterfaceForRoomPrice getRoomPriceByType(RoomType roomType) {
        return null;
    }

    @Override
    public List<RoomType> getAllRoomTypes() {
        return null;
    }

    @Override
    public Double getRoomPrice(Enum<RoomType> roomType) {
        String query = "Select room_price from RoomPrice where room_type=?";
        List<Double> price =(List<Double>) hibernateTemplate.find(query,roomType);
        Double roomPrice = price.get(0);
        System.out.println(roomPrice);
        return roomPrice;
    }
}
