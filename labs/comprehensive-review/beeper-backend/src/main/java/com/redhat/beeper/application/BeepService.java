package com.redhat.beeper.application;

import com.redhat.beeper.application.model.Beep;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

@Service
public class BeepService {

    private BeepRepository beepRepository;

    private static final Comparator<Beep> byDateDesc = Comparator.comparing(Beep::getCreationDate).reversed();

    public BeepService(BeepRepository beepRepository) {
        this.beepRepository = beepRepository;
    }

    public Beep create(Beep newBeep) {
        return beepRepository.save(newBeep);
    }

    public List<Beep> getBeeps() {
        return beepRepository.findAll().stream()
                .sorted(byDateDesc)
                .collect(toList());
    }

    public void deleteBeep(Long id) {
        beepRepository.delete(id);
    }

    public void beepUpvote(Long id) {
        updateBeep(id, beep -> beep.upvote());
    }

    public void beepDownvote(Long id) {
        updateBeep(id, beep -> beep.downvote());
    }

    private void updateBeep(Long id, Consumer<Beep> updateBeep) {
        Beep beep = beepRepository.findById(id);
        updateBeep.accept(beep);
        beepRepository.save(beep);
    }
}
