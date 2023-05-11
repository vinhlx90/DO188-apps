package com.redhat.beeper.api.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BeepApiMapper {

    Beep toApi(com.redhat.beeper.application.model.Beep model);

    @Mapping(target = "creationDate", expression = "java(new java.util.Date())")
    com.redhat.beeper.application.model.Beep toModel(NewBeep newBeep);

}
