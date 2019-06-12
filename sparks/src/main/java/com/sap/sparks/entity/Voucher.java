package com.sap.sparks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "voucher")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "textm")
    private String text;

//    @JsonBackReference(value = "voucher")
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private Users user;

//    @JsonBackReference(value = "voucher_staffUser")
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_staff_id")
    private Users userStaff;

    public Voucher()
    {
        //default
    }

    public Voucher(Integer id, String text, Users user, Users userStaff) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.userStaff = userStaff;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getUserStaff() {
        return userStaff;
    }

    public void setUserStaff(Users userStaff) {
        this.userStaff = userStaff;
    }
}
