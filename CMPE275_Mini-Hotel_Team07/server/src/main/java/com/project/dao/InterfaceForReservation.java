package com.project.dao;

import com.project.entities.Reservation;

import java.util.List;


/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for Reservation
 */

public interface InterfaceForReservation {
    Reservation save(Reservation reservation);
    public void update(Reservation reservation);
    public void delete(Reservation reservation);
    public Reservation getReservationByLicense_Token(String reservation_token, Integer guest_Id);
    public List<Reservation> getAllReservations();

    Reservation getReservationById(Integer reservation_id);
}