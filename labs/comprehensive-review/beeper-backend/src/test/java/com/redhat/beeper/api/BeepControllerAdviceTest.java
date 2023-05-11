package com.redhat.beeper.api;

import com.redhat.beeper.api.model.BeepApiMapper;
import com.redhat.beeper.api.model.BeepApiMapperImpl;
import com.redhat.beeper.application.BeepService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest({BeepsController.class, BeepController.class})
class BeepControllerAdviceTest {

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
    void exception() {
    }
    @Test
    void test_runtime_exception() throws Exception {
        doThrow(new RuntimeException()).
                when(beepService).getBeeps();
        this.mockMvc.perform(get(this.BEEPS_RESOURCE ))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath(".code").value(500))
        ;
    }

}


