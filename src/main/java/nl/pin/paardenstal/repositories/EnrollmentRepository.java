package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findAllByCancellationRequested(Boolean cancellationRequested);
    List<Enrollment> findAllByCustomerProfileId(Long customerProfileId);
    List<Enrollment> findAllBySubscriptionId(Long subscriptionId);
    List<Enrollment> findAllEnrollmentsByIsOngoing(Boolean isOngoing);
}
