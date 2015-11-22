package com.project.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * CheckinRoomMapping Entity class
 */

@Entity
@Table(name = "checkin_room_mapping")
public class CheckinRoomMapping implements Serializable{

    @Id
    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_no")
    private Room room;

    @Column(name = "guest_count", unique = false, nullable = false)
    private Integer guest_count;

    @Column(name = "checkin_date", unique = false, nullable = false)
    private Date checkin_date;

    @Column(name = "checkout_date", unique = false, nullable = false)
    private Date checkout_date;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Integer getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(Integer guest_count) {
        this.guest_count = guest_count;
    }

    public Date getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(Date checkin_date) {
        this.checkin_date = checkin_date;
    }

    public Date getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Date checkout_date) {
        this.checkout_date = checkout_date;
    }
}