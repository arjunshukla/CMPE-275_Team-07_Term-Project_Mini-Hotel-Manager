package com.project.dao;

import com.project.entities.Organization;
import com.project.entities.Person;

import java.util.List;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar.
 * Interface for Person
 */

public interface InterfaceForPersons {

    Person save(Person personObject);

    public void update(Person person);

    public void delete(Person person);

    public Person getPersonById(Integer personID);

    Person getPersonByEmail(String email);

    Organization getOrganizationById(Integer org_id);
}