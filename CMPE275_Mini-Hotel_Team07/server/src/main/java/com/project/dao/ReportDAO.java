package com.project.dao;

import com.project.entities.CheckinRoomMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;


public class ReportDAO implements InterfaceForReport {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public List<CheckinRoomMapping> getRooms(Date d) {


        System.out.println("I am in getrooms function");

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

        String dateS = dt.format(d);


        String query = "";//"Checkin_room_mapping.room_no from checkin_room_mapping Where checkin_room_mapping.checkin_date <= '? ' and checkin_room_mapping.checkout_date >= '?' ";

        System.out.println("in check friendship with query: " + query);

        List<CheckinRoomMapping> rooms = (List<CheckinRoomMapping>) hibernateTemplate.find(query);

        System.out.println(rooms);
        return rooms;
    }
}

/*
        if (rooms.isEmpty())
            return null;
        else

        return rooms;
    }

}*/