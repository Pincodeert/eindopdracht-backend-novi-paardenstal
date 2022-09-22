package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.CustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {



}
