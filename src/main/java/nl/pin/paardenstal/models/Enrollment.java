package nl.pin.paardenstal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //private LocalDate startDate;
    // vooralsnog String ipv LocalDate gebruiken:
    //vooralsnog in deze applicatie als startdatum het moment van aanmaken van de instantie gebruiken: LocalDat.now()
    private String startDate;

    //standaard instellen 12 maanden later. wordt door de applicatie berekend
    private String expireDate;

    //looptijd in maanden. wordt berekend vanaf de LocalDate .now
    private int duration;

    //de default van Boolean is false. Bij aanmaken van een Enrollmant willen we dat isOngoing op true staat:
    private boolean isOngoing = true;

    private boolean cancellationRequested;

    @ManyToOne
    @JoinColumn(name = "customer_profile_id", referencedColumnName = "id")
    @JsonIgnore
    private CustomerProfile customer;

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    @JsonIgnore
    private Subscription subscription;

    //No args constructor (nodig, omdat Java niet meer default no args constructor aanmaakt omdat we zelf een
    // (gedeeltelijke) args constructor maken, nodig om in de associatieklasse af te dwingen dat de instantie van de
    // associatieklasse alleen wordt aangemaakt wanneer de addCustomerProfileToSubscription()-operatie wordt aangeroepen.
    public Enrollment(){};

    //Constructor die we nodig hebben om af te dwingen dat in associatieklasse de instantie van de
    //associatieklasse alleen wordt aangemaakt wanneer de addCustomerProfileToSubscription()-operatie wordt aangeroepen.
    // Vooralsnog startDate ook in de Constructor gezet omdat we vooralsnog een String gebruiken. Wanneer we LocalDate
    // gaan gebruiken, lossen we dit elders op.
    public Enrollment(Subscription s, CustomerProfile cp, String startDate ) {
        this.subscription = s;
        this.customer = cp;
        this.startDate = startDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /*public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    */
    public String getStartDate() {
        return startDate;
    }

    //vooralsnog, omdat set startDate in de constructor wordt geregeld, nu geen setter nodig
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public boolean isCancellationRequested() {
        return cancellationRequested;
    }

    public void setCancellationRequested(boolean cancellationRequested) {
        this.cancellationRequested = cancellationRequested;
    }

    //voor CustomerProfile en Subscription is alleen een getter nodig, omdat ze al in de constructor zijn opgenomen.
    public CustomerProfile getCustomer() {
        return customer;
    }


    public Subscription getSubscription() {
        return subscription;
    }


}
