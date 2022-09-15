package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Horse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HorseRepository extends JpaRepository<Horse, Long> {

}
