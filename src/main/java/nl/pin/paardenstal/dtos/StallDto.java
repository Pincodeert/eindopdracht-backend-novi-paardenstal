package nl.pin.paardenstal.dtos;

public class StallDto {

    private long id;

    private String name;

    private String size;

    private String type;

    private HorseDto horse;
    private SubscriptionDto subscription;



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

    public HorseDto getHorseDto() {
        return horse;
    }

    public void setHorseDto(HorseDto horse) {
        this.horse = horse;
    }

    public SubscriptionDto getSubscription(){
        return subscription;
    }

    public void setSubscription(SubscriptionDto subscription){
        this.subscription = subscription;
    }



}
