package com.project.controller;

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
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody RoomDTO createRoom (@Valid @RequestBody RoomDTO roomDTO) {
        return roomImplementation.createRoom(roomDTO);
    }

    /*Get Room by room_no*/

    @RequestMapping(value="/room/{room_no}", method= RequestMethod.GET, headers = {"Content-type=application/json"})
    @ResponseStatus(HttpStatus.OK)

    public @ResponseBody RoomDTO getRoom (@Valid @PathVariable Integer room_no){
        RoomDTO roomDTo = roomImplementation.getRoom(room_no);
        return roomDTo;
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
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody RoomDTO updateRoom(@Valid @RequestBody RoomDTO roomDTO){
//        return roomImplementation.updateRoom(roomDTO);
//    }

    /*4. Guest service
        a. A service agent can check in guests.
        b. If a guest does not have a reservation, the service agent can create a room order on the fly.
        c. If a guest has a reservation, he must provide the reservation ID and driver license.
           The service agent will look up the reservation by the reservation ID, and then turn the reservation to a room order.
        d. The service agent can make modifications to a reservation when turning it into a room order,
           including assign/reassign and add/remove rooms from the order/reservation.
        e. The service agent can also optionally apply a discount (10% to 30%) to the whole order.
           An admin can apply a discount upto 100%.
        f. Upon checking, the guest must also provide the # of total persons to stay in each room.
        g. The guest can check out on the planned checkout date or sooner.
           The bill will be charged on a daily basis; i.e., once checked in, it’s charged for at least a day.
        h. Upon checkout, a receipt with the billing details (room charges, discounts, duration of the stay, etc) will be mailed to the guest.
    * */

    /* Check If Reservation exists for a guest.
    Also check the checkinRoomMapping to verify the number of rooms booked on one reservation Id*/

    @RequestMapping(value = "/reservationcheck/{reservation_token}/{license_no}", method=RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<CheckinRoomMappingDTO> reservationCheck(@Valid @PathVariable("reservation_token") String reservation_token, @PathVariable("license_no") String license_no){
        Integer guest_id = guestImplementation.getGuestByLicenseNo(license_no);
        //ReservationDTO reservationDTO = reservationImplementation.getReservationById(reservation_token, guest_id);

        Integer reservation_id = reservationImplementation.getReservationById(reservation_token,guest_id);
        List<CheckinRoomMappingDTO> checkinRoomMappingDTOs = checkinRoomMappingImplementation.getReservationFromCheckinMapping(reservation_id);
        return checkinRoomMappingDTOs;
    }

    /* Check in for the guest */

    @RequestMapping(value = "/checkinGuest", method=RequestMethod.POST, headers="Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody CheckinRoomMappingDTO guestCheckin (@Valid @RequestBody CheckinRoomMappingDTO checkinRoomMappingDTO) {
        return checkinRoomMappingImplementation.checkin(checkinRoomMappingDTO);
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
