package com.project.implementation;
import com.project.dto.ReportDTO;
import com.project.dao.InterfaceForCheckinRoomMapping;
import com.project.dto.CheckinRoomMappingDTO;
import com.project.entities.CheckinRoomMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.project.entities.Room;
import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * CheckinRoomMapping Implementation Class
 */

public class CheckinRoomMappingImplementation {
    @Autowired
    InterfaceForCheckinRoomMapping ReportDaoObject;
    @Autowired
    InterfaceForCheckinRoomMapping checkinRoomMappingDao;

    /*  Create Checkin */

  public CheckinRoomMappingDTO checkin(CheckinRoomMappingDTO checkinRoomMappingDTO) {

        CheckinRoomMapping checkinRoomMappingObject = new CheckinRoomMapping();

        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(checkinRoomMappingObject, checkinRoomMappingDTO);
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
      checkinRoomMappingObject = checkinRoomMappingDao.save(checkinRoomMappingObject);
      return checkinRoomMappingDTO;
  }

    @Transactional
    public List<CheckinRoomMappingDTO> getReservationFromCheckinMapping(Integer reservation_id) {
        List<CheckinRoomMapping> checkinList = checkinRoomMappingDao.getAllCheckins(reservation_id);
        //List<Room> allRoomsList = new ArrayList<Room>();
        List<CheckinRoomMappingDTO> checkinDTOList = new ArrayList<CheckinRoomMappingDTO>();
        if (!checkinList.isEmpty()) {
            //CheckinRoomMappingDTO checkinRoomMappingDTO = new CheckinRoomMappingDTO();
            for (CheckinRoomMapping checkroom : checkinList) {
                CheckinRoomMappingDTO checkinRoomMappingDTO = new CheckinRoomMappingDTO();
                checkinRoomMappingDTO.setReservation_id(checkroom.getReservation_id());
                checkinRoomMappingDTO.setRoom_no(checkroom.getRoom_no());
                checkinRoomMappingDTO.setGuest_count(checkroom.getGuest_count());
                checkinRoomMappingDTO.setCheckin_date(checkroom.getCheckin_date());
                checkinRoomMappingDTO.setCheckout_date(checkroom.getCheckout_date());
                checkinRoomMappingDTO.setMappingId(checkroom.getMappingId());
                checkinDTOList.add(checkinRoomMappingDTO);
            }
        }
        else
        {
            return null;
        }
        return checkinDTOList;
    }

    @Transactional
    public ReportDTO getOccupiedRooms(Date date) {
        ReportDTO reportDTOObject = new ReportDTO();

        List<Room> roomReportObject;
        roomReportObject = ReportDaoObject.getOccupiedRoomsData(date);

        if (roomReportObject != null) {
            List<Integer> roomIntegerList = new ArrayList<Integer>();

            for (Room room : roomReportObject) {
                roomIntegerList.add(room.getRoom_no());
            }

            reportDTOObject.setRooms(roomIntegerList);
            return reportDTOObject;
        }
        else{
            return null;
        }
    }
}
