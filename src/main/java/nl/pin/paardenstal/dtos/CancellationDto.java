package nl.pin.paardenstal.dtos;

public class CancellationDto {

    private long id;

    private String endDate;

    // hier geen "dto" in de naamgeving vermeld, omdat deze naam zichtbaar wordt in interactie met gebruiker(en dto voor
    // een leek waarchijnlijk een nietszeggende term is)
    private SubscriptionDto subscription;

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

    public SubscriptionDto getSubscription(){
        return subscription;
    }

    public void setSubscription(SubscriptionDto subscription){
        this.subscription = subscription;
    }

}
