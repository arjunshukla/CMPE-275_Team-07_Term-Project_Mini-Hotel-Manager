
package com.project.dao;

import java.util.List;
import javax.transaction.Transactional;

import com.project.entities.Organization;
import com.project.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar.
 * Person Table Data Access Object
 */

@Transactional
public class PersonDAO implements InterfaceForPersons{

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Person save(Person person) {
        System.out.println("in DAO save of PersonDetailsDAO class with personObj: "+person.getPerson_id());
        Integer person_id = (Integer)hibernateTemplate.save(person);
        person.setPerson_id(person_id);
        System.out.println("in DAO save of PersonDetailsDAO class with personObj: "+person.getPerson_id());
        return person;
    }

    @Override
    public void update(Person person) {

        hibernateTemplate.update(person);
    }

    @Override
    public void delete(Person person) {

        hibernateTemplate.delete(person);

    }

    @Override
    public Person getPersonById(Integer person_id) {
        String query = "from Person p where p.person_id = ?";
        @SuppressWarnings("unchecked")
        List<Person> persons = (List<Person>) hibernateTemplate.find(query,person_id);
        if (persons.isEmpty()) {
            return null;
        } else {
            System.out.println();
            return persons.get(0);
        }
    }


    @Override
    public Person getPersonByEmail(String email) {
        String query = "from Person p where p.email = ?";
        @SuppressWarnings("unchecked")
        List<Person> persons = (List<Person>) hibernateTemplate.find(query,email);
        if (persons.isEmpty()) {
            return null;
        } else {
            System.out.println();
            return persons.get(0);
        }
    }

    @Override
    public Organization getOrganizationById(Integer org_id) {
        System.out.println("orgid is: "+org_id);
        String query = "from Organization o where o.org_id = ?";
        @SuppressWarnings("unchecked")
        List<Organization> organizations = (List<Organization>) hibernateTemplate.find(query, org_id);

        if (organizations.isEmpty()) {
            return null;
        } else {
            return organizations.get(0);
        }
    }

}
