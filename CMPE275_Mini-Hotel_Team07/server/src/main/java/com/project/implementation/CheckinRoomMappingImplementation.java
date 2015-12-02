package com.project.implementation;
import com.project.dao.InterfaceForReservation;
import com.project.dto.ReportDTO;
import com.project.dao.InterfaceForCheckinRoomMapping;
import com.project.dto.CheckinRoomMappingDTO;
import com.project.dto.ReservationDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.entities.Room;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
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

    @Transactional
    public ReportDTO getOccupiedRooms(Date date) {
        ReportDTO reportDTOObject = new ReportDTO();

        List<Room> roomReportObject;
        roomReportObject = ReportDaoObject.getOccupiedRoomsData(date);

        if (roomReportObject != null) {
            List<Integer> roomIntegerList = new ArrayList<Integer>();

            for (Room room : roomReportObject) {
                roomIntegerList.add(room.getRoom_no());
            }

            reportDTOObject.setRooms(roomIntegerList);
            return reportDTOObject;
        }
        else{
            return null;
        }
    }
}
