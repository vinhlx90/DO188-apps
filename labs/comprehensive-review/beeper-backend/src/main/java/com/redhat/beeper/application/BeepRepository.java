package com.redhat.beeper.application;

import com.redhat.beeper.application.model.Beep;

import java.util.List;

public interface BeepRepository {
    List<Beep> findAll();

    Beep save(Beep beep);

    Beep findById(Long id);

    void delete(Long id);
}
