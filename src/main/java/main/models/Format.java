package main.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KGelashvili on 11/2/2015.
 */
@Entity
@Table(name = "formats")
public class Format {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "formatId")
    private long id;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private float price;

    @JsonIgnore
    @OneToMany(mappedBy = "format",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Parcel> parcels;

    public Format() {
    }
    public Format(String name,float price) {
        this.name=name;
        this.price=price;
        this.parcels=new ArrayList<Parcel>();
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
