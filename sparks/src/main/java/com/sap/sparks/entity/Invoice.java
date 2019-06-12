package com.sap.sparks.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "totalcost")
    private Double totalCost;

    @Column(name = "firm")
    private String firm = "spark";

    public enum TypeInvoice {
        monthly,
        daily
    }

    @Column(name = "typeinvoice")
    @Enumerated(EnumType.STRING)
    private TypeInvoice typeInvoice;

    @Column(name = "dateofinvoice")
    private LocalDateTime dateOfInvoice = LocalDateTime.now();

//    @JsonBackReference(value = "invoices")
    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Users user;

//    @JsonBackReference
    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "invoice_trips",
            joinColumns = @JoinColumn(name = "invoice_id"),
            inverseJoinColumns = @JoinColumn(name = "trip_id"))
   private List<Trips> trips;

    public Invoice()
    {
    }

    public Invoice(Integer id, Double totalCost, String firm, TypeInvoice typeInvoice, LocalDateTime dateOfInvoice, Users user, List<Trips> trips) {
        this.id = id;
        this.totalCost = totalCost;
        this.firm = firm;
        this.typeInvoice = typeInvoice;
        this.dateOfInvoice = dateOfInvoice;
        this.user = user;
        this.trips = trips;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public TypeInvoice getTypeInvoice() {
        return typeInvoice;
    }

    public void setTypeInvoice(TypeInvoice typeInvoice) {
        this.typeInvoice = typeInvoice;
    }

    public LocalDateTime getDateOfInvoice() {
        return dateOfInvoice;
    }

    public void setDateOfInvoice(LocalDateTime dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Trips> getTrips() {
        return trips;
    }

    public void setTrips(List<Trips> trips) {

        this.trips = trips;
    }

    public void addTrip(Trips tempTrip){
        if(trips == null){
            trips = new ArrayList<>();
        }

        trips.add(tempTrip);
    }

}
