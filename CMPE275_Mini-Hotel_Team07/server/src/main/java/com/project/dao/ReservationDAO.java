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
        return null;
    }

    @Override
    public void update(Reservation reservation) {

    }

    @Override
    public void delete(Reservation reservation) {

    }

    @Override
    public Reservation getReservationById(Integer reservationId) {
        return null;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return null;
    }
}
