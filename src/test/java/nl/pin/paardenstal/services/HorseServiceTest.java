package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.CustomerProfileDto;
import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.exceptions.NotYetRemovedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.*;
import nl.pin.paardenstal.repositories.CustomerProfileRepository;
import nl.pin.paardenstal.repositories.FileUploadRepository;
import nl.pin.paardenstal.repositories.HorseRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HorseServiceTest {

    @Mock
    HorseRepository horseRepository;

    @Mock
    CustomerProfileRepository customerProfileRepository;

    @Mock
    CustomerProfileService customerProfileService;

    @Mock
    StallService stallService;

    @Mock
    FileUploadRepository fileUploadRepository;

    @InjectMocks
    HorseService horseService;

    @Captor
    ArgumentCaptor<Horse> captor;

    CustomerProfile owner1;
    CustomerProfile owner2;
    CustomerProfileDto ownerDto1;
    CustomerProfileDto ownerDto2;
    Stall stall1;
    StallDto stallDto1;
    FileUploadResponse passport1;
    Enrollment enrollment1;
    Horse horse1;
    Horse horse2;

    Horse horse3;
    HorseDto horseDto1;
    HorseDto horseDto2;
    HorseDto horseDto3;

    HorseInputDto horseInputDto1;


    @BeforeEach
    void setUp() {

        owner1 = new CustomerProfile();
        owner1.setId(1L);
        owner1.setFirstName("Owni1");

        owner2 = new CustomerProfile();
        owner2.setId(2L);
        owner2.setFirstName("Owni2");

        ownerDto1 = new CustomerProfileDto();
        ownerDto1.setId(1L);
        ownerDto1.setFirstName("Owni1");

        ownerDto2 = new CustomerProfileDto();
        ownerDto2.setId(2L);
        ownerDto2.setFirstName("Owni2");

        stall1 = new Stall();
        stall1.setId(1L);
        stall1.setName("stall1");

        stallDto1 = new StallDto();
        stallDto1.setId(1L);
        stallDto1.setName("stall1");

        passport1 = new FileUploadResponse();
        passport1.setFileName("passport1");

        enrollment1 = new Enrollment();
        enrollment1.setId(1L);

        horse1 = new Horse();
        horse1.setId(1L);
        horse1.setName("horse1");
        horse1.setHorseNumber("horsNum1111");
        horse1.setTypeOfFeed("feed1");
        horse1.setTypeOfBedding("bedding1");
        horse1.setNameOfVet("vet1");
        horse1.setResidenceOfVet("vetcity1");
        horse1.setTelephoneOfVet("telvet1111");
        horse1.setPreferredSubscription("subscrip1");
        horse1.setOwner(owner1);
        horse1.setStall(null);
        horse1.setPassport(null);
        horse1.setEnrollment(null);

        horse2 = new Horse();
        horse2.setId(2L);
        horse2.setName("horse2");
        horse2.setHorseNumber("horsNum2222");
        horse2.setTypeOfFeed("feed2");
        horse2.setTypeOfBedding("bedding2");
        horse2.setNameOfVet("vet2");
        horse2.setResidenceOfVet("vetcity2");
        horse2.setTelephoneOfVet("telvet2222");
        horse2.setPreferredSubscription("subscrip2");
        horse2.setOwner(owner2);
        horse2.setStall(stall1);
        horse2.setPassport(passport1);
        horse2.setEnrollment(enrollment1);

        horse3 = new Horse();
        horse3.setId(3L);
        horse3.setName("horse3");
        horse3.setOwner(owner2);

        horseDto1 = new HorseDto();
        horseDto1.setId(1L);
        horseDto1.setName("horse1");
        horseDto1.setHorseNumber("horsNum1111");
        horseDto1.setTypeOfFeed("feed1");
        horseDto1.setTypeOfBedding("bedding1");
        horseDto1.setNameOfVet("vet1");
        horseDto1.setResidenceOfVet("vetcity1");
        horseDto1.setTelephoneOfVet("telvet1111");
        horseDto1.setPreferredSubscription("subscrip1");
        horseDto1.setOwner(ownerDto1);
        horseDto1.setStall(null);
        horseDto1.setPassport(null);

        horseDto2 = new HorseDto();
        horseDto2.setId(2L);
        horseDto2.setName("horse2");
        horseDto2.setHorseNumber("horsNum2222");
        horseDto2.setTypeOfFeed("feed2");
        horseDto2.setTypeOfBedding("bedding2");
        horseDto2.setNameOfVet("vet2");
        horseDto2.setResidenceOfVet("vetcity2");
        horseDto2.setTelephoneOfVet("telvet2222");
        horseDto2.setPreferredSubscription("subscrip2");
        horseDto2.setOwner(ownerDto2);
        horseDto2.setStall(stallDto1);
        horseDto2.setPassport(passport1);

        horseDto3 = new HorseDto();
        horseDto3.setId(3L);
        horseDto3.setName("horse3");
        horseDto3.setOwner(ownerDto2);

        horseInputDto1 = new HorseInputDto();
        horseInputDto1.setName("horse1");
        horseInputDto1.setHorseNumber("horsNum1111");
        horseInputDto1.setTypeOfFeed("feed1");
        horseInputDto1.setTypeOfBedding("bedding1");
        horseInputDto1.setNameOfVet("vet1");
        horseInputDto1.setResidenceOfVet("vetcity1");
        horseInputDto1.setTelephoneOfVet("telvet1111");
        horseInputDto1.setPreferredSubscription("subscrip1");

    }

    @AfterEach
    void tearDown() {
    }

    //getAllHorses()
    @Test
    void shouldReturnListOfHorseDtos() {

        List<Horse> horses = new ArrayList<>();
        horses.add(horse1);
        horses.add(horse2);

        List<HorseDto> horseDtos = new ArrayList<>();
        horseDtos.add(horseDto1);
        horseDtos.add(horseDto2);

        when(horseRepository.findAll()).thenReturn(horses);

        List<HorseDto> result = horseService.getAllHorses();

        assertEquals(horseDtos.size(), result.size());
        assertEquals(horseDtos.get(0).getId(), result.get(0).getId());
        assertEquals(horseDtos.get(1).getName(), result.get(1).getName());

    }

    //getHorse()
    @Test
    void getHorse() {

        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));
        when(horseRepository.findById(2L)).thenReturn(Optional.of(horse2));
        when(stallService.transferToDto(stall1)).thenReturn(stallDto1);

        HorseDto result1 = horseService.getHorse(1L);
        HorseDto result2 = horseService.getHorse(2L);

        assertEquals(horseDto1.getId(), result1.getId());
        assertEquals(horseDto1.getName(), result1.getName());
        assertEquals(horseDto1.getHorseNumber(), result1.getHorseNumber());
        assertEquals(horseDto1.getStall(), result1.getStall());

        assertEquals(horseDto2.getId(), result2.getId());
        assertEquals(horseDto2.getStall().getName(), result2.getStall().getName());
        assertEquals(horseDto2.getPassport().getFileName(), result2.getPassport().getFileName());


    }

    //getHorse
    @Test
    void shouldThrowExceptionWhenHorseIdIsUnkown () {
        assertThrows(RecordNotFoundException.class, () -> horseService.getHorse(null));
    }

    //getAllHorsesByCustomerProfileId()
    @Test
    void CustomerProfileIdOfOwnerOfHorseShouldBeSameAsGivenCustomerProfile() {

        List<Horse> horsesOfOwner1 = new ArrayList<>();
        horsesOfOwner1.add(horse1);

        List<Horse> horsesOfOwner2 = new ArrayList<>();
        horsesOfOwner2.add(horse2);
        horsesOfOwner2.add(horse3);

        when(horseRepository.findAllByOwnerId(1L)).thenReturn(List.of(horse1));
        when(horseRepository.findAllByOwnerId(2L)).thenReturn(List.of(horse2, horse3));

        List<Horse> result1 = horseRepository.findAllByOwnerId(1L);
        List<Horse> result2 = horseRepository.findAllByOwnerId(2L);

        assertEquals(horsesOfOwner1.get(0).getOwner().getId(), horsesOfOwner1.get(0).getOwner().getId());
        assertEquals(horsesOfOwner2.get(0).getOwner().getId(), horsesOfOwner2.get(0).getOwner().getId());
        assertEquals(horsesOfOwner2.get(1).getOwner().getId(), horsesOfOwner2.get(1).getOwner().getId());

    }

    //getAllHorsesByCustomerProfileId()
    @Test
    void shouldReturnAllHorsesByCustomerProfileWhenGivenCustomerProfileId() {

        List<HorseDto> horsesOfOwner1 = new ArrayList<>();
        horsesOfOwner1.add(horseDto1);

        List<HorseDto> horsesOfOwner2 = new ArrayList<>();
        horsesOfOwner2.add(horseDto2);
        horsesOfOwner2.add(horseDto3);

        when(horseRepository.findAllByOwnerId(1L)).thenReturn(List.of(horse1));
        when(horseRepository.findAllByOwnerId(2L)).thenReturn(List.of(horse2, horse3));

        List<HorseDto> result1 = horseService.getAllHorsesByCustomerProfileId(1L);
        List<HorseDto> result2 = horseService.getAllHorsesByCustomerProfileId(2L);

        assertEquals(horsesOfOwner1.size(), result1.size());
        assertEquals(horsesOfOwner1.get(0).getId(), result1.get(0).getId());
        assertEquals(horsesOfOwner1.get(0).getName(), result1.get(0).getName());
        assertEquals(horsesOfOwner2.size(), result2.size());

    }

    //getAllHorsesByCustomerProfileId()
    @Test
    void shouldSetStallDtoToHorseDto() {

        when(horseRepository.findAllByOwnerId(2L)).thenReturn(List.of(horse2));

        List<HorseDto> result = horseService.getAllHorsesByCustomerProfileId(2L);

        assertEquals(horseDto2.getName(), result.get(0).getName());
        assertEquals(horseDto2.getNameOfVet(), result.get(0).getNameOfVet());

    }


    @Test
    void addNewHorse() {

        horse1.setOwner(null);
        when(horseRepository.save(any(Horse.class))).thenReturn(horse2);

        horseService.addNewHorse(horseInputDto1);
        verify(horseRepository, times(1)).save(captor.capture());
        Horse horse = captor.getValue();

        assertEquals(horse1.getName(), horse.getName());
        assertEquals(horse1.getHorseNumber(), horse.getHorseNumber());

    }

    //addNewHorse
    @Test
    void shouldReturnLongIdWhenGivenHorseInputDto() {
        horse1.setOwner(null);

        when(horseRepository.save(any(Horse.class))).thenReturn(horse1);

        Long result = horseService.addNewHorse(horseInputDto1);

        assertEquals(horse1.getId(), result);
    }

    //updateHorse
    @Test
    void updateHorse() {

        when(horseRepository.findById(2L)).thenReturn(Optional.of(horse2));
        when(horseRepository.save(any())).thenReturn(horse1);

        horseService.updateHorse(2L, horseInputDto1);
        verify(horseRepository, times(1)).save(captor.capture());

        Horse captured = captor.getValue();

        assertEquals(horse1.getName(), captured.getName());
        assertEquals(horse1.getHorseNumber(), captured.getHorseNumber());
        assertEquals(horse1.getTypeOfFeed(), captured.getTypeOfFeed());
        assertEquals(horse1.getTypeOfBedding(), captured.getTypeOfBedding());
        assertEquals(horse1.getNameOfVet(), captured.getNameOfVet());
        assertEquals(horse1.getResidenceOfVet(), captured.getResidenceOfVet());
        assertEquals(horse1.getTelephoneOfVet(), captured.getTelephoneOfVet());

    }

    //updateHorse
    @Test
    void shouldThrowExceptionWhenIdUnknown() {
        assertThrows(RecordNotFoundException.class, () -> horseService.updateHorse(5L, horseInputDto1));
    }

    //deleteHorse
    @Test
    void shouldDeleteHorseWhenOwnerAndStallAndEnrollmentAreNull() {

        Horse testHorse = new Horse();
        testHorse.setId(4L);
        testHorse.setName("horse1");
        testHorse.setHorseNumber("horsNum1111");
        testHorse.setTypeOfFeed("feed1");
        testHorse.setTypeOfBedding("bedding1");
        testHorse.setNameOfVet("vet1");
        testHorse.setResidenceOfVet("vetcity1");
        testHorse.setTelephoneOfVet("telvet1111");
        testHorse.setPreferredSubscription("subscrip1");
        testHorse.setOwner(null);
        testHorse.setStall(null);
        testHorse.setPassport(null);
        testHorse.setEnrollment(null);

        when(horseRepository.findById(4L)).thenReturn(Optional.of(testHorse));

        horseService.deleteHorse(4L);
        verify(horseRepository).delete(testHorse);
    }


    @Test
    void shouldDeleteHorseWhenOwnerIsNotNull() {

        Horse resultHorse = new Horse();
        resultHorse.setId(1L);
        resultHorse.setName("horse1");
        resultHorse.setHorseNumber("horsNum1111");
        resultHorse.setTypeOfFeed("feed1");
        resultHorse.setTypeOfBedding("bedding1");
        resultHorse.setNameOfVet("vet1");
        resultHorse.setResidenceOfVet("vetcity1");
        resultHorse.setTelephoneOfVet("telvet1111");
        resultHorse.setPreferredSubscription("subscrip1");
        resultHorse.setOwner(null);
        resultHorse.setStall(null);
        resultHorse.setPassport(null);
        resultHorse.setEnrollment(null);

        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));
        when(horseRepository.save(horse1)).thenReturn(resultHorse);

        horseService.deleteHorse(1L);
        verify(horseRepository).delete(resultHorse);
    }

    //deleteHorse
    @Test
    void shouldThrowErrorWhenStallIsNotNull() {
        Horse testHorse = new Horse();
        testHorse.setId(1L);
        testHorse.setName("horse1");
        testHorse.setHorseNumber("horsNum1111");
        testHorse.setTypeOfFeed("feed1");
        testHorse.setTypeOfBedding("bedding1");
        testHorse.setNameOfVet("vet1");
        testHorse.setResidenceOfVet("vetcity1");
        testHorse.setTelephoneOfVet("telvet1111");
        testHorse.setPreferredSubscription("subscrip1");
        testHorse.setOwner(null);
        testHorse.setStall(stall1);
        testHorse.setPassport(null);
        testHorse.setEnrollment(null);

        when(horseRepository.findById(4L)).thenReturn(Optional.of(testHorse));

        assertThrows(NotYetRemovedException.class, () -> horseService.deleteHorse(4L));

    }

    //deleteHorse
    @Test
    void shouldThrowErrorWhenEnrollmentIsNotNull() {
        Horse testHorse = new Horse();
        testHorse.setId(4L);
        testHorse.setName("horse1");
        testHorse.setHorseNumber("horsNum1111");
        testHorse.setTypeOfFeed("feed1");
        testHorse.setTypeOfBedding("bedding1");
        testHorse.setNameOfVet("vet1");
        testHorse.setResidenceOfVet("vetcity1");
        testHorse.setTelephoneOfVet("telvet1111");
        testHorse.setPreferredSubscription("subscrip1");
        testHorse.setOwner(null);
        testHorse.setStall(null);
        testHorse.setPassport(null);
        testHorse.setEnrollment(enrollment1);

        when(horseRepository.findById(4L)).thenReturn(Optional.of(testHorse));

        assertThrows(NotYetRemovedException.class, () -> horseService.deleteHorse(4L));

    }

    //deleteHorse
    @Test
    void shouldThrowRecordNotFoundExceptionWhenHorseIsIsNotFound() {

        assertThrows(RecordNotFoundException.class, () -> horseService.deleteHorse(5l));
    }



    //transferToDto
    @Test
    void shouldReturnHorseDtoWhenGivenHorse() {

        HorseDto result = horseService.transferToDto(horse1);

        assertEquals(horseDto1.getId(), result.getId());
        assertEquals(horseDto1.getName(), result.getName());
        assertEquals(horseDto1.getHorseNumber(), result.getHorseNumber());
        assertEquals(horseDto1.getTypeOfFeed(), result.getTypeOfFeed());
        assertEquals(horseDto1.getTypeOfBedding(), result.getTypeOfBedding());
        assertEquals(horseDto1.getNameOfVet(), result.getNameOfVet());
        assertEquals(horseDto1.getResidenceOfVet(), result.getResidenceOfVet());
        assertEquals(horseDto1.getTelephoneOfVet(), result.getTelephoneOfVet());
        assertEquals(horseDto1.getPreferredSubscription(), result.getPreferredSubscription());

    }

    //transferToHorse
    @Test
    void shouldReturnHorseWhenGivenHorseDto() {

        Horse result = horseService.transferToHorse(horseInputDto1);

        assertEquals(horseInputDto1.getName(), result.getName());
        assertEquals(horseInputDto1.getHorseNumber(), result.getHorseNumber());
        assertEquals(horseInputDto1.getTypeOfFeed(), result.getTypeOfFeed());
        assertEquals(horseInputDto1.getTypeOfBedding(), result.getTypeOfBedding());
        assertEquals(horseInputDto1.getNameOfVet(), result.getNameOfVet());
        assertEquals(horseInputDto1.getResidenceOfVet(), result.getResidenceOfVet());
        assertEquals(horseInputDto1.getTelephoneOfVet(), result.getTelephoneOfVet());
        assertEquals(horseInputDto1.getPreferredSubscription(), result.getPreferredSubscription());

    }

    //assignCustomerProfileToHorse
    @Test
    void shouldSetOwnerWhenWhenGivenCustomerProfileId() {

        Horse horse4 = new Horse();
        horse4.setId(1L);
        horse4.setName("horse1");
        horse4.setHorseNumber("horsNum1111");
        horse4.setTypeOfFeed("feed1");
        horse4.setTypeOfBedding("bedding1");
        horse4.setNameOfVet("vet1");
        horse4.setResidenceOfVet("vetcity1");
        horse4.setTelephoneOfVet("telvet1111");
        horse4.setPreferredSubscription("subscrip1");
        horse4.setOwner(null);
        horse4.setStall(null);
        horse4.setPassport(null);
        horse4.setEnrollment(null);

        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse4));
        when(customerProfileRepository.findById(1L)).thenReturn(Optional.of(owner1));
        when(horseRepository.save(any(Horse.class))).thenReturn(horse1);

        horseService.assignCustomerProfileToHorse(1l, 1L);

        verify(horseRepository, times(1)).save(captor.capture());

        Horse captured = captor.getValue();

        assertEquals(horse1.getId(), captured.getId());
        assertEquals(horse1.getOwner().getId(), captured.getOwner().getId());

    }

    //assignCustomerProfileToHorse
    @Test
    void shouldThrowExceptionWhenHorseIdIsUnknown() {
        assertThrows(RecordNotFoundException.class, () -> horseService.assignCustomerProfileToHorse(5L, 1L));
    }

    //assignCustomerProfileToHorse
    @Test
    void shouldThrowExceptionWhenCustomerProfileIdIsUnknown() {

        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));

        assertThrows(RecordNotFoundException.class, () -> horseService.assignCustomerProfileToHorse(1L, null));
    }

    //removeCustomerProfileFromHorse
    @Test
    void removeCustomerProfileFromHorse() {

        Horse horse4 = new Horse();
        horse4.setId(1L);
        horse4.setName("horse1");
        horse4.setHorseNumber("horsNum1111");
        horse4.setTypeOfFeed("feed1");
        horse4.setTypeOfBedding("bedding1");
        horse4.setNameOfVet("vet1");
        horse4.setResidenceOfVet("vetcity1");
        horse4.setTelephoneOfVet("telvet1111");
        horse4.setPreferredSubscription("subscrip1");
        horse4.setOwner(null);
        horse4.setStall(null);
        horse4.setPassport(null);
        horse4.setEnrollment(null);

        when(horseRepository.save(any(Horse.class))).thenReturn(horse4);

        horseService.removeCustomerProfileFromHorse(horse1);

        verify(horseRepository, times(1)).save(captor.capture());

        Horse captured = captor.getValue();

        assertEquals(horse4.getId(), captured.getId());
        assertEquals(horse4.getOwner(), captured.getOwner());


    }

    //assignPassportToHorse
    @Test
    void assignPassportToHorse() {

        Horse testHorse = new Horse();
        testHorse.setId(1L);
        testHorse.setName("horse1");
        testHorse.setHorseNumber("horsNum1111");
        testHorse.setTypeOfFeed("feed1");
        testHorse.setTypeOfBedding("bedding1");
        testHorse.setNameOfVet("vet1");
        testHorse.setResidenceOfVet("vetcity1");
        testHorse.setTelephoneOfVet("telvet1111");
        testHorse.setPreferredSubscription("subscrip1");
        testHorse.setOwner(owner1);
        testHorse.setStall(null);
        testHorse.setPassport(passport1);
        testHorse.setEnrollment(null);

        when(fileUploadRepository.findByFileName("passport1")).thenReturn(Optional.of(passport1));
        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));
        when(horseRepository.save(any(Horse.class))).thenReturn(testHorse);

        horseService.assignPassportToHorse("passport1", 1L);

        verify(horseRepository, times(1)).save(captor.capture());

        Horse captured = captor.getValue();

        assertEquals(testHorse.getName(), captured.getName());
        assertEquals(testHorse.getPassport().getFileName(), captured.getPassport().getFileName());

    }

    //assignPassportToHorse
    @Test
    void shouldThrowRecordNotFoundExceptionWhenHorseIdIsUnknown() {
        assertThrows(RecordNotFoundException.class, () -> horseService.assignPassportToHorse("passport1", null));

    }
}