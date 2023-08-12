package nl.pin.paardenstal.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "horses")
public class Horse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String typeOfFeed;

    private String typeOfBedding;

    private String nameOfVet;

    private String residenceOfVet;

    private String telephoneOfVet;

    //alternatief als variable binnen klasse Horse
    //private byte[] LOB;

    //upload als aparte klasse
    @OneToOne
    private FileUploadResponse passport;

    @OneToOne(mappedBy = "horse")
    private Stall stall;

    @ManyToOne
    @JoinColumn(name = "customer_profile_id", referencedColumnName = "id")
    @JsonIgnore
    private CustomerProfile owner;

    @OneToOne(mappedBy = "horse")
    private Enrollment enrollment;

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

    public FileUploadResponse getPassport() {
        return passport;
    }

    public void setPassport(FileUploadResponse passport) {
        this.passport = passport;
    }

    public Stall getStall() {
        return stall;
    }

    public void setStall(Stall stall) {
        this.stall = stall;
    }

    public CustomerProfile getOwner() {
       return owner;
    }

    public void setOwner(CustomerProfile owner) {
        this.owner = owner;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }
}
