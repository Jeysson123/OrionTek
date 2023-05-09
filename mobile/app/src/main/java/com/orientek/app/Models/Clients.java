package com.orientek.app.Models;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Clients implements Parcelable {

    private long Id;

    private String Name;

    private String Enterprise;

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

    protected Clients(Parcel in) {
        Id = in.readLong();
        Name = in.readString();
        Enterprise = in.readString();
    }

    public static final Creator<Clients> CREATOR = new Creator<Clients>() {
        @Override
        public Clients createFromParcel(Parcel in) {
            return new Clients(in);
        }

        @Override
        public Clients[] newArray(int size) {
            return new Clients[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(Id);
        dest.writeString(Name);
        dest.writeString(Enterprise);
    }
}
