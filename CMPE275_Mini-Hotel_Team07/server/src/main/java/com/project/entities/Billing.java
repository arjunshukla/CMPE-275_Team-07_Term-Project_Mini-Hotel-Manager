package com.project.entities;



import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Team07 on 11/21/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar, Ankit Sharma, Tejas Pai
 * Billing Entity class
 */

@Entity
@Table(name = "billing")
public class Billing implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id", unique = true, nullable = false)
    private Integer bill_id;

    @Column(name = "bill_no", unique = true, nullable = false)
    private Integer bill_no;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne//(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "discount", unique = false, nullable = false)
    private Double discount;

    @Column(name = "amount", unique = false, nullable = false)
    private Double amount;


    public Integer getBill_id() {
        return bill_id;
    }

    public void setBill_id(Integer bill_id) {
        this.bill_id = bill_id;
    }

    public Integer getBill_no() {
        return bill_no;
    }

    public void setBill_no(Integer bill_no) {
        this.bill_no = bill_no;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}