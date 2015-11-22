package com.project.entities;

import com.project.ENUMS.RoomStatus;
import com.project.ENUMS.RoomType;

import javax.persistence.*;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Guest Entity class
 */

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @Column(name = "room_no", unique = true, nullable = false)
    private Integer room_no;

    @Enumerated(EnumType.STRING)
    @JoinColumn(name = "room_type")
    private RoomType room_type;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "room_status", unique = false, nullable = false)
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
