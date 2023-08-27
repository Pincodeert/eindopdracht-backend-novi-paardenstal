package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.Authority;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserDto {

    @Size(min=8, max=20, message = "username moet tussen 8 en 20 tekens bevatten")
    public String username;

    @Size(min=8, max=20, message = "password moet tussen 8 en 20 tekens bevatten")
    public String password;

    public boolean enabled;

    public String apikey;

    @Email(message = "ongeldig emailadres")
    public String email;

    public Set<Authority> authorities;

    public CustomerProfileDto customerProfile;


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

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public CustomerProfileDto getCustomerProfile() {
        return customerProfile;
    }

}


