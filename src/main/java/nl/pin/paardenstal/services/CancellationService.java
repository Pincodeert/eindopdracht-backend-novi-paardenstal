package nl.pin.paardenstal.services;

import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Cancellation;
import nl.pin.paardenstal.repositories.CancellationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CancellationService {

    private final CancellationRepository cancellationRepository;

    @Autowired
    public CancellationService(CancellationRepository cancellationRepository){
        this.cancellationRepository = cancellationRepository;
    }

    public List<Cancellation> getAllCancellations(){
        List<Cancellation> cancellations = cancellationRepository.findAll();
        return cancellations;
    }

    public Cancellation getCancellation(long id){
        Optional<Cancellation> optionalCancellation = cancellationRepository.findById(id);

        if(optionalCancellation.isPresent()){
            return optionalCancellation.get();
        } else {
            throw new RecordNotFoundException("Can't find any cancellation by this ID");
        }
    }

    public long createCancellation(Cancellation cancellation){
        Cancellation newCancellation = cancellationRepository.save(cancellation);
        long id = newCancellation.getId();
        return id;
    }

    public void deleteCancellation(long id){
        Optional<Cancellation> optionalCancellation = cancellationRepository.findById(id);

        if(optionalCancellation.isPresent()){
            cancellationRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("Can't find any cancellation by this ID");
        }
    }

}
