package com.project.implementation;

import com.project.ENUMS.RoomType;
import com.project.dao.InterfaceForCheckinRoomMapping;
import com.project.dao.InterfaceForReservation;
import com.project.dto.CheckinRoomMappingDTO;
import com.project.dto.ReservationDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Reservation;
import com.project.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * CheckinRoomMapping Implementation Class
 */

public class CheckinRoomMappingImplementation {
    @Autowired
    InterfaceForCheckinRoomMapping ReportDaoObject;
    @Autowired
    InterfaceForCheckinRoomMapping checkinRoomMappingDao;
    @Autowired
    InterfaceForReservation reservationDao;

    public ArrayList<HashMap<String, String>> getAvailableReservations(CheckinRoomMappingDTO checkinDTO) {

        Date checkin_date = checkinDTO.getCheckin_date();
        Date checkout_date = checkinDTO.getCheckout_date();
        Integer no_of_rooms = checkinDTO.getNumber_of_rooms();
        RoomType room_type = checkinDTO.getRoom_type();

        System.out.println("checkin_date: " + checkin_date);
        System.out.println("checkout_date: " + checkout_date);
        System.out.println("no_of_rooms: " + no_of_rooms);
        System.out.println("room_type: " + room_type);
        ArrayList<HashMap<String, String>> checkinRoomMapping =
                checkinRoomMappingDao.getAvailableRooms(checkin_date, checkout_date, no_of_rooms, room_type);
        // ChekinRoomMappingDTO checkinAvailable = new ChekinRoomMappingDTO();
        //checkinAvailable.setNumber_of_rooms(checkinRoomMapping.size());
        return checkinRoomMapping;

    }


//
//    /*  Create Checkin */
//
//  public CheckinRoomMappingDTO checkin(CheckinRoomMappingDTO checkinRoomMappingDTO) {
//
//        CheckinRoomMapping checkinRoomMappingObject = new CheckinRoomMapping();
//
//        try {
//            org.apache.commons.beanutils.BeanUtils.copyProperties(checkinRoomMappingObject, checkinRoomMappingDTO);
//        }
//        catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//      Reservation reservation = reservationDao.getReservationById(checkinRoomMappingDTO.getReservation_id());
//      if(reservation!=null) {
//          checkinRoomMappingObject.setReservation(reservation);
//
//
//          List<CheckinRoomMapping> checkinRoomMappingList;
//          checkinRoomMappingList = checkinRoomMappingDao.getCheckinByReservationId(reservation.getReservation_id());
//          for(CheckinRoomMapping crm : checkinRoomMappingList)
//          checkinRoomMappingDao.update(crm);
//          return checkinRoomMappingDTO;
//      }else
//          return null;
//  }


    /*  Create Checkin */

    public void checkinGuest(List<CheckinRoomMappingDTO> crmDTOList) {

        for (CheckinRoomMappingDTO crmDTO : crmDTOList) {

            CheckinRoomMapping checkinRoomMappingObject = new CheckinRoomMapping();

            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(checkinRoomMappingObject, crmDTO);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            Reservation reservation = reservationDao.getReservationById(crmDTO.getReservation_id());
            if (reservation != null) {
                checkinRoomMappingObject.setReservation(reservation);
//            List<CheckinRoomMapping> checkinRoomMappingList;
//            checkinRoomMappingList = checkinRoomMappingDao.getCheckinByReservationId(reservation.getReservation_id());
                checkinRoomMappingDao.update(checkinRoomMappingObject);
            }

        }
    }


    @Transactional
    public List<CheckinRoomMappingDTO> getReservationFromCheckinMapping(ReservationDTO reservationDTO) {
        List<CheckinRoomMapping> checkinList = checkinRoomMappingDao.getAllCheckins(reservationDTO);
        //List<Room> allRoomsList = new ArrayList<Room>();
        List<CheckinRoomMappingDTO> checkinDTOList = new ArrayList<CheckinRoomMappingDTO>();
        if (!checkinList.isEmpty()) {
            //CheckinRoomMappingDTO checkinRoomMappingDTO = new CheckinRoomMappingDTO();
            for (CheckinRoomMapping checkroom : checkinList) {
                CheckinRoomMappingDTO checkinRoomMappingDTO = new CheckinRoomMappingDTO();
                checkinRoomMappingDTO.setReservation_id(checkroom.getReservation().getReservation_id());
                checkinRoomMappingDTO.setRoom_no(checkroom.getRoom_no());
                checkinRoomMappingDTO.setGuest_count(checkroom.getGuest_count());
                checkinRoomMappingDTO.setCheckin_date(checkroom.getCheckin_date());
                checkinRoomMappingDTO.setCheckout_date(checkroom.getCheckout_date());
                checkinRoomMappingDTO.setMappingId(checkroom.getMappingId());
                checkinDTOList.add(checkinRoomMappingDTO);
            }
            return checkinDTOList;
        }
        else
        {
            return null;
        }

    }

//    @Transactional
//    public HashMap<String, List<Integer>> getOccupiedRooms(Date date) {
//
//        // create a java calendar instance
//        Calendar calendar = Calendar.getInstance();
//        java.util.Date currentDate = calendar.getTime();
//
//        Date todayDate = new Date(currentDate.getTime());
//
//        HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
//        List<Integer> roomReportList;
//        List<Integer> totalRooms = ReportDaoObject.getTotalRooms();
//        List<Integer> notOccupiedRooms = new ArrayList();
//
//        List<Integer> reservedRoomIntegerList = new ArrayList<Integer>();
//        List<Integer> occupiedRoomIntegerList = new ArrayList<Integer>();
//
//        System.out.println("Todays date is: " + todayDate);
//        System.out.println("Date date is: " + date);
//
//
//        if(todayDate.toString().equalsIgnoreCase(date.toString())) {// checks if current date
//            // Occupied Rooms
//            System.out.println("Im am here in today");
//            roomReportList = ReportDaoObject.getOccupiedRoomsData(date);
//            map.put("occupiedrooms", roomReportList);
//            Integer occupiedCount = roomReportList.size();
//
//
//
//            //Reserved rooms
//            roomReportList = ReportDaoObject.getReservationData(date);
//            map.put("reservedrooms", roomReportList);
//            Integer reservedCount = roomReportList.size();
//
//
//            // Available rooms
//
//            Integer notOccupied = totalRooms.size() - occupiedCount - reservedCount;
//            notOccupiedRooms.add(notOccupied);
//
//            map.put("notOccupiedCount", notOccupiedRooms);
//
//            return map;
//        }
//        else if (date.after(todayDate)) { // checks if future date
//            roomReportList = ReportDaoObject.getReservationData(date);
//            System.out.println("Im am here in after");
//            if (roomReportList != null) {
//
//                map.put("reservedrooms", roomReportList);
//
//
//                // Calculate not occupied rooms
//               // System.out.println("Total rooms" + totalRooms.size());
//
//
//                Integer notOccupied = totalRooms.size() - roomReportList.size();
//                notOccupiedRooms.add(notOccupied);
//
//                map.put("notOccupiedCount", notOccupiedRooms);
//
//                return map;
//
//            } else {
//                return null;
//            }
//
//        } else if (date.before(todayDate)) {// checks if past date
//            System.out.println("Im am here in before");
//
//            roomReportList = ReportDaoObject.getOccupiedRoomsData(date);
//
//            if (roomReportList != null) {
//                // Occupied Rooms
//                roomReportList = ReportDaoObject.getOccupiedRoomsData(date);
//                map.put("occupiedrooms", roomReportList);
//                Integer occupiedCount = roomReportList.size();
//
//                // Available rooms
//                Integer notOccupied = totalRooms.size() - occupiedCount;
//                notOccupiedRooms.add(notOccupied);
//
//                map.put("notOccupiedCount", notOccupiedRooms);
//
//                return map;
//
//            } else {
//                return null;
//            }
//        }
//        return map;
//
//    }


