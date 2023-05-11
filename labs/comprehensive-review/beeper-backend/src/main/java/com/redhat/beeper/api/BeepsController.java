package com.redhat.beeper.api;


import com.redhat.beeper.api.model.Beep;
import com.redhat.beeper.api.model.BeepApiMapper;
import com.redhat.beeper.application.BeepService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static java.util.stream.Collectors.toList;


@Controller
@RequestMapping("${openapi.beeper.base-path:/api}")
public class BeepsController implements BeepsApi {

    private BeepService beepService;

    private BeepApiMapper mapper;

    public BeepsController(BeepService beepService, BeepApiMapper mapper) {
        this.beepService = beepService;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<List<Beep>> getBeeps() {
        List<Beep> beeps = beepService.getBeeps().stream()
                .map(mapper::toApi)
                .collect(toList());
        return ResponseEntity.ok().body(beeps);
    }
}
