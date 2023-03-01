package nl.pin.paardenstal.models;

import javax.persistence.*;

@Entity
@Table(name = "stalls")
public class Stall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String size;

    private String type;

    @OneToOne
    private Horse horse;

    @OneToOne
    private Subscription subscription;

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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

    public Subscription getSubscription(){
        return subscription;
    }

    public void setSubscription(Subscription subscription){
        this.subscription = subscription;
    }
}
