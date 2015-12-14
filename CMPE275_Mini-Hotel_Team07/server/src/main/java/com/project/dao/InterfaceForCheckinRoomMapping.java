package com.project.dao;

import com.project.ENUMS.RoomType;
import com.project.dto.ReservationDTO;
import com.project.dto.RoomDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Reservation;
import com.project.entities.Room;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
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
    public List<CheckinRoomMapping> getCheckinByReservationId(Integer reservation_id);
//    public List<CheckinRoomMapping> getAllCheckins();
    public List<Integer> getOccupiedRoomsData(Date d);
    List<Integer> getReservationData(Date date);
    List<Integer> getTotalRooms();

    List<CheckinRoomMapping> getAllCheckins(ReservationDTO reservationDTO);
    public ArrayList<HashMap<String, String>> getAvailableRooms(Date checkin_date, Date checkout_date, Integer no_of_rooms, RoomType room_type);


    Integer deleteReservationRecords(Integer reservation_id);

    List<CheckinRoomMapping> findMappingForBilling(ReservationDTO reservation_id);

    List<Integer> getRoom(Integer room_no);

    List<Integer> getRoomByReservationID(Reservation reservation);


    //void checkIfRoomisReserver(Integer room_no);
}