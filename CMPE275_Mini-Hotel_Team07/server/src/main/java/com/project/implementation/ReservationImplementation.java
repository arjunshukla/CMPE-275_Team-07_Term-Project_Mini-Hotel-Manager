package com.project.implementation;


import com.project.dao.CheckinRoomMappingDAO;
import com.project.dao.InterfaceForReservation;
import com.project.dto.ReservationDTO;
import com.project.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Reservation Implementation Class
 */

public class ReservationImplementation {

    @Autowired
    InterfaceForReservation reservationDAO;

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
