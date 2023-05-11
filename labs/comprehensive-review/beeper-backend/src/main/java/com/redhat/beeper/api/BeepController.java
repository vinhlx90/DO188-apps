package com.redhat.beeper.api;

import com.redhat.beeper.api.model.BeepApiMapper;
import com.redhat.beeper.api.model.Error;
import com.redhat.beeper.api.model.NewBeep;
import com.redhat.beeper.application.BeepService;
import com.redhat.beeper.application.model.Beep;
import com.redhat.beeper.application.model.BeepNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;


@Controller
@RequestMapping("${openapi.beeper.base-path:/api}")
public class BeepController implements BeepApi {

    private final BeepService beepService;
    private final BeepApiMapper mapper;

    public BeepController(BeepService beepService, BeepApiMapper mapper) {
        this.beepService = beepService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Void> create(NewBeep newBeep) {
        Beep saved = beepService.create(mapper.toModel(newBeep));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteBeep(Long id) {
        beepService.deleteBeep(id);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> beepUpvote(Long id) {
        beepService.beepUpvote(id);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> beepDownvote(Long id) {
        beepService.beepDownvote(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleBeepNotFound(BeepNotFoundException beepNotFoundException) {
        Error error = new Error().code(HttpStatus.NOT_FOUND.value()).message("Beep not found!");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

}
