package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/22/15.
 */
@Entity
@Table(name = "Organisations")
public class Organisation {

    @Id
    @Column(name = "organisationId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String address;

    @NotNull
    @Column
    private String mobileNumber;

    @NotNull
    @Column
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "organisation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Parcel> parcels;

    @JsonIgnore
    @OneToMany(mappedBy = "organisation",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> users;


    @ManyToOne
    @JoinColumn(name = "regionId")
    private Region region;

    public Organisation(String name, String address, String mobileNumber, String email, Region region) {
        this.name = name;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.region = region;
    }
    public Organisation(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegionId(Region region) {
        this.region = region;
    }

    public long getId() {
        return id;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {
        this.parcels = parcels;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
