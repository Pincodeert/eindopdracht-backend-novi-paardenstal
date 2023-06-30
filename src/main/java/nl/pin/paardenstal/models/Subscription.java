package nl.pin.paardenstal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private double price;

    private String typeOfCare;

    private String typeOfStall;

    @OneToOne(mappedBy = "subscription")
    @JoinColumn(nullable = false)
    private Stall stall;

    @ManyToMany(mappedBy = "subscriptions", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<CustomerProfile> customers = new ArrayList<>();

    @OneToMany(mappedBy = "subscription", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Enrollment> enrollments;

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
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTypeOfCare() {
        return typeOfCare;
    }

    public void setTypeOfCare(String typeOfCare) {
        this.typeOfCare = typeOfCare;
    }

    public String getTypeOfStall() {
        return typeOfStall;
    }

    public void setTypeOfStall(String typeOfStall) {
        this.typeOfStall = typeOfStall;
    }

    public Stall getStall() {
        return stall;
    }

    public void setStall(Stall stall) {
        this.stall = stall;
    }

    public List<CustomerProfile> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerProfile> customers) {
        this.customers = customers;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
}
