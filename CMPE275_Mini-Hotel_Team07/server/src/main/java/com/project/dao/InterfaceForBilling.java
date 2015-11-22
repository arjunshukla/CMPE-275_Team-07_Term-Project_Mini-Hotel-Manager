package com.project.dao;

import com.project.entities.Billing;

import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Interface for Billing
 */

public interface InterfaceForBilling {

    Billing save(Billing bill);
    public void update(Billing bill);
    public void delete(Billing bill);
    public Billing getBillByNo(Integer bill_no);
    public List<Billing> getAllBills();
}