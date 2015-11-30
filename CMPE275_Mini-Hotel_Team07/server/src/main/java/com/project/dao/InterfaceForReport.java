package com.project.dao;

import com.project.entities.CheckinRoomMapping;

import java.sql.Date;
import java.util.List;

/**
 * Created by PankajPai on 11/22/2015.
 */
public interface InterfaceForReport {
    List<CheckinRoomMapping> getRooms(Date d);
//    Room save(Room room);
}
