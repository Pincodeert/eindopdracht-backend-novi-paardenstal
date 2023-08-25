package nl.pin.paardenstal.services;

import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.repositories.StallRepository;
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

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    //@Disabled
    void shouldReturnStal1enStal2() {
        //Arrange: Stall1, Stall2, StallRepository, StallService, Horse?,


        //Stall stall1 = new Stall("stal1", "3 x 4", "kleine binnenstal", false, null);
        Stall stal1 = new Stall();
        stal1.setName("stal1");
        stal1.setSize("4 x 5");
        stal1.setType("grote buitenstal");
        stal1.setOccupied(false);

        Stall stal2 = new Stall();
        stal2.setName("stal2");
        stal2.setSize("3 x 4");
        stal2.setType("kleine binnenstal");
        stal2.setOccupied(true);
        //stal2.setHorse();

        when(stallRepository.findAll()).thenReturn(java.util.List.of(stal1,stal2));

        //Act
        List<Stall> stalls = stallRepository.findAll();

        //assert
        assertEquals(stal1, stalls.get(0));
        assertEquals(stal2, stalls.get(1));
    }

    @Test
    @Disabled
    void getAllStallsByIsOccupied() {
    }

    @Test
    @Disabled
    void getAllStallsByType() {
    }

    @Test
    @Disabled
    void getStall() {
    }

    @Test
    @Disabled
    void addStall() {
    }

    @Test
    @Disabled
    void transferToDto() {
    }

    @Test
    @Disabled
    void transferToStall() {
    }

    @Test
    @Disabled
    void assignHorseToStall() {
    }
}