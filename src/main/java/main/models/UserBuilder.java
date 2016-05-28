package main.models;

import java.util.List;

public class UserBuilder {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String address;
    private Organisation organisation;
    private String mobile;
    private String personalNumber;
    private int type;
    private Region region;
    private Zone zone;
    private List<Session> sessions;
    private Filial filial;

    public UserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public UserBuilder setOrganisationId(Organisation organisation) {
        this.organisation = organisation;
        return this;
    }

    public UserBuilder setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public UserBuilder setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
        return this;
    }

    public UserBuilder setType(int type) {
        this.type = type;
        return this;
    }

    public UserBuilder setRegion(Region region) {
        this.region = region;
        return this;
    }

    public UserBuilder setZone(Zone zone) {
        this.zone = zone;
        return this;
    }
    public UserBuilder setSessions(List<Session> sessions){
        this.sessions=sessions;
        return this;
    }

    public User createUser() {
        return new User(username, password, email, name, surname, address, organisation, mobile, personalNumber, type, zone, sessions,region,filial);
    }

    public UserBuilder setFilial(Filial filial) {
        this.filial = filial;
        return this;
    }
}