package com.project.dao;

import com.project.ENUMS.RoomType;
import com.project.dto.ReservationDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Reservation;
import com.project.entities.Room;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.*;
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

    @Autowired
    SessionFactory sessionFactory;

    Transaction txn;

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
//        String query = "Select room_no from Room where room_no not in \n" +
//                "(select room_no from CheckinRoomMapping Where checkin_date <= ? \n" +
//                "and checkout_date >= ? ) and room_type = ?";

        String query = "Select room_no from Room where room_no not in \n" +
                "(select room_no from CheckinRoomMapping Where (checkin_date between ? AND ?) OR (checkout_date between ? AND ?) ) and room_type = ?";

        //  @SuppressWarnings("unchecked")
        List<Integer> checkinRoomMapping = (List<Integer>) hibernateTemplate.find(query,checkin_date,checkout_date,checkin_date,checkout_date,room_type);
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
            return roomList;
        }
    }

    @Override
    public Integer deleteReservationRecords(Integer reservation_id) {
       // String query = "Delete from CheckinRoomMapping where reservation =?";
        Integer deletedRecords = hibernateTemplate.bulkUpdate("delete from CheckinRoomMapping where reservation="+reservation_id);
        System.out.println("deleted: "+deletedRecords);
        return deletedRecords;
    }

    @Override
    public List<CheckinRoomMapping> findMappingForBilling(ReservationDTO reservationDTO) {
        Reservation reserv = new Reservation();
        reserv.setReservation_id(reservationDTO.getReservation_id());
        String query = "from CheckinRoomMapping where reservation=?";
        List<CheckinRoomMapping>crmRecords = (List<CheckinRoomMapping>)hibernateTemplate.find(query,reserv);
        System.out.println(crmRecords.size());
        return crmRecords;
    }

//    @Override
//    public void checkIfRoomisReserver(Integer room_no) {
//        String query = "SELECT * FROM minihotel.checkin_room_mapping where guest_count>0 and ? between checkin_date and checkout_date;";
//        List<Integer> listOfOccupiedRooms = (List<Integer>) hibernateTemplate.find(query,date,date);
//        //System.out.println(listOfOccupiedRooms.size());
//        if (listOfOccupiedRooms.isEmpty()) {
//            return null;
//        } else {
//            return listOfOccupiedRooms;
//        }
//    }

    public List<Integer> getOccupiedRoomsData(Date date) {
        String query =  "Select Distinct room_no from CheckinRoomMapping where checkin_date <= ? and checkout_date >= ? AND guest_count != 0 Order by room_no";
        //@SuppressWarnings("unchecked")
        List<Integer> listOfOccupiedRooms = (List<Integer>) hibernateTemplate.find(query,date,date);
        //System.out.println(listOfOccupiedRooms.size());
        if (listOfOccupiedRooms.isEmpty()) {
            return null;
        } else {
            return listOfOccupiedRooms;
        }
    }

    @Override
    public List<Integer> getReservationData(Date date) {
        String query =  "Select Distinct room_no from CheckinRoomMapping where checkin_date <= ? and checkout_date >= ? AND guest_count = 0 order by room_no";
        //@SuppressWarnings("unchecked")
        List<Integer> listOfOccupiedRooms = (List<Integer>) hibernateTemplate.find(query,date,date);
        //System.out.println(listOfOccupiedRooms.size());
        if (listOfOccupiedRooms.isEmpty()) {
            return null;
        } else {
            return listOfOccupiedRooms;
        }
    }


    @Override
    public List<Integer> getTotalRooms() {

        String query =  "Select room_no from Room";

        //@SuppressWarnings("unchecked")
        List<Integer> listOfOccupiedRooms = new ArrayList();

        listOfOccupiedRooms = (List<Integer>) hibernateTemplate.find(query);
        //System.out.println(listOfOccupiedRooms.size());
        if (listOfOccupiedRooms.isEmpty()) {
            return null;
        } else {
            return listOfOccupiedRooms;
        }
    }

    @Override
    public List<Integer> getRoom(Integer room_no){
        String query = "select room_no from CheckinRoomMapping where room_no=? and checkout_date > ?";
        //java.util.Date currdate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        List<Integer> room_no_temp = (List<Integer>) hibernateTemplate.find(query,room_no,sqlDate);
        if(room_no_temp == null){
            return null;
        }else{
            return room_no_temp;
        }
    }

    @Override
    public List<Integer> getRoomByReservationID(Reservation reservation) {

        String query =  "Select room_no from CheckinRoomMapping where reservation=?";

        //@SuppressWarnings("unchecked")
        List<Integer> roomsList = new ArrayList();

        roomsList = (List<Integer>) hibernateTemplate.find(query,reservation);
        //System.out.println(listOfOccupiedRooms.size());
        if (roomsList.isEmpty()) {
            return null;
        } else {
            return roomsList;
        }
    }


}
