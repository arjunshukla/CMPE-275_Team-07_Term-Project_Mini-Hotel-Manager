package com.project.implementation;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Guest Implementation Class
 */

public class GuestImplementation {
    //    @Autowired
//    InterfaceForPersons personsDao;
//
//    @Autowired
//    InterfaceForFriendship friendshipDao;
//
//    @Autowired
//    FriendshipImplementation friendshipImplementation;
//
//
//	 /*  Create Person*/
//
//    public PersonDTO createPerson(PersonDTO personDTObject) {
//
//        Person personObject = new Person();
//
//        try { org.apache.commons.beanutils.BeanUtils.copyProperties(personObject, personDTObject);}
//        catch (IllegalAccessException e) { e.printStackTrace(); }
//        catch (InvocationTargetException e) { e.printStackTrace(); }
//
//
//        personObject.setFirstname(personDTObject.getFirstname());
//        personObject.setLastname(personDTObject.getLastname());
//        personObject.setEmail(personDTObject.getEmail());
//        personObject.setDescription(personDTObject.getDescription());
//
//        personObject.setStreet(personDTObject.getAddressDTO().getStreet());
//        personObject.setCity(personDTObject.getAddressDTO().getCity());
//        personObject.setState(personDTObject.getAddressDTO().getState());
//        personObject.setZip(personDTObject.getAddressDTO().getZip());
//
//        // for org
//        Organization organization = personsDao.getOrganizationById(personDTObject.getOrg_id());
//
//        if(organization!=null)
//            personObject.setOrganization(organization);
//
//        personObject = personsDao.save(personObject);
//
//        personDTObject.setPerson_id(personObject.getPerson_id());
//        return personDTObject;
//    }
//
//    @Transactional
//    public PersonDTO getPersonbyId(Integer personId)
//
//    {
//        PersonDTO personDTO = new PersonDTO();
//
//        Person person = personsDao.getPersonById(personId);
//        if(person!=null) {
//            AddressDTO addressDTO = new AddressDTO();
//            Organization organization = new Organization();
//
//            personDTO.setPerson_id(person.getPerson_id());
//            personDTO.setFirstname(person.getFirstname());
//            personDTO.setLastname(person.getLastname());
//            personDTO.setEmail(person.getEmail());
//            personDTO.setDescription(person.getDescription());
//
//            addressDTO.setStreet(person.getStreet());
//            addressDTO.setCity(person.getCity());
//            addressDTO.setState(person.getState());
//            addressDTO.setZip(person.getZip());
//
//            personDTO.setAddressDTO(addressDTO);
//
//            ArrayList<Friendship> friends = new ArrayList<Friendship>();
//            friends = friendshipDao.getFriendsForId(person.getPerson_id());
//            personDTO.setFriendship(friends);
//
//            if (person.getOrganization() != null) {
//                System.out.println("person has org");
//                if (person.getOrganization().getOrg_id() != null)
//                    personDTO.setOrg_id(person.getOrganization().getOrg_id());
//                else
//                    personDTO.setOrg_id(0);
//            }
//            return personDTO;
//        } else
//            return null;
//    }
//
//
//    public PersonDTO updatePerson(PersonDTO personDTOObject) {
//        Person personObject = new Person();
//
//        personObject = personsDao.getPersonByEmail(personDTOObject.getEmail());
//
//        if (personObject != null && personObject.getPerson_id() == personDTOObject.getPerson_id()) {
//
//            try {
//                org.apache.commons.beanutils.BeanUtils.copyProperties(personObject, personDTOObject);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//
//
//            personObject.setFirstname(personDTOObject.getFirstname());
//            personObject.setLastname(personDTOObject.getLastname());
//            personObject.setEmail(personDTOObject.getEmail());
//            personObject.setDescription(personDTOObject.getDescription());
//
//            personObject.setStreet(personDTOObject.getAddressDTO().getStreet());
//            personObject.setCity(personDTOObject.getAddressDTO().getCity());
//            personObject.setState(personDTOObject.getAddressDTO().getState());
//            personObject.setZip(personDTOObject.getAddressDTO().getZip());
//
//
//            ArrayList<Friendship> friends = new ArrayList<Friendship>();
//            friends = friendshipDao.getFriendsForId(personObject.getPerson_id());
//            personDTOObject.setFriendship(friends);
//
//
//            // for org
//            Organization organization = new Organization();
//
//            organization = personsDao.getOrganizationById(personDTOObject.getOrg_id());
//
//            if (organization != null)
//                personObject.setOrganization(organization);
//
//
//            personsDao.update(personObject);
//            personObject = personsDao.getPersonByEmail(personDTOObject.getEmail());
//
//            personDTOObject.setPerson_id(personObject.getPerson_id());
//            return personDTOObject;
//        }
//        return null;
//    }
//
//    public PersonDTO deletePersonbyId(Integer person_id) {
//        PersonDTO personDTOObject = getPersonbyId(person_id);
//
//        if (personDTOObject != null) {
//            Person personObject = new Person();
//            personObject.setPerson_id(person_id);
//            personObject.setFirstname(personDTOObject.getFirstname());
//            personObject.setLastname(personDTOObject.getLastname());
//            personObject.setEmail(personDTOObject.getEmail());
//            personObject.setDescription(personDTOObject.getDescription());
//
//            personObject.setStreet(personDTOObject.getAddressDTO().getStreet());
//            personObject.setCity(personDTOObject.getAddressDTO().getCity());
//            personObject.setState(personDTOObject.getAddressDTO().getState());
//            personObject.setZip(personDTOObject.getAddressDTO().getZip());
//
//            ArrayList<Integer> friends = friendshipDao.getFriendIdsForId(personObject.getPerson_id());
//
//            for (int i = 0; i < friends.size(); i++) {
//                friendshipImplementation.removeFriendship(person_id, friends.get(i));
//            }
//            personsDao.delete(personObject);
//            return personDTOObject;
//        }
//        return null;
//    }
}
