package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Horse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorseRepository extends JpaRepository<Horse, Long> {

    List<Horse> findAllByOwnerId(Long ownerId);
}
