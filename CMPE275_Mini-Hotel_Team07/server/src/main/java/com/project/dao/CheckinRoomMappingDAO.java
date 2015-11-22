package com.project.dao;

import com.project.entities.CheckinRoomMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * CheckinRoomMapping Data Access Object
 */

@Transactional
public class CheckinRoomMappingDAO implements InterfaceForCheckinRoomMapping {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public CheckinRoomMapping save(CheckinRoomMapping checkinRoomMapping) {
        return null;
    }

    @Override
    public void update(CheckinRoomMapping checkinRoomMapping) {

    }

    @Override
    public void delete(CheckinRoomMapping checkinRoomMapping) {

    }

    @Override
    public CheckinRoomMapping getCheckinByReservationId(Integer reservation_id) {
        return null;
    }

    @Override
    public List<CheckinRoomMapping> getAllCheckins() {
        return null;
    }
}
