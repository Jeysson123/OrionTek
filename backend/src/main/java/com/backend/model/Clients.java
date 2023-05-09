package com.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Clients")
public class Clients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(name="Name")
    @NotBlank
    private String Name;

    @Column(name="Enterprise")
    @NotBlank
    private String Enterprise;

    @OneToMany(mappedBy = "Client", cascade = CascadeType.ALL)
    private List<Directions> ListDirections = new ArrayList<>();

    public Clients() {

    }
    public Clients(long id, String name, String enterprise) {
        Id = id;
        Name = name;
        Enterprise = enterprise;
    }

    public Clients(String name, String enterprise) {
        Name = name;
        Enterprise = enterprise;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEnterprise() {
        return Enterprise;
    }

    public void setEnterprise(String enterprise) {
        Enterprise = enterprise;
    }

    public List<Directions> getListDirections() {
        return ListDirections;
    }

    public void setListDirections(List<Directions> listDirections) {
        ListDirections = listDirections;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Enterprise='" + Enterprise + '\'' +
                '}';
    }
}
