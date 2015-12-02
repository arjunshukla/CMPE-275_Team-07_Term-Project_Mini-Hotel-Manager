package com.project.controller;

import com.project.ENUMS.RoomStatus;
import com.project.configuration.AppConfiguration;
import com.project.dao.CheckinRoomMappingDAO;
import com.project.dto.*;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Friendship;
import com.project.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.String;
import javax.validation.Valid;
import java.sql.Date;
import java.util.ArrayList;
/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * This is the project's REST controller for accepting REST requests
 * and responding to them with JSON objects
 */

@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/CmpE275Team07Fall2015TermProject/v1/*")
@Import(AppConfiguration.class)
public class AppController extends WebMvcConfigurerAdapter {

    @Autowired
    BillingImplementation billingImplementation;

    @Autowired
    CheckinRoomMappingImplementation checkinRoomMappingImplementation;

    @Autowired
    GuestImplementation guestImplementation;

    @Autowired
    ReservationImplementation reservationImplementation;

    @Autowired
    RoomImplementation roomImplementation;

    @Autowired
    RoomPriceImplementation roomPriceImplementation;

    @Autowired
    UserImplementation userImplementation;

    /* Login APIs*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String createUser (@Valid @RequestBody UserDTO userDTO) {
        System.out.println("in /login "+userDTO.getPassword());
        String result = userImplementation.loginUser(userDTO);
        System.out.println(result);
        //return new ResponseEntity<>(UserImplementation.createUser(userDTOObject), HttpStatus.OK);
        return result;
    }

    /* Create Room API */

    @RequestMapping(value="/room", method = RequestMethod.POST,  headers = {"Content-type=application/json"})
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO room = roomImplementation.getRoom(roomDTO.getRoom_no());
        try{
            if(room != null){
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<>(roomImplementation.createRoom(roomDTO),HttpStatus.CREATED);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    }

    /*Delete a room*/
    @RequestMapping(value="/room/{room_no}", method = RequestMethod.DELETE,  headers = {"Content-type=application/json"})
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> deleteRoom(@Valid @PathVariable("room_no") Integer room_no) {
        RoomDTO room = roomImplementation.getRoom(room_no);
        try {
            if (room == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                roomImplementation.deleteRoom(room_no);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*Get Room by room_no*/

    @RequestMapping(value="/room/{room_no}", method= RequestMethod.GET, headers = {"Content-type=application/json"})
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRoom (@Valid @PathVariable Integer room_no){
        try{
            if(room_no == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(roomImplementation.getRoom(room_no),HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    }

    /*Get all rooms*/

    @RequestMapping(value="/room", method= RequestMethod.GET, headers = {"Content-type=application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<RoomDTO> getAllRooms(){
        List<RoomDTO> roomDTO = roomImplementation.getAllRooms();
        return roomDTO;
    }

    /*Update a room*/

//    @RequestMapping(value="/room", method= RequestMethod.PUT, headers = {"Content-type=application/json"})
//    //@ResponseStatus(HttpStatus.OK)
//    public @ResponseBody HttpStatus updateRoom(@Valid @RequestBody RoomDTO roomDTO){
//        Integer room_no = roomDTO.getRoom_no();
//        RoomDTO room = roomImplementation.getRoom(room_no);
//        if(room == null){
//           return HttpStatus.NOT_FOUND;
//        }else{
//            roomImplementation.updateRoom(roomDTO);
//            return HttpStatus.OK;
//        }
//
//    }

    @RequestMapping(value="/room", method= RequestMethod.PUT, headers = {"Content-type=application/json"})
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateRoom(@Valid @RequestBody RoomDTO roomDTO){
        Integer room_no = roomDTO.getRoom_no();
        RoomDTO room = roomImplementation.getRoom(room_no);
        try{
            if(room == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else{
                roomImplementation.updateRoom(roomDTO);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    }


    /* Check If Reservation exists for a guest.
    Also check the checkinRoomMapping to verify the number of rooms booked on one reservation Id*/

    @RequestMapping(value = "/reservationcheck/{reservation_token}/{license_no}", method=RequestMethod.GET, headers="Content-Type=application/json")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> reservationCheck(@Valid @PathVariable("reservation_token") String reservation_token, @PathVariable("license_no") String license_no){
        Integer guest_id = guestImplementation.getGuestByLicenseNo(license_no);
        try{
            if(guest_id == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }else{
                ReservationDTO reservationDTO = reservationImplementation.getReservationById(reservation_token,guest_id);
                //List<CheckinRoomMappingDTO> checkinRoomMappingDTOs = checkinRoomMappingImplementation.getReservationFromCheckinMapping(reservationDTO);
                return new ResponseEntity<>(checkinRoomMappingImplementation.getReservationFromCheckinMapping(reservationDTO),HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //ReservationDTO reservationDTO = reservationImplementation.getReservationById(reservation_token, guest_id);


    }

    /* Check in a Guest, updates the CheckinRoomMapping and Rooms tables */

    @RequestMapping(value="/checkinGuest", method=RequestMethod.PUT, headers="Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void checkinGuest(@Valid @RequestBody List<CheckinRoomMappingDTO> crmDTOList){

        checkinRoomMappingImplementation.checkinGuest(crmDTOList);
        for(CheckinRoomMappingDTO crm: crmDTOList){
            RoomDTO roomDTO = roomImplementation.getRoom(crm.getRoom_no());
            roomDTO.setRoom_status(RoomStatus.NA);
            roomImplementation.updateRoom(roomDTO);
        }
    }

    /* CheckOut a Guest, updates the Rooms table */

    @RequestMapping(value="/checkoutGuest/{room_no}", method=RequestMethod.PUT, headers="Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void checkoutGuest(@Valid @PathVariable("room_no") Integer room_no) {
        RoomDTO roomDTO = roomImplementation.getRoom(room_no);
            roomDTO.setRoom_status(RoomStatus.A);
            roomImplementation.updateRoom(roomDTO);
        }

    /*
      Implementation of report API
       */
    @RequestMapping(value = "/report/{reportDate}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRoomsReport(@Valid @PathVariable String reportDate){
        Date date= Date.valueOf(reportDate);
//>>>>>>> Stashed changes

        ReportDTO reportDTO = checkinRoomMappingImplementation.getOccupiedRooms(date);

        if(reportDTO == null){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(reportDTO,HttpStatus.OK);
        }

    }

}
