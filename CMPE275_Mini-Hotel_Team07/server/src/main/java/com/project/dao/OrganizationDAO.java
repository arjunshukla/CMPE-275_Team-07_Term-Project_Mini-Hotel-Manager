package com.project.dao;

import com.project.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar.
 * Organization Table Data Access Object
 */

@Transactional
public class OrganizationDAO implements InterfaceForOrganization{

    @Autowired
    HibernateTemplate hibernateTemplate;

    @Override
    public Organization save(Organization organization) {
        System.out.println("in orgDAO save");
        Integer org_id = (Integer) hibernateTemplate.save(organization);
        organization.setOrg_id(org_id);
        return organization;
    }

    @Override
    public void update(Organization organization) {
        hibernateTemplate.update(organization);
    }

    @Override
    public void delete(Organization organization) {
        hibernateTemplate.delete(organization);
    }

    @Override
    public Organization getOrganizationById(Integer org_id) {
        String query = "from Organization o where o.org_id = ?";
        @SuppressWarnings("unchecked")
        List<Organization> organizations = (List<Organization>) hibernateTemplate.find(query, org_id);

        if (organizations.isEmpty()) {
            return null;
        } else {
            return organizations.get(0);
        }
    }

    @Override
    public List<Organization> getAllOrganization() {
        return null;
    }
}
