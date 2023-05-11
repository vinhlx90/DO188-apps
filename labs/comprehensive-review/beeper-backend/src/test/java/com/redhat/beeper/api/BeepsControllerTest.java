package com.redhat.beeper.api;


import com.redhat.beeper.api.model.BeepApiMapper;
import com.redhat.beeper.api.model.BeepApiMapperImpl;
import com.redhat.beeper.application.BeepService;
import com.redhat.beeper.application.model.Beep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeepsController.class)
class BeepsControllerTest {

    @TestConfiguration
    static class BeepControllerTestConfiguration {

        @Bean
        BeepApiMapper artistViewModelMapper() {
            return new BeepApiMapperImpl();
        }

    }

    private final String BEEPS_RESOURCE = "/api/beeps";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BeepService beepService;

    @Test
    public void test_get_beeps_ok() throws Exception {

        // Arrange
        List<Beep> beeps = List.of(
                new Beep("alice01", "beep1"),
                new Beep("jhon01", "beep2")
        );
        beeps.get(0).upvote();
        when(beepService.getBeeps()).thenReturn(beeps);

        // Act + Assert
        this.mockMvc.perform(get(this.BEEPS_RESOURCE).accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("alice01"))
                .andExpect(jsonPath("$[0].content").value("beep1"))
                .andExpect(jsonPath("$[0].votes").value("1"))
                .andExpect(jsonPath("$[1].username").value("jhon01"))
                .andExpect(jsonPath("$[1].content").value("beep2"))
                .andExpect(jsonPath("$[1].votes").value("0"))
        ;

    }

    @Test
    public void test_cors_ok() throws Exception {
        this.mockMvc.perform(options(this.BEEPS_RESOURCE)
                        .header("Access-Control-Request-Method", "GET")
                        .header("Access-Control-Request-Headers", "Content-Type")
                        .header("Origin", "http://www.example.com")
                        .header("Content-Type", "application/json")
                )
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Methods", "GET"))
                .andExpect(header().string("Access-Control-Allow-Origin", "*"))
                .andExpect(header().string("Access-Control-Allow-Headers", "Content-Type"))
        ;
    }

}