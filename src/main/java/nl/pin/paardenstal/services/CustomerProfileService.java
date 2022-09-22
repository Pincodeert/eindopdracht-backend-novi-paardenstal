package nl.pin.paardenstal.services;

import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.CustomerProfile;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerProfileService {

    private final CustomerProfileRepository customerProfileRepository;

    @Autowired
    public CustomerProfileService(CustomerProfileRepository customerProfileRepository){
        this.customerProfileRepository = customerProfileRepository;
    }

    public List<CustomerProfile> getAllCustomerProfiles(){
        List<CustomerProfile> customerProfiles = customerProfileRepository.findAll();
        return customerProfiles;
    }

    public CustomerProfile getCustomerProfile(long id){
        Optional<CustomerProfile> customerProfile = customerProfileRepository.findById(id);

        if(customerProfile.isPresent()){
            return customerProfile.get();
        } else {
            throw new RecordNotFoundException("this ID does not exist");
        }

    }

    public long createNewCustomerProfile(CustomerProfile customerProfile){
        CustomerProfile newCustomerProfile = customerProfileRepository.save(customerProfile);
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

    public void updateCustomerProfile(long id, CustomerProfile customerProfile){
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(id);

        if(optionalCustomerProfile.isPresent()){
            CustomerProfile storedCustomerProfile = optionalCustomerProfile.get();
            customerProfile.setId(storedCustomerProfile.getId());
            customerProfileRepository.save(customerProfile);
        } else {
            throw new RecordNotFoundException("This ID doesn't exist");
        }
    }

    public void partialUpdateCustomerProfile(long id, CustomerProfile customerProfile){
        Optional<CustomerProfile> optionalCustomerProfile = customerProfileRepository.findById(id);

        if(optionalCustomerProfile.isPresent()){
            CustomerProfile storedCustomerProfile = customerProfileRepository.findById(id).orElse(null);
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

}
