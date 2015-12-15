package com.project.dao;

import com.project.entities.Guest;

import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for Guest
 */

public interface InterfaceForGuest {
    Guest save(Guest guest);

    public void update(Guest guest);

    public void delete(Guest guest);

    public Guest getGuestById(Integer guestID);

   public Guest getGuestByEmail(String email);

   public Guest getGuestByLicenseNo(String licenseNo);

    List<Guest> findGuestEmailId(Integer guest_id);

    Integer checkGuestExist(String guest_email,String license_no);

    Integer updateUserDetails(Integer guestID, String guest_name, String street, String city, String state, Integer zip);

    Guest getGuestRecord(Integer guestID);
}
