package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.*;
import nl.pin.paardenstal.exceptions.NotYetAssignedException;
import nl.pin.paardenstal.exceptions.NotYetRemovedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.models.Enrollment;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.User;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import nl.pin.paardenstal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final HorseService horseService;


    @Autowired
    public CustomerProfileService(CustomerProfileRepository customerProfileRepository,
                                  UserRepository userRepository,
                                  UserService userService, HorseService horseService
    ){
        this.customerProfileRepository = customerProfileRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.horseService = horseService;
    }

    public List<CustomerProfileDto> getAllCustomerProfiles(){
        List<CustomerProfile> customerProfiles = customerProfileRepository.findAll();
        List<CustomerProfileDto> dtos = new ArrayList<>();

        for(CustomerProfile c: customerProfiles){
            CustomerProfileDto dto = transferToDto(c);
            dtos.add(dto);
        }
        return dtos;
    }

    public CustomerProfileDto getCustomerProfile(Long id){
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(id);

        if(customerProfile.isPresent()){
            CustomerProfile storedCustomer = customerProfile.get();
            CustomerProfileDto dto = transferToDto(storedCustomer);

            List<Horse> horses = storedCustomer.getHorses();
            List<HorseDto> horseDtos = new ArrayList<>();
            for(Horse h: horses){
                HorseDto horseDto = horseService.transferToDto(h);
                horseDtos.add(horseDto);
            }
            dto.setHorses(horseDtos);


            return dto;
        } else {
            throw new RecordNotFoundException("this ID does not exist");
        }

    }

    public Long createNewCustomerProfile(CustomerProfileInputDto inputDto){
        CustomerProfile newCustomerProfile = transferToCustomerProfile(inputDto);
                customerProfileRepository.save(newCustomerProfile);
        Long id = newCustomerProfile.getId();
        return id;
    }

    public void deleteCustomerProfile(Long id){
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(id);

        if(customerProfile.isPresent()){
            CustomerProfile customer = customerProfile.get();
            //checkt of er nog paarden aan een Enrollment gekoppeld zijn voor deze klant. Zo ja, wordt een error voorkomen
            // door een exception te gooien.
            List<Horse> horses = customer.getHorses();
            List<Horse> enrolledHorses = new ArrayList<>();
            if(horses != null) {
                for(Horse h: horses) {
                    if(h.getEnrollment() != null) {
                        enrolledHorses.add(h);
                    }
                }
                if(enrolledHorses.size() > 0) {
                    throw new NotYetRemovedException("can't delete this customer; terminate subscription of all its horses first");
                }
            }
            //om customerProfile te kunnen deleten, moet eerst de foreignkey verwijderd worden voor iedere Enrollment
            List<Enrollment> enrollments = customer.getEnrollments();
            List<Enrollment> updatedEnrollments = new ArrayList<>();
            if(enrollments != null) {
                for(Enrollment e: enrollments) {
                    e.setCustomer(null);
                    updatedEnrollments.add(e);
                }
                customer.setEnrollments(updatedEnrollments);
            }
            if(customer.getUser() != null) {
                throw new NotYetRemovedException("remove user from customer first");
            }
            customerProfileRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This Id doesn't exist");
        }
    }

    public void updateCustomerProfile(Long id, CustomerProfileInputDto inputDto){
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(id);

        if(optionalCustomerProfile.isPresent()){
            CustomerProfile storedCustomerProfile = optionalCustomerProfile.get();
            CustomerProfile customerProfile = transferToCustomerProfile(inputDto);
            customerProfile.setId(storedCustomerProfile.getId());
            customerProfileRepository.save(customerProfile);
        } else {
            throw new RecordNotFoundException("This ID doesn't exist");
        }
    }

    public void partialUpdateCustomerProfile(Long id, CustomerProfileInputDto inputDto){
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(id);

        if(optionalCustomerProfile.isPresent()){
            CustomerProfile storedCustomerProfile = customerProfileRepository.findById(id).orElse(null);
            CustomerProfile customerProfile = transferToCustomerProfile(inputDto);
            //if(customerProfile.getFirstName()!= null && !customerProfile.getFirstName().isEmpty()){
            //    storedCustomerProfile.setFirstName(customerProfile.getFirstName());
            //}
            //if(customerProfile.getLastName() != null && !customerProfile.getLastName().isEmpty()){
            //    storedCustomerProfile.setLastName(customerProfile.getLastName());
            //}
            if(customerProfile.getStreet() != null && !customerProfile.getStreet().isEmpty()){
                storedCustomerProfile.setHouseNumber(customerProfile.getHouseNumber());
            }
            if(customerProfile.getHouseNumber() != null && !customerProfile.getHouseNumber().isEmpty()){
                storedCustomerProfile.setHouseNumber(customerProfile.getHouseNumber());
            }
            if(customerProfile.getPostalCode() != null && !customerProfile.getPostalCode().isEmpty()){
                storedCustomerProfile.setPostalCode(customerProfile.getPostalCode());
            }
            if(customerProfile.getResidence() != null && !customerProfile.getResidence().isEmpty()){
                storedCustomerProfile.setResidence(customerProfile.getResidence());
            }
            if(customerProfile.getTelephoneNumber() != null && !customerProfile.getTelephoneNumber().isEmpty()){
                storedCustomerProfile.setTelephoneNumber(customerProfile.getTelephoneNumber());
            }
            if(customerProfile.getEmailAddress() != null && !customerProfile.getEmailAddress().isEmpty()){
                storedCustomerProfile.setEmailAddress(customerProfile.getEmailAddress());
            }
            if(customerProfile.getBankAccountNumber() !=null && !customerProfile.getBankAccountNumber().isEmpty()){
                storedCustomerProfile.setBankAccountNumber(customerProfile.getBankAccountNumber());
            }
            customerProfileRepository.save(storedCustomerProfile);
        } else {
            throw new RecordNotFoundException("Geen klant bekend met deze ID");
        }
    }

    public CustomerProfile transferToCustomerProfile(CustomerProfileInputDto customerProfileInputDto){
        CustomerProfile customerProfile = new CustomerProfile();

        customerProfile.setFirstName(customerProfileInputDto.getFirstName());
        customerProfile.setLastName(customerProfileInputDto.getLastName());
        customerProfile.setStreet(customerProfileInputDto.getStreet());
        customerProfile.setHouseNumber(customerProfileInputDto.getHouseNumber());
        customerProfile.setPostalCode(customerProfileInputDto.getPostalCode());
        customerProfile.setResidence(customerProfileInputDto.getResidence());
        customerProfile.setTelephoneNumber(customerProfileInputDto.getTelephoneNumber());
        customerProfile.setEmailAddress(customerProfileInputDto.getEmailAddress());
        customerProfile.setBankAccountNumber(customerProfileInputDto.getBankAccountNumber());

        return customerProfile;
    }

    public CustomerProfileDto transferToDto(CustomerProfile customerProfile){
        CustomerProfileDto dto = new CustomerProfileDto();

        dto.setId(customerProfile.getId());
        dto.setFirstName(customerProfile.getFirstName());
        dto.setLastName(customerProfile.getLastName());
        dto.setStreet(customerProfile.getStreet());
        dto.setHouseNumber(customerProfile.getHouseNumber());
        dto.setPostalCode(customerProfile.getPostalCode());
        dto.setResidence(customerProfile.getResidence());
        dto.setTelephoneNumber(customerProfile.getTelephoneNumber());
        dto.setEmailAddress(customerProfile.getEmailAddress());
        dto.setBankAccountNumber(customerProfile.getBankAccountNumber());

        if(customerProfile.getUser() != null){
            UserDto userDto = userService.transferToDto(customerProfile.getUser());
            dto.setUser(userDto);
        }
        return dto;
    }

    public void assignUserToCustomerProfile(Long id, String username){
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(id);
        Optional<User> optionalUser = userRepository.findById(username);

        if(optionalCustomerProfile.isPresent() && optionalUser.isPresent()){
            CustomerProfile customer = optionalCustomerProfile.get();
            User user = optionalUser.get();
            customer.setUser(user);
            customerProfileRepository.save(customer);
        } else if(!optionalCustomerProfile.isPresent() && !optionalUser.isPresent()){
            throw new RecordNotFoundException("There's no customer nor user with this ID");
        } else if (!optionalCustomerProfile.isPresent()){
            throw new RecordNotFoundException("There's no customer with this ID");
        } else if (!optionalUser.isPresent()){
            throw new RecordNotFoundException("There's no user with this ID");
        }
    }

    public void removeUserFromCustomerProfile(Long customerId, String username) {
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(customerId);
        Optional<User> optionalUser = userRepository.findById(username);

        if (!optionalCustomerProfile.isPresent() && !optionalUser.isPresent()) {
            throw new RecordNotFoundException("There's no customer nor user with this ID");
        } else if (!optionalCustomerProfile.isPresent()) {
            throw new RecordNotFoundException("There's no customer with this ID");
        } else if (!optionalUser.isPresent()) {
            throw new RecordNotFoundException("There's no user with this ID");
        } else if (optionalCustomerProfile.isPresent() && optionalUser.isPresent()) {
            CustomerProfile customer = optionalCustomerProfile.get();
            User user = optionalUser.get();
            if (customer.getUser() == null) {
                throw new NotYetAssignedException("there's no user to be removed");
            } else {
                customer.setUser(null);
                customerProfileRepository.save(customer);
            }
        }
    }

}
