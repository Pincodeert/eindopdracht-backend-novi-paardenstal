package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.dtos.StallInputDto;
import nl.pin.paardenstal.exceptions.AlreadyAssignedException;
import nl.pin.paardenstal.exceptions.NotYetAssignedException;
import nl.pin.paardenstal.exceptions.NotYetRemovedException;
import nl.pin.paardenstal.exceptions.RecordNotFoundException;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.StallRepository;
import org.junit.jupiter.api.*;
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
class StallServiceTest {

    @Mock
    StallRepository stallRepository;

    @Mock
    HorseRepository horseRepository;

    @Mock
    HorseService horseService;

    @InjectMocks
    StallService stallService;

    @Captor
    ArgumentCaptor<Stall> captor;

    Stall stall1;
    Stall stall2;
    Stall stall3;
    StallDto stallDto1;
    StallDto stallDto2;
    StallDto stallDto3;
    StallInputDto stallInputDto1;
    Horse horse1;
    HorseDto horseDto1;
    Horse horse2;
    HorseDto horseDto2;


    @BeforeEach
    void setUp() {

        horse1 = new Horse();
        horse1.setName("horse1");

        horseDto1 = new HorseDto();
        horseDto1.setName("horse1");

        horse2 = new Horse();
        horse2.setName("horse2");

        horseDto2 = new HorseDto();
        horseDto2.setName("horse2");

        stall1 = new Stall();
        stall1.setId(1L);
        stall1.setName("1-available small stall");
        stall1.setSize("3 x 4");
        stall1.setType("small stall");
        stall1.setOccupied(false);

        stallDto1 = new StallDto();
        stallDto1.setId(1L);
        stallDto1.setName("1-available small stall");
        stallDto1.setSize("3 x 4");
        stallDto1.setType("small stall");
        stallDto1.setOccupied(false);

        stallInputDto1 = new StallInputDto();
        stallInputDto1.setName("1-available small stall");
        stallInputDto1.setSize("3 x 4");
        stallInputDto1.setType("small stall");

        stall2 = new Stall();
        stall2.setId(2L);
        stall2.setName("2-occupied big stall");
        stall2.setSize("4 x 5");
        stall2.setType("big stall");
        stall2.setOccupied(true);
        stall2.setHorse(horse1);

        stallDto2 = new StallDto();
        stallDto2.setId(2L);
        stallDto2.setName("2-occupied big stall");
        stallDto2.setSize("4 x 5");
        stallDto2.setType("big stall");
        stallDto2.setOccupied(true);
        stallDto2.setHorse(horseDto1);

        stall3 = new Stall();
        stall3.setId(3L);
        stall3.setName("3-occupied small stall");
        stall3.setSize("3 x 4");
        stall3.setType("small stall");
        stall3.setOccupied(true);
        stall3.setHorse(horse2);

        stallDto3 = new StallDto();
        stallDto3.setId(3L);
        stallDto3.setName("3-occupied small stall");
        stallDto3.setSize("3 x 4");
        stallDto3.setType("small stall");
        stallDto3.setOccupied(true);
        stallDto3.setHorse(horseDto2);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("testGetAllStallsWithoutParams")
    void shouldReturnAListWithAllStallDtoObjects() {

        //Arrange
        List<StallDto> stallDtos = new ArrayList<>();
        stallDtos.add(stallDto1);
        stallDtos.add(stallDto2);
        stallDtos.add(stallDto3);

        when(stallRepository.findAll()).thenReturn(java.util.List.of(stall1,stall2, stall3));
        when(horseService.transferToDto(horse1)).thenReturn(horseDto1);

        //Act
        List<StallDto> result = stallService.getAllStalls("", false);

        //assert
        assertEquals(stallDtos.get(0).getId(), result.get(0).getId());
        assertEquals(stallDtos.get(1).getHorse(), result.get(1).getHorse());
        assertEquals(stallDtos.get(2).getName(), result.get(2).getName());
        assertEquals(3, result.size());

    }

    @Test
    @DisplayName("testGetAllStallsWithParams")
    void shouldReturnOnlyStallDtoObjectsByGivenParams() {

        //Arrange
        List<StallDto> stallDtos = new ArrayList<>();
        stallDtos.add(stallDto1);
        stallDtos.add(stallDto2);
        stallDtos.add(stallDto3);

        when(stallRepository.findAllByTypeIgnoreCaseAndIsOccupied("small stall", true)).thenReturn(java.util.List.of(stall3));

        //Act
        List<StallDto> result = stallService.getAllStalls("small stall", true);

        //assert
        assertEquals(stallDtos.get(2).getId(), result.get(0).getId());
        assertEquals(stallDtos.get(2).getType(), result.get(0).getType());
        assertEquals(stallDtos.get(2).isOccupied(), result.get(0).isOccupied());
        assertEquals(1, result.size());

    }

    @Test
    @DisplayName("testGetAllStallsByIsOccupied()")
    void shouldReturnOnlyStallsWithIsOccupiedIsTrueWhenGivenIsOccupiedIsTrue() {

        //Arrange
        List<StallDto> occupiedStalls = new ArrayList<>();
        occupiedStalls.add(stallDto2);
        occupiedStalls.add(stallDto3);
        List<StallDto> availableStalls = new ArrayList<>();
        availableStalls.add(stallDto1);

        when(stallRepository.findAllByIsOccupied(true)).thenReturn(java.util.List.of(stall2, stall3));
        when(stallRepository.findAllByIsOccupied(false)).thenReturn(java.util.List.of(stall1));

        List<StallDto> resultTrue = stallService.getAllStallsByIsOccupied(true);
        List<StallDto> resultFalse = stallService.getAllStallsByIsOccupied(false);

        assertEquals(2, resultTrue.size());
        assertEquals(occupiedStalls.get(0).getName(), resultTrue.get(0).getName());
        assertEquals(occupiedStalls.get(1).getName(), resultTrue.get(1).getName());
        assertEquals(1,resultFalse.size());
        assertEquals(availableStalls.get(0).isOccupied(), resultFalse.get(0).isOccupied());
    }

    @Test
    @DisplayName("getAllStallsByType()")
    void shouldReturnAllStallsOfCertainTypeWhenGivenThatType() {

        List<StallDto> smallStalls = new ArrayList<>();
        smallStalls.add(stallDto1);
        smallStalls.add(stallDto3);

        List<StallDto> bigStalls = new ArrayList<>();
        bigStalls.add(stallDto2);

        when(stallRepository.findAllByTypeIgnoreCase("small stall")).thenReturn(java.util.List.of(stall1, stall3));
        when(stallRepository.findAllByTypeIgnoreCase("big stall")).thenReturn(java.util.List.of(stall2));

        List<StallDto> smallResult = stallService.getAllStallsByType("small stall");
        List<StallDto> bigResult = stallService.getAllStallsByType("big stall");

        assertEquals(2, smallResult.size());
        assertEquals(smallStalls.get(0).getType() ,smallResult.get(0).getType());
        assertEquals(smallStalls.get(1).getType(), smallResult.get(1).getType());
        assertEquals(1, bigResult.size());
        assertEquals(bigStalls.get(0).getType(), bigResult.get(0).getType());
    }

    @Test
    void shouldReturnStallsByTypeWhenGivenTypeInUpperCase() {
        List<StallDto> smallStalls = new ArrayList<>();
        smallStalls.add(stallDto1);
        smallStalls.add(stallDto3);

        when(stallRepository.findAllByTypeIgnoreCase("SMALL STALL")).thenReturn(java.util.List.of(stall1, stall3));

        List<StallDto> smallResult = stallService.getAllStallsByType("SMALL STALL");

        assertEquals(2, smallResult.size());
        assertEquals(smallStalls.get(0).getType() ,smallResult.get(0).getType());
        assertEquals(smallStalls.get(1).getType(), smallResult.get(1).getType());

    }

    @Test
    @DisplayName("testGetStall")
    void shouldReturnStallObject() {

        //Arrange
        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));
        when(stallRepository.findById(2L)).thenReturn(Optional.of(stall2));
        when(horseService.transferToDto(horse1)).thenReturn(horseDto1);

