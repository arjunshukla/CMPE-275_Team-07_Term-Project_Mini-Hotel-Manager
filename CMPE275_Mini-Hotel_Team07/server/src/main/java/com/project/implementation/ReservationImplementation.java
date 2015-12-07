package com.project.implementation;


import com.project.ENUMS.ReservationStatus;
import com.project.dao.InterfaceForCheckinRoomMapping;
import com.project.dao.InterfaceForGuest;
import com.project.dao.InterfaceForReservation;
import com.project.dto.GuestDTO;
import com.project.dto.ReservationDTO;
import com.project.entities.CheckinRoomMapping;
import com.project.entities.Guest;
import com.project.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.*;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Reservation Implementation Class
 */

public class ReservationImplementation {

    @Autowired
    InterfaceForReservation reservationDAO;

    @Autowired
    InterfaceForCheckinRoomMapping checkinRoomMappingDAO;

    @Autowired
    InterfaceForGuest guestDAO;

    public String makeReservation(GuestDTO guestDTO, Integer no_of_rooms_Int, Date checkin_date, Date checkout_date) {

        String room_selected = guestDTO.getRoom_no_selected();
        Guest guestObject = new Guest();

        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(guestObject, guestDTO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        guestObject = guestDAO.save(guestObject);


        String token_no = guestObject.getLicense_no() + guestObject.getGuest_id();

        java.util.Date todayUtilDate = Calendar.getInstance().getTime();//2015-12-05
        java.sql.Date todaySqlDate = new java.sql.Date(todayUtilDate.getTime());

        Reservation reservationObject = new Reservation();
//        reservationObject.setReservation_id(0);
        reservationObject.setGuest_id(guestObject.getGuest_id());
        reservationObject.setReservation_token(token_no);
        reservationObject.setReservation_date(todaySqlDate);
        reservationObject.setReservation_status(ReservationStatus.R);
        reservationObject = reservationDAO.save(reservationObject);
       // Integer reservation_id = reservationObject.getReservation_id();

        String[] resultStringArray = guestDTO.getRoom_no_selected().split(",");
        ArrayList<Integer> arrRooms = new ArrayList<Integer>();
        for (String str : resultStringArray) {
            arrRooms.add(Integer.parseInt(str));
            CheckinRoomMapping checkinRoomMappingObject = new CheckinRoomMapping();
            checkinRoomMappingObject.setReservation(reservationObject);
            checkinRoomMappingObject.setRoom_no(Integer.parseInt(str));
            checkinRoomMappingObject.setCheckin_date(checkin_date);
            checkinRoomMappingObject.setCheckout_date(checkout_date);
            checkinRoomMappingObject.setGuest_count(0);
            checkinRoomMappingObject.setMappingId(0);
            checkinRoomMappingDAO.save(checkinRoomMappingObject);

        }
        //add email code start here

        final String from = "express.minihotel@gmail.com";
        String to = guestObject.getGuest_email();
        String body = "Hello "+guestObject.getGuest_name()+
                ", <br/><br></br>Your reservation has been confirmed.Your reservation ID is <font color='red'><b>"+token_no+
                "</b></font>.</br>Please bring the Driving License for verification at the time of Check In.</br><br></br>" +
                "<b>Note:</b>If you want to cancel the reservation, " +
                "please <a href=\'http://localhost:8080/CmpE275Team07Fall2015TermProject/v1/cancelReservation?reservation_token="+token_no+"\'>Click Here</a><br></br>--<i>Express Hotel</i>";
        String subject = "Express Hotel Reservation Confirmation <"+token_no +">";

        final String password = "Minihotel@2015";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        try {
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(from));
            email.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(to));
            email.setSubject(subject);
            email.setContent(body, "text/html");
            Transport.send(email);



        } catch (MessagingException e) {

            throw new RuntimeException(e);

        }
        //email code end here

        return token_no;
    }

    @Transactional
    public ReservationDTO getReservationById(String reservation_token, Integer guest_Id){
      ReservationDTO reservationDTO = new ReservationDTO();
        Reservation reservationEntityObject = reservationDAO.getReservationByLicense_Token(reservation_token, guest_Id);
        if(reservationEntityObject != null){
            reservationDTO.setReservation_id(reservationEntityObject.getReservation_id());
            reservationDTO.setGuest_id(reservationEntityObject.getGuest_id());
            reservationDTO.setReservation_token(reservationEntityObject.getReservation_token());
            reservationDTO.setReservation_date(reservationEntityObject.getReservation_date());
            reservationDTO.setReservation_status(reservationEntityObject.getReservation_status());
            return reservationDTO;//.getReservation_id();
        }
        else{
            return null;
        }
    }

    public boolean isValidReservation(Integer reservation_id) {
         if(reservationDAO.getReservationById(reservation_id) != null){
             return true;
         }
        return false;
    }

    public ArrayList<HashMap<String,String>> cancelReservation(ReservationDTO reservationDTO) {
        System.out.println("reservation token - cancelReservation: "+reservationDTO.getReservation_token());
        ArrayList<HashMap<String,String>> statusChanged = new ArrayList<>();


        List<Reservation> reservObject = reservationDAO.find(reservationDTO.getReservation_token());
        System.out.println("res id: "+reservObject.get(0).getReservation_id());
        System.out.println("reserv status: "+reservObject.get(0).getReservation_status());

        if((reservObject.get(0).getReservation_id()!=null)&&(reservObject.get(0).getReservation_status().toString().equals("R"))){
            Integer checkNumberOfRooms = checkinRoomMappingDAO.deleteReservationRecords(reservObject.get(0).getReservation_id());
            if(checkNumberOfRooms>0){
                Integer reservationStatusChanged = reservationDAO.update(reservObject.get(0).getReservation_id());
                System.out.println(reservationStatusChanged);
                if(reservationStatusChanged>0){

                    HashMap<String,String> status = new HashMap<>();
                    status.put("status","Cancelled");
                    statusChanged.add(status);
                }
            }
        }

        return statusChanged;
    }
}
