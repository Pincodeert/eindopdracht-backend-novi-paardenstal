package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancellationRepository extends JpaRepository<Cancellation, Long> {

}
