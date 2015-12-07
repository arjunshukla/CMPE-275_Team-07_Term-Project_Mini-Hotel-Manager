package com.project.dao;

import com.project.entities.CheckinRoomMapping;

import java.sql.Date;
import java.util.List;


public interface InterfaceForReport {
    List<CheckinRoomMapping> getRooms(Date d);
//    Room save(Room room);
}
