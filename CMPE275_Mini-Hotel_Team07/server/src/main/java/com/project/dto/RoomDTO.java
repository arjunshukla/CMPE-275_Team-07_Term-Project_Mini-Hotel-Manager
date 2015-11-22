package com.project.dto;

import com.project.ENUMS.RoomStatus;
import com.project.ENUMS.RoomType;


/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Room Table Data Transaction Object Class
 */

public class RoomDTO {

    private Integer room_no;
    private RoomType room_type;
    private RoomStatus room_status;

    public Integer getRoom_no() {
        return room_no;
    }

    public void setRoom_no(Integer room_no) {
        this.room_no = room_no;
    }

    public RoomType getRoom_type() {
        return room_type;
    }

    public void setRoom_type(RoomType room_type) {
        this.room_type = room_type;
    }

    public RoomStatus getRoom_status() {
        return room_status;
    }

    public void setRoom_status(RoomStatus room_status) {
        this.room_status = room_status;
    }
}
