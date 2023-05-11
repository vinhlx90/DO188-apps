package com.redhat.beeper.application;

import com.redhat.beeper.application.model.Beep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static com.redhat.beeper.BeepTestUtils.userBeepFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeepServiceTest {

    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";
    private static final String BEEP_TEXT = "beep text";

    @Mock
    private BeepRepository repo;

    @InjectMocks
    private BeepService sut;

    @Captor
    ArgumentCaptor<Beep> beepCaptor;

    @Captor
    ArgumentCaptor<Long> beepIdCaptor;

    @Test
    void test_create_ok() {
        // Arrange
        // Act
        Beep expected = new Beep(USER_1, BEEP_TEXT);
        sut.create(expected);
        // Assert
        verify(repo).save(beepCaptor.capture());
        Beep actual = beepCaptor.getValue();
        assertThat(actual.getUsername()).isEqualTo(USER_1);
        assertThat(actual.getContent()).isEqualTo(BEEP_TEXT);
    }


    @Test
    void test_get_feeds_returns_ordered_by_date_desc_ok() {
        // Arrange
        var user1BeepProvider = userBeepFactory(USER_1);
        var user2BeepProvider = userBeepFactory(USER_2);
        long now = new Date().getTime();
        List<Beep> beepList = List.of(
                user1BeepProvider.apply(1L, new Date(now + 1_000)),
                user2BeepProvider.apply(2L, new Date(now + 2_000)),
                user1BeepProvider.apply(3L, new Date(now + 1_500)));
        when(repo.findAll()).thenReturn(beepList);
        // Act
        List<Beep> actual = sut.getBeeps();
        // Assert
        List<Long> beepIds = actual.stream().map(Beep::getId).toList();
        assertThat(beepIds).containsExactly(2L, 3L, 1L);
    }

    @Test
    void test_delete_beep_ok() {
        // Arrange
        // Act
        sut.deleteBeep(10L);
        // Assert
        verify(repo).delete(beepIdCaptor.capture());
        assertThat(beepIdCaptor.getValue()).isEqualTo(10L);
    }

}