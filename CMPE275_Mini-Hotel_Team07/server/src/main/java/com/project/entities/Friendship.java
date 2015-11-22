package com.project.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Team11 on 11/4/15.
 * Members: Arjun Shukla, Arpit Khare, Sneha Pimpalkar
 * Friendship Entity class
 */

@Entity
@Table(name = "friendship")
public class Friendship implements Serializable{

    @Id
    @Column
    private Integer friend1;

    @Id
    @Column
    private Integer friend2;

    public Integer getFriend1() {
        return friend1;
    }

    public void setFriend1(Integer friend1) {
        this.friend1 = friend1;
    }

    public Integer getFriend2() {
        return friend2;
    }

    public void setFriend2(Integer friend2) {
        this.friend2 = friend2;
    }
}
