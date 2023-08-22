package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.Enrollment;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionDto {

    private Long id;

    private String name;

    private double price;

    private String typeOfCare;

    private String typeOfStall;

    private List<EnrollmentDto> enrollments = new ArrayList<>();

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

    public List<EnrollmentDto> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentDto> enrollments) {
        this.enrollments = enrollments;
    }

}
