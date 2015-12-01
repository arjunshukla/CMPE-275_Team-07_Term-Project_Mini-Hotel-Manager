package com.project.dao;

import com.project.dto.CheckinRoomMappingDTO;
import com.project.entities.Room;
import com.project.entities.CheckinRoomMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.text.SimpleDateFormat;
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
        Integer reservation_id = (Integer)hibernateTemplate.save(checkinRoomMapping);
        checkinRoomMapping.setReservation_id(reservation_id);
        return checkinRoomMapping;
    }

    @Override
    public void update(CheckinRoomMapping checkinRoomMapping) {

    }

    @Override
    public void delete(CheckinRoomMapping checkinRoomMapping) {

    }

    @Override
    public CheckinRoomMapping getCheckinByReservationId(Integer reservation_id) {
        return null;
    }

    @Override
    public List<CheckinRoomMapping> getAllCheckins() {
        return null;
    }

    @Override
    public List<CheckinRoomMapping> getAllCheckins(Integer reservation_id) {
        String query ="from CheckinRoomMapping crm where crm.reservation_id=?";
        List<CheckinRoomMapping> checkinlist = (List<CheckinRoomMapping>)hibernateTemplate.find(query, reservation_id);
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
