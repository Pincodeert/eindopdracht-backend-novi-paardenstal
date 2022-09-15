package nl.pin.paardenstal.services;

import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Stable;
import nl.pin.paardenstal.repositories.StableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StableService {

    private final StableRepository stableRepository;

    @Autowired
    public StableService(StableRepository stableRepository){
        this.stableRepository = stableRepository;
    }

    List<Stable> stables = new ArrayList<>();

    public List<Stable> getAllStables(){
        stables = stableRepository.findAll();
        return stables;
    }

    public Stable getStable(long id){
        Optional<Stable> optionalStable = stableRepository.findById(id);

        if(optionalStable.isPresent()){
            return optionalStable.get();
        } else {
            throw new RecordNotFoundException("this ID does not exist");
        }
    }

    public long addStable(Stable stable){
        Stable newStable = stableRepository.save(stable);
        long newId = newStable.getId();
        return newId;
    }


}
