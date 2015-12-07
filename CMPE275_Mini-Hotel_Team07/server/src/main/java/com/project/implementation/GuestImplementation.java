package com.project.implementation;

import com.project.dao.InterfaceForGuest;
import com.project.dto.GuestDTO;
import com.project.entities.Guest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Guest Implementation Class
 */

public class GuestImplementation {

    @Autowired
    InterfaceForGuest guestDAO;

    @Transactional
    public Integer getGuestByLicenseNo(String license_no) {
        GuestDTO guestDTO = new GuestDTO();
        Guest guestEntityObject = guestDAO.getGuestByLicenseNo(license_no);
        if (guestEntityObject != null) {
            guestDTO.setGuest_id(guestEntityObject.getGuest_id());
            return guestDTO.getGuest_id();
        } else {
            return null;
        }
    }
}
