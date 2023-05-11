package com.redhat.beeper;

import com.redhat.beeper.application.model.Beep;

import java.util.Date;
import java.util.function.BiFunction;

public class BeepTestUtils {


    public static final String DEFAULT_TEXT = "Default Text";

    public static BiFunction<Long, Date, Beep> userBeepFactory(String username) {
        return (Long id, Date creationDate) -> {
            Beep beep = new Beep(username, DEFAULT_TEXT);
            beep.setCreationDate(creationDate);
            beep.setId(id);
            return beep;
        };
    }
}