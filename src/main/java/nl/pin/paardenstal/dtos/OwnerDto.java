package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.Stall;

import java.util.ArrayList;
import java.util.List;

public class OwnerDto {

    private long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private UserDto user;

    private List<StallDto> stalls = new ArrayList<>();


    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
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

    public List<StallDto> getStalls(){
        return stalls;
    }

    public void setStalls(List<StallDto> stalls){
        this.stalls = stalls;
    }


}
