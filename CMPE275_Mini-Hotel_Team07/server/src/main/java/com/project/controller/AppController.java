package com.project.controller;

import com.project.configuration.AppConfiguration;
import com.project.dto.*;
import com.project.entities.Friendship;
import com.project.implementation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * This is the project's REST controller for accepting REST requests
 * and responding to them with JSON objects
 */

@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/CmpE275Team07Fall2015TermProject/v1/*")
@Import(AppConfiguration.class)
public class AppController extends WebMvcConfigurerAdapter {

    @Autowired
    BillingImplementation billingImplementation;

    @Autowired
    CheckinRoomMappingImplementation checkinRoomMappingImplementation;

    @Autowired
    GuestImplementation guestImplementation;

    @Autowired
    ReservationImplementation reservationImplementation;

    @Autowired
    RoomImplementation roomImplementation;

    @Autowired
    RoomPriceImplementation roomPriceImplementation;

    @Autowired
    UserImplementation userImplementation;

//    @Autowired
//    PersonImplementation personImplementation;
//
//    @Autowired
//    OrganizationImplementation organizationImplementation;
//
//    @Autowired
//    FriendshipImplementation friendshipImplementation;


//    /* Person APIs */
//
//    /*
//    (1) Create a person
//    Path: person?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$...
//    Method: POST
//
//    This API creates a person object.
//    For simplicity, all the person fields (firstname, lastname, email, street, city, organization, etc), except ID and friends, are passed in as query parameters. Only the firstname, lastname, and email are required. Anything else is optional.
//    Friends is not allowed to be passed in as a parameter.
//    The organization parameter, if present, must be the ID of an existing organization.
//    The request returns the newly created person object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.)
//    If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.
//    * */
//    @RequestMapping(value = "/person",
//            method = RequestMethod.POST)
//    public ResponseEntity<?> createPerson(
//            @RequestParam(value = "firstname") String firstname,
//            @RequestParam(value = "lastname") String lastname,
//            @RequestParam(value = "email") String email,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "street") String street,
//            @RequestParam(value = "city") String city,
//            @RequestParam(value = "state") String state,
//            @RequestParam(value = "zip") String zip,
//            @RequestParam(value = "org_id") Integer org_id) {
//        PersonDTO personDTOObject = new PersonDTO();
//
//        AddressDTO addressDTOObject = new AddressDTO();
//        addressDTOObject.setStreet(street);
//        addressDTOObject.setCity(city);
//        addressDTOObject.setState(state);
//        addressDTOObject.setZip(zip);
//
//        personDTOObject.setFirstname(firstname);
//        personDTOObject.setLastname(lastname);
//        personDTOObject.setEmail(email);
//        personDTOObject.setDescription(description);
//        personDTOObject.setAddressDTO(addressDTOObject);
//        personDTOObject.setOrg_id(org_id);
//
//        ArrayList<Friendship> friends = new ArrayList<Friendship>();
//        personDTOObject.setFriendship(friends);
//
//        try
//        {
//            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty())
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            else
//            return new ResponseEntity<>(personImplementation.createPerson(personDTOObject), HttpStatus.OK);
//        }
//        catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    /*
//    (2) Get a person
//    Path:person/{id}?format={json | xml | html}
//    Method: GET
//
//    This returns a full person object with the given ID in the given format in its HTTP payload.
//    All existing fields, including the optional organization and list of friends should be returned.
//    The payload should contain the full organization object, if present.
//    The list of friends can be either (a) list of person IDs, or (b) list of “shallow” person objects that do not have their friends list populated. If you take option (b), you want to use techniques like lazy loading to avoid serializing the whole social network starting from the requested person in the returned payload.
//    If the person of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.
//    The format parameter is optional, and the value is case insensitive. If missing, JSON is assumed.
//    */
//
////----------------------------------------JSON|XML|HTML--------------------------------------------------------------------------
//
//    @RequestMapping(value = "/person/{person_id}", method = RequestMethod.GET, headers="Content-Type=application/xml,application/json,text/html")
//    public ResponseEntity<?> getPersonById(@Valid @PathVariable Integer person_id){
//        System.out.println("in /person/id");
//
//        //return new ResponseEntity<PersonDTO>(personImplementation.getPersonbyId(person_id),HttpStatus.ACCEPTED);
//
//        PersonDTO personDTO = personImplementation.getPersonbyId(person_id);
//        if(personDTO == null){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//        else{
//            return new ResponseEntity<PersonDTO>(personDTO, HttpStatus.OK);
//        }
//    }
//
//
//    /*(3) Update a person
//    Path: person/{id}?firstname=XX&lastname=YY&email=ZZ&description=UU&street=VV$...
//    Method: POST
//
//    This API updates a person object.
//    For simplicity, all the person fields (firstname, lastname, email, street, city, organization, etc), except friends, should be passed in as query parameters. Required fields like email must be present. The object constructed from the parameters will completely replace the existing object in the server, except that it does not change the person’s list of friends.
//    Similar to the get method, the request returns the updated person object, including all attributes (first name, last name, email, friends, organization, etc), in JSON. If the person ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.
//    */
//
//    @RequestMapping(value = "/person/{person_id}",
//            method = RequestMethod.POST)
//    public ResponseEntity<PersonDTO> updatePerson(@Valid @PathVariable Integer person_id,
//            @RequestParam(value = "firstname") String firstname,
//            @RequestParam(value = "lastname") String lastname,
//            @RequestParam(value = "email") String email,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "street") String street,
//            @RequestParam(value = "city") String city,
//            @RequestParam(value = "state") String state,
//            @RequestParam(value = "zip") String zip,
//            @RequestParam(value = "org_id") Integer org_id) {
//
//        PersonDTO personDTOObject = new PersonDTO();
//
//
//        AddressDTO addressDTOObject = new AddressDTO();
//        addressDTOObject.setStreet(street);
//        addressDTOObject.setCity(city);
//        addressDTOObject.setState(state);
//        addressDTOObject.setZip(zip);
//
//        personDTOObject.setPerson_id(person_id);
//        personDTOObject.setFirstname(firstname);
//        personDTOObject.setLastname(lastname);
//        personDTOObject.setEmail(email);
//        personDTOObject.setDescription(description);
//        personDTOObject.setAddressDTO(addressDTOObject);
//        personDTOObject.setOrg_id(org_id);
//
//        try
//        {
//            if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty())
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            else {
//            PersonDTO personDTOObjectToCheckNull = personImplementation.updatePerson(personDTOObject);
//            if(personDTOObjectToCheckNull == null)
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }
//        catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(personDTOObject, HttpStatus.OK);
//    }
//
//    /*
//    (4) Delete a person
//    URL: http://person/{id}
//    Method: DELETE
//
//    This deletes the person object with the given ID.
//    If the person with the given ID does not exist, return 404.
//    Otherwise, delete the person and remove any reference of this person from your persistence of friendship relations, and return HTTP status code 200 and the deleted person in JSON.
//    Organization APIs
//    */
//
//    @RequestMapping(value = "/person/{person_id}", method = RequestMethod.DELETE)
//    public ResponseEntity<PersonDTO> deletePersonById(@Valid @PathVariable Integer person_id){
//        System.out.println("in delete /person/id");
//
//        PersonDTO personDTOCheckNull = personImplementation.deletePersonbyId(person_id);
//
//        if(personDTOCheckNull == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else{
//            return new ResponseEntity<PersonDTO>(personDTOCheckNull,HttpStatus.OK);
//        }
//    }
//
//    /*
//    (5) Create an organization
//    Path: org?name=XX&description=YY&street=ZZ&...
//    Method: POST
//
//    This API creates an organization object.
//    For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query parameters.
//    Only name is required.
//    The request returns the newly created organization object in JSON in its HTTP payload, including all attributes. (Please note this differs from generally recommended practice of only returning the ID.)
//    If the request is invalid, e.g., missing required parameters, the HTTP status code should be 400; otherwise 200.
//    */
//
//
//    @RequestMapping(value = "/org",
//            method = RequestMethod.POST)
//    public ResponseEntity<OrganizationDTO> createOrganization(
//            @RequestParam(value = "name") String org_name,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "street") String street,
//            @RequestParam(value = "city") String city,
//            @RequestParam(value = "state") String state,
//            @RequestParam(value = "zip") String zip) {
//        OrganizationDTO organizationDTOObject = new OrganizationDTO();
//
//        AddressDTO addressDTOObject = new AddressDTO();
//        addressDTOObject.setStreet(street);
//        addressDTOObject.setCity(city);
//        addressDTOObject.setState(state);
//        addressDTOObject.setZip(zip);
//
//        organizationDTOObject.setOrg_name(org_name);
//        organizationDTOObject.setOrg_description(description);
//        organizationDTOObject.setAddressDTO(addressDTOObject);
//
//        if (org_name.isEmpty())
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        else
//        return new ResponseEntity<>(organizationImplementation.createOrganization(organizationDTOObject),HttpStatus.OK);
//
//    }
//
//
//    /*(6) Get a organization
//    Path:org/{id}?format={json | xml | html}
//    Method: GET
//
//    This returns a full organization object with the given ID in the given format.
//    All existing fields, including the optional ones should be returned.
//    If the organization of the given user ID does not exist, the HTTP return code should be 404; otherwise, 200.
//    The format parameter is optional, and the value is case insensitive. If missing, JSON is assumed.
//    */
//
//    //----------------------------------------JSON|XML|HTML--------------------------------------------------------------------------
//    @RequestMapping(value = "/org/{org_id}", method = RequestMethod.GET, headers="Content-Type=application/xml,application/json,text/html")
//    @ResponseBody
//    public ResponseEntity<?> getOrganizationById(@Valid @PathVariable Integer org_id){
//        System.out.println("in /org/id for JSON");
//
//        OrganizationDTO organizationDTO = organizationImplementation.getOrganizationbyId(org_id);
//        if(organizationDTO == null){
//            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
//            }
//        else{
//            return new ResponseEntity<>(organizationDTO,HttpStatus.OK);
//            }
//    }
//
//    /*
//    (7) Update an organization
//    Path: org/{id}?name=XX&description=YY&street=ZZ&...
//    Method: POST
//
//    This API updates an organization object.
//    For simplicity, all the fields (name, description, street, city, etc), except ID, are passed in as query parameters. Only name is required.
//    Similar to the get method, the request returns the updated organization object, including all attributes in JSON. If the organization ID does not exist, 404 should be returned. If required parameters are missing, return 400 instead. Otherwise, return 200.
//    */
//
//    @RequestMapping(value = "/org/{org_id}",
//            method = RequestMethod.POST)
//    public ResponseEntity<OrganizationDTO> updateOrganization(@Valid @PathVariable Integer org_id,
//            @RequestParam(value = "name") String org_name,
//            @RequestParam(value = "description") String description,
//            @RequestParam(value = "street") String street,
//            @RequestParam(value = "city") String city,
//            @RequestParam(value = "state") String state,
//            @RequestParam(value = "zip") String zip) {
//        OrganizationDTO organizationDTOObject = new OrganizationDTO();
//
//        AddressDTO addressDTOObject = new AddressDTO();
//        addressDTOObject.setStreet(street);
//        addressDTOObject.setCity(city);
//        addressDTOObject.setState(state);
//        addressDTOObject.setZip(zip);
//
//        organizationDTOObject.setOrg_id(org_id);
//        organizationDTOObject.setOrg_name(org_name);
//        organizationDTOObject.setOrg_description(description);
//        organizationDTOObject.setAddressDTO(addressDTOObject);
//
//        try
//        {
//            if (org_name.isEmpty())
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            else {
//                OrganizationDTO organizationDTOToCheckNull = organizationImplementation.updateOrganization(organizationDTOObject);
//                if(organizationDTOToCheckNull == null)
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        }
//        catch(Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(organizationDTOObject, HttpStatus.OK);
//    }
//
//    /*
//    (8) Delete an organization
//    URL: http://org/{id}
//    Method: DELETE
//
//    This method deletes the organization object with the given ID.
//    If there is still any person belonging to this organization, return 400.
//    If the organization with the given ID does not exist, return 404.
//    Return HTTP code 200 and the deleted object in JSON if the object is deleted;
//    Friendship APIs
//    */
//
//    @RequestMapping(value = "/org/{org_id}", method = RequestMethod.DELETE)
//    public ResponseEntity<OrganizationDTO> deleteOrganizationById(@Valid @PathVariable Integer org_id){
//        OrganizationDTO organizationDTOCheckNull = organizationImplementation.deleteOrganizationbyId(org_id);
//        if(organizationDTOCheckNull == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        else{
//            return new ResponseEntity<OrganizationDTO>(organizationDTOCheckNull,HttpStatus.OK);
//        }
//    }
//
//    /*
//    (9) Add a friend
//    Path:friends/{id1}/{id2}
//    Method: PUT
//
//    This makes the two persons with the given IDs friends with each other.
//    If either person does not exist, return 404.
//    If the two persons are already friends, do nothing, just return 200. Otherwise,
//    Record this friendship relation. If all is successful, return HTTP code 200 and any informative text message in the HTTP payload.
//    */
//    @RequestMapping(value = "/friends/{friend1}/{friend2}", method = RequestMethod.PUT)
//    public String updateFriendship(@Valid @PathVariable Integer friend1, @Valid @PathVariable Integer friend2){
//        if(friend1 == friend2) {
//            return "Error: same person";
//        }else{
//            String result = friendshipImplementation.updateFriendship(friend1, friend2);
//            if (result.equalsIgnoreCase("success: friendship recorded"))
//                return "Success";
//            else if (result.equalsIgnoreCase("success: already friends"))
//                return "Already friends";
//            else
//                return result;
//        }
//    }
//
//    /*
//    (10) Remove a friend
//    Path:friends/{id1}/{id2}
//    Method: DELETE
//
//    This request removes the friendship relation between the two persons.
//    If either person does not exist, return 404.
//    If the two persons are not friends, return 404. Otherwise,
//    Remove this friendship relation. Return HTTP code 200 and a meaningful text message if all is successful.
//    */
//
//    @RequestMapping(value = "/friends/{friend1}/{friend2}", method = RequestMethod.DELETE)
//    public String removeFriendship(@Valid @PathVariable Integer friend1, @Valid @PathVariable Integer friend2){
//        if(friend1 == friend2) {
//            return "Error: same person";
//        }else{
//            String result = friendshipImplementation.removeFriendship(friend1, friend2);
//            if (result.equalsIgnoreCase("success: friendship recorded"))
//                return "Success";
//            else if (result.equalsIgnoreCase("success: already friends"))
//                return "Already friends";
//            else
//                return result;
//        }
//    }
}
