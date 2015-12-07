package com.project.dao;

import com.project.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * REservation Data Access Object
 */

@Transactional
public class ReservationDAO implements InterfaceForReservation {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Reservation save(Reservation reservation) {
        Integer res_id = (Integer)hibernateTemplate.save(reservation);
        reservation.setReservation_id(res_id);
        return reservation;
    }

    @Override
    public Integer update(Integer reservation) {
        Integer updatedReservation =
                hibernateTemplate.bulkUpdate("Update Reservation set reservation_status = 'C' where reservation_id="+reservation);
        System.out.println(updatedReservation);
        return updatedReservation;

    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public Reservation getReservationByLicense_Token(String reservation_token, Integer guest_id) {
        String query = "from Reservation r where r.reservation_token =? and r.guest_id=?";
        List<Reservation> reservationList = (List<Reservation>) hibernateTemplate.find(query,reservation_token, guest_id);
        if(reservationList.isEmpty()){
            return null;
        }
        else{
            return reservationList.get(0);
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return null;
    }

    @Override
    public Reservation getReservationById(Integer reservation_id) {
        String query = "from Reservation where reservation_id =?";
        List<Reservation> reservationList = (List<Reservation>) hibernateTemplate.find(query,reservation_id);
        if(reservationList.isEmpty()){
            return null;
        }
        else{
            return reservationList.get(0);
        }
    }

    @Override
    public List<Reservation> find(String reservationToken) {
        String query = "from Reservation where reservation_token =?";
        List<Reservation> reservationList = (List<Reservation>) hibernateTemplate.find(query,reservationToken);

        return reservationList;
    }
}
