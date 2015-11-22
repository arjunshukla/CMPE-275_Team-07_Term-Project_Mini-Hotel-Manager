package com.project.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import com.project.dao.*;
import com.project.entities.Billing;
import com.project.implementation.*;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * It is the configuration class which contains
 * beans and data source
 */

@Configuration
@EnableTransactionManagement
public class AppConfiguration {

/*
 * Bean for Implementation
 */

    @Bean
    public BillingImplementation getBillingImplementation(){
        return new BillingImplementation();
    }

    @Bean
    public CheckinRoomMappingImplementation getCheckinRoomMappingImplementation(){
        return new CheckinRoomMappingImplementation();
    }
    @Bean
    public GuestImplementation getGuestImplementation(){
        return new GuestImplementation();
    }
    @Bean
    public ReservationImplementation getReservationImplementation(){
        return new ReservationImplementation();
    }
    @Bean
    public RoomImplementation getRoomImplementation(){
        return new RoomImplementation();
    }
    @Bean
    public RoomPriceImplementation getRoomPriceImplementation(){
        return new RoomPriceImplementation();
    }
    @Bean
    public UserImplementation getUserImplementation(){
        return new UserImplementation();
    }
    @Bean
    public PersonImplementation getPersonImplementation(){
        return new PersonImplementation();
    }

    @Bean
    public OrganizationImplementation getOrganizationImplementation(){
        return new OrganizationImplementation();
    }

    @Bean
    public FriendshipImplementation getFriendshipImplementation(){
        return new FriendshipImplementation();
    }

    /*
     * Bean For DAO
     */


    @Bean
    public InterfaceForBilling getBillingDao(){
        return new BillingDAO();
    }


    @Bean
    public InterfaceForCheckinRoomMapping getCheckinRoomMappingDao(){
        return new CheckinRoomMappingDAO();
    }


    @Bean
    public InterfaceForGuest getGuestDao(){
        return new GuestDAO();
    }


    @Bean
    public InterfaceForReservation getReservationDao(){
        return new ReservationDAO();
    }


    @Bean
    public InterfaceForRoom getRoomDao(){
        return new RoomDAO();
    }


    @Bean
    public InterfaceForRoomPrice getRoomPriceDao(){
        return new RoomPriceDAO();
    }


    @Bean
    public InterfaceForUser getUserDao(){
        return new UserDAO();
    }

    @Bean
    public InterfaceForPersons getPersonDao(){
        return new PersonDAO();
    }

    @Bean
    public InterfaceForOrganization getOrganizationDao(){
        return new OrganizationDAO();
    }

    @Bean
    public InterfaceForFriendship getFriendshipDao(){
        return new FriendshipDAO();
    }




    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
        dataSource.setUrl("jdbc:mysql://practicedbinstance.ctivigkusjjd.us-west-2.rds.amazonaws.com:3306/minihotel");
        dataSource.setUsername("root");
        dataSource.setPassword("12345678");
        dataSource.setInitialSize(2);
        dataSource.setMaxTotal(5);
        return dataSource;
    }

    /**
          * @return HibernateTemplate() This is bean creation method for
          *         HibernateTemplate.
          */
    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory());

    }
    /**
          * @return SessionFactory() This is bean creation method for SessionFactory.
          */
    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.project.*");
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        builder.addProperties(hibernateProperties);
        return builder.buildSessionFactory();
    }


    /**
          * @return HibernateTransactionManager() This is bean creation method for
          *         HibernateTransactionManager.
          */
    @Bean
    @Primary
    public HibernateTransactionManager hibTransMan() {
        return new HibernateTransactionManager(sessionFactory());
    }
}