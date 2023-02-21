package nl.pin.paardenstal.services;

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
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    List<User> users = new ArrayList<>();

    public List<User> getAllUsers(){
        users = userRepository.findAll();
        return users;
    }

    public User getUser(long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }

    }

    public long addNewUser(User user){
        User newUser = userRepository.save(user);
        long newId = newUser.getId();
        return newId;
    }

    public void deleteUser(long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isPresent()){
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This ID does not exist");
        }
    }




}
