package com.redhat.beeper.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.beeper.api.model.BeepApiMapper;
import com.redhat.beeper.api.model.BeepApiMapperImpl;
import com.redhat.beeper.api.model.NewBeep;
import com.redhat.beeper.application.BeepService;
import com.redhat.beeper.application.model.Beep;
import com.redhat.beeper.application.model.BeepNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeepController.class)
class BeepControllerTest {

    @TestConfiguration
    static class BeepControllerTestConfiguration {

        @Bean
        BeepApiMapper artistViewModelMapper() {
            return new BeepApiMapperImpl();
        }

    }

    private final String BEEP_RESOURCE = "/api/beep";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeepService beepService;

    @Captor
    ArgumentCaptor<Beep> beepCaptor;

    @Test
    void test_beep_create_ok() throws Exception {

        // Arrange
        NewBeep newBeep = new NewBeep();
        newBeep.setContent("This is a new beep");
        newBeep.setUsername("alice01");

        // Act + Assert
        this.mockMvc.perform(post(this.BEEP_RESOURCE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBeep)))
                .andExpect(status().isOk());

        verify(beepService).create(beepCaptor.capture());
        Beep actual = beepCaptor.getValue();
        assertThat(actual.getUsername()).isEqualTo("alice01");
        assertThat(actual.getContent()).isEqualTo("This is a new beep");
    }

    @Test
    void test_beep_delete_ok() throws Exception {
        // Act + Assert
        this.mockMvc.perform(delete(this.BEEP_RESOURCE + "/1"))
                .andExpect(status().isOk());
    }

    @Test
    void test_beep_delete_not_found_exception() throws Exception {
        doThrow(new BeepNotFoundException()).
                when(beepService).deleteBeep(1L);
        this.mockMvc.perform(delete(this.BEEP_RESOURCE + "/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath(".code").value(404))
                .andExpect(jsonPath(".message").value("Beep not found!"))
        ;
    }

    @Test
    void test_beep_upvote_ok() throws Exception {
        this.mockMvc.perform(put(this.BEEP_RESOURCE + "/1/upvote"))
                .andExpect(status().isOk());
    }

    @Test
    void test_beep_downvote_ok() throws Exception {
        this.mockMvc.perform(put(this.BEEP_RESOURCE + "/1/downvote"))
                .andExpect(status().isOk());
    }

}