package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
