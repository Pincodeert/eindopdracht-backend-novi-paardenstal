package nl.pin.paardenstal.dtos;

public class HorseDto {

    private long id;

    private String name;

    private String typeOfFeed;

    private String typeOfBedding;

    private String nameOfVet;

    private String residenceOfVet;

    private String telephoneOfVet;

    private CustomerProfileDto ownerDto;


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

    public String getTypeOfFeed() {
        return typeOfFeed;
    }

    public void setTypeOfFeed(String typeOfFeed) {
        this.typeOfFeed = typeOfFeed;
    }

    public String getTypeOfBedding() {
        return typeOfBedding;
    }

    public void setTypeOfBedding(String typeOfBedding) {
        this.typeOfBedding = typeOfBedding;
    }

    public String getNameOfVet() {
        return nameOfVet;
    }

    public void setNameOfVet(String nameOfVet) {
        this.nameOfVet = nameOfVet;
    }

    public String getResidenceOfVet() {
        return residenceOfVet;
    }

    public void setResidenceOfVet(String residenceOfVet) {
        this.residenceOfVet = residenceOfVet;
    }

    public String getTelephoneOfVet() {
        return telephoneOfVet;
    }

    public void setTelephoneOfVet(String telephoneOfVet) {
        this.telephoneOfVet = telephoneOfVet;
    }

    public CustomerProfileDto getOwnerDto() {
        return ownerDto;
    }

    public void setOwnerDto(CustomerProfileDto ownerDto) {
        this.ownerDto = ownerDto;
    }
}
