package nl.pin.paardenstal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;

    //standaard instellen 12 maanden later. wordt door de applicatie berekend
    private LocalDate expireDate;

    //looptijd in maanden. wordt berekend vanaf de LocalDate .now
    private int duration;

    //de default waarde van Boolean is false. Bij aanmaken van een Enrollment willen we dat isOngoing op true staat:
    private boolean isOngoing = true;

    private boolean cancellationRequested;

    private String horseNumber;

    @ManyToOne
    @JoinColumn(name = "customer_profile_id", referencedColumnName = "id")
    @JsonIgnore
    private CustomerProfile customerProfile;

    @OneToOne
    private Horse horse;

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    @JsonIgnore
    private Subscription subscription;



    //No-args-constructor, hier nodig omdat we  (nodig, omdat Java niet meer default no args constructor aanmaakt omdat we zelf een
    // (gedeeltelijke) args constructor maken, nodig om in de associatieklasse af te dwingen dat de instantie van de
    // associatieklasse alleen wordt aangemaakt wanneer de addCustomerProfileToSubscription()-operatie wordt aangeroepen.
    public Enrollment(){};

    //Constructor die we nodig hebben om af te dwingen dat in associatieklasse de instantie van de
    //associatieklasse alleen wordt aangemaakt wanneer de addCustomerProfileToSubscription()-operatie wordt aangeroepen.
    public Enrollment(Subscription s, CustomerProfile cp, Horse h) {
        this.subscription = s;
        this.customerProfile =cp;
        this.horse = h;
        this.horseNumber = h.getHorseNumber();
        this.startDate = LocalDate.now();
        this.expireDate = startDate.plusMonths(12);
    }

    //all args constructor (handig voor als je bv zelf een andere startdatum wil meegeven dan de datum van vandaag
    public Enrollment(Subscription s,  CustomerProfile cp, Horse h, LocalDate startDate) {
        this.subscription = s;
        this.customerProfile = cp;
        this.horse = h;
        this.horseNumber = h.getHorseNumber();
        this.startDate = startDate;
        this.expireDate = startDate.plusMonths(12);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    //vooralsnog, omdat set startDate in de constructor wordt geregeld, nu geen setter nodig
    //public void setStartDate(String startDate) {
    //    this.startDate = startDate;
    //}

    public LocalDate getExpireDate() {
        return expireDate;
    }

    /*public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }*/

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

    public String getHorseNumber() {
        return horseNumber;
    }

    public void setHorseNumber(String horseNumber) {
        this.horseNumber = horseNumber;
    }

    public CustomerProfile getCustomer() {
        return customerProfile;
    }

    public void setCustomer(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public Horse getHorse() {
        return horse;
    }

    public void setHorse(Horse horse) {
        this.horse = horse;
    }

}
