package nl.pin.paardenstal.controllers;

import nl.pin.paardenstal.dtos.UserDto;
import nl.pin.paardenstal.dtos.UserInputDto;
import nl.pin.paardenstal.exceptions.BadRequestException;
import nl.pin.paardenstal.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        List<UserDto> userDtos = new ArrayList<>();
        userDtos = userService.getAllUsers();
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable String username){
        UserDto userDto = userService.getUser(username);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createNewUser(@Valid @RequestBody UserDto userDto,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                stringBuilder.append(fieldError.getDefaultMessage());
                stringBuilder.append("\n");
            }
            return ResponseEntity.badRequest().body(stringBuilder.toString());
        } else {
            String newUsername = userService.createUser(userDto);
            userService.addAuthority(newUsername, "ROLE_USER");

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{username}")
                    .buildAndExpand(newUsername).toUri();

            return ResponseEntity.created(location).build();
        }
    }

    @PutMapping("/users/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username, @RequestBody UserDto dto) {

        userService.updateUser(username, dto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/users/{username}")
    public ResponseEntity<Object> deleteUser(@PathVariable String username){
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}/authorities")
    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
        return ResponseEntity.ok().body(userService.getAuthorities(username));
    }

    @PostMapping("/users/{username}/authorities")
    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username,
                                                   @RequestBody Map<String, Object> fields) {
        try {
            String authorityName = (String) fields.get("authority");
            userService.addAuthority(username, authorityName);
            return ResponseEntity.noContent().build();
        }
        catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @DeleteMapping("/users/{username}/authorities/{authority}")
    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username,
                                                      @PathVariable("authority") String authority) {
        userService.removeAuthority(username, authority);
        return ResponseEntity.noContent().build();
    }

}
