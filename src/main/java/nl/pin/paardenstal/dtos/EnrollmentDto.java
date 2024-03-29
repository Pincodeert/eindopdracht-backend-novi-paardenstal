package nl.pin.paardenstal.dtos;

import java.time.LocalDate;

public class EnrollmentDto {

    private Long id;

    private LocalDate startDate;

    private LocalDate expireDate;

    private int duration;

    private boolean isOngoing;

    private boolean cancellationRequested;

    private String horseNumber;

    private double subscriptionPrice;

    private Long customerNumber;

    private CustomerProfileDto customer;
    private SubscriptionDto subscription;

    private HorseDto horse;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
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

    public String getHorseNumber() {
        return horseNumber;
    }

    public void setHorseNumber(String horseNumber) {
        this.horseNumber = horseNumber;
    }

    public double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice(double subscriptionPrice) {
        this.subscriptionPrice = subscriptionPrice;
    }

    public Long getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(Long customerNumber) {
        this.customerNumber = customerNumber;
    }

    public CustomerProfileDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerProfileDto customer) {
        this.customer = customer;
    }

    public SubscriptionDto getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionDto subscription) {
        this.subscription = subscription;
    }

    public HorseDto getHorse() {
        return horse;
    }

    public void setHorse(HorseDto horse) {
        this.horse = horse;
    }

}
