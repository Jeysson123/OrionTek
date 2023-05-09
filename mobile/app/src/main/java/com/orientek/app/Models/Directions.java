package com.orientek.app.Models;


public class Directions {

    private long Id;

    private String Address;

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

    public Clients getClient() {
        return Client;
    }

    public void setClient(Clients client) {
        Client = client;
    }

    @Override
    public String toString() {
        return "Directions{" +
                "Id=" + Id +
                ", Address='" + Address + '\'' +
                '}';
    }
}
