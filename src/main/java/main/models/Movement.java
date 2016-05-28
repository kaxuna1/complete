package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.models.Enum.MovementType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by KGelashvili on 10/23/2015.
 */
@Entity
@Table(name = "movements")
public class Movement {
    @Id
    @Column(name = "movementid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private int movementTypeId;

    @Column
    private String movementText;

    @Column
    private Date date;


    @ManyToOne
    @JoinColumn(name = "parcelId")
    @JsonIgnore
    private Parcel parcel;

    public Movement(){

    }


    public Movement(int movementTypeId, String movementText, Date date, Parcel parcel) {
        this.movementTypeId = movementTypeId;
        this.movementText = movementText;
        this.date = date;
        this.parcel=parcel;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getMovementTypeId() {
        return movementTypeId;
    }

    public void setMovementTypeId(int movementTypeId) {
        this.movementTypeId = movementTypeId;
    }

    public String getMovementText() {
        return movementText;
    }

    public void setMovementText(String movementText) {
        this.movementText = movementText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

  /*  public long getParcel() {
        return parcel;
    }

    public void setParcel(long parcel) {
        this.parcel = parcel;
    }*/


    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }
}
