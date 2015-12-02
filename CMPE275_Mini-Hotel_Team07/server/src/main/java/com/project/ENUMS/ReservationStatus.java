package com.project.ENUMS;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * ReservationStatus ENUM
 */

public enum ReservationStatus {
    R("R"),C("C"),O("O");
    private String type;
    private ReservationStatus(String type){this.type=type;}
    public static ReservationStatus getEnum(String code){
        switch(code){
            case "R": return R;

            case "C" : return  C;

            case "O" : return O;


        }
        return  null;
    }

    // R - reserved
    // C- cancelled
    // O - occupied
}