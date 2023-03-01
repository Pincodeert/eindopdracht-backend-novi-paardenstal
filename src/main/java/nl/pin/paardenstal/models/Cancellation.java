package nl.pin.paardenstal.models;

import javax.persistence.*;

@Entity
@Table(name = "cancellations")
public class Cancellation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String endDate;
    @OneToOne
    private Subscription subscription;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public Subscription getSubscription(){
        return subscription;
    }

    public void setSubscription(Subscription subscription){
        this.subscription = subscription;
    }

}
