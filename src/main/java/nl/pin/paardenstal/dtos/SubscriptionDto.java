package nl.pin.paardenstal.dtos;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionDto {

    private long id;

    private double price;

    private String typeOfCare;

    private String typeOfStall;

//    private List<CustomerProfileDto> customers = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    //public List<CustomerProfileDto> getCustomers() {
    //    return customers;
    //}

    //public void setCustomers(List<CustomerProfileDto> customers) {
    //    this.customers = customers;
    //}
}
