package com.project.ENUMS;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * UserType ENUM
 */

public enum UserType {
    A{
        @Override
        public String toString() {
            return "Admin";
        }
    }, SA {
        @Override
        public String toString() {
            return "Service Agent";
        }
    }

    // A - Admin
    // SA - ServiceAgent
}