package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

}