        //Act
        StallDto result1 = stallService.getStall(1L);
        StallDto result2 = stallService.getStall(2L);

        //Assert
        assertEquals(stallDto1.getId(), result1.getId());
        assertEquals(stallDto1.getName(), result1.getName());
        assertEquals(stallDto1.getSize(), result1.getSize());
        assertEquals(stallDto1.getType(), result1.getType());
        assertEquals(stallDto1.isOccupied(), result1.isOccupied());
        assertEquals(stallDto1.getHorse(), result1.getHorse());

        assertEquals(stallDto2.getId(), result2.getId());
        assertEquals(stallDto2.getName(), result2.getName());
        assertEquals(stallDto2.getSize(), result2.getSize());
        assertEquals(stallDto2.getType(), result2.getType());
        assertEquals(stallDto2.isOccupied(), result2.isOccupied());
        assertEquals(stallDto2.getHorse().getName(), result2.getHorse().getName());
    }

    @Test
    void shouldThrowExceptionWhenIdIsNotKnown() {

        assertThrows(RecordNotFoundException.class, () -> stallService.getStall(null));

    }

    //addStall
    @Test
    void shouldSaveStallWhenGivenStallInputDto() {

        when(stallRepository.save(any(Stall.class))).thenReturn(stall1);

        stallService.addStall(stallInputDto1);
        verify(stallRepository, times(1)).save(captor.capture());
        Stall stall = captor.getValue();

        assertEquals(stall1.getName(), stall.getName());
        assertEquals(stall1.getSize(), stall.getSize());
        assertEquals(stall1.getType(), stall.getType());
        assertEquals(stall1.isOccupied(), stall.isOccupied());
        assertEquals(stall1.getHorse(), stall.getHorse());

    }

    //addStall
    @Test
    void shouldReturnLongIdWhenGivenStallInputDto() {

        when(stallRepository.save(any(Stall.class))).thenReturn(stall1);

        Long resultId = stallService.addStall(stallInputDto1);

        assertEquals(stall1.getId(), resultId);
    }

    //updateStall
    @Test
    void shouldUpdateNameSizeAndTypeWhenGivenInputDto() {
        StallInputDto stallInputDto = new StallInputDto();
        stallInputDto.setName("1-tiny stall");
        stallInputDto.setSize("1 x 2");
        stallInputDto.setType("micro stall");

        Stall stall4 = new Stall();
        stall4.setName("1-tiny stall");
        stall4.setSize("1 x 2");
        stall4.setType("micro stall");

        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));
        when(stallRepository.save(any(Stall.class))).thenReturn(stall4);

        stallService.updateStall(1L, stallInputDto);

        verify(stallRepository, times(1)).save(captor.capture());

        Stall captured = captor.getValue();

        assertEquals(stall1.getName(), captured.getName());
        assertEquals(stall1.getSize(), captured.getSize());
        assertEquals(stall1.getType(), captured.getType());
        assertEquals(stall1.getId(), captured.getId());

    }

    //updateStall
    @Test
    @DisplayName("testUpdateStallShouldThrowExceptionWhenStallIdUnknown")
    void shouldThrowExceptionWhenIdUnknown() {
        assertThrows(RecordNotFoundException.class, () -> stallService.updateStall(null, stallInputDto1));
    }

    //deleteStall
    @Test
    @DisplayName("testDeleteStallShouldDeleteStallWithHorseIsNull")
    void shouldDeleteStall() {

        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));

        Optional<Stall> optionalStall = stallRepository.findById(1L);

        stallService.deleteStall(1L);

        verify(stallRepository).delete(optionalStall.get());
    }

    //deleteStall
    @Test
    @DisplayName("shouldThrowExceptionWhenGivenStallIdIsUnknown")
    void shouldThrowExceptionWhenGivenStallIdIsUnknown() {
        assertThrows(RecordNotFoundException.class, () -> stallService.deleteStall(null));
    }

    //deleteStall
    @Test
    @DisplayName("shouldThrowNotYetRemovedExceptionWhenGivenStallWithHorseIsNotNull")
    void shouldThrowExceptionWhenGivenStallHasHorse() {

        when(stallRepository.findById(2L)).thenReturn(Optional.of(stall2));

        assertThrows(NotYetRemovedException.class, () -> stallService.deleteStall(2L));

    }

    @Test
    @DisplayName("testTransferToDto()")
    void shouldReturnStallDtoWhenGivenStall(){

        //Act
        StallDto result = stallService.transferToDto(stall1);

        //Assert(expected, result)
        assertEquals(stallDto1.getId(), result.getId());
        assertEquals(stallDto1.getName(), result.getName());
        assertEquals(stallDto1.getSize(), result.getSize());
        assertEquals(stallDto1.getType(), result.getType());
        assertEquals(stallDto1.isOccupied(), result.isOccupied());

    }

    @Test
    @DisplayName("testTransferToStall()")
    void shouldReturnStallWhenGivenStallInputDto() {

        //Act
        Stall result = stallService.transferToStall(stallInputDto1);

        //Assert
        assertEquals(stallInputDto1.getName(), result.getName());
        assertEquals(stallInputDto1.getSize(), result.getSize());
        assertEquals(stallInputDto1.getType(), result.getType());

    }

    //AssignHorseToStall
    @Test
    @DisplayName("testAssignHorseToStall()")
    void shouldSetStallToIsOccupiedAndHorseIsNotNullWhenGivenExistingStallIdAndHorseId() {
        Stall stall4 = new Stall();
        stall4.setId(1L);
        stall4.setName("1-available small stall");
        stall4.setSize("3 x 4");
        stall4.setType("small stall");
        stall4.setOccupied(true);
        stall4.setHorse(horse1);

        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));
        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));
        when(stallRepository.save(any(Stall.class))).thenReturn(stall4);

        stallService.assignHorseToStall(1L, 1L);

        assertEquals(stall4.getId(), stall1.getId());
        assertEquals(stall4.getHorse().getName(), stall1.getHorse().getName());
        assertEquals(stall4.isOccupied(), stall1.isOccupied());

    }

    //assignHorseToStall
    @Test
    void assignHorseShouldThrowExceptionWhenStallIdIsUnknown() {

        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));

        assertThrows(RecordNotFoundException.class, () -> stallService.assignHorseToStall(10L, 1L));
    }

    //assignHorseToStall
    @Test
    void assignHorseShouldThrowExceptionWhenHorseIdIsUnknown() {

        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));

        assertThrows(RecordNotFoundException.class, () -> stallService.assignHorseToStall(1L, 10L));
    }

    //assignHorseToStall
    @Test
    void assignHorseShouldThrowExceptionWhenBothStallIdAndHorseIdAreUnknown() {
        assertThrows(RecordNotFoundException.class, () -> stallService.assignHorseToStall(1L,1L));
    }

    //assignHorseToStall
    @Test
    void assignHorseShouldThrowExceptionWhenGivenStallAlreadyHasHorse() {

        when(stallRepository.findById(3L)).thenReturn(Optional.of(stall3));
        when(horseRepository.findById(1L)).thenReturn(Optional.of(horse1));

        assertThrows(AlreadyAssignedException.class, () -> stallService.assignHorseToStall(3L, 1L));
    }

    //assignHorseToStall
    @Test
    void assignHorseShouldThrowExceptionWhenGivenHorseIsAlreadyAssignedToAStall() {
        horse2.setStall(stall3);

        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));
        when(horseRepository.findById(2L)).thenReturn(Optional.of(horse2));

        assertThrows(AlreadyAssignedException.class, () -> stallService.assignHorseToStall(1L, 2L));
    }

    //removeHorseFromStall
    @Test
    void shouldSetHorseToNullAndIsOccupiedToFalseWhenStallIdIsGiven() {
        Stall stall4 = new Stall();
        stall4.setId(2L);
        stall4.setName("2-occupied big stall");
        stall4.setSize("4 x 5");
        stall4.setType("big stall");
        stall4.setOccupied(false);
        stall4.setHorse(null);

        when(stallRepository.findById(2L)).thenReturn(Optional.of(stall2));
        when(stallRepository.save(any(Stall.class))).thenReturn(stall4);

        stallService.removeHorseFromStall(2L);

        assertEquals(stall4.getId(), stall2.getId());
        assertEquals(stall4.getHorse(), stall2.getHorse());
        assertEquals(stall4.isOccupied(), stall2.isOccupied());

    }

    //removeHorseFromStall
    @Test
    void removeHorseShouldThrowExceptionWhenGivenStallIdIsUnknown() {
        assertThrows(RecordNotFoundException.class, () -> stallService.removeHorseFromStall(null));
    }

    //removeHorseFromStall
    @Test
    void removeHorseShouldThrowExceptionWhenHorseIsNull() {

        when(stallRepository.findById(1L)).thenReturn(Optional.of(stall1));

        assertThrows(NotYetAssignedException.class, () -> stallService.removeHorseFromStall(1L));

    }


}