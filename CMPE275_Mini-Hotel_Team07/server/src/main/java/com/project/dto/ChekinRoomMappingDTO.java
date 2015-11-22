package com.project.dto;

import javax.persistence.Column;
import java.security.Timestamp;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * CheckinRoomMapping Table Data Transaction Object Class
 */

public class ChekinRoomMappingDTO {
    private Integer reservation_id;
    private Integer room_no;
    private Integer guest_count;
    private Timestamp checkin_date;
    private Timestamp checkout_date;

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
    }

    public Integer getRoom_no() {
        return room_no;
    }

    public void setRoom_no(Integer room_no) {
        this.room_no = room_no;
    }

    public Integer getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(Integer guest_count) {
        this.guest_count = guest_count;
    }

    public Timestamp getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(Timestamp checkin_date) {
        this.checkin_date = checkin_date;
    }

    public Timestamp getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Timestamp checkout_date) {
        this.checkout_date = checkout_date;
    }
}
