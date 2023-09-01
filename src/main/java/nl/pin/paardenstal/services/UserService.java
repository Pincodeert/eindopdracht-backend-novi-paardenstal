package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.UserDto;
import nl.pin.paardenstal.dtos.UserInputDto;
import nl.pin.paardenstal.exceptions.NotYetRemovedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Authority;
import nl.pin.paardenstal.models.User;
import nl.pin.paardenstal.repositories.UserRepository;
import nl.pin.paardenstal.utils.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (User u : users) {
            UserDto dto = transferToDto(u);
            dtos.add(dto);
        }
        return dtos;
    }

    public UserDto getUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);
        UserDto dto = new UserDto();
        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();
            dto = transferToDto(storedUser);
            return dto;
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserDto userDto) {
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        userDto.setApikey(randomString);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        //userDto.setEnabled(true);
        User newUser = userRepository.save(transferToUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findById(username);

        if (optionalUser.isPresent()) {
            if(optionalUser.get().getCustomerProfile() != null){
                throw new NotYetRemovedException("remove customerprofile first");
            } else {
                userRepository.deleteById(username);
            }
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public void updateUser(String username, UserDto newUser) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Authority> getAuthorities(String username) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserDto userDto = transferToDto(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new UsernameNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public static UserDto transferToDto(User user){

        var dto = new UserDto();

        dto.username = user.getUsername();
        dto.password = user.getPassword();
        dto.enabled = user.isEnabled();
        dto.apikey = user.getApikey();
        dto.email = user.getEmail();
        dto.authorities = user.getAuthorities();

        return dto;
    }

    public User transferToUser(UserDto userDto) {

        var user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEnabled(userDto.getEnabled());
        user.setApikey(userDto.getApikey());
        user.setEmail(userDto.getEmail());

        return user;
    }

}
