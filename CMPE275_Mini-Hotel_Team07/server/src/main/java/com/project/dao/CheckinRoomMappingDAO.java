package com.project.dao;

import com.project.dto.ReservationDTO;
import com.project.entities.Reservation;
import com.project.entities.Room;
import com.project.entities.CheckinRoomMapping;
import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * CheckinRoomMapping Data Access Object
 */

@Transactional
public class CheckinRoomMappingDAO implements InterfaceForCheckinRoomMapping {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public CheckinRoomMapping save(CheckinRoomMapping checkinRoomMapping) {
        Integer mapping_id = (Integer)hibernateTemplate.save(checkinRoomMapping);
        return checkinRoomMapping;
    }

    @Override
    public void update(CheckinRoomMapping checkinRoomMappingObject) {
        Integer room_no = checkinRoomMappingObject.getRoom_no();
        Integer guest_count = checkinRoomMappingObject.getGuest_count();
        String query ="CheckinRoomMapping crm set crm.guest_count=? where crm.room_no=?";
        //hibernateTemplate.saveOrUpdate(query,checkinRoomMappingObject);
        hibernateTemplate.update(query, checkinRoomMappingObject);
    }

    @Override
    public void delete(CheckinRoomMapping checkinRoomMapping) {

    }

    @Override
    public List<CheckinRoomMapping> getCheckinByReservationId(Integer reservation_id) {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(reservation_id);
        String query ="from CheckinRoomMapping crm where crm.reservation_id=?";
        List<CheckinRoomMapping> checkinlist = (List<CheckinRoomMapping>)hibernateTemplate.find(query, reservation);
        if(checkinlist.isEmpty()){
            return null;
        }
        else{
            return checkinlist;
        }
    }

    @Override
    public List<CheckinRoomMapping> getAllCheckins(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        reservation.setReservation_id(reservationDTO.getReservation_id());
        String query ="from CheckinRoomMapping crm where crm.reservation=?";
        List<CheckinRoomMapping> checkinlist = (List<CheckinRoomMapping>)hibernateTemplate.find(query, reservation);
        if(checkinlist.isEmpty()){
            return null;
        }
        else{
            return checkinlist;
        }
    }


    public List<Room> getOccupiedRoomsData(Date date) {

        String query =  "Select room from CheckinRoomMapping where checkin_date <= ? and checkout_date >= ?";

        //@SuppressWarnings("unchecked")
        List<Room> listOfOccupiedRooms = (List<Room>) hibernateTemplate.find(query,date,date);
        System.out.println(listOfOccupiedRooms.size());
        if (listOfOccupiedRooms.isEmpty()) {
            return null;
        } else {
            return listOfOccupiedRooms;
        }
    }


}
