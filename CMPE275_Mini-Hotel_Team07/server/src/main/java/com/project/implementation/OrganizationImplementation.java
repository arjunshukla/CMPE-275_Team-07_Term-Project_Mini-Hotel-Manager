package com.project.implementation;

import com.project.dao.InterfaceForOrganization;
import com.project.dto.AddressDTO;
import com.project.dto.OrganizationDTO;
import com.project.entities.Organization;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Organization implementation class
 */

public class OrganizationImplementation {
    @Autowired
    InterfaceForOrganization organizationDao;

	 /*  Create Organization*/
    public OrganizationDTO createOrganization(OrganizationDTO organizationDTObject) {

        Organization organizationObject = new Organization();

        try { org.apache.commons.beanutils.BeanUtils.copyProperties(organizationObject, organizationDTObject);}
        catch (IllegalAccessException e) { e.printStackTrace(); }
        catch (InvocationTargetException e) { e.printStackTrace(); }

        organizationObject.setOrg_name(organizationDTObject.getOrg_name());
        organizationObject.setOrg_description(organizationDTObject.getOrg_description());

        organizationObject.setCity(organizationDTObject.getAddressDTO().getCity());
        organizationObject.setStreet(organizationDTObject.getAddressDTO().getStreet());
        organizationObject.setState(organizationDTObject.getAddressDTO().getState());
        organizationObject.setZip(organizationDTObject.getAddressDTO().getZip());

        organizationObject = organizationDao.save(organizationObject);
        organizationDTObject.setOrg_id(organizationObject.getOrg_id());
        return organizationDTObject;
    }

    @Transactional
    public OrganizationDTO getOrganizationbyId(Integer org_id) {
        OrganizationDTO organizationDTOObject = new OrganizationDTO();

        Organization organization = organizationDao.getOrganizationById(org_id);
        if (organization != null) {
            AddressDTO addressDTO = new AddressDTO();

            addressDTO.setStreet(organization.getStreet());
            addressDTO.setCity(organization.getCity());
            addressDTO.setState(organization.getState());
            addressDTO.setZip(organization.getZip());

            organizationDTOObject.setOrg_id(organization.getOrg_id());
            organizationDTOObject.setOrg_name(organization.getOrg_name());
            organizationDTOObject.setOrg_description(organization.getOrg_description());
            organizationDTOObject.setAddressDTO(addressDTO);

            return organizationDTOObject;
        }else {
            return null;
        }
    }

    public OrganizationDTO updateOrganization(OrganizationDTO organizationDTOObject) {
        Organization organizationObject = organizationDao.getOrganizationById(organizationDTOObject.getOrg_id());
        if (organizationObject != null){

            try {
                org.apache.commons.beanutils.BeanUtils.copyProperties(organizationObject, organizationDTOObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            organizationObject.setOrg_name(organizationDTOObject.getOrg_name());
            organizationObject.setOrg_description(organizationDTOObject.getOrg_description());

            organizationObject.setCity(organizationDTOObject.getAddressDTO().getCity());
            organizationObject.setStreet(organizationDTOObject.getAddressDTO().getStreet());
            organizationObject.setState(organizationDTOObject.getAddressDTO().getState());
            organizationObject.setZip(organizationDTOObject.getAddressDTO().getZip());

            organizationDao.update(organizationObject);
            return organizationDTOObject;
        }
        return null;
    }

    public OrganizationDTO deleteOrganizationbyId(Integer org_id) {
        OrganizationDTO organizationDTOObject = getOrganizationbyId(org_id);

        Organization organizationObject = new Organization();

        try { org.apache.commons.beanutils.BeanUtils.copyProperties(organizationObject, organizationDTOObject);}
        catch (IllegalAccessException e) { e.printStackTrace(); }
        catch (InvocationTargetException e) { e.printStackTrace(); }
        organizationDao.delete(organizationObject);

        return organizationDTOObject;
    }
}