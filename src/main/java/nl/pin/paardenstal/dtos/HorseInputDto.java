package nl.pin.paardenstal.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class HorseInputDto {

    @Size(min=2, max=60, message = "naam moet tussen 2 en 60 tekens lang zijn")
    private String name;

    @Size(min=14, max=14, message = "het paardnummer(levensnummer) moet uit 14 tekens bestaan")
    private String horseNumber;

    @NotBlank(message = "type voeding mag niet leeg zijn")
    private String typeOfFeed;

    @NotBlank(message = "type stalbedekking mag niet leeg zijn")
    private String typeOfBedding;

    @Size(min=2, max=60, message = "naam moet tussen 2 en 60 tekens lang zijn")
    private String nameOfVet;

    @Size(min=2, max=60, message = "plaats moet tussen 2 en 60 tekens lang zijn")
    private String residenceOfVet;

    @Size(min=10, max=10, message = "telefoonnummer moet uit 10 cijfers bestaan")
    private String telephoneOfVet;

    @NotBlank
    private String preferredSubscription;



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

}
