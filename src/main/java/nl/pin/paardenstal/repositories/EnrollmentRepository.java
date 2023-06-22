package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

}
