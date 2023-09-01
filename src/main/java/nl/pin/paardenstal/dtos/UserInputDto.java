package nl.pin.paardenstal.dtos;

import nl.pin.paardenstal.models.Authority;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserInputDto {

    @Size(min=8, max=20, message = "username moet tussen 8 en 20 tekens bevatten")
    public String username;
    @Size(min=8, max=20, message = "password moet tussen 8 en 20 tekens bevatten")
    public String password;
    public boolean enabled;
    public String apikey;
    @Email(message = "ongeldig emailadres")
    public String email;
    public Set<Authority> authorities;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public String getApikey() {
        return apikey;
    }

    public String getEmail() {
        return email;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }
}
