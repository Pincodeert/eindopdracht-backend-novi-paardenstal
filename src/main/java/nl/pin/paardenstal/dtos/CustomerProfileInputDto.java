package nl.pin.paardenstal.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerProfileInputDto {

    @Size(min=2, max=60, message = "voornaam moet tussen 2 en 60 tekens lang zijn")
    private String firstName;

    @Size(min=2, max=60, message = "achternaam moet tussen 2 en 60 tekens lang zijn")
    private String lastName;

    @Size(min=3, max=60, message = "straat moet tussen 3 en 60 tekens lang zijn")
    private String street;

    @Size(min=1, max = 20, message = "huisnummer moet tussen 1 en 20 tekens lang zijn")
    private String houseNumber;

    @Size(min=4, max=6, message = "postcode moet 4 tot 6 tekens lang zijn")
    private String postalCode;

    @Size(min=2, max=60, message = "woonplaats moet tussen 2 en 60 tekens lang zijn")
    private String residence;

    @Size(min=10, max=10, message = "telefoonnummer moet uit 10 cijfers bestaan")
    private String telephoneNumber;

    @Email(message = "ongeldig emailadres")
    private String emailAddress;

    @Size(min = 16, max = 16, message = "bankrekeningnummer moet uit 16 tekens bestaan")
    private String bankAccountNumber;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}
