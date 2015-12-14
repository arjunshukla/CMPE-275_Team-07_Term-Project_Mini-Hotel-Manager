package com.project.implementation;


import com.project.ENUMS.ReservationStatus;
import com.project.ENUMS.RoomType;
import com.project.dao.*;
import com.project.dto.BillingDTO;
import com.project.dto.GuestDTO;
import com.project.dto.ReservationDTO;
import com.project.dto.RoomDTO;
import com.project.entities.*;
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

    @Autowired
    InterfaceForUser userDAO;

    @Autowired
    InterfaceForRoom roomDAO;

    @Autowired
    InterfaceForRoomPrice roomPriceDAO;

    @Autowired
    InterfaceForBilling billingDAO;

    public final static long MILLISECONDS_IN_DAY = 24 * 60 * 60 * 1000;

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

    public ArrayList<HashMap<String,String>> calculateBill(String reservation_token, String user_name, Double discountDouble) {


        //check if service agent exist
        List<User> userName = userDAO.verifyUserByUserName(user_name);
        Integer user_id=userName.get(0).getUser_id();
        System.out.println("user_id: "+user_id);
        String userNameString = userName.get(0).getUser_name().toString();
        System.out.println("userNameString: "+userNameString);

        ArrayList<HashMap<String,String>> billList = new ArrayList<>();
        HashMap<String,String> billHash = new HashMap<>();

        if(user_name.equals(userNameString)){
            //fetch reservation id
            List<Reservation> reservRecords = reservationDAO.find(reservation_token);
            Integer reservation_id = reservRecords.get(0).getReservation_id();
            System.out.println("reservation_id: "+reservation_id);
            Integer guest_id  = reservRecords.get(0).getGuest_id();
            System.out.println("guest_id:: "+guest_id);
            List<Guest> emailRecord = guestDAO.findGuestEmailId(reservRecords.get(0).getGuest_id());
            String guestEmailID=emailRecord.get(0).getGuest_email();
            String guestName = emailRecord.get(0).getGuest_name();
            System.out.println("emailRecord: "+guestEmailID);
            System.out.println("guestName: "+guestName);
            System.out.println(reservRecords.size());

            System.out.println(reservRecords.get(0).getReservation_id());
            Integer s = reservRecords.get(0).getReservation_id();
            System.out.println("integer value: "+s);
            //Integer reservation_id = reservRecords.get(0).getReservation_id();

            //fetch checkin mapping table data
            ReservationDTO reservDTO = new ReservationDTO();
            reservDTO.setReservation_id(reservRecords.get(0).getReservation_id());
            List<CheckinRoomMapping> checkinMappingRecords = checkinRoomMappingDAO.findMappingForBilling(reservDTO);
            Date checkin_date = checkinMappingRecords.get(0).getCheckin_date();
            Date checkout_date=checkinMappingRecords.get(0).getCheckout_date();

            long daysStay = ((checkout_date.getTime()-checkin_date.getTime())/MILLISECONDS_IN_DAY)+1;//
            System.out.println("daysStay: "+daysStay);
            String noOfDaysStayed = String.valueOf(daysStay);//
            System.out.println("noOfDaysStayed: "+noOfDaysStayed);
            Double totalBill = 0.0;
            ArrayList<HashMap<String,String>> mailContent = new ArrayList<>();
            for(int i = 0;i<checkinMappingRecords.size();i++){
                System.out.println("Room("+i+") : "+checkinMappingRecords.get(i).getRoom_no());

                Integer room_no=checkinMappingRecords.get(i).getRoom_no();
                String roomNoInString = String.valueOf(room_no);//
                System.out.println("roomNoInString: "+roomNoInString);
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoom_no(room_no);

                Enum<RoomType> roomType = roomDAO.findRoomType(roomDTO);
                String type = roomType.toString();//
                if(type.equalsIgnoreCase("K")) type = "King";
                if(type.equalsIgnoreCase("Q"))type = "Queen";
                if(type.equalsIgnoreCase("SK")) type = "Smoking - King";
                if(type.equalsIgnoreCase("SQ"))type = "Smoking - Queen";
                System.out.println("room type: "+type);

                //Fetch price by room type
                Double roomPrice = roomPriceDAO.getRoomPrice(roomType);//
                System.out.println("roomPrice:::"+roomPrice);
                String roomPriceString = String.valueOf(roomPrice);
                System.out.println("roomPriceString: "+roomPriceString);
                Double billPerRoom = roomPrice*daysStay;//
                String billPerRoomString = String.valueOf(billPerRoom);
                System.out.println("billPerRoomString:"+billPerRoomString);
                totalBill = totalBill+billPerRoom;

                //set hash map
                HashMap<String,String> mailBody = new HashMap<>();
                mailBody.put("Room_Number",roomNoInString);
                mailBody.put("Room_Type",type);
                mailBody.put("Room_Price",roomPriceString);
                mailBody.put("NoOfDays",noOfDaysStayed);
                mailBody.put("BillPerRoom",billPerRoomString);

                mailContent.add(mailBody);
                //end hash map

                System.out.println("-------");
            }
            System.out.println("totalBill::"+totalBill);
            String templateForEmailBody="";
            for(HashMap<String,String> h: mailContent){
                System.out.println("room_numer"+h.get("Room_Number"));
                templateForEmailBody=templateForEmailBody+"<tr><td>"+h.get("Room_Number")+"</td><td>"+h.get("Room_Type")+"</td><td>"+h.get("Room_Price")+"</td><td>"+h.get("NoOfDays")+"</td><td>"
                        +h.get("BillPerRoom")+"</td><tr><br>";




            }
            System.out.println("templateForEmailBody"+templateForEmailBody);
            Integer bill_no = guest_id;
            String bill_no_String = String.valueOf(bill_no);
            System.out.println("discount"+discountDouble);
            //String bill_no_String = String.valueOf(discountDouble);

            Double totalDiscount = (discountDouble/100)*totalBill;
            String totalDiscountString =String.valueOf(totalDiscount);

            Double amountPayable =totalBill-totalDiscount;
            String amountPayableString = String.valueOf(amountPayable);



            //ReservationDTO res = new ReservationDTO();
            BillingDTO billingDTO = new BillingDTO();
            billingDTO.setBill_no(bill_no);
            billingDTO.setReservation_id(reservation_id);
            billingDTO.setUser_id(user_id);
            billingDTO.setDiscount(totalDiscount);
            billingDTO.setAmount(amountPayable);

            Reservation resObj = new Reservation();
            resObj.setReservation_id(reservation_id);

            User userObj = new User();
            userObj.setUser_id(user_id);

            Billing billObject = new Billing();
            billObject.setBill_no(bill_no);
            billObject.setReservation(resObj);
            billObject.setUser(userObj);
            billObject.setDiscount(totalDiscount);
            billObject.setAmount(amountPayable);

//            try {
//                org.apache.commons.beanutils.BeanUtils.copyProperties(billObject, billingDTO);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }

            Integer billID = billingDAO.insertBillData(billObject);
            System.out.println("Bill id after Insert: "+billID);

            if(billID!=null){
                //email

                final String from = "express.minihotel@gmail.com";
                String to =guestEmailID;
                String body = "Hello "+guestName+",<br><br>You bill details are as follows:<br>" +
                        "<table border='1' style=\"border-collapse: collapse;\"><tr>" +
                        "<td><b>Room Number</b></td>" +
                        "<td><b>Room Type</b></td>" +
                        "<td><b>Price per day ($)</b></td>" +
                        "<td><b>Duration of Stay (days)</b></td>" +
                        "<td><b>Bill for Room ($)</b></td></tr>"+templateForEmailBody+"</table><br>Net Bill: $"+totalBill+
                        "<br><br>" +
                        "Discount: $"+totalDiscountString+"<br></br>" +
                        "<br/><b>Total Bill Paid($): <font color='red'>"+amountPayable+"</font></b><br></br><br></br>Thank You for choosing Express Hotel.<br/><br/><i>--Express Hotel</i>";

                String subject="Express Hotel - Bill Receipt (Receipt No : "+bill_no_String+")";
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

                //email
                billHash.put("bill_amount",amountPayableString);
                billHash.put("bill_body", body);
                billList.add(billHash);
                //return billList;
            }
        }
        else
        {
            //username doesnot exist
            billHash.put("bill_amount","User does not exist");
            billList.add(billHash);
            //return billList;
        }


        return billList;

    }

    @Transactional
    public Reservation getReservationByToken(String reservation_token) {
        return reservationDAO.getReservationByToken(reservation_token);
    }


    /*
    ArrayList list
    * for(iterate){e
    * HashMap h =
    *
    * list.add(h)
    * }
    * String mailBody
    * (for list.size)
    * {
    * hashmap h  = list.get(i);
    * tempString = "RoomNo" + h.get(roomNo) + "room Price "+ h.get;+"\n"
    * mailBody = mailBody.append(tempString)
    * }
    *
    * mailBody = mailBody.append(totalBill)
    *
    *
    *
    * */
}
