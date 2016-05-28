package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userId")
	private long id;
    @NotNull
    @Column
	private String username;
    @Column
    @NotNull
	private String password;
    @Column
    @NotNull
    private String email;
    @Column
    @NotNull
    private String name;
    @Column
    @NotNull
    private String surname;
    @Column
    private String address;
    @ManyToOne
    @JoinColumn(name = "organisationId")
    private Organisation organisation;
    @ManyToOne
    @JoinColumn(name = "filialId")
    private Filial filial;
    @Column
    private String mobile;
    @Column
    @NotNull
    private String personalNumber;
    @Column
    @NotNull
    private int type;
    @ManyToOne
    @JoinColumn(name = "regionId")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "zoneId")
    private Zone zone;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Session> sessions;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Parcel> parcels;


    public User(long id){
        this.id=id;
    }
    public User(){
    }


    public User(String username, String password, String email,
                String name, String surname, String address,
                Organisation organisation, String mobile, String personalNumber,
                int type, Zone zone, List<Session> sessions, Region region,Filial filial){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.organisation = organisation;
        this.mobile = mobile;
        this.personalNumber = personalNumber;
        this.type = type;
        this.region = region;
        //this.regionId = regionId;
        this.zone = zone;
        this.sessions = sessions;
        this.filial=filial;
        this.parcels=new ArrayList<Parcel>();
    }


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public long getId() {
        return id;
    }

    public void setId() { this.id=id;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

   /* public int getRegion() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }
*/

    public Zone getZone() {
        return zone;
    }

    public void setZoneId(Zone zone) {
        this.zone = zone;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public List<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(List<Parcel> parcels) {
        this.parcels = parcels;
    }
}
