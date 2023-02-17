package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.models.User;
import nl.pin.paardenstal.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    List<User> users = new ArrayList<>();

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id){
        User user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> addNewUser(@RequestBody User user){
        long newId = userService.addNewUser(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}
