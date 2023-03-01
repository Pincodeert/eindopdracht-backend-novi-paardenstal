package nl.pin.paardenstal.models;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double price;

    private String typeOfCare;

    private String typeOfStall;

    @OneToOne(mappedBy = "subscription")
    private Stall stall;
    @OneToOne(mappedBy = "subscription")
    private Cancellation cancellation;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Cancellation getCancellation(){
        return cancellation;
    }

    public void setCancellation(Cancellation cancellation){
        this.cancellation = cancellation;
    }
}
