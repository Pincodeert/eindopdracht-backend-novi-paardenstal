package nl.pin.paardenstal.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

public class UserInputDto {

    @Size(min=8, max=20, message = "username moet tussen 8 en 20 tekens bevatten")
    private String username;

    @Size(min=8, max=20, message = "password moet tussen 8 en 20 tekens bevatten")
    private String password;

    @Email(message = "ongeldig emailadres")
    private String emailAdress;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }
}
