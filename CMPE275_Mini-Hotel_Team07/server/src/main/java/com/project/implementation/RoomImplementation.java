package com.project.implementation;

import com.project.dao.InterfaceForRoom;
import com.project.dto.RoomDTO;
import com.project.entities.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Room Implementation Class
 */

public class RoomImplementation {

    @Autowired
    InterfaceForRoom roomDAO;

     /* Create Room*/

    public RoomDTO createRoom(RoomDTO roomDTO){

        Room roomEntityObject = new Room();

        try
        {
            org.apache.commons.beanutils.BeanUtils.copyProperties(roomEntityObject, roomDTO);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        roomEntityObject = roomDAO.save(roomEntityObject);
        return roomDTO;
    }

    /* Get Room by Room No*/

    @Transactional

    public RoomDTO getRoom(Integer room_no){
        RoomDTO roomDTO = new RoomDTO();
        Room roomEntityObject = roomDAO.getRoomByNo(room_no);
        if(roomEntityObject!= null){
            roomDTO.setRoom_no(roomEntityObject.getRoom_no());
            roomDTO.setRoom_type(roomEntityObject.getRoom_type());
            roomDTO.setRoom_status(roomEntityObject.getRoom_status());
            return roomDTO;
        }
        else{
            return null;
        }
    }

    public Room getRoomByNumber(Integer room_no){

        Room room = roomDAO.getRoomByNo(room_no);
        if(room!= null)
            return room;
        else
            return null;
    }

    /*Get all rooms*/

    @Transactional
    public List<RoomDTO> getAllRooms(){
         List<Room> allRoomsList = roomDAO.getAllRooms();
        //List<Room> allRoomsList = new ArrayList<Room>();
        List<RoomDTO> allRoomsDTOList = new ArrayList<RoomDTO>();
        if(!allRoomsList.isEmpty()) {

            for (Room room : allRoomsList) {
                RoomDTO roomDTO = new RoomDTO();
                roomDTO.setRoom_no(room.getRoom_no());
                roomDTO.setRoom_type(room.getRoom_type());
                roomDTO.setRoom_status(room.getRoom_status());
                allRoomsDTOList.add(roomDTO);
            }
        }
        else{
            return null;
        }
        return allRoomsDTOList;
    }


    /*Update a room*/

    @Transactional
    public void updateRoom(RoomDTO roomDTO){
        Room roomEntityObject = new Room();
        try
        {
            org.apache.commons.beanutils.BeanUtils.copyProperties(roomEntityObject, roomDTO);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        catch (InvocationTargetException e)
        {
            e.printStackTrace();
        }

        roomDAO.update(roomEntityObject);

    }

    /*Delete a room*/
    @Transactional
    public void deleteRoom(Room room) {
        roomDAO.delete(room);
    }

}
