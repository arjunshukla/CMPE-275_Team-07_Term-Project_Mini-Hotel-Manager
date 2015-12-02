package com.project.dao;

import com.project.ENUMS.RoomType;
import com.project.dto.ReservationDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Reservation;
import com.project.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
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

    @Override
    public ArrayList<HashMap<String, String>> getAvailableRooms(Date checkin_date, Date checkout_date, Integer no_of_rooms, RoomType room_type) {
        System.out.println("hello");
        // Date
        ArrayList<HashMap<String, String>> roomList = new ArrayList<>();
        String query = "Select room_no from Room where room_no not in \n" +
                "(select room_no from CheckinRoomMapping Where checkin_date <= ? \n" +
                "and checkout_date >= ? ) and room_type = ?";
        //  @SuppressWarnings("unchecked")
        List<Integer> checkinRoomMapping = (List<Integer>) hibernateTemplate.find(query,checkin_date,checkout_date,room_type);
        if (checkinRoomMapping.isEmpty()) {
            return null;
        } else {
            for(int i=0;i<checkinRoomMapping.size();i++){

                System.out.println("display: "+"i: "+checkinRoomMapping.get(i));
                String room_name = checkinRoomMapping.get(i).toString();
                HashMap<String, String> hm = new HashMap<>();
                hm.put("room_no", room_name);
                roomList.add(hm);

            }

            //System.out.println("size: "+checkinRoomMapping.size());
//            for(int i =0;i<checkinRoomMapping.size();i++) {
//                //Date checkin_date1 = checkinRoomMapping.get(0).getCheckin_date();
//                System.out.print("checkin_date"+ checkin_date);
//                Date checkout = checkinRoomMapping.get(0).getCheckout_date();
//                System.out.println("checkout: "+checkout);
//            }
            return roomList;
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
