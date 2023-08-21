package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.UserDto;
import nl.pin.paardenstal.dtos.UserInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.User;
import nl.pin.paardenstal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /*public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (User u : users) {
            UserDto dto = transferToDto(u);
            dtos.add(dto);
        }
        return dtos;
    }*/

    /*public UserDto getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User storedUser = optionalUser.get();
            UserDto dto = transferToDto(storedUser);
            return dto;
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }

    }*/

    /*public Long addNewUser(UserInputDto userInputDto) {
        User newUser = transferToUser(userInputDto);
        userRepository.save(newUser);
        Long newId = newUser.getId();
        return newId;
    }*/

    /*public void deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }*/

    /*public UserDto transferToDto(User user) {
        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        return dto;
    }*/

    public User transferToUser(UserInputDto userInputDto) {
        User user = new User();

        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());

        return user;

    }

}
