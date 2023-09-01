package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.FileUploadResponse;

public class HorseDto {

    private Long id;

    private String name;

    private String horseNumber;

    private String typeOfFeed;

    private String typeOfBedding;

    private String nameOfVet;

    private String residenceOfVet;

    private String telephoneOfVet;

    private String preferredSubscription;

    private FileUploadResponse passport;

    private CustomerProfileDto owner;

    private StallDto stall;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHorseNumber() {
        return horseNumber;
    }

    public void setHorseNumber(String horseNumber) {
        this.horseNumber = horseNumber;
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

    public String getPreferredSubscription() {
        return preferredSubscription;
    }

    public void setPreferredSubscription(String preferredSubscription) {
        this.preferredSubscription = preferredSubscription;
    }

    public FileUploadResponse getPassport() {
        return passport;
    }

    public void setPassport(FileUploadResponse passport) {
        this.passport = passport;
    }

    public CustomerProfileDto getOwner() {
        return owner;
    }

    public void setOwner(CustomerProfileDto owner) {
        this.owner = owner;
    }

    public StallDto getStall() {
        return stall;
    }

    public void setStall(StallDto stall) {
        this.stall = stall;
    }


}
