package com.project.controller;

import com.project.ENUMS.RoomStatus;
import com.project.configuration.AppConfiguration;
import com.project.dto.*;
import com.project.entities.Reservation;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    /*Billing*/
    @RequestMapping(value = "/billing",method=RequestMethod.GET, headers="Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ArrayList<HashMap<String,String>> generateBill
    (
     @Valid @RequestParam(value="reservation_token") String reservation_token,
     @Valid @RequestParam(value="user_name") String user_name,
     @Valid @RequestParam(value="discount") String discount){
        //System.out.println("room_no"+room_no);
        System.out.println("reservation_token"+reservation_token);
        System.out.println("user_name"+user_name);
        System.out.println("discount"+discount);

        Double discountDouble = Double.parseDouble(discount);
        ArrayList<HashMap<String,String>> sendBill = reservationImplementation.calculateBill(reservation_token,user_name,discountDouble);

        return sendBill;
    }

    /* Login APIs*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String createUser (@Valid @RequestBody UserDTO userDTO) {
        System.out.println("in /login "+userDTO.getPassword());
        String result = userImplementation.loginUser(userDTO);
        System.out.println(result);
        //return new ResponseEntity<>(UserImplementation.createUser(userDTOObject), HttpStatus.OK);
        return result;
    }

    /* Cancel Reservation */
    @RequestMapping(value = "/cancelReservation", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ArrayList<HashMap<String,String>> cancelReservation
    (@Valid @RequestParam(value = "reservation_token") String reservation_token){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setReservation_token(reservation_token);

    //(@Valid @RequestBody ReservationDTO reservationDTO){
        System.out.println("reservation token: "+reservationDTO.getReservation_token());
        ArrayList<HashMap<String,String>> reservDTO = reservationImplementation.cancelReservation(reservationDTO);
        return reservDTO;
    }
    /* Search for available rooms

     * checkin_date
     * checkout_date
     * no_of_rooms
     * room_type

   */

    @RequestMapping(value = "/searchRoomAvailability", method = RequestMethod.POST,
            headers="Content-Type=application/json")

    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ArrayList<HashMap<String, String>> fetchAvailableReservations
            (@Valid @RequestBody CheckinRoomMappingDTO checkinDTO){
        System.out.println(checkinDTO.getCheckin_date());
        System.out.println(checkinDTO.getCheckout_date());
//        String s = checkinDTO.

        // if() room is already reserved for given dates then return null
        //else proceed with following:
        ArrayList<HashMap<String, String>> checkingDTO = checkinRoomMappingImplementation.getAvailableReservations(checkinDTO);
        return checkingDTO;
    }


    /* Add a room*/
    @RequestMapping(value="/room", method = RequestMethod.POST,  headers = {"Content-type=application/json"})
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRoom(@Valid @RequestBody RoomDTO roomDTO) {
        RoomDTO room = roomImplementation.getRoom(roomDTO.getRoom_no());
        try{
            if(room != null){
                return new ResponseEntity<Object>(HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<Object>(roomImplementation.createRoom(roomDTO),HttpStatus.CREATED);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    }

    /*
    Make reservation

     */

    @RequestMapping(value = "/makeReservations", method = RequestMethod.POST
            ,headers="Content-Type=application/json")

    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    String makeReservations(@Valid @RequestBody GuestDTO guestDTO,
                            @Valid @RequestParam(value = "checkin_date") String checkin_date,
                            @Valid @RequestParam(value = "checkout_date") String checkout_date,
                            @Valid @RequestParam(value = "no_of_rooms") String no_of_rooms){

        Date converted_checkin_date = Date.valueOf(checkin_date);
        Date converted_checkout_date = Date.valueOf(checkout_date);
        Integer no_of_rooms_Int = Integer.parseInt(no_of_rooms);

        System.out.println("rooms selected: "+guestDTO.getRoom_no_selected());

        System.out.println(guestDTO.getGuest_name());
        System.out.println(guestDTO.getGuest_email());
        System.out.println(guestDTO.getLicense_no());
        System.out.println(guestDTO.getStreet());
        System.out.println(guestDTO.getCity());
        System.out.println(guestDTO.getState());
        System.out.println(guestDTO.getZip());
        System.out.println(converted_checkin_date);
        System.out.println(converted_checkout_date);
        System.out.println(no_of_rooms_Int);

        try{
            return reservationImplementation.makeReservation(guestDTO,no_of_rooms_Int,converted_checkin_date,converted_checkout_date);
        }
        catch(Exception e){
            return null;
        }

//        return reservationDTO;
    }

    /*Delete a room*/
    @RequestMapping(value="/room/{room_no}", method = RequestMethod.DELETE,  headers = {"Content-type=application/json"})
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> deleteRoom(@Valid @PathVariable("room_no") Integer room_no) {
        RoomDTO room = roomImplementation.getRoom(room_no);
        try {
            if (room != null) {
                List<Integer> roomsList = checkinRoomMappingImplementation.getRoom(room_no);
                if(roomsList==null){
                    roomImplementation.deleteRoom(roomImplementation.getRoomByNumber(room_no));
                    return new ResponseEntity<Object>(HttpStatus.OK);
                }else{
                    return new ResponseEntity<Object>(HttpStatus.CONFLICT);
                }
                //return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    /*Get Room by room_no*/

    @RequestMapping(value="/room/{room_no}", method= RequestMethod.GET, headers = {"Content-type=application/json"})
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRoom (@Valid @PathVariable Integer room_no){
        try{
            if(room_no == null){
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<Object>(roomImplementation.getRoom(room_no),HttpStatus.OK);
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
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }
            else{
                roomImplementation.updateRoom(roomDTO);
                return new ResponseEntity<Object>(HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    }

    /*
    @RequestMapping(value="/room/{room_no}", method= RequestMethod.DELETE, headers = {"Content-type=application/json"})
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteRoom(@Valid @PathVariable Integer room_no){


        1. check if room exists
        2. if exists then check if reserved from checkinMappingTable
        3. So if the room number is valid and not reserved, then delete it

        Room room = roomImplementation.getRoomByNumber(room_no);
        try{
            if(room == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
//                checkinRoomMappingImplementation.checkIfRoomisReserved(room);

                roomImplementation.deleteRoom(room.getRoom_no());
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

    } */

    /* Check If Reservation exists for a guest.
    Also check the checkinRoomMapping to verify the number of rooms booked on one reservation Id*/

    @RequestMapping(value = "/reservationcheck/{reservation_token}/{license_no}", method=RequestMethod.GET, headers="Content-Type=application/json")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> reservationCheck(@Valid @PathVariable("reservation_token") String reservation_token, @PathVariable("license_no") String license_no){
        Integer guest_id = guestImplementation.getGuestByLicenseNo(license_no);
        try{
            if(guest_id == null){
                return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
            }else{
                ReservationDTO reservationDTO = reservationImplementation.getReservationById(reservation_token,guest_id);
                //List<CheckinRoomMappingDTO> checkinRoomMappingDTOs = checkinRoomMappingImplementation.getReservationFromCheckinMapping(reservationDTO);
                return new ResponseEntity<Object>(checkinRoomMappingImplementation.getReservationFromCheckinMapping(reservationDTO),HttpStatus.OK);
            }
        }catch(Exception e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        //ReservationDTO reservationDTO = reservationImplementation.getReservationById(reservation_token, guest_id);


    }

    /* Check in a Guest, updates the CheckinRoomMapping and Rooms tables */

    @RequestMapping(value="/checkinGuest", method=RequestMethod.PUT, headers="Content-Type=application/json")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> checkinGuest(@Valid @RequestBody List<CheckinRoomMappingDTO> crmDTOList) {

        checkinRoomMappingImplementation.checkinGuest(crmDTOList);
        try {
            for (CheckinRoomMappingDTO crm : crmDTOList) {
                RoomDTO roomDTO = roomImplementation.getRoom(crm.getRoom_no());
                roomDTO.setRoom_status(RoomStatus.NA);
                roomImplementation.updateRoom(roomDTO);

            }
        } catch (Exception e) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

//    /* CheckOut a Guest, updates the Rooms table */
//
//    @RequestMapping(value="/checkoutGuest/{room_no}", method=RequestMethod.PUT, headers="Content-Type=application/json")
//    @ResponseStatus(HttpStatus.OK)
//    public @ResponseBody ResponseEntity<?> checkoutGuest(@Valid @PathVariable("room_no") Integer room_no) {
//        RoomDTO roomDTO = roomImplementation.getRoom(room_no);
//        try{
//            roomDTO.setRoom_status(RoomStatus.A);
//            roomImplementation.updateRoom(roomDTO);
//        } catch(Exception e){
//            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<Object>(HttpStatus.OK);
//        }

     /* CheckOut a Guest, updates the Rooms table */

    @RequestMapping(value="/checkoutGuest/{reservation_token}", method=RequestMethod.PUT, headers="Content-Type=application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<?> checkoutGuest(@Valid @PathVariable("reservation_token") String reservation_token) {
        Reservation reservation = reservationImplementation.getReservationByToken(reservation_token);
        if(reservation!=null){
        List<Integer> roomsList = checkinRoomMappingImplementation.getRoomNo(reservation);
            for(int i=0; i<roomsList.size();i++) {

                Integer room_no = roomsList.get(i);
                RoomDTO roomDTO = roomImplementation.getRoom(room_no);
                try {
                    roomDTO.setRoom_status(RoomStatus.A);
                    roomImplementation.updateRoom(roomDTO);
                } catch (Exception e) {
                    return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
                }
            }
                return new ResponseEntity<Object>(HttpStatus.OK);
            }else{
                return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
            }
    }


    /*
      Implementation of report API
       */
    @RequestMapping(value = "/report/{reportDate}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRoomsReport(@Valid @PathVariable String reportDate){
        Date date= Date.valueOf(reportDate);
        HashMap<String, List<Integer>> map=checkinRoomMappingImplementation.getOccupiedRooms(date);

        if(map == null){
            return new ResponseEntity<Object>(null,HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<Object>(map,HttpStatus.OK);
        }
    }
}