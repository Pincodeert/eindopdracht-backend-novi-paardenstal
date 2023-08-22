package nl.pin.paardenstal.repositories;

import nl.pin.paardenstal.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
