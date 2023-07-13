package nl.pin.paardenstal.repositories;


import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Stall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StallRepository extends JpaRepository<Stall, Long> {

    public List<Stall> findAllByIsOccupied(Boolean isOccupied);

    public List<Stall> findAllByTypeIgnoreCase(String type);

    public List<Stall> findAllByTypeIgnoreCaseAndIsOccupied(String type, Boolean isOccupied);

}
