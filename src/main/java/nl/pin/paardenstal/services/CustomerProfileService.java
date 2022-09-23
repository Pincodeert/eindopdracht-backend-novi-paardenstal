package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.CustomerProfileInputDto;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public CustomerProfileService(CustomerProfileRepository customerProfileRepository){
        this.customerProfileRepository = customerProfileRepository;
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

    public CustomerProfileDto getCustomerProfile(long id){
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(id);

        if(customerProfile.isPresent()){
            CustomerProfileDto dto = transferToDto(customerProfile.get());

            return dto;
        } else {
            throw new RecordNotFoundException("this ID does not exist");
        }

    }

    public long createNewCustomerProfile(CustomerProfileInputDto inputDto){
        CustomerProfile newCustomerProfile = transferToCustomerProfile(inputDto);
                customerProfileRepository.save(newCustomerProfile);
        long id = newCustomerProfile.getId();
        return id;
    }

    public void deleteCustomerProfile(long id){
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(id);

        if(customerProfile.isPresent()){
            customerProfileRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("This Id doesn't exist");
        }
    }

    public void updateCustomerProfile(long id, CustomerProfileInputDto inputDto){
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

    public void partialUpdateCustomerProfile(long id, CustomerProfileInputDto inputDto){
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(id);

        if(optionalCustomerProfile.isPresent()){
            CustomerProfile storedCustomerProfile = customerProfileRepository.findById(id).orElse(null);
            CustomerProfile customerProfile = transferToCustomerProfile(inputDto);
            if(customerProfile.getFirstName()!= null && !customerProfile.getFirstName().isEmpty()){
                storedCustomerProfile.setFirstName(customerProfile.getFirstName());
            }
            if(customerProfile.getLastName() != null && !customerProfile.getLastName().isEmpty()){
                storedCustomerProfile.setLastName(customerProfile.getLastName());
            }
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
            customerProfileRepository.save(storedCustomerProfile);
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

        return dto;
    }

}
