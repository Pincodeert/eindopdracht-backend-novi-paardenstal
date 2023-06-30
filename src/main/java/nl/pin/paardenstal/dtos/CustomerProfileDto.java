package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.Horse;

import java.util.ArrayList;
import java.util.List;

public class CustomerProfileDto {

    private long id;

    private String firstName;

    private String lastName;

    private String street;

    private String houseNumber;

    private String postalCode;

    private String residence;

    private String telephoneNumber;

    private String emailAddress;

    private UserDto user;

    private List<HorseDto> horses = new ArrayList<>();

    private List<Long> subscriptionIds = new ArrayList<>();


    private List<EnrollmentDto> enrollments = new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<HorseDto> getHorses(){
        return horses;
    }

    public void setHorses(List<HorseDto> horses){
        this.horses = horses;
    }

    public List<Long> getSubscriptions() {
        return subscriptionIds;
    }

    public void setSubscriptions(List<Long> subscriptions) {
        this.subscriptionIds = subscriptions;
    }

    public List<EnrollmentDto> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<EnrollmentDto> enrollments) {
        this.enrollments = enrollments;
    }

    public void addHorseDtoToList(HorseDto horseDto){
        horses.add(horseDto);
    }

    public void deleteHorseDtoFromList(HorseDto horse){
        horses.remove(horse);
    }

    public void addEnrollmentDtoToList(EnrollmentDto enrollmentDto) {
        enrollments.add(enrollmentDto);
    }

    public void deleteEnrollmentDtoFromList(EnrollmentDto enrollmentDto) {
        enrollments.remove(enrollmentDto);
    }

}
