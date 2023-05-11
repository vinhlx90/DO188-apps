package com.redhat.beeper.application.model;

import com.redhat.beeper.BeepTestUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class BeepTest {

    @Test
    public void upvote_new_beep() {
        // Arrange
        Beep newBeep = getBeep();
        // Act
        newBeep.upvote();
        // Assert
        assertThat(newBeep.getVotes()).isEqualTo(1);
    }

    @Test
    public void downvote_new_beep() {
        // Arrange
        Beep newBeep = getBeep();
        // Act
        newBeep.downvote();
        // Assert
        assertThat(newBeep.getVotes()).isEqualTo(-1);
    }

    private Beep getBeep() {
        return BeepTestUtils
                .userBeepFactory("user1")
                .apply(1L, new Date());
    }

}