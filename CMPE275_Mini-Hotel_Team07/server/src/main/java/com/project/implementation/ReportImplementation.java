package com.project.implementation;

import com.project.dao.InterfaceForReport;
import com.project.dto.ReportDTO;
import com.project.entities.CheckinRoomMapping;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;

/**
 * Created by PankajPai on 11/22/2015.
 */
public class ReportImplementation {

    /*
    @Autowired
    InterfaceForReport ReportDao;


    @Transactional
    public ReportDTO getRooms(Date date) {
        ReportDTO reportDTOObject = new ReportDTO();

        System.out.println("Start of program");
        System.out.println(date);

        List<CheckinRoomMapping> report;
        report = ReportDao.getRooms(date);

        System.out.println(report);

        List<Integer> r = null;

        for (int i = 0; i < report.size(); i++) {
            r.add(Integer.parseInt(String.valueOf(report.get(i))));
        }

        reportDTOObject.setRooms(r);

        return reportDTOObject;
    }
    */
}
