package com.project.implementation;


import com.project.ENUMS.ReservationStatus;
import com.project.dao.InterfaceForCheckinRoomMapping;
import com.project.dao.InterfaceForGuest;
import com.project.dao.InterfaceForReservation;
import com.project.dto.GuestDTO;
import com.project.dto.ReservationDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Guest;
import com.project.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Reservation Implementation Class
 */

public class ReservationImplementation {

    @Autowired
    InterfaceForReservation reservationDAO;

    @Autowired
    InterfaceForCheckinRoomMapping checkinRoomMappingDAO;

    @Autowired
    InterfaceForGuest guestDAO;

    public String makeReservation(GuestDTO guestDTO, Integer no_of_rooms_Int, Date checkin_date, Date checkout_date) {

        String room_selected = guestDTO.getRoom_no_selected();
        Guest guestObject = new Guest();

        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(guestObject, guestDTO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        guestObject = guestDAO.save(guestObject);


        String token_no = guestObject.getLicense_no() + guestObject.getGuest_id();

        java.util.Date todayUtilDate = Calendar.getInstance().getTime();//2015-12-05
        java.sql.Date todaySqlDate = new java.sql.Date(todayUtilDate.getTime());

        Reservation reservationObject = new Reservation();
//        reservationObject.setReservation_id(0);
        reservationObject.setGuest_id(guestObject.getGuest_id());
        reservationObject.setReservation_token(token_no);
        reservationObject.setReservation_date(todaySqlDate);
        reservationObject.setReservation_status(ReservationStatus.R);
        reservationObject = reservationDAO.save(reservationObject);
       // Integer reservation_id = reservationObject.getReservation_id();

        String[] resultStringArray = guestDTO.getRoom_no_selected().split(",");
        ArrayList<Integer> arrRooms = new ArrayList<Integer>();
        for (String str : resultStringArray) {
            arrRooms.add(Integer.parseInt(str));
            CheckinRoomMapping checkinRoomMappingObject = new CheckinRoomMapping();
            checkinRoomMappingObject.setReservation(reservationObject);
            checkinRoomMappingObject.setRoom_no(Integer.parseInt(str));
            checkinRoomMappingObject.setCheckin_date(checkin_date);
            checkinRoomMappingObject.setCheckout_date(checkout_date);
            checkinRoomMappingObject.setGuest_count(0);
            checkinRoomMappingObject.setMappingId(0);
            checkinRoomMappingDAO.save(checkinRoomMappingObject);
        }

        return token_no;
    }

    @Transactional
    public ReservationDTO getReservationById(String reservation_token, Integer guest_Id){
      ReservationDTO reservationDTO = new ReservationDTO();
        Reservation reservationEntityObject = reservationDAO.getReservationByLicense_Token(reservation_token, guest_Id);
        if(reservationEntityObject != null){
            reservationDTO.setReservation_id(reservationEntityObject.getReservation_id());
            reservationDTO.setGuest_id(reservationEntityObject.getGuest_id());
            reservationDTO.setReservation_token(reservationEntityObject.getReservation_token());
            reservationDTO.setReservation_date(reservationEntityObject.getReservation_date());
            reservationDTO.setReservation_status(reservationEntityObject.getReservation_status());
            return reservationDTO;//.getReservation_id();
        }
        else{
            return null;
        }
    }

    public boolean isValidReservation(Integer reservation_id) {
         if(reservationDAO.getReservationById(reservation_id) != null){
             return true;
         }
        return false;
    }
}
