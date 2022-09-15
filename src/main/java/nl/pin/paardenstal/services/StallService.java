package nl.pin.paardenstal.services;

import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.repositories.StallRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StallService {

    private final StallRepository stallRepository;

    @Autowired
    public StallService(StallRepository stallRepository){
        this.stallRepository = stallRepository;
    }

    List<Stall> stalls = new ArrayList<>();

    public List<Stall> getAllStalls(){
        stalls = stallRepository.findAll();
        return stalls;
    }

    public Stall getStall(long id){
        Optional<Stall> optionalStall = stallRepository.findById(id);

        if(optionalStall.isPresent()){
            return optionalStall.get();
        } else {
            throw new RecordNotFoundException("this ID does not exist");
        }
    }

    public long addStall(Stall stall){
        Stall newStall = stallRepository.save(stall);
        long newId = newStall.getId();
        return newId;
    }


}
