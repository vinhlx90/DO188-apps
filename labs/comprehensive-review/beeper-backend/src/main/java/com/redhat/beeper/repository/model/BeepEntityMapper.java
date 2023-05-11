package com.redhat.beeper.repository.model;

import org.mapstruct.Mapper;

@Mapper
public interface BeepEntityMapper {
    Beep toEntity(com.redhat.beeper.application.model.Beep model);
    com.redhat.beeper.application.model.Beep toModel(Beep entity);
}
