package com.project.dao;

import com.project.entities.Guest;

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
}