    @Transactional
    public HashMap<String, List<Integer>> getOccupiedRooms(Date date) {

        // create a java calendar instance
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();

        Date todayDate = new Date(currentDate.getTime());

        HashMap<String, List<Integer>> map = new HashMap<String, List<Integer>>();
        List<Integer> roomReportList;
        List<Integer> totalRooms = ReportDaoObject.getTotalRooms();
        List<Integer> notOccupiedRooms = new ArrayList();

        List<Integer> reservedRoomIntegerList = new ArrayList<Integer>();
        List<Integer> occupiedRoomIntegerList = new ArrayList<Integer>();

        Integer occupiedCount = 0;
        Integer notOccupied = totalRooms.size();
        Integer reservedCount =0;

        System.out.println("Todays date is: " + todayDate);
        System.out.println("Date date is: " + date);


        if(todayDate.toString().equalsIgnoreCase(date.toString())) {// checks if current date
            // Occupied Rooms
            System.out.println("Im am here in today");


            roomReportList = ReportDaoObject.getOccupiedRoomsData(date);
            System.out.println("Room list is: " + roomReportList);
            if(roomReportList != null) {
                map.put("occupiedrooms", roomReportList);
                occupiedCount = roomReportList.size();
            }
            else
            {
                map.put("occupiedrooms", roomReportList);
            }

            roomReportList = ReportDaoObject.getReservationData(date);

            if(roomReportList != null) {
                //Reserved rooms
                map.put("reservedrooms", roomReportList);
                reservedCount = roomReportList.size();
            }
            else{
                map.put("reservedrooms", roomReportList);
            }

            // Available rooms

            notOccupied = totalRooms.size() - occupiedCount - reservedCount;
            notOccupiedRooms.add(notOccupied);

            map.put("notOccupiedCount", notOccupiedRooms);

            return map;

        }
        else if (date.after(todayDate)) { // checks if future date
            roomReportList = ReportDaoObject.getReservationData(date);
            System.out.println("Im am here in after");
            if (roomReportList != null) {

                map.put("reservedrooms", roomReportList);
                reservedCount = roomReportList.size();
            }
            else
            {
                map.put("reservedrooms", roomReportList);
            }

            // Calculate not occupied rooms
            // System.out.println("Total rooms" + totalRooms.size());


            notOccupied = totalRooms.size() - reservedCount;
            notOccupiedRooms.add(notOccupied);

            map.put("notOccupiedCount", notOccupiedRooms);

            return map;

        } else if (date.before(todayDate)) {// checks if past date
            System.out.println("Im am here in before");

            roomReportList = ReportDaoObject.getOccupiedRoomsData(date);

            if (roomReportList != null) {
                // Occupied Rooms
                map.put("occupiedrooms", roomReportList);
                occupiedCount = roomReportList.size();
            }
            else{
                map.put("occupiedrooms", roomReportList);
            }


            // Available rooms
            notOccupied = totalRooms.size() - occupiedCount;
            notOccupiedRooms.add(notOccupied);

            map.put("notOccupiedCount", notOccupiedRooms);

            return map;
        }
        return map;

    }

    public List<Integer> getRoom(Integer room_no) {
        List<Integer> room_no_from_crm = checkinRoomMappingDao.getRoom(room_no);
        if(room_no_from_crm.isEmpty()){
            return null;
        }else{
            return room_no_from_crm;
        }
    }
//
//    public void checkIfRoomisReserved(Room room) {
//        checkinRoomMappingDao.checkIfRoomisReserver(room.getRoom_no());
//    }
}
