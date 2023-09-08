package nl.pin.paardenstal.integrationtests;

import nl.pin.paardenstal.dtos.StallDto;
import nl.pin.paardenstal.dtos.StallInputDto;
import nl.pin.paardenstal.models.Stall;
import nl.pin.paardenstal.repositories.StallRepository;
import nl.pin.paardenstal.services.StallService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class StallIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    StallService stallService;

    @Autowired
    StallRepository stallRepository;

    Stall stall1;

    StallDto stallDto1;

    //StallInputDto stallInputDto;

    @BeforeEach
    void setUp() {

        if (stallRepository.count() > 0) {
            stallRepository.deleteAll();
        }

        stall1 = new Stall();
        stall1.setName("stall1");
        stall1.setType("zwijnenstal");

        stallDto1 = new StallDto();
        stallDto1.setName("stall1");
        stallDto1.setType("zwijnenstal");

        stallRepository.save(stall1);
    }

    @Test
    void shouldRetrieveCorrectStall() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/stalls/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(stallDto1.getName())))
                .andExpect(jsonPath("$.type", is(stallDto1.getType())));

    }



}
