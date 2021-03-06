package com.project.dto;

import java.lang.Enum;
import java.sql.Date;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Reservation Table Data Transaction Object
 */


public class ReservationDTO{

    private Integer reservation_id;
    private Integer guest_id;
    private String reservation_token;
    private Enum reservation_status;
    private Date reservation_date;


    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public String getReservation_token() {
        return reservation_token;
    }

    public void setReservation_token(String reservation_token) {
        this.reservation_token = reservation_token;
    }


    public Enum getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(Enum reservation_status) {
        this.reservation_status = reservation_status;
    }

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {

        this.reservation_date=reservation_date;
    }

}