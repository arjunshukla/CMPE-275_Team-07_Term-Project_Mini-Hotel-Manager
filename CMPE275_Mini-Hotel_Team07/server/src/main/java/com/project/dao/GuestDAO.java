package com.project.dao;

import com.project.entities.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Guest Data Access Object
 */

@Transactional
public class GuestDAO implements InterfaceForGuest {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Guest save(Guest guest) {
        System.out.println("in DAO save of PersonDetailsDAO class with personObj: "+guest.getGuest_id());
        Integer guest_id = (Integer)hibernateTemplate.save(guest);
        guest.setGuest_id(guest_id);
        System.out.println("in DAO save of PersonDetailsDAO class with personObj: "+guest.getGuest_id());
        return guest;
    }

    @Override
    public void update(Guest guest) {

    }

    @Override
    public void delete(Guest guest) {

    }

    @Override
    public Guest getGuestById(Integer guestID) {
        return null;
    }

    @Override
    public Guest getGuestByEmail(String email) {
        return null;
    }

    @Override
    public Guest getGuestByLicenseNo(Integer licenseNo) {
        return null;
    }
}
