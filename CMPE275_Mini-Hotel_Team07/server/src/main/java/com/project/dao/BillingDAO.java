package com.project.dao;

import com.project.entities.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Billing Data Access Object
 */

@Transactional
public class BillingDAO implements InterfaceForBilling {

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Billing save(Billing bill) {
        return null;
    }

    @Override
    public void update(Billing bill) {

    }

    @Override
    public void delete(Billing bill) {

    }

    @Override
    public Billing getBillByNo(Integer bill_no) {
        return null;
    }

    @Override
    public List<Billing> getAllBills() {
        return null;
    }

    @Override
    public Integer insertBillData(Billing billing) {
        Integer bill_id = (Integer)hibernateTemplate.save(billing);
        return bill_id;
    }
}
