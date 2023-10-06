package nl.pin.paardenstal.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;


public class SubscriptionInputDto {

    @NotBlank(message = "het invullen van een naam is verplicht")
    private String name;

    @Min(value = 1, message = "prijs moet minimaal 1 zijn")
    @Max(value = 2000, message = "meer dan 2000 is een beetje teveel van het goede")
    private double price;

    @NotBlank(message = "het invullen van een type verzorging is verplicht")
    private String typeOfCare;

    @NotBlank(message = "het invullen van een type stall is verplicht")
    private String typeOfStall;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTypeOfCare() {
        return typeOfCare;
    }

    public void setTypeOfCare(String typeOfCare) {
        this.typeOfCare = typeOfCare;
    }

    public String getTypeOfStall() {
        return typeOfStall;
    }

    public void setTypeOfStall(String typeOfStall) {
        this.typeOfStall = typeOfStall;
    }


}
