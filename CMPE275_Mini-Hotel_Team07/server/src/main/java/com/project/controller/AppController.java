package com.project.controller;

import com.project.configuration.AppConfiguration;
import com.project.dto.*;
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

//<<<<<<< Updated upstream
//<<<<<<< Updated upstream
//<<<<<<< Updated upstream
//<<<<<<< Updated upstream

   /* @RequestMapping(value = { "/", "/welcome**" }, method = RequestMethod.GET)
    public ModelAndView defaultPage() {

        ModelAndView model = new ModelAndView();
        model.addObject("title", "Spring Security + Hibernate Example");
        model.addObject("message", "This is default page!");
        model.setViewName("hello");
        return model;

    }*/

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public String login(@RequestParam(value = "error", required = false) String error,
//                              @RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
//
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
//        }
//
//        if (logout != null) {
//            model.addObject("msg", "You've been logged out successfully.");
//        }
//        model.setViewName("login");
//
//        return model;
//
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String createUser (@Valid @RequestBody UserDTO userDTO) {
        System.out.println("in /login "+userDTO.getPassword());
        String result = userImplementation.loginUser(userDTO);
        System.out.println(result);
        //return new ResponseEntity<>(UserImplementation.createUser(userDTOObject), HttpStatus.OK);
        return result;
    }

//        personDTOObject.setEmail(email);
//        personDTOObject.setDescription(description);
//        personDTOObject.setAddressDTO(addressDTOObject);
//        personDTOObject.setOrg_id(org_id);

//        try
//        {
//            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty())
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            else
//                return new ResponseEntity<>(personImplementation.createPerson(personDTOObject), HttpStatus.OK);
//        }
//        catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//  }



//    @Autowired
//    PersonImplementation personImplementation;
//
//    @Autowired
//    OrganizationImplementation organizationImplementation;
//
//    @Autowired
//    FriendshipImplementation friendshipImplementation;
//=======
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
