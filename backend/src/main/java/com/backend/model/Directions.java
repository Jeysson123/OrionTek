package com.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Directions")
public class Directions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name="Address")
    @NotBlank
    private String Address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Client_ID")
    private Clients Client;

    public Directions() {
    }

    public Directions(long id, String address, Clients client) {
        Id = id;
        Address = address;
        Client = client;
    }

    public Directions(String address, Clients client) {
        Address = address;
        Client = client;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "Directions{" +
                "Id=" + Id +
                ", Address='" + Address + '\'' +
                '}';
    }
}
