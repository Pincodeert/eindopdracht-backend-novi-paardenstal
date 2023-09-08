package nl.pin.paardenstal.integrationtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.pin.paardenstal.controllers.FileController;
import nl.pin.paardenstal.dtos.HorseDto;
import nl.pin.paardenstal.dtos.HorseInputDto;
import nl.pin.paardenstal.models.Horse;
import nl.pin.paardenstal.repositories.HorseRepository;
import nl.pin.paardenstal.services.CustomUserDetailsService;
import nl.pin.paardenstal.services.HorseService;
import nl.pin.paardenstal.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class HorseIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private HorseService horseService;

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private FileController fileController;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    Horse horse1;
    Horse horse2;

    HorseDto horseDto1;
    HorseDto horseDto2;

    HorseInputDto horseInputDto1;

    @BeforeEach
    void setUp() {

        if(horseRepository.count()>0) {
            horseRepository.deleteAll();
        }

        horse1 = new Horse();
        horse1.setName("horse1");
        horse1.setHorseNumber("horseNum111111");

        horse2 = new Horse();
        horse2.setName("horse2");
        horse2.setHorseNumber("horseNum2222");

        horseDto1 = new HorseDto();
        horseDto1.setId(1L);
        horseDto1.setName("horse1");
        horseDto1.setHorseNumber("horseNum111111");

        horseDto2 = new HorseDto();
        horseDto2.setName("horse2");
        horseDto2.setHorseNumber("horseNum2222");

        horseInputDto1 = new HorseInputDto();
        horseInputDto1.setName("horse1");
        horseInputDto1.setHorseNumber("horseNum111111");
        horseInputDto1.setTypeOfFeed("hoi");
        horseInputDto1.setTypeOfBedding("roses");
        horseInputDto1.setNameOfVet("drdre");
        horseInputDto1.setResidenceOfVet("smallville");
        horseInputDto1.setTelephoneOfVet("0612345678");
        horseInputDto1.setPreferredSubscription("abonnement");


        horseRepository.save(horse1);
        horseRepository.save(horse2);

    }

    @Test
    void shouldRetrieveAllHorses() throws Exception {
        /*List<Horse> horses = new ArrayList<>();
        horses.add(horse1);
        horses.add(horse2);

        List<HorseDto> horseDtos = new ArrayList<>();
        horseDtos.add(horseDto1);
        horseDtos.add(horseDto2);*/

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/horses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(horseDto1.getName())))
                .andExpect(jsonPath("$[0].horseNumber", is(horseDto1.getHorseNumber())))
                .andExpect(jsonPath("$[1].name", is(horseDto2.getName())));
                //.andExpect(MockMvcResultMatchers.jsonPath("$horses.size()",is(horseDtos.size())));
    }

    //getHorse
    @Test
    void shouldRetrieveCorrectHorse() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/horses/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(horseDto1.getName())))
                .andExpect(jsonPath("$.horseNumber", is(horseDto1.getHorseNumber())));

    }

    //addNewHorse
    @Test
    void shouldSaveCorrectHorse() throws Exception{

        this.mockMvc
                .perform(post("/horses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(horseInputDto1)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/horses/7"));
    }

    public static String asJsonString(final HorseInputDto obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
