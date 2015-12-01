package com.project.dao;

import com.project.entities.CheckinRoomMapping;
import com.project.entities.Room;

import java.sql.Date;
import java.util.List;


/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for CheckinRoomMapping
 */

public interface InterfaceForCheckinRoomMapping {
    CheckinRoomMapping save(CheckinRoomMapping checkinRoomMapping);
    public void update(CheckinRoomMapping checkinRoomMapping);
    public void delete(CheckinRoomMapping checkinRoomMapping);
    public CheckinRoomMapping getCheckinByReservationId(Integer reservation_id);
    public List<CheckinRoomMapping> getAllCheckins();
    public List<Room> getOccupiedRoomsData(Date d);
    List<CheckinRoomMapping> getAllCheckins(Integer reservation_id);
}