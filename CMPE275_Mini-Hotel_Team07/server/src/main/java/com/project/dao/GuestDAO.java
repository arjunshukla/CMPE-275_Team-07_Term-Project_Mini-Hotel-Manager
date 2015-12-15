package com.project.dao;

import com.project.entities.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Guest Data Access Object
 */

@Transactional
public class GuestDAO implements InterfaceForGuest {
    int guestID;

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
    public Guest getGuestByLicenseNo(String licenseNo) {
    String query = "from Guest g where g.license_no=?";
    List<Guest> guestList = (List<Guest>)hibernateTemplate.find(query,licenseNo);
        if(guestList.isEmpty()){
            return null;
        }
        else{
            return guestList.get(0);
        }
    }

    @Override
    public List<Guest> findGuestEmailId(Integer guest_id) {
        String query = "from Guest where guest_id=?";
        List<Guest> emailList = (List<Guest>) hibernateTemplate.find(query,guest_id);
        //String emailId=emailList.get(0).toString();
        //System.out.println("email in guestDAO: "+emailId);
        return emailList;
    }

    @Override
    public Integer checkGuestExist(String guest_email,String license_no) {

        String query = "from Guest where guest_email=? and license_no=?";
        List<Guest> guestRecord = (List<Guest>) hibernateTemplate.find(query,guest_email,license_no);
        //System.out.println("emailid: "+guestRecord.get(0).getGuest_email());
        //System.out.println("license no: "+guestRecord.get(0).getLicense_no());
        if(guestRecord.size()>0)
        { guestID = guestRecord.get(0).getGuest_id();}

        return guestID;
    }

    @Override
    public Integer updateUserDetails(Integer guestID, String guest_name, String street, String city, String state, Integer zip) {

        Integer count =
                (Integer) hibernateTemplate.bulkUpdate("Update Guest set guest_name ='"+guest_name+"',street='"+street+"',city='"+city+"',state='"+state+"',zip='"+zip+"' where guest_id="+guestID);
        System.out.println("count"+count);


        return count;
    }

    @Override
    public Guest getGuestRecord(Integer guestID) {
        String query = "from Guest where guest_id=?";
        List<Guest> guestRecord = (List<Guest>)hibernateTemplate.find(query,guestID);
        //System.out.println("email:::: "+guestRecord.getGuest_email());
        return guestRecord.get(0);
    }


}
