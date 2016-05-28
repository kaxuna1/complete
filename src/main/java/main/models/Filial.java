package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaxa on 5/24/16.
 */

@Entity
@Table(name = "Filials")
public class Filial {
    @Id
    @Column(name = "filialId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column
    private String name;

    @NotNull
    @Column
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "filial",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Parcel> parcels;

    @JsonIgnore
    @OneToMany(mappedBy = "filial",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<User> users;

    @ManyToOne
    @JoinColumn(name = "regionId")
    private Region region;

    public Filial(String name, String address, Region region) {
        this.name = name;
        this.address = address;
        this.region = region;
        this.users=new ArrayList<User>();
        this.parcels=new ArrayList<Parcel>();
    }
    public Filial(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}
