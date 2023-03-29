package nl.pin.paardenstal.dtos;

public class StallDto {

    private long id;

    private String name;

    private String size;

    private String type;

    private HorseDto horse;

    private SubscriptionDto subscription;

    private OwnerDto owner;

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

    public void setHorseDto(HorseDto horseDto) {
        this.horse = horseDto;
    }

    public SubscriptionDto getSubscription(){
        return subscription;
    }

    public void setSubscription(SubscriptionDto subscription){
        this.subscription = subscription;
    }

    public OwnerDto getOwner(){
        return owner;
    }

    public void setOwner(OwnerDto owner){
        this.owner = owner;
    }

}
