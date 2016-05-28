package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vakhtanggelashvili on 10/23/15.
 */
@Entity
@Table(name = "parcels")
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "parcelId")
    private long id;

    @ManyToOne
    @JoinColumn(name = "organisationId")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "filialId")
    private Filial filial;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private Long courier;

    @Column
    @NotNull
    private String barcode;
    @Column
    @NotNull
    private String reciever;

    @Column
    @NotNull
    private String address;

    @Column
    @NotNull
    private String sentFrom;

    @Column
    @NotNull
    private Date expectedDeliveryDate;

    @Column
    private Date deliveryDate;

    @Column
    @NotNull
    private int status;

    @ManyToOne
    @JoinColumn(name = "formatId")
    private Format format;

    @ManyToOne
    @JoinColumn(name = "serviceTypeId")
    private ServiceType serviceType;

    @Column
    private String comment;

    @Column
    private String recievedBy;

    @ManyToOne
    @JoinColumn(name = "regionId")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "zoneId")
    private Zone zone;

    @Column
    private byte[] signature;

    @OneToMany(mappedBy = "parcel",cascade = CascadeType.ALL)
    private List<Movement> movements;

    public Parcel(){

    }

    public Parcel(Organisation organisation, User user, String reciever, String address, String sentFrom, Date expectedDeliveryDate, int status, Format format, ServiceType serviceType, String barCode, Region region, Zone zone) {
        this.setRegion(region);
        this.zone = zone;
        //this.regionId = regionId;
        this.setOrganisation(organisation);
        this.setUser(user);
        this.setReciever(reciever);
        this.setAddress(address);
        this.setSentFrom(sentFrom);
        this.setExpectedDeliveryDate(expectedDeliveryDate);
        this.setStatus(status);
        this.setFormat(format);
        this.setServiceType(serviceType);
        this.setBarcode(barCode);
        this.filial=null;
        this.courier=0l;
        //this.setZoneId(zoneId);
        this.movements=new ArrayList<Movement>();
    }
    public void addMovement(Movement movement){
        this.movements.add(movement);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRecievedBy() {
        return recievedBy;
    }

    public void setRecievedBy(String recievedBy) {
        this.recievedBy = recievedBy;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<Movement> getMovements() {
        return movements;
    }

    public void setMovements(List<Movement> movements) {
        this.movements = movements;
    }

/*    public long getRegion() {
        return regionId;
    }

    public void setRegionId(long regionId) {
        this.regionId = regionId;
    }

    public long getZoneId() {
        return zoneId;
    }

    public void setZoneId(long zoneId) {
        this.zoneId = zoneId;
    }*/

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public Filial getFilial() {
        return filial;
    }

    public void setFilial(Filial filial) {
        this.filial = filial;
    }

    public long getCourier() {
        return courier;
    }

    public void setCourier(long courier) {
        this.courier = courier;
    }
}
