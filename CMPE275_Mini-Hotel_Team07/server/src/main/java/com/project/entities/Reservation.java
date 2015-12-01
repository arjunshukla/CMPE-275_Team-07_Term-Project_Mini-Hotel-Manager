package com.project.entities;

import com.project.ENUMS.ReservationStatus;

import javax.persistence.*;
import java.sql.Date;


/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Reservations Entity class
 */

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @Column(name = "reservation_id", unique = true, nullable = false)
    private Integer reservation_id;

    //@OneToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "guest_id")
    private Integer guest_id;

    @Column(name = "reservation_token", unique = true, nullable = false)
    private String reservation_token;

    @Column(name="reservation_date", unique=false, nullable = false)
    private Date reservation_date;

    @Enumerated(EnumType.STRING)
    @Column(name="reservation_status", unique = false, nullable = false)
    private ReservationStatus reservation_status;

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

    public Date getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(Date reservation_date) {
        this.reservation_date = reservation_date;
    }

    public ReservationStatus getReservation_status() {
        return reservation_status;
    }

    public void setReservation_status(ReservationStatus reservation_status) {
        this.reservation_status = reservation_status;
    }
}
